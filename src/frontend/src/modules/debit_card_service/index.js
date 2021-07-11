//! Ant Imports

import { Card } from "antd";
import { useState } from "react";

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

const contentList = {
  tab1: <p>content1</p>,
  tab2: <p>content2</p>,
  tab3: <p>content3</p>,
};

function DebitCardServices() {
  const [state, setState] = useState({
    key: "tab1",
  });

  const onTabChange = (key, type) => {
    setState({
      ...state,
      [type]: key,
    });
  };

  const title = <span className="cb-text-strong">Debit Card Services</span>;

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

export default DebitCardServices;
