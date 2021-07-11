import { useEffect, useState } from "react";

//! Ant Imports

import { Card } from "antd";

//! User Files

import api from "common/api";
import { toast } from "common/utils";
import ServerError from "components/ServerError";
import Loading from "components/Loading";
import SetLimit from "./components/SetLimit";
import SetPin from "./components/SetPin";

const tabList = [
  {
    key: "tab1",
    tab: "Set Limit",
  },
  {
    key: "tab2",
    tab: "Turn Debit Card ON/OFF",
  },
  {
    key: "tab3",
    tab: "Set PIN",
  },
];

function DebitCardServices() {
  const [state, setState] = useState({
    key: "tab1",
  });
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState(false);
  const [accountData, setAccountData] = useState({});

  const debitCardNumber = accountData?.debitCardNumber;

  const contentList = {
    tab1: <SetLimit debitCardNumber={debitCardNumber} />,
    tab2: <p>content2</p>,
    tab3: <SetPin debitCardNumber={debitCardNumber} />,
  };

  const onTabChange = (key, type) => {
    setState({
      ...state,
      [type]: key,
    });
  };

  const fetchUserAccount = async () => {
    setLoading(true);
    try {
      const response = await api.get(`/account/me`);
      const { data } = response;
      setAccountData(data);
    } catch (err) {
      setErr(true);
      toast({
        message: "Something went wrong!",
        type: "error",
      });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUserAccount();
  }, []);

  const title = <span className="cb-text-strong">Debit Card Services</span>;

  if (loading) return <Loading />;
  if (err) return <ServerError />;
  return (
    <div>
      <Card
        style={{ width: "100%" }}
        title={title}
        tabList={tabList}
        activeTabKey={state.key}
        onTabChange={(key) => {
          onTabChange(key, "key");
        }}
      >
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            padding: "2rem 0",
          }}
        >
          {contentList[state.key]}
        </div>
      </Card>
    </div>
  );
}

export default DebitCardServices;
