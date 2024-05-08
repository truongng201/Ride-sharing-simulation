import { Outlet, Link } from "react-router-dom";
import "./RootLayout.css";
import React from "react";
import HomeIcon from "../assets/icons/car.png";
import BlogIcon from "../assets/icons/blog.png";
import FileIcon from "../assets/icons/file.png";
import Avatar from "../assets/avatar.png";

export default function RootLayout() {
  const pages = [
    {
      name: "Home",
      link: "/",
      className: "page-component home",
      iconPath: HomeIcon,
    },
    {
      name: "Readme",
      link: "https://github.com/truongng201/ride-sharing-simulation/blob/main/README.md",
      className: "page-component readme",
      iconPath: FileIcon,
    },
    {
      name: "Blog",
      link: "https://truongng012.notion.site/Term-Project-COMP1020-3418219bb5ed42a98b8d71eb277a3edd",
      className: "page-component blog",
      iconPath: BlogIcon,
    },
  ];
  return (
    <div className="root-layout">
      <div className="left-container">
        <div className="pages">
          {pages.map((page, index) => {
            return (
              <Link key={index} className={page.className} to={page.link}>
                <img
                  width={32}
                  height={32}
                  src={page.iconPath}
                  alt="page icon"
                />
                <div>{page.name}</div>
              </Link>
            );
          })}
        </div>
        <div className="developer-info">
          <div className="username">COMP 1020 Term Project</div>
          <img src={Avatar} className="avatar" alt="developer avatar" />
          <div className="bio">
            The ride-sharing simulation replicates services like Uber or Lyft,
            analyzing user demand, driver availability, and pricing dynamics for
            optimization.
          </div>
          <div className="social-media">
            <div className="github">
              <a href="https://github.com/truongng201">
                <i className="fab fa-github"></i>
              </a>
            </div>
            <div className="facebook">
              <a href="https://www.facebook.com/profile.php?id=100009498819215">
                <i className="fab fa-facebook"></i>
              </a>
            </div>
          </div>
        </div>
      </div>
      <div className="right-container">
        <Outlet />
      </div>
    </div>
  );
}
