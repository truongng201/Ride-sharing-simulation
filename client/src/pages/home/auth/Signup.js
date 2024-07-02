import "./Signup.css";
import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import Form from "react-bootstrap/Form";

import BackIcon from "../../../assets/icons/back.png";
import CustomerIcon from "../../../assets/icons/customer.png";
import DriverIcon from "../../../assets/icons/driver.png";
import LoadingComponent from "../../../components/Loading";
import {
  EmailValidation,
  PasswordValidation,
  UsernameValidation,
} from "../../../utils/validation";
import AlertComponent from "../../../components/Alerts";

export default function Signup() {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [retypePassword, setRetypePassword] = useState("");
  const [role, setRole] = useState("");
  const [vehicleType, setVehicleType] = useState("MOTORBIKE");
  const [access_token] = useState(localStorage.getItem("access_token"));
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (access_token !== null && access_token !== "") {
      navigate("/");
    }
  }, [access_token, navigate]);

  const signup = (e) => {
    e.preventDefault();
    if (!EmailValidation(email)) {
      setErrorMessage("Invalid email address");
    } else if (!UsernameValidation(username)) {
      setErrorMessage(
        "Username must be 8-32 characters long and contain at least one letter and one number"
      );
    } else if (!PasswordValidation(password)) {
      setErrorMessage(
        "Password must be 8-32 characters long and contain at least one letter and one number"
      );
    } else if (password !== retypePassword) {
      setErrorMessage("Passwords do not match");
    } else if (role !== "CUSTOMER" && role !== "DRIVER") {
      setErrorMessage("Please select a role");
    } else if (
      role === "DRIVER" &&
      vehicleType !== "MOTORBIKE" &&
      vehicleType !== "CAR4" &&
      vehicleType !== "CAR7"
    ) {
      console.log(vehicleType);
      setErrorMessage("Please select a vehicle type");
    } else {
      setErrorMessage("");
      setIsLoading(true);
      axios
        .post(`${process.env.REACT_APP_SERVER_URL}/auth/signup`, {
          email: email,
          password: password,
          username: username,
          role: role,
          vehicle_type: vehicleType,
        })
        .then(() => {
          localStorage.setItem("email", email);
          setIsLoading(false);
          navigate("/confirm-verify");
        })
        .catch((err) => {
          setErrorMessage(err.response?.data?.message);
          setIsLoading(false);
        });
    }

    setTimeout(() => {
      setErrorMessage("");
    }, 5000);
  };

  return (
    <div className="shared-container Signup">
      <div className="back">
        <Link to="/signin">
          <img src={BackIcon} alt="icon" width={"16px"} height={"16px"} />
          <span>Back</span>
        </Link>
      </div>
      <form className="shared-form">
        <div className="shared-form-group form-group-signup">
          <div className="role-selection">
            <div
              className={
                role === "CUSTOMER"
                  ? "role-button customer-button role-selected"
                  : "role-button customer-button"
              }
              onClick={() => {
                setRole("CUSTOMER");
              }}>
              <img
                className="signup-button"
                alt="icon"
                src={CustomerIcon}
                width={30}
                height={30}
              />
              <span>Customer</span>
            </div>
            <div
              className={
                role === "DRIVER"
                  ? "role-button customer-button role-selected"
                  : "role-button customer-button"
              }
              onClick={() => setRole("DRIVER")}>
              <img
                className="signup-button"
                alt="icon"
                src={DriverIcon}
                width={30}
                height={30}
              />
              <span>Driver</span>
            </div>
          </div>
          <input
            type="text"
            className="shared-form-control form-control-signup"
            id="email"
            placeholder="simulation@gmail.com"
            onChange={(e) => setEmail(e.target.value)}
          />
          <input
            type="text"
            className="shared-form-control form-control-signup"
            id="username"
            placeholder="Username"
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            className="shared-form-control form-control-signup"
            id="password"
            placeholder="Password"
            onChange={(e) => setPassword(e.target.value)}
          />
          <div className="last-form">
            <input
              type="password"
              className={
                role === "DRIVER"
                  ? "shared-form-control form-control-signup retype-password-input"
                  : "shared-form-control form-control-signup"
              }
              id="retype-password"
              placeholder="Retype Password"
              onChange={(e) => setRetypePassword(e.target.value)}
            />
            {role === "DRIVER" && (
              <Form.Select
                aria-label="Select your vehicle"
                onChange={(e) => {
                  setVehicleType(e.target.value);
                }}>
                <option>MOTORBIKE</option>
                <option value="CAR4">CAR4</option>
                <option value="CAR7">CAR7</option>
              </Form.Select>
            )}
          </div>
        </div>
        <button
          className="shared-button"
          id="signup-button"
          onClick={signup}
          onEnter={signup}
          disabled={isLoading}>
          {isLoading ? <LoadingComponent size={"sm"} /> : <span>Sign Up</span>}
        </button>
      </form>
      {errorMessage !== "" && (
        <AlertComponent
          message={errorMessage}
          handleClose={() => setErrorMessage("")}
        />
      )}
    </div>
  );
}
