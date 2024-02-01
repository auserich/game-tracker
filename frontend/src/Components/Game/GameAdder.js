import React, { useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";

const GameAdder = ({ onGameAdded }) => {
	const [winningPlayer, setWinningPlayer] = useState(null);
	const [playerNames, setPlayerNames] = useState(
		Array.from({ length: 4 }, (_, index) => "")
	);

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

	const handleSubmit = async (e) => {
		e.preventDefault();

		const formData = new FormData(e.target);
		const date = formData.get("date");
		const gameNumber = formData.get("gameNumber");
		const deckNames = playerNames.map(
			(name, index) => (index < 3 ? name : "") // Assuming you only have 4 players
		);
		const winnerName = formData.get("winnerName");

		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/game?date=${date}&gameNumber=${gameNumber}&deckName1=${deckNames[0]}&deckName2=${deckNames[1]}&deckName3=${deckNames[2]}&deckName4=${deckNames[3]}&winnerName=${winnerName}`,
				{
					method: "POST",
					headers: {
						Authorization: `Bearer ${jwt}`,
						"Content-Type": "application/json",
					},
				}
			);

			if (response.ok) {
				console.log("Game added successfully");
				onGameAdded();
			} else {
				console.error("Failed to add game");
			}
		} catch (error) {
			console.error("Error adding game: ", error.message);
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
										<Form.Control type="date" name="date" />
									</Col>
									<Col md={3} className="mb-3">
										<Form.Label>Game Number</Form.Label>
										<Form.Control
											type="number"
											name="gameNumber"
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
