import React from 'react'
import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { NavigationBar, ButtonEvent, StoryTable } from "../../components";
import { Container } from "react-bootstrap";

function Stories () {
  const [authenticated, setauthenticated] = useState(null);
    useEffect(() => {
    const loggedInUser = localStorage.getItem("authenticated");
    if (loggedInUser) {
        setauthenticated(loggedInUser);
    }
}, []);

    return (
        <div >
            <NavigationBar />
            <ButtonEvent />
            <StoryTable />
        </div>
    );
}

export { Stories }