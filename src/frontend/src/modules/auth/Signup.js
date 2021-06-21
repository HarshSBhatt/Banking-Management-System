import { useContext, useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import moment from "moment";

//! Ant Imports

import { Form, Input, Button, Typography, Select, DatePicker } from "antd";

//! Ant Icons

import { MailOutlined, LockOutlined, UserOutlined } from "@ant-design/icons";

//! User Files

import { toast } from "common/utils";
import { AppContext } from "AppContext";
import { APP_NAME, REGEX, ROUTES } from "common/constants";
import api from "common/api";

const { Title } = Typography;
const { Option } = Select;

function Signup() {
  const date = new Date();
  const eligibleYear = date.getFullYear() - 1;
  const {
    state: { authenticated },
  } = useContext(AppContext);
  const [loading, setLoading] = useState(false);
  const { push } = useHistory();
  const onFinish = async (values) => {
    const {
      firstName,
      lastName,
      username,
      email,
      birthday,
      prefix,
      phone,
      address,
      accountType,
      password,
    } = values;
    setLoading(true);
    try {
      const userDetails = {
        firstName,
        lastName,
        username,
        phone: `${prefix}${phone}`,
        email,
        birthday: moment(birthday).format("YYYY-MM-DD"),
        address,
        accountType,
        password,
        role: "ROLE_USER",
      };
      const response = await api.post("/auth/signup", userDetails);
      const { data } = response;
      if (data?.success) {
        toast({
          message: data.message,
          type: "success",
        });
        setTimeout(() => {
          push(ROUTES.LOGIN);
        }, 2000);
      }
    } catch (err) {
      toast({
        message: err.response.data.message,
        type: "error",
      });
    }
    setLoading(false);
  };

  useEffect(() => {
    if (authenticated) {
      push("/");
    }
    // eslint-disable-next-line
  }, [authenticated]);

  const disabledDate = (current) => {
    let customDate = `${eligibleYear}-01-01`;
    return current && current > moment(customDate, "YYYY-MM-DD");
  };

  const prefixSelector = (
    <Form.Item name="prefix" noStyle>
      <Select style={{ width: 70 }}>
        <Option value="91">+91</Option>
        <Option value="1">+1</Option>
      </Select>
    </Form.Item>
  );

  return (
    <div className="login">
      <Title level={3} className="sdp-text-strong">
        {APP_NAME}
      </Title>
      <Form
        name="normal_signup"
        className="signup-form form"
        initialValues={{ prefix: "+91" }}
        onFinish={onFinish}
      >
        <Form.Item
          name="firstName"
          rules={[{ required: true, message: "First name is required" }]}
        >
          <Input placeholder="First Name" />
        </Form.Item>
        <Form.Item
          name="lastName"
          rules={[{ required: true, message: "Last name is required" }]}
        >
          <Input placeholder="Last Name" />
        </Form.Item>
        <Form.Item
          name="username"
          rules={[{ required: true, message: "Username is required" }]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="Username"
          />
        </Form.Item>
        <Form.Item
          name="email"
          rules={[
            {
              type: "email",
              message: "The input is not valid a email!",
            },
            { required: true, message: "Email is required" },
          ]}
        >
          <Input
            prefix={<MailOutlined className="site-form-item-icon" />}
            placeholder="Email"
          />
        </Form.Item>
        <Form.Item
          name="birthday"
          rules={[{ required: true, message: "Birthday is required" }]}
        >
          <DatePicker
            placeholder="Select Birthday"
            disabledDate={disabledDate}
            style={{
              width: "100%",
            }}
          />
        </Form.Item>
        <Form.Item
          name="phone"
          rules={[
            { required: true, message: "Phone number is required!" },
            {
              pattern: REGEX.PHONE,
              message: "Enter valid phone number",
            },
          ]}
        >
          <Input addonBefore={prefixSelector} style={{ width: "100%" }} />
        </Form.Item>
        <Form.Item
          name="address"
          rules={[{ required: true, message: "Address name is required" }]}
        >
          <Input placeholder="Address" />
        </Form.Item>
        <Form.Item
          name="accountType"
          rules={[{ required: true, message: "Account Type is required" }]}
        >
          <Select allowClear placeholder="Account Type">
            <Option value="SAVINGS">SAVINGS</Option>
            <Option value="CURRENT">CURRENT</Option>
          </Select>
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            { required: true, message: "Please enter your password!" },
            {
              pattern: REGEX.PASSWORD,
              message:
                "Password must contain combination of lowercase, uppercase, special characters",
            },
          ]}
        >
          <Input.Password
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
          />
        </Form.Item>
        <Form.Item
          name="confirm"
          dependencies={["password"]}
          rules={[
            {
              required: true,
              message: "Please confirm your password!",
            },
            ({ getFieldValue }) => ({
              validator(_, value) {
                if (!value || getFieldValue("password") === value) {
                  return Promise.resolve();
                }
                return Promise.reject(
                  new Error("The two passwords that you entered do not match!")
                );
              },
            }),
          ]}
        >
          <Input.Password
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Confirm Password"
          />
        </Form.Item>
        <Form.Item>
          <Button
            loading={loading}
            type="primary"
            htmlType="submit"
            className="signup-form-button button"
          >
            Register
          </Button>
          <div className="reg-user-actions">
            <Link to={ROUTES.LOGIN}>Already a user!</Link>
          </div>
        </Form.Item>
      </Form>
    </div>
  );
}

export default Signup;
