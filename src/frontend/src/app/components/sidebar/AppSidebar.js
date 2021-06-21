import { useContext, useState } from "react";
import { useHistory } from "react-router-dom";

//! Ant Imports

import Menu from "antd/lib/menu";
import Sider from "antd/lib/layout/Sider";

//! Ant Icons

import {
  AppstoreOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
  UsergroupAddOutlined,
  PlusCircleOutlined,
  SnippetsOutlined,
  FormOutlined,
} from "@ant-design/icons";

//! User Files

import { MODULES, ROLES, ROUTES } from "common/constants";
import { AppContext } from "AppContext";

const { SubMenu } = Menu;

function AppSidebar() {
  const {
    state: { role },
  } = useContext(AppContext);
  const {
    push,
    location: { pathname },
  } = useHistory();
  const [openKeys, setOpenKeys] = useState([]);
  const [collapsed, setCollapsed] = useState(false);

  const rootSubMenuKeys = ["user", "leave", "resign"];

  const toggle = () => {
    setCollapsed(!collapsed);
  };

  const onMenuSelect = (e) => {
    push(e.key);
  };

  const onOpenChange = (keys) => {
    const latestOpenKey = keys.find((key) => openKeys.indexOf(key) === -1);
    if (rootSubMenuKeys.indexOf(latestOpenKey) === -1) {
      setOpenKeys(keys);
    } else {
      setOpenKeys(latestOpenKey ? [latestOpenKey] : []);
    }
  };

  return (
    <Sider
      trigger={null}
      collapsible
      width={280}
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
          openKeys={openKeys}
          onOpenChange={onOpenChange}
          selectedKeys={[pathname]}
          defaultSelectedKeys={[ROUTES.USERS_MANAGEMENT]}
          onSelect={onMenuSelect}
        >
          {role === ROLES.ROLE_USER ? (
            <>
              <Menu.Item key={ROUTES.MAIN} icon={<AppstoreOutlined />}>
                <span>{MODULES.DASHBOARD}</span>
              </Menu.Item>
              <Menu.Item key={ROUTES.USERS_MANAGEMENT} icon={<UserOutlined />}>
                <span>{MODULES.USERS_MANAGEMENT}</span>
              </Menu.Item>
            </>
          ) : (
            <>
              <Menu.Item key={ROUTES.MAIN} icon={<AppstoreOutlined />}>
                <span>{MODULES.DASHBOARD}</span>
              </Menu.Item>
              <Menu.Item key={ROUTES.USERS_MANAGEMENT} icon={<UserOutlined />}>
                <span>{MODULES.USERS_MANAGEMENT}</span>
              </Menu.Item>
              <SubMenu key="user" icon={<UsergroupAddOutlined />} title="User">
                <Menu.Item key={ROUTES.ADD_USER} icon={<PlusCircleOutlined />}>
                  <span>{MODULES.ADD_USER}</span>
                </Menu.Item>
              </SubMenu>
              <SubMenu key="leave" icon={<SnippetsOutlined />} title="Leave">
                <Menu.Item key="5">Option 5</Menu.Item>
                <Menu.Item key="6">Option 6</Menu.Item>
              </SubMenu>
              <SubMenu key="resign" icon={<FormOutlined />} title="Resign">
                <Menu.Item key="9">Option 9</Menu.Item>
                <Menu.Item key="10">Option 10</Menu.Item>
                <Menu.Item key="11">Option 11</Menu.Item>
                <Menu.Item key="12">Option 12</Menu.Item>
              </SubMenu>
            </>
          )}
        </Menu>
      </div>
    </Sider>
  );
}

export default AppSidebar;
