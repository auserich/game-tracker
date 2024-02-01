import React, { useState } from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import DeckAdder from "./DeckAdder";
import DeckViewer from "./DeckViewer";

const DeckDashboard = () => {
	const [decksUpdated, setDecksUpdated] = useState(false);

	const handleDeckAdded = () => {
		setDecksUpdated(!decksUpdated);
	};

	return (
		<>
			<AppNavbar />
			<h3 className="text-center mt-3">Deck Dashboard</h3>
			<DeckAdder onDeckAdded={handleDeckAdded} />
			<DeckViewer decksUpdated={decksUpdated} />
		</>
	);
};

export default DeckDashboard;
