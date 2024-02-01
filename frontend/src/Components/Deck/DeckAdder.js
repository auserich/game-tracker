import React, { useState, useEffect } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";

const DeckAdder = ({ onDeckAdded }) => {
	const [players, setPlayers] = useState([]);
	const [commanders, setCommanders] = useState([]);

	useEffect(() => {
		fetchPlayers();
		fetchCommanders();
	}, []);

	const fetchPlayers = async () => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				"http://localhost:8080/api/player/user",
				{
					method: "GET",
					headers: {
						Authorization: `Bearer ${jwt}`,
						"Content-Type": "application/json",
					},
				}
			);

			if (!response.ok) {
				console.error("Failed to get players");
			}

			const data = await response.json();
			const sortedPlayers = data.sort((a, b) =>
				a.name.localeCompare(b.name)
			);
			setPlayers(sortedPlayers);
		} catch (error) {
			console.error("Error fetching players: ", error.message);
		}
	};

	const fetchCommanders = async () => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				"http://localhost:8080/api/commander",
				{
					method: "GET",
					headers: {
						Authorization: `Bearer ${jwt}`,
						"Content-Type": "application/json",
					},
				}
			);

			if (!response.ok) {
				console.error("Failed to get commanders");
			}

			const data = await response.json();
			const sortedCommanders = data.sort((a, b) =>
				a.name.localeCompare(b.name)
			);
			setCommanders(sortedCommanders);
		} catch (error) {
			console.error("Error fetching commanders: ", error.message);
		}
	};

	const handleSubmit = async (event) => {
		event.preventDefault();

		// Collect form data
		const form = event.currentTarget;
		const playerSelect = form.elements.playerSelect;
		const commanderSelect = form.elements.commanderSelect;
		const deckName = form.elements.deckName.value;
		const playerName = playerSelect.value;
		const commanderName = commanderSelect.value;

		console.log("Form:", form);
		console.log("Player Select:", playerSelect);
		console.log("Commander Select:", commanderSelect);
		console.log("Deck Name:", deckName);
		console.log("Player Name:", playerName);
		console.log("Commander Name:", commanderName);

		// Make a POST request
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/deck?playerName=${playerName}&commanderName=${commanderName}&deckName=${deckName}`,
				{
					method: "POST",
					headers: {
						Authorization: `Bearer ${jwt}`,
						"Content-Type": "application/json",
					},
				}
			);

			if (response.ok) {
				console.log("Deck created successfully");
				onDeckAdded();
			} else {
				console.error("Failed to create a new deck");
			}
		} catch (error) {
			console.error("Error creating deck: ", error.message);
		}
	};

	return (
		<Container className="mt-5">
			<Row className="justify-content-center">
				<Col md={6}>
					<Card>
						<Card.Title className="text-center mt-3">
							Add a Deck
						</Card.Title>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<Form.Group className="mb-3">
									<Form.Label>Player Name</Form.Label>
									<Form.Select
										id="playerSelect"
										name="playerSelect"
									>
										<option>Select player</option>
										{players.map((player) => (
											<option
												key={player.id}
												value={player.name}
											>
												{player.name}
											</option>
										))}
									</Form.Select>
								</Form.Group>
								<Form.Group className="mb-3">
									<Form.Label>Commander Name</Form.Label>
									<Form.Select
										id="commanderSelect"
										name="commanderSelect"
									>
										<option>Select commander</option>
										{commanders.map((commander) => (
											<option
												key={commander.id}
												value={commander.name}
											>
												{commander.name}
											</option>
										))}
									</Form.Select>
								</Form.Group>
								<Form.Group className="mb-3">
									<Form.Label>Deck Name</Form.Label>
									<Form.Control
										id="deckName"
										name="deckName"
										placeholder="Enter deck name"
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

export default DeckAdder;
