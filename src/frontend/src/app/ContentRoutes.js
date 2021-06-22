import { Route, Switch } from "react-router-dom";

//! User Files

import { ROLES, ROUTES } from "common/constants";
import Dashboard from "modules/dashboard";
import Error404 from "Error404";
import AddUser from "modules/admin/user_management/AddUser";
import { useContext } from "react";
import { AppContext } from "AppContext";
import ChangePassword from "modules/auth/components/ChangePassword";
import AccountOpeningRequests from "modules/admin/user_management/AccountOpeningRequests";
import CreditCardRequests from "modules/admin/user_management/CreditCardRequests";
import ApplyLeave from "modules/admin/leave/ApplyLeave";
import LeaveRequests from "modules/admin/leave/LeaveRequests";
import ResignationRequests from "modules/admin/resign/ResignationRequests";
import ApplyResignation from "modules/admin/resign/ApplyResignation";

const ContentRoutes = () => {
  const {
    state: { role },
  } = useContext(AppContext);
  const customerRoutes = (
    <Switch>
      <Route path={ROUTES.MAIN} exact component={Dashboard} />
      <Route path={ROUTES.CHANGE_PASSWORD} exact component={ChangePassword} />
      <Route path="*" exact component={Error404} />
    </Switch>
  );

  const managerRoutes = (
    <Switch>
      <Route path={ROUTES.MAIN} exact component={Dashboard} />
      <Route path={ROUTES.CHANGE_PASSWORD} exact component={ChangePassword} />
      <Route path={ROUTES.ADD_USER} exact component={AddUser} />
      <Route
        path={ROUTES.ACCOUNT_OPENING_REQUEST}
        exact
        component={AccountOpeningRequests}
      />
      <Route
        path={ROUTES.CREDIT_CARD_REQUEST}
        exact
        component={CreditCardRequests}
      />
      <Route path={ROUTES.APPLY_LEAVE} exact component={ApplyLeave} />
      <Route path={ROUTES.LEAVE_REQUEST} exact component={LeaveRequests} />
      <Route
        path={ROUTES.APPLY_RESIGNATION}
        exact
        component={ApplyResignation}
      />
      <Route
        path={ROUTES.RESIGNATION_REQUEST}
        exact
        component={ResignationRequests}
      />
      <Route path="*" exact component={Error404} />
    </Switch>
  );

  const hrRoutes = (
    <Switch>
      <Route path={ROUTES.MAIN} exact component={Dashboard} />
      <Route path={ROUTES.CHANGE_PASSWORD} exact component={ChangePassword} />
      <Route path={ROUTES.ADD_USER} exact component={AddUser} />
      <Route path={ROUTES.APPLY_LEAVE} exact component={ApplyLeave} />
      <Route path={ROUTES.LEAVE_REQUEST} exact component={LeaveRequests} />
      <Route
        path={ROUTES.APPLY_RESIGNATION}
        exact
        component={ApplyResignation}
      />
      <Route
        path={ROUTES.RESIGNATION_REQUEST}
        exact
        component={ResignationRequests}
      />
      <Route path="*" exact component={Error404} />
    </Switch>
  );

  const employeeRoutes = (
    <Switch>
      <Route path={ROUTES.MAIN} exact component={Dashboard} />
      <Route path={ROUTES.CHANGE_PASSWORD} exact component={ChangePassword} />
      <Route
        path={ROUTES.ACCOUNT_OPENING_REQUEST}
        exact
        component={AccountOpeningRequests}
      />
      <Route
        path={ROUTES.CREDIT_CARD_REQUEST}
        exact
        component={CreditCardRequests}
      />
      <Route path={ROUTES.APPLY_LEAVE} exact component={ApplyLeave} />
      <Route
        path={ROUTES.APPLY_RESIGNATION}
        exact
        component={ApplyResignation}
      />
      <Route path="*" exact component={Error404} />
    </Switch>
  );

  if (role === ROLES.ROLE_USER) {
    return customerRoutes;
  } else if (role === ROLES.ROLE_MANAGER) {
    return managerRoutes;
  } else if (role === ROLES.ROLE_HR) {
    return hrRoutes;
  } else {
    return employeeRoutes;
  }
};

export default ContentRoutes;
