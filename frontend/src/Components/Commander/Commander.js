import React from "react";
import { Button, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const Commander = () => {
	return (
		<Container>
			<h3>Commander</h3>
			<Link to="/dashboard">
				<Button>Dashboard</Button>
			</Link>
		</Container>
	);
};

export default Commander;
