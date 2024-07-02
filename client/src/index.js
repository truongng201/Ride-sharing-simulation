import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from "./components/RootLayout";
import JourneyMap from "./pages/JourneyMap";
import NotFoundPage from "./pages/NotFoundPage";
import Signin from "./pages/home/auth/Signin";
import Signup from "./pages/home/auth/Signup";
import Home from "./pages/home/Home";
import ForgotPassword from "./pages/home/auth/ForgotPassword";
import ResetPassword from "./pages/home/auth/ResetPassword";
import SentEmail from "./pages/home/auth/SentEmail";
import Reverify from "./pages/home/auth/Reverify";
import ConfirmEmail from "./pages/home/auth/ConfirmEmail";

const router = createBrowserRouter([
  {
    path: "/",
    element: window.innerWidth >= 1200 ? <RootLayout /> : <NotFoundPage />,
    errorElement: <NotFoundPage />,
    children: [
      { path: "/", element: <Home /> },
      {
        path: "/",
        element: <JourneyMap />,
        children: [
          {
            path: "/confirm-reset",
            element: <ConfirmEmail email_type="reset" />,
          },
          {
            path: "/confirm-verify",
            element: <ConfirmEmail email_type="verify" />,
          },
          {
            path: "/verify-email",
            element: <SentEmail email_type="verify" />,
          },
          {
            path: "/reset-email",
            element: <SentEmail email_type="reset" />,
          },
          { path: "/reverify", element: <Reverify /> },
          { path: "/reset/:resetID", element: <ResetPassword /> },
          { path: "/forgot", element: <ForgotPassword /> },
          { path: "/signup", element: <Signup /> },
          { path: "/signin", element: <Signin /> },
        ],
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
