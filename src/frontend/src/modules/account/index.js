import { useContext, useEffect, useState } from "react";

//! Ant Imports

import { Card } from "antd";

//! User Files

import moment from "moment";
import api from "common/api";
import { toast } from "common/utils";
import ServerError from "components/ServerError";
import Loading from "components/Loading";
import { AppContext } from "AppContext";

const InnerTitle = ({ title }) => {
  return <span className="cb-text-strong">{title}</span>;
};

function Account() {
  const {
    state: { authToken },
  } = useContext(AppContext);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState(false);
  const [accountData, setAccountData] = useState({});

  const accountNumber = accountData?.accountNumber;
  const accountCreatedAt = accountData?.accountCreatedAt;
  const lastActivityAt = accountData?.lastActivityAt;
  const balance = accountData?.balance;
  const creditScore = accountData?.creditScore;
  const accountType = accountData?.accountType;
  const user = accountData?.userMetaResponse;
  const customerId = user?.id;
  const username = user?.username;
  const firstName = user?.firstName;
  const lastName = user?.lastName;
  const phone = user?.phone;
  const email = user?.email;
  const debitCardNumber = accountData?.debitCardNumber;

  const title = <span className="cb-text-strong">My Account</span>;

  const fetchUserAccount = async () => {
    setLoading(true);
    try {
      const response = await api.get(`/account/me`, {
        headers: {
          Authorization: `Bearer ${authToken}`,
        },
      });
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
    // eslint-disable-next-line
  }, []);

  if (loading) return <Loading />;
  if (err) return <ServerError />;
  return (
    <div className="profile">
      <Card title={title} className="profile-card">
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Customer ID" />}
        >
          <span>{`DAL${customerId}`}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Account Number" />}
        >
          <span>{accountNumber}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Balance" />}
        >
          <span>{balance}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Credit Score" />}
        >
          <span>{creditScore}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Account Type" />}
        >
          <span>{accountType}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Username" />}
        >
          <span>{username}</span>
        </Card>
        <Card type="inner" bordered={false} title={<InnerTitle title="Name" />}>
          <span>{`${firstName} ${lastName}`}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Email" />}
        >
          <span>{email}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Phone" />}
        >
          <span>{phone}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Debit Card Number" />}
        >
          <span>{debitCardNumber}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Account Creation Date" />}
        >
          <span>
            {moment(accountCreatedAt).format("Do MMM YYYY hh:mm:ss A")}
          </span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Account Last Activity" />}
        >
          <span>{moment(lastActivityAt).format("Do MMM YYYY hh:mm:ss A")}</span>
        </Card>
      </Card>
    </div>
  );
}

export default Account;
