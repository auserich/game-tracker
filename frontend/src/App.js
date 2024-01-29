import React from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Components/Login/Login";
import Signup from "./Components/Signup/Signup";
import Dashboard from "./Components/Dashboard/Dashboard";
import Player from "./Components/Player/Player";
import Deck from "./Components/Deck/Deck";
import Game from "./Components/Game/Game";
import Commander from "./Components/Commander/Commander";

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Login />}></Route>
				<Route path="/signup" element={<Signup />}></Route>
				<Route path="/dashboard" element={<Dashboard />}></Route>
				<Route path="/player" element={<Player />}></Route>
				<Route path="/deck" element={<Deck />}></Route>
				<Route path="/game" element={<Game />}></Route>
				<Route path="/commander" element={<Commander />}></Route>
			</Routes>
		</BrowserRouter>
	);
}

export default App;
