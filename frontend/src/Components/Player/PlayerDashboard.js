import React, { useState } from "react";
import PlayerAdder from "./PlayerAdder";
import PlayerViewer from "./PlayerViewer";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";

const PlayerDashboard = () => {
	const [playersUpdated, setPlayersUpdated] = useState(false);

	const handlePlayerAdded = () => {
		setPlayersUpdated(!playersUpdated);
	};

	return (
		<>
			<div>Player Dashboard</div>
			<PlayerAdder onPlayerAdded={handlePlayerAdded} />
			<PlayerViewer playersUpdated={playersUpdated} />
			<Link to="/dashboard">
				<Button>Dashboard</Button>
			</Link>
		</>
	);
};

export default PlayerDashboard;
