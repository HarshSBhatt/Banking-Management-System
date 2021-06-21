import { Route, Switch } from "react-router-dom";

//! User Files

import { ROLES, ROUTES } from "common/constants";
import Dashboard from "modules/dashboard";
import UserManagement from "modules/user_management";
import Error404 from "Error404";
import AddUser from "modules/admin/add_user";
import { useContext } from "react";
import { AppContext } from "AppContext";
import ChangePassword from "modules/user_management/components/ChangePassword";

const ContentRoutes = () => {
  const {
    state: { role },
  } = useContext(AppContext);
  const customerRoutes = (
    <Switch>
      <Route path={ROUTES.MAIN} exact component={Dashboard} />
      <Route path={ROUTES.USERS_MANAGEMENT} exact component={UserManagement} />
      <Route path={ROUTES.CHANGE_PASSWORD} exact component={ChangePassword} />
      <Route path="*" exact component={Error404} />
    </Switch>
  );

  const adminRoutes = (
    <Switch>
      <Route path={ROUTES.MAIN} exact component={Dashboard} />
      <Route path={ROUTES.USERS_MANAGEMENT} exact component={UserManagement} />
      <Route path={ROUTES.CHANGE_PASSWORD} exact component={ChangePassword} />
      <Route path={ROUTES.ADD_USER} exact component={AddUser} />
      <Route path="*" exact component={Error404} />
    </Switch>
  );

  if (role === ROLES.ROLE_USER) {
    return customerRoutes;
  } else {
    return adminRoutes;
  }
};

export default ContentRoutes;
