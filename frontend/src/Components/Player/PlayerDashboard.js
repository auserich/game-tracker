import React from "react";
import PlayerAdder from "./PlayerAdder";
import PlayerViewer from "./PlayerViewer";
import { Link } from "react-router-dom";
import { Button } from "react-bootstrap";

const PlayerDashboard = () => {
	return (
		<>
			<div>Player Dashboard</div>
			<PlayerAdder />
			<PlayerViewer />
			<Link to="/dashboard">
				<Button>Dashboard</Button>
			</Link>
		</>
	);
};

export default PlayerDashboard;
