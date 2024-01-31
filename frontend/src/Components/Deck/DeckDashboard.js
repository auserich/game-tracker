import React from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import DeckAdder from "./DeckAdder";

const DeckDashboard = () => {
	return (
		<>
			<AppNavbar />
			<h3>Deck Dashboard</h3>
			<DeckAdder />
		</>
	);
};

export default DeckDashboard;
