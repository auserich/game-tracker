import React from "react";
import { Button, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const Game = () => {
	return (
		<Container>
			<h3>Game</h3>
			<Link to="/dashboard">
				<Button>Dashboard</Button>
			</Link>
		</Container>
	);
};

export default Game;
