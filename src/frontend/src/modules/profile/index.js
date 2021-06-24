import { useContext } from "react";

//! Ant Imports

import { Card } from "antd";

//! User Files

import { AppContext } from "AppContext";
import moment from "moment";

const InnerTitle = ({ title }) => {
  return <span className="cb-text-strong">{title}</span>;
};

function Profile() {
  const {
    state: { currentUser, role },
  } = useContext(AppContext);

  const customerId = currentUser?.id;
  const username = currentUser?.username;
  const firstName = currentUser?.firstName;
  const lastName = currentUser?.lastName;
  const accountStatus = currentUser?.accountStatus;
  const address = currentUser?.address;
  const phone = currentUser?.phone;
  const birthday = currentUser?.birthday;
  const zipCode = currentUser?.zipCode;
  const city = currentUser?.city;
  const state = currentUser?.state;

  const title = <span className="cb-text-strong">User Profile</span>;

  return (
    <div className="profile">
      <Card title={title} className="profile-card">
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Customer ID" />}
        >
          <span>{`DAL${customerId}`}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Username" />}
        >
          <span>{username}</span>
        </Card>
        <Card type="inner" bordered={false} title={<InnerTitle title="Name" />}>
          <span>{`${firstName} ${lastName}`}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Account Status" />}
        >
          <span>{accountStatus}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Phone" />}
        >
          <span>{phone}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Birth Date" />}
        >
          <span>{moment(birthday).format("Do MMM YYYY")}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Address" />}
        >
          <span>{address}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="Zip Code" />}
        >
          <span>{zipCode}</span>
        </Card>
        <Card type="inner" bordered={false} title={<InnerTitle title="City" />}>
          <span>{city}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="State" />}
        >
          <span>{state}</span>
        </Card>
        <Card
          type="inner"
          bordered={false}
          title={<InnerTitle title="User Type" />}
        >
          <span>{role.split("_")[1]}</span>
        </Card>
      </Card>
    </div>
  );
}

export default Profile;
