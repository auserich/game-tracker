import React, { useState } from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import CommanderAdder from "./CommanderAdder";
import CommanderViewer from "./CommanderViewer";

const CommanderDashboard = () => {
	const [commandersUpdated, setCommandersUpdated] = useState(false);

	const handleCommanderAdded = () => {
		setCommandersUpdated(!commandersUpdated);
	};

	return (
		<>
			<AppNavbar />
			<h3>Commander Dashboard</h3>
			<CommanderAdder onCommanderAdded={handleCommanderAdded} />
			<CommanderViewer commandersUpdated={commandersUpdated} />
		</>
	);
};

export default CommanderDashboard;
