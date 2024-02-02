import React, { useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import moment from "moment";

const GameAdder = ({ onGameAdded }) => {
	const [winningPlayer, setWinningPlayer] = useState(null);
	const [playerNames, setPlayerNames] = useState(
		Array.from({ length: 4 }, (_, index) => "")
	);
	const [gameNumber, setGameNumber] = useState(null); // Initialize Game Number

	const handleCheckboxChange = (playerId) => {
		setWinningPlayer((prevWinner) =>
			prevWinner === playerNames[playerId - 1]
				? null
				: playerNames[playerId - 1]
		);
	};

	const handlePlayerNameChange = (playerId, event) => {
		const newPlayerNames = [...playerNames];
		newPlayerNames[playerId - 1] = event.target.value;
		setPlayerNames(newPlayerNames);
	};

	const handleDateChange = (event) => {
		const selectedDate = event.target.value;
		const currentDate = moment().startOf("day");
		console.log("Selected Date: ", selectedDate);

		// Compare the strings directly
		if (selectedDate > currentDate.format("YYYY-MM-DD")) {
			// Display a message or disable the submit button to prevent selecting future dates
			console.log("Cannot select future dates");
			// Optionally, you can reset the date to the current date
			// event.target.value = currentDate.format('YYYY-MM-DD');
		}

		// Update Game Number to 1 when date changes
		setGameNumber(1);
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		const formData = new FormData(e.target);
		const date = formData.get("date");

		// Convert gameNumber to a number
		const gameNumber = parseInt(formData.get("gameNumber"), 10);

		// Create an array to store deck IDs
		const deckIds = [];

		// Loop through each player to get the deck IDs
		for (let playerId = 1; playerId <= 4; playerId++) {
			const deckName = formData.get(`deckName${playerId}`);

			// Check if deckName is non-empty
			if (deckName.trim() === "") {
				// If it's empty, skip this player
				continue;
			}

			// Perform a lookup to get the deck ID based on the entered deck name
			const deckId = await getDeckId(deckName);

			// Check if deckId is null
			if (deckId === null) {
				console.error(`No matching deck ID found for ${deckName}`);
				// Display an error message, prevent form submission, or handle the error as needed
				return;
			}

			// Push the deck ID to the array
			deckIds.push(deckId);
		}

		// Use the getDeckId function to get the winner's ID
		const winnerName = formData.get("winnerName");
		const winnerId = await getDeckId(winnerName);

		console.log("Deck IDs: ", deckIds);
		console.log("Winner ID: ", winnerId);

		const requestBody = {
			date: date,
			gameNumber: gameNumber,
			deck1: { id: deckIds[0] },
			deck2: { id: deckIds[1] },
			deck3: { id: deckIds[2] },
			deck4: { id: deckIds[3] },
			winner: { id: winnerId },
		};

		try {
			const jwt = localStorage.getItem("jwt");
			console.log("Attempting to POST: ", requestBody);
			const response = await fetch(`http://localhost:8080/api/game`, {
				method: "POST",
				headers: {
					Authorization: `Bearer ${jwt}`,
					"Content-Type": "application/json",
				},
				body: JSON.stringify(requestBody),
			});

			if (response.ok) {
				console.log("Game added successfully");
				onGameAdded();
				// Increment Game Number after successful submission
				setGameNumber(
					(prevGameNumber) => parseInt(prevGameNumber, 10) + 1
				);
				// Clear player names after successful submission
				setPlayerNames(Array.from({ length: 4 }, (_, index) => ""));
			} else {
				console.error("Failed to add game");
			}
		} catch (error) {
			console.error("Error adding game: ", error.message);
		}
	};

	// Helper function to get deck ID based on deck name
	const getDeckId = async (deckName) => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/deck/name/${deckName}`,
				{
					method: "GET",
					headers: {
						Authorization: `Bearer ${jwt}`,
						"Content-Type": "application/json",
					},
				}
			);

			if (!response.ok) {
				console.error(`Failed to get deck ID for ${deckName}`);
				return null;
			}

			const data = await response.json();
			return data.id;
		} catch (error) {
			console.error(
				`Error getting deck ID for ${deckName}: `,
				error.message
			);
			return null;
		}
	};

	return (
		<Container className="mt-5">
			<Row className="justify-content-center">
				<Col md={6}>
					<Card>
						<Card.Title className="text-center mt-3">
							Add a Game
						</Card.Title>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<Row>
									<Col md={9} className="mb-3">
										<Form.Label>Date</Form.Label>
										<Form.Control
											type="date"
											name="date"
											onChange={handleDateChange}
										/>
									</Col>
									<Col md={3} className="mb-3">
										<Form.Label>Game Number</Form.Label>
										<Form.Control
											type="number"
											name="gameNumber"
											value={gameNumber}
											onChange={(e) =>
												setGameNumber(e.target.value)
											}
										/>
									</Col>
								</Row>
								{Array.from(
									{ length: 4 },
									(_, index) => index + 1
								).map((playerId) => (
									<Row key={playerId} className="mb-3">
										<Col md={6}>
											<Form.Label>{`Player ${playerId}`}</Form.Label>
										</Col>
										<Col
											md={6}
											className="d-flex align-items-center justify-content-end"
										>
											<Form.Check
												type="checkbox"
												label="Winner"
												onChange={() =>
													handleCheckboxChange(
														playerId
													)
												}
												checked={
													winningPlayer ===
													playerNames[playerId - 1]
												}
												name={`winnerName`}
												value={
													playerNames[playerId - 1]
												} // Add this line
											/>
										</Col>
										<Col md={12}>
											<Form.Control
												value={
													playerNames[playerId - 1]
												}
												onChange={(e) =>
													handlePlayerNameChange(
														playerId,
														e
													)
												}
												name={`deckName${playerId}`}
											/>
										</Col>
									</Row>
								))}
								<Button type="submit">Submit</Button>
							</Form>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default GameAdder;
