import { useContext, useState } from "react";
import { useHistory } from "react-router-dom";

//! Ant Imports

import Avatar from "antd/lib/avatar";
import Card from "antd/lib/card";
import Divider from "antd/lib/divider";
import Menu from "antd/lib/menu";
import Popover from "antd/lib/popover";
import Title from "antd/lib/typography/Title";
import Text from "antd/lib/typography/Text";

//! User Files

import { AppContext } from "AppContext";
import { ReactComponent as LogoutIcon } from "assets/svg/logout.svg";
import { ReactComponent as LockIcon } from "assets/svg/lock.svg";
import { ROUTES } from "common/constants";

function UserProfile() {
  const {
    state: { currentUser },
  } = useContext(AppContext);
  const history = useHistory();
  const [clicked, setClicked] = useState(false);

  const firstName = currentUser?.firstName;
  const lastName = currentUser?.lastName;
  const userEmail = currentUser?.email;
  const userName = currentUser?.username;

  const handleHide = ({ key }) => {
    setClicked(false);
    history.push(key);
  };

  const handleClick = (visible) => {
    setClicked(visible);
  };

  const content = (
    <>
      <Menu selectable={false}>
        <Menu.Item className="cb-user-info" key="user-data">
          <Title level={5}>{userName}</Title>
          <Text type="secondary" ellipsis title={userEmail}>
            {userEmail}
          </Text>
        </Menu.Item>
      </Menu>
      <Divider className="cb-divider" />
      <Menu>
        <Menu.Item
          icon={<LockIcon className="cb-svg" />}
          onClick={handleHide}
          key={ROUTES.CHANGE_PASSWORD}
          className="cb-menu-item"
        >
          Change Password
        </Menu.Item>
        <Menu.Item
          icon={<LogoutIcon className="cb-svg" />}
          onClick={handleHide}
          key={ROUTES.LOGOUT}
          className="cb-menu-item"
        >
          Logout
        </Menu.Item>
      </Menu>
    </>
  );

  return (
    <Popover
      placement="bottom"
      content={content}
      trigger="click"
      visible={clicked}
      onVisibleChange={handleClick}
    >
      <Card className="app-header-user-profile">
        <div className="app-header-user-avatar">
          <Avatar
            size={{
              xs: 30,
              sm: 30,
              md: 40,
              lg: 40,
              xl: 40,
              xxl: 40,
            }}
          >
            {userName?.[0]}
          </Avatar>
        </div>
        <div className="app-header-user-data">
          <span className="user-name" title={userName}>
            {`${firstName} ${lastName}`}
          </span>
        </div>
      </Card>
    </Popover>
  );
}

export default UserProfile;
