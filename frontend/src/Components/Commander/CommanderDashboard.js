import React from "react";
import AppNavbar from "../AppNavbar/AppNavbar";
import CommanderAdder from "./CommanderAdder";

const CommanderDashboard = () => {
	return (
		<>
			<AppNavbar />
			<div>CommanderDashboard</div>
			<CommanderAdder />
		</>
	);
};

export default CommanderDashboard;
