import React, { useState } from "react";
import PlayerAdder from "./PlayerAdder";
import PlayerViewer from "./PlayerViewer";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";
import AppNavbar from "../AppNavbar/AppNavbar";

const PlayerDashboard = () => {
	const [playersUpdated, setPlayersUpdated] = useState(false);

	const handlePlayerAdded = () => {
		setPlayersUpdated(!playersUpdated);
	};

	return (
		<>
			<AppNavbar />
			<h3>Player Dashboard</h3>
			<PlayerAdder onPlayerAdded={handlePlayerAdded} />
			<PlayerViewer playersUpdated={playersUpdated} />
		</>
	);
};

export default PlayerDashboard;
