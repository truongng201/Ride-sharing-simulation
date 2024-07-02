import "./Home.css";
import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import axios from "axios";
import Loading from "../../components/Loading";
import AlertComponent from "../../components/Alerts";
import MapComponent from "../../components/MapComponent";

export default function Home() {
  const [access_token] = useState(localStorage.getItem("access_token"));
  const [map, setMap] = useState([]);
  const [user, setUser] = useState({});
  const [allBestDrivers, setAllBestDrivers] = useState([]);
  const [userLocation, setUserLocation] = useState([]);
  const [pickedCoordinates, setPickedCoordinates] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [shortestPath, setShortestPath] = useState([]); // [ [x, y], [x, y], ...
  const [mode, setMode] = useState("Private"); // Shared | Private | Public | Pool
  const cellSize = 8;
  const handleMapClick = (event) => {
    const mapElement = event.currentTarget;
    const rect = mapElement.getBoundingClientRect();
    const y = Math.floor((event.clientX - rect.left) / cellSize);
    const x = Math.floor((event.clientY - rect.top) / cellSize);
    if (mode === "Private") {
      setPickedCoordinates([[x, y]]);
    } else if (mode === "Public") {
      const newPickedCoordinates = [...pickedCoordinates];
      newPickedCoordinates.push([x, y]);
      if (newPickedCoordinates.length <= 10) {
        setPickedCoordinates(newPickedCoordinates);
      } else {
        setErrorMessage("You can only pick up to 10 locations");
      }
    }
  };

  const navigate = useNavigate();
  useEffect(() => {
    if (errorMessage !== "") {
      setTimeout(() => {
        setErrorMessage("");
      }, 5000);
    }
    if (access_token === null || access_token === "") {
      navigate("/signin");
    } else {
      axios
        .get(`${process.env.REACT_APP_SERVER_URL}/map`)
        .then((res) => {
          setMap(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
      axios
        .get(`${process.env.REACT_APP_SERVER_URL}/user-info`, {
          headers: {
            Authorization: `Bearer ${access_token}`,
          },
        })
        .then((res) => {
          setUser(res.data.data?.user);
          if (res.data.data.user.role === "CUSTOMER") {
            setUserLocation([
              res.data.data.customer?.location?.x,
              res.data.data.customer?.location?.y,
            ]);
            axios
              .get(`${process.env.REACT_APP_SERVER_URL}/all-best-drivers`, {
                headers: {
                  Authorization: `Bearer ${access_token}`,
                },
              })
              .then((res) => {
                setAllBestDrivers(res.data.data);
              })
              .catch((err) => {
                console.log(err);
              });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [navigate, access_token, errorMessage]);

  const handleBookRides = () => {
    if (pickedCoordinates.length === 0) {
      setErrorMessage("Please pick a location on the map");
      return;
    }
    if (mode === "Private" && pickedCoordinates.length === 1) {
      axios
        .post(`${process.env.REACT_APP_SERVER_URL}/find-shortest-path`, {
          startX: userLocation[0],
          startY: userLocation[1],
          endX: pickedCoordinates[0][0],
          endY: pickedCoordinates[0][1],
        })
        .then((res) => {
          console.log(res)
          setShortestPath(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    } else if (
      mode === "Public" &&
      pickedCoordinates.length > 1 &&
      pickedCoordinates.length <= 10
    ) {
      axios
        .post(`${process.env.REACT_APP_SERVER_URL}/ride-sharing`, {
          start_x: userLocation[0],
          start_y: userLocation[1],
          end_points: pickedCoordinates,
        })
        .then((res) => {
          setShortestPath(res.data.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };
  const handleChangeMode = () => {
    if (mode === "Private") {
      setMode("Public");
    } else if (mode === "Public") {
      setMode("Private");
    }
  };

  return (
    <div className="Home-Simulation">
      <div className="Home-left-container">
        <div className="User-info">
          <div className="Title">User info</div>
          {user && (
            <div className="group-info">
              <img
                className="user-avatar"
                src={user.image_url ? user.image_url : ""}
                alt="user-avatar"
                style={{ width: 100, height: 100 }}
              />
              <div className="welcome">
                Welcome <span>{user.username}</span> to Journey map. Please
                choose a location on the map to book a ride
              </div>
            </div>
          )}
        </div>
        <div className="Booking">
          <div className="Title">Rides - {mode}</div>
          <div>
            Current Location: {userLocation[0]}, {userLocation[1]}
          </div>
          <div>
            {mode === "Private"
              ? "Destination Location: "
              : "Picked Locations: "}
            {mode === "Private" &&
              pickedCoordinates.length === 1 &&
              `${pickedCoordinates[0][0]}, ${pickedCoordinates[0][1]}`}
            {mode === "Public" &&
              pickedCoordinates.length > 1 &&
              pickedCoordinates.map((coord, index) => (
                <span key={index}>
                  [{coord[0]}, {coord[1]}] -{" "}
                </span>
              ))}
            {pickedCoordinates.length === 0 && "No location picked"}
          </div>
          <div className="button-group">
            <button className="shared-button" onClick={handleChangeMode}>
              <span>Change mode</span>
            </button>
            <button className="shared-button" onClick={handleBookRides}>
              <span>Book rides</span>
            </button>
          </div>
        </div>
      </div>
      <div className="Home-map">
        {map.length === 0 ? (
          <Loading />
        ) : (
          <MapComponent
            mapData={map}
            drivers={allBestDrivers}
            user={userLocation}
            pickedCoordinates={pickedCoordinates}
            shortestPath={shortestPath}
            handleMapClick={handleMapClick}
          />
        )}
      </div>
      {errorMessage !== "" && (
        <AlertComponent
          message={errorMessage}
          variant={"danger"}
          handleClose={() => setErrorMessage("")}
        />
      )}
    </div>
  );
}
