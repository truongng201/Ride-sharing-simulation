// src/MapComponent.js
import React from "react";
import { TransformWrapper, TransformComponent } from "react-zoom-pan-pinch";
import "./MapComponent.css";
import carTagIcon from "../assets/icons/carTag.png";
import userLocationTag from "../assets/icons/userLocationTag.png";
import pickLocationTag from "../assets/icons/pickLocationTag.png";

export default function MapComponent({
  mapData,
  drivers,
  user,
  handleMapClick,
  pickedCoordinates,
  shortestPath,
}) {
  const cellSize = 8;
  return (
    <div className="map-component">
      <TransformWrapper>
        {() => (
          <>
            <TransformComponent>
              <div
                onClick={handleMapClick}
                style={{
                  display: "grid",
                  gridTemplateColumns: `repeat(${mapData[0].length},8px)`,
                }}>
                {mapData.flat().map((cell, index) => (
                  <div
                    key={index}
                    style={{
                      width: 8,
                      height: 8,
                      backgroundColor:
                        cell === 0 ? "#e8e9ed" : "rgb(186, 198, 211)",
                    }}></div>
                ))}
                {drivers &&
                  drivers.length > 0 &&
                  drivers.map((driver, id) => (
                    <img
                      key={id}
                      className="driver-tag"
                      src={carTagIcon}
                      alt="driver-tag"
                      style={{
                        top: driver[0] * cellSize,
                        left: driver[1] * cellSize,
                        width: cellSize,
                        height: cellSize,
                      }}
                    />
                  ))}
                {user && (
                  <img
                    className="user-tag"
                    src={userLocationTag}
                    alt="user-tag"
                    style={{
                      top: user[0] * cellSize,
                      left: user[1] * cellSize,
                      width: cellSize,
                      height: cellSize,
                    }}
                  />
                )}
                {pickedCoordinates &&
                  pickedCoordinates.length > 0 &&
                  pickedCoordinates.map((coord, index) => (
                    <img
                      key={index}
                      className="picked-tag"
                      alt="picked-tag"
                      src={pickLocationTag}
                      style={{
                        top: coord[0] * cellSize,
                        left: coord[1] * cellSize,
                        width: 12,
                        height: 12,
                      }}
                    />
                  ))}

                {shortestPath &&
                  shortestPath.length > 0 &&
                  shortestPath.map((coord, index) => (
                    <div
                      key={index}
                      className={`shortest-path-cell shortest-path-cell-appear block-${index}`}
                      style={{
                        top: coord[0] * cellSize,
                        left: coord[1] * cellSize,
                        width: cellSize,
                        height: cellSize,
                        position: "absolute",
                      }}
                    />
                  ))}
              </div>
            </TransformComponent>
          </>
        )}
      </TransformWrapper>
    </div>
  );
}
