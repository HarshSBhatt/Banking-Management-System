import { ROUTES } from "./constants";

export const roleBasedDashboard = {
  ROLE_USER: [
    {
      link: ROUTES.PROFILE,
      label: "My Profile",
    },
    {
      link: ROUTES.UPDATE_PROFILE,
      label: "Update Profile",
    },
    {
      link: ROUTES.MY_ACCOUNT,
      label: "My Account",
    },
    {
      link: ROUTES.ACCOUNT_STATEMENT,
      label: "Account Statement",
    },
    {
      link: ROUTES.CHEQUE_SERVICES,
      label: "Cheque Services",
    },
    {
      link: ROUTES.DEBIT_CARD_SERVICES,
      label: "Debit Card Services",
    },
    {
      link: ROUTES.CREDIT_CARD_SERVICES,
      label: "Credit Card Services",
    },
    {
      link: ROUTES.FD_SERVICES,
      label: "FD Services",
    },
    {
      link: ROUTES.LOGOUT,
      label: "Logout",
    },
  ],
  BANK: [
    {
      link: ROUTES.ADD_USER,
      label: "Add User",
      allowedRoles: ["ROLE_MANAGER", "ROLE_HR"],
    },
    {
      link: ROUTES.ACCOUNT_OPENING_REQUEST,
      label: "Account Opening Requests",
      allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE"],
    },
    {
      link: ROUTES.CREDIT_CARD_REQUEST,
      label: "Credit Card Requests",
      allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE"],
    },
    {
      link: ROUTES.APPLY_LEAVE,
      label: "Apply Leave",
      allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_HR"],
    },
    {
      link: ROUTES.LEAVE_REQUEST,
      label: "Leave Requests",
      allowedRoles: ["ROLE_MANAGER", "ROLE_HR"],
    },
    {
      link: ROUTES.APPLY_RESIGNATION,
      label: "Apply Resignation",
      allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_HR"],
    },
    {
      link: ROUTES.RESIGNATION_REQUEST,
      label: "Resign Requests",
      allowedRoles: ["ROLE_MANAGER", "ROLE_HR"],
    },
    {
      link: ROUTES.LOGOUT,
      label: "Logout",
    },
  ],
};
