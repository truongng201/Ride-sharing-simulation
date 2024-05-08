import "./JourneyMap.css";
import React from "react";
import Logo from "../assets/logo/journey-map-high-resolution-logo.svg";
import { Outlet } from "react-router-dom";

export default function JourneyMap() {
  return (
    <div className="JourneyMap">
      <div className="JourneyMap-layout">
        <div className="JourneyMap-left-container">
          <Outlet />
        </div>
        <div className="JourneyMap-right-container">
          <img
            className="login-logo"
            alt="logo"
            src={Logo}
            width={300}
            height="auto"
          />
        </div>
      </div>
    </div>
  );
}
