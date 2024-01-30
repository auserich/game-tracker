import React from "react";
import { Button } from "react-bootstrap";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link, useNavigate } from "react-router-dom";

const AppNavbar = () => {
	const navigate = useNavigate();

	const handleLogout = () => {
		localStorage.removeItem("jwt");
		navigate("/");
	};

	return (
		<Navbar bg="primary" data-bs-theme="dark">
			<Container>
				<Navbar.Brand href="#home">Game Tracker</Navbar.Brand>
				<Nav className="me-auto">
					<Nav.Link href="/home">Home</Nav.Link>
					<Nav.Link href="/player">Player</Nav.Link>
					<Nav.Link href="/commander">Commander</Nav.Link>
					<Nav.Link href="/deck">Deck</Nav.Link>
					<Nav.Link href="/game">Game</Nav.Link>
				</Nav>
				<Button variant="danger" onClick={handleLogout}>
					Logout
				</Button>
			</Container>
		</Navbar>
	);
};

export default AppNavbar;
