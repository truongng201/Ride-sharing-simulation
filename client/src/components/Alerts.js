import "./Alerts.css";
import Alert from "react-bootstrap/Alert";
import React from "react";

export default function AlertComponent({ message, handleClose }) {
  return (
    <div className="AlertComponent">
      <Alert key="danger" variant="danger" onClose={handleClose} dismissible>
        {message}
      </Alert>
    </div>
  );
}
