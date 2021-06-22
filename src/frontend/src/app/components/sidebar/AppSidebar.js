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

  const rootSubMenuKeys = [
    "user_management",
    "profile",
    "account",
    "leave",
    "resign",
  ];

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
          <Menu.Item key={ROUTES.MAIN} icon={<AppstoreOutlined />}>
            <span>{MODULES.DASHBOARD}</span>
          </Menu.Item>
          <SubMenu key="profile" icon={<UserOutlined />} title="Profile">
            <Menu.Item key={ROUTES.PROFILE}>
              <span>{MODULES.PROFILE}</span>
            </Menu.Item>
            <Menu.Item key={ROUTES.UPDATE_PROFILE}>
              <span>{MODULES.UPDATE_PROFILE}</span>
            </Menu.Item>
          </SubMenu>
          {role === ROLES.ROLE_USER ? (
            <>
              <SubMenu key="account" icon={<UserOutlined />} title="Account">
                <Menu.Item key={ROUTES.MY_ACCOUNT}>
                  <span>{MODULES.MY_ACCOUNT}</span>
                </Menu.Item>
                <Menu.Item key={ROUTES.ACCOUNT_STATEMENT}>
                  <span>{MODULES.ACCOUNT_STATEMENT}</span>
                </Menu.Item>
              </SubMenu>
            </>
          ) : (
            <>
              <SubMenu
                key="user_management"
                icon={<UsergroupAddOutlined />}
                title="User Management"
              >
                <Menu.Item key={ROUTES.ADD_USER}>
                  <span>{MODULES.ADD_USER}</span>
                </Menu.Item>
                <Menu.Item key={ROUTES.ACCOUNT_OPENING_REQUEST}>
                  <span>{MODULES.ACCOUNT_OPENING_REQUEST}</span>
                </Menu.Item>
                <Menu.Item key={ROUTES.CREDIT_CARD_REQUEST}>
                  <span>{MODULES.CREDIT_CARD_REQUEST}</span>
                </Menu.Item>
              </SubMenu>
              <SubMenu key="leave" icon={<SnippetsOutlined />} title="Leave">
                <Menu.Item key={ROUTES.APPLY_LEAVE}>
                  <span>{MODULES.APPLY_LEAVE}</span>
                </Menu.Item>
                <Menu.Item key={ROUTES.LEAVE_REQUEST}>
                  <span>{MODULES.LEAVE_REQUEST}</span>
                </Menu.Item>
              </SubMenu>
              <SubMenu key="resign" icon={<FormOutlined />} title="Resign">
                <Menu.Item key={ROUTES.APPLY_RESIGNATION}>
                  <span>{MODULES.APPLY_RESIGNATION}</span>
                </Menu.Item>
                <Menu.Item key={ROUTES.RESIGNATION_REQUEST}>
                  <span>{MODULES.RESIGNATION_REQUEST}</span>
                </Menu.Item>
              </SubMenu>
            </>
          )}
        </Menu>
      </div>
    </Sider>
  );
}

export default AppSidebar;
