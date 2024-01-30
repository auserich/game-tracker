import React from "react";
import { Button, Card, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const Dashboard = () => {
	return (
		<Container>
			<h3>Dashboard</h3>
			<div className="d-grid gap-2">
				<Link to="/player">
					<Button>Player Module</Button>
				</Link>
				<Link to="/deck">
					<Button>Deck Module</Button>
				</Link>
				<Link to="/game">
					<Button>Game Module</Button>
				</Link>
				<Link to="/commander">
					<Button>Commander Module</Button>
				</Link>
				<Link to="/">
					<Button variant="danger">Logout</Button>
				</Link>
			</div>
		</Container>
	);
};

export default Dashboard;
