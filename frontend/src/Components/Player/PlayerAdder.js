import React, { useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

const PlayerAdder = () => {
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

			if (response.ok) {
				console.log("Player created successfully");
			} else {
				console.error("Failed to create player");
			}
		} catch (error) {
			console.error("Error creating player", error);
		}
	};

	return (
		<Container>
			<Row>
				<Col>
					<Card>
						<Card.Title>Player Adder</Card.Title>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<h3 className="text-center mb-4"></h3>
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
								<Button
									block
									variant="primary"
									type="submit"
									className="w-100"
								>
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
