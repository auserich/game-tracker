import React from "react";
import { Button, Card, Container } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import AppNavbar from "../AppNavbar/AppNavbar";

const Dashboard = () => {
	const navigate = useNavigate();

	const handleLogout = () => {
		localStorage.removeItem("jwt");
		navigate("/");
	};

	return (
		<>
			<AppNavbar />
			<Container>
				<h3>Home</h3>
				<div className="d-grid gap-2">
					<Link to="/player">
						<Button>Player Module</Button>
					</Link>
					<Link to="/commander">
						<Button>Commander Module</Button>
					</Link>
					<Link to="/deck">
						<Button>Deck Module</Button>
					</Link>
					<Link to="/game">
						<Button>Game Module</Button>
					</Link>
				</div>
			</Container>
		</>
	);
};

export default Dashboard;
