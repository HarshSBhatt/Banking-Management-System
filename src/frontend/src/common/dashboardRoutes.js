import { ROUTES } from "./constants";

export const rootSubMenuKeys = [
  "Dashboard",
  "Profile",
  "Account",
  "Cheque Services",
  "Debit Card Services",
  "Credit Card Services",
  "FD Services",
  "User Management",
  "Leave",
  "Resign",
];

export const siderMenu = [
  {
    link: ROUTES.MAIN,
    label: "Dashboard",
    allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_HR", "ROLE_USER"],
  },
  {
    link: ROUTES.PROFILE,
    label: "My Profile",
    allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_HR", "ROLE_USER"],
  },
  {
    link: ROUTES.UPDATE_PROFILE,
    label: "Update Profile",
    allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_HR", "ROLE_USER"],
  },
  {
    link: ROUTES.MY_ACCOUNT,
    label: "My Account",
    allowedRoles: ["ROLE_USER"],
  },
  {
    link: ROUTES.ACCOUNT_STATEMENT,
    label: "Account Statement",
    allowedRoles: ["ROLE_USER"],
  },
  {
    link: ROUTES.CHEQUE_SERVICES,
    label: "Cheque Services",
    allowedRoles: ["ROLE_USER"],
  },
  {
    link: ROUTES.DEBIT_CARD_SERVICES,
    label: "Debit Card Services",
    allowedRoles: ["ROLE_USER"],
  },
  {
    link: ROUTES.CREDIT_CARD_SERVICES,
    label: "Credit Card Services",
    allowedRoles: ["ROLE_USER"],
  },
  {
    link: ROUTES.FD_SERVICES,
    label: "FD Services",
    allowedRoles: ["ROLE_USER"],
  },
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
    link: ROUTES.LEAVE,
    label: "Leave",
    allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_HR"],
  },
  {
    link: ROUTES.RESIGN,
    label: "Resign",
    allowedRoles: ["ROLE_MANAGER", "ROLE_EMPLOYEE", "ROLE_HR"],
  },
];
