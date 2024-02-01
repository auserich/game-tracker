import React from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import GameAdder from "./GameAdder";
import GameViewer from "./GameViewer";

const GameDashboard = () => {
	return (
		<>
			<AppNavbar />
			<h3 className="text-center mt-3">Game Dashboard</h3>
			<GameAdder />
			<GameViewer />
		</>
	);
};

export default GameDashboard;
