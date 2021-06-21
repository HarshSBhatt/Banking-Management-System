import { useState } from "react";
import { useHistory } from "react-router-dom";

//! Ant Imports

import Menu from "antd/lib/menu";
import Sider from "antd/lib/layout/Sider";

//! Ant Icons

import AppstoreOutlined from "@ant-design/icons/AppstoreOutlined";
import MenuUnfoldOutlined from "@ant-design/icons/MenuUnfoldOutlined";
import MenuFoldOutlined from "@ant-design/icons/MenuFoldOutlined";
import UserOutlined from "@ant-design/icons/UserOutlined";

//! User Files

import { MODULES, ROUTES } from "common/constants";

function AppSidebar() {
  const {
    push,
    location: { pathname },
  } = useHistory();
  const [collapsed, setCollapsed] = useState(false);
  const toggle = () => {
    setCollapsed(!collapsed);
  };

  const onMenuSelect = (e) => {
    push(e.key);
  };

  return (
    <Sider
      trigger={null}
      collapsible
      width={250}
      theme="light"
      collapsed={collapsed}
    >
      <div className="app-layout-sider-header">
        <div
          className={`${
            collapsed ? "app-icon-btn-collapsed" : "app-icon-btn-open"
          } app-icon-btn`}
          onClick={toggle}
        >
          {collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
        </div>
      </div>
      <div className="app-sidebar-content">
        <Menu
          theme="lite"
          mode="inline"
          selectedKeys={[`/${pathname.split("/")[1]}`]}
          defaultSelectedKeys={[ROUTES.USERS_MANAGEMENT]}
          onSelect={onMenuSelect}
        >
          <Menu.Item key={ROUTES.MAIN} icon={<AppstoreOutlined />}>
            <span>{MODULES.DASHBOARD}</span>
          </Menu.Item>
          <Menu.Item key={ROUTES.USERS_MANAGEMENT} icon={<UserOutlined />}>
            <span>{MODULES.USERS_MANAGEMENT}</span>
          </Menu.Item>
        </Menu>
      </div>
    </Sider>
  );
}

export default AppSidebar;
