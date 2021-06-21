import { Route, Switch } from "react-router-dom";

//! User Files

import { ROUTES } from "common/constants";
import Dashboard from "modules/dashboard";
import UserManagement from "modules/user_management";
import Error404 from "Error404";

const ContentRoutes = () => {
  const renderRoutes = (
    <Switch>
      <Route path={ROUTES.MAIN} exact component={Dashboard} />
      <Route path={ROUTES.USERS_MANAGEMENT} exact component={UserManagement} />
      <Route path="*" exact component={Error404} />
    </Switch>
  );

  return renderRoutes;
};

export default ContentRoutes;
