import { DASHBOARD_PATH, REQUEST, RESULT_MANAGEMENT } from "@/constants/paths";
import React from "react";

const AdminPage = React.lazy(() => import("./pages/admin"));
const ManageLocationPage = React.lazy(() => import("./pages/manage"));
const Dashboard = React.lazy(() => import("./pages/dashboards"));

const routes = [
  {
    path: DASHBOARD_PATH,
    element: Dashboard,
  },
  {
    path: REQUEST,
    element: AdminPage,
  },

  {
    path: RESULT_MANAGEMENT,
    element: ManageLocationPage,
  },
];

export default routes;
