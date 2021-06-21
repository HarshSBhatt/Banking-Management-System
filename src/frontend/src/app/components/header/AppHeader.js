import { Button, Typography } from "antd";
import Layout from "antd/lib/layout";
import { ROUTES } from "common/constants";
import { useHistory } from "react-router-dom";

const { Header } = Layout;
const { Title } = Typography;

const AppHeader = () => {
  const { push } = useHistory();
  const handleLogout = () => {
    push(ROUTES.LOGOUT);
  };
  return (
    <Header>
      <Title level={4}>Header</Title>
      <Button onClick={handleLogout}>Logout</Button>
    </Header>
  );
};

export default AppHeader;
