import React, { useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

const PlayerAdder = ({ onPlayerAdded }) => {
	const [playerName, setPlayerName] = useState("");

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/player?name=${playerName}`,
				{
					method: "POST",
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${jwt}`,
					},
					body: JSON.stringify({ name: playerName }),
				}
			);

			if (response.ok && typeof onPlayerAdded === "function") {
				console.log("Player created successfully");
				// Notify the parent component about the new player
				onPlayerAdded();
			} else {
				const data = await response.json();
				console.error("Failed to create player", data);
			}
		} catch (error) {
			console.error("Error creating player", error);
		}
	};

	return (
		<Container className="mt-5">
			<Row>
				<Col>
					<Card>
						<Card.Header className="text-center">
							Player Adder
						</Card.Header>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<h3 className="text-center"></h3>
								<Form.Group className="mb-3">
									<Form.Label>Player name</Form.Label>
									<Form.Control
										placeholder="Enter player name"
										value={playerName}
										onChange={(e) =>
											setPlayerName(e.target.value)
										}
									/>
								</Form.Group>
								<Button block variant="primary" type="submit">
									Submit
								</Button>
							</Form>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default PlayerAdder;
