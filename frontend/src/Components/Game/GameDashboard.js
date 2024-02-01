import React, { useState } from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import GameAdder from "./GameAdder";
import GameViewer from "./GameViewer";

const GameDashboard = () => {
	const [gamesUpdated, setGamesUpdated] = useState(false);

	const handleGameAdded = () => {
		setGamesUpdated(!gamesUpdated);
	};

	return (
		<>
			<AppNavbar />
			<h3 className="text-center mt-3">Game Dashboard</h3>
			<GameAdder onGameAdded={handleGameAdded} />
			<GameViewer gamesUpdated={gamesUpdated} />
		</>
	);
};

export default GameDashboard;
