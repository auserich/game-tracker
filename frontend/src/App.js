import React from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Components/Login/Login";
import Signup from "./Components/Signup/Signup";
import HomeDashboard from "./Components/Home/HomeDashboard";
import PlayerDashboard from "./Components/Player/PlayerDashboard";
import CommanderDashboard from "./Components/Commander/CommanderDashboard";
import DeckDashboard from "./Components/Deck/DeckDashboard";
import GameDashboard from "./Components/Game/GameDashboard";

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Login />}></Route>
				<Route path="/signup" element={<Signup />}></Route>
				<Route path="/home" element={<HomeDashboard />}></Route>
				<Route path="/player" element={<PlayerDashboard />}></Route>
				<Route path="/deck" element={<DeckDashboard />}></Route>
				<Route path="/game" element={<GameDashboard />}></Route>
				<Route
					path="/commander"
					element={<CommanderDashboard />}
				></Route>
			</Routes>
		</BrowserRouter>
	);
}

export default App;
