//! Ant Imports

import { Card } from "antd";
import { useState } from "react";

const tabList = [
  {
    key: "tab1",
    tab: "Request a New Chequebook",
  },
  {
    key: "tab2",
    tab: "Status Enquiry",
  },
  {
    key: "tab3",
    tab: "Stop Cheque",
  },
];

const contentList = {
  tab1: <p>content1</p>,
  tab2: <p>content2</p>,
  tab3: <p>content3</p>,
};

function ChequeServices() {
  const [state, setState] = useState({
    key: "tab1",
  });

  const onTabChange = (key, type) => {
    setState({
      ...state,
      [type]: key,
    });
  };

  const title = <span className="cb-text-strong">Cheque Services</span>;

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
        {contentList[state.key]}
      </Card>
    </div>
  );
}

export default ChequeServices;
