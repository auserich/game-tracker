import React from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Components/Login/Login";
import Signup from "./Components/Signup/Signup";
import Dashboard from "./Components/Dashboard/Dashboard";

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Login />}></Route>
				<Route path="/signup" element={<Signup />}></Route>
				<Route path="/dashboard" element={<Dashboard />}></Route>
			</Routes>
		</BrowserRouter>
	);
}

export default App;
