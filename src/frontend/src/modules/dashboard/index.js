import { useContext } from "react";
import { Link } from "react-router-dom";

//! Ant Imports

import { Card } from "antd";

//! User Files

import { AppContext } from "AppContext";
import { dashboardRoutes } from "common/dashboardRoutes";

function Dashboard() {
  const {
    state: { role },
  } = useContext(AppContext);

  const gridStyle = {
    width: "25%",
    textAlign: "center",
  };

  const renderDashboard = dashboardRoutes.map((route) => {
    const isAuthorizedUser = route.allowedRoles.includes(role);
    return (
      isAuthorizedUser && (
        <Link to={route.link}>
          <Card.Grid style={gridStyle}>{route.label}</Card.Grid>
        </Link>
      )
    );
  });

  const title = <span className="cb-text-strong">Services</span>;

  return (
    <div>
      <Card title={title}>{renderDashboard}</Card>
    </div>
  );
}

export default Dashboard;
