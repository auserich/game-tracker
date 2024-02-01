import React, { useState, useEffect } from "react";
import {
	Accordion,
	Button,
	Card,
	Col,
	Container,
	Form,
	ListGroup,
	Row,
} from "react-bootstrap";

const DeckViewer = ({ decksUpdated }) => {
	const [players, setPlayers] = useState([]);
	const [decks, setDecks] = useState({});
	const [editDeck, setEditDeck] = useState({
		playerId: null,
		deckId: null,
		deckName: "",
		commanderId: null,
	});

	useEffect(() => {
		fetchPlayers();
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
				return;
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

	useEffect(() => {
		const fetchDecksForAllPlayers = async () => {
			const jwt = localStorage.getItem("jwt");

			const decksData = await Promise.all(
				players.map(async (player) => {
					const response = await fetch(
						`http://localhost:8080/api/deck/playerId?playerId=${player.id}`,
						{
							method: "GET",
							headers: {
								Authorization: `Bearer ${jwt}`,
								"Content-Type": "application/json",
							},
						}
					);

					if (!response.ok) {
						console.error(
							`Failed to get decks for player ${player.id}`
						);
						return { playerId: player.id, decks: [] };
					}

					const decks = await response.json();
					const sortedDecks = decks.sort((a, b) =>
						a.name.localeCompare(b.name)
					);

					return { playerId: player.id, decks: sortedDecks };
				})
			);

			const decksObject = {};
			decksData.forEach(({ playerId, decks }) => {
				decksObject[playerId] = decks;
			});

			setDecks(decksObject);
		};

		fetchDecksForAllPlayers();
	}, [players, decksUpdated]);

	const handleDeleteDeck = async (deckId) => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/deck/id?id=${deckId}`,
				{
					method: "DELETE",
					headers: {
						Authorization: `Bearer ${jwt}`,
						"Content-Type": "application/json",
					},
				}
			);

			if (!response.ok) {
				console.error(`Failed to delete deck with ID: ${deckId}`);
				return;
			}

			setDecks((prevDecks) =>
				Object.keys(prevDecks).reduce((acc, playerId) => {
					acc[playerId] = prevDecks[playerId].filter(
						(deck) => deck.id !== deckId
					);
					return acc;
				}, {})
			);
		} catch (error) {
			console.error("Error deleting deck: ", error.message);
		}
	};

	const handleEdit = (playerId, deckId, deckName, commanderId) => {
		setEditDeck({
			playerId,
			deckId,
			deckName,
			commanderId,
		});
	};

	const handleCancelEdit = () => {
		setEditDeck({
			playerId: null,
			deckId: null,
			deckName: "",
			commanderId: null,
		});
	};

	const handleSaveEdit = async () => {
		try {
			const jwt = localStorage.getItem("jwt");
			console.log("EDIT DECK: ", editDeck);
			const response = await fetch(`http://localhost:8080/api/deck`, {
				method: "PUT",
				headers: {
					Authorization: `Bearer ${jwt}`,
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					id: editDeck.deckId,
					player: { id: editDeck.playerId },
					commander: { id: editDeck.commanderId }, // Assuming commander is an object with an id property
					name: editDeck.deckName,
				}),
			});

			if (!response.ok) {
				console.error(
					`Failed to edit deck with ID: ${editDeck.deckId}`
				);
				return;
			}

			// Update state to reflect edited deck
			setDecks((prevDecks) => {
				const updatedDecks = { ...prevDecks };
				updatedDecks[editDeck.playerId] = updatedDecks[
					editDeck.playerId
				].map((deck) =>
					deck.id === editDeck.deckId
						? { ...deck, name: editDeck.deckName }
						: deck
				);
				return updatedDecks;
			});

			// Clear the editDeck state
			handleCancelEdit();
		} catch (error) {
			console.error("Error editing deck: ", error.message);
		}
	};

	const renderDeckItem = (deck, playerId) => (
		<ListGroup.Item
			key={deck.id}
			className="d-flex justify-content-between align-items-center"
		>
			<div>
				{editDeck.deckId === deck.id ? (
					<Form.Control
						type="text"
						value={editDeck.deckName}
						onChange={(e) =>
							setEditDeck({
								...editDeck,
								deckName: e.target.value,
							})
						}
					/>
				) : (
					deck.name
				)}
			</div>
			<div>
				{editDeck.deckId === deck.id ? (
					<>
						<Button variant="success" onClick={handleSaveEdit}>
							Save
						</Button>
						<Button variant="secondary" onClick={handleCancelEdit}>
							Cancel
						</Button>
					</>
				) : (
					<>
						<Button
							variant="secondary"
							onClick={() =>
								handleEdit(
									playerId,
									deck.id,
									deck.name,
									deck.commanderId
								)
							}
							disabled
						>
							Edit
						</Button>
						<Button
							variant="danger"
							onClick={() => handleDeleteDeck(deck.id)}
						>
							Delete
						</Button>
					</>
				)}
			</div>
		</ListGroup.Item>
	);

	return (
		<Container className="mt-5">
			{players.map((player) => (
				<Row key={player.id}>
					<Col>
						<Accordion defaultActiveKey="0" className="mb-3">
							<Accordion.Item eventKey="0">
								<Accordion.Header>
									{player.name}'s Decks
								</Accordion.Header>
								<Accordion.Body className="p-0">
									<ListGroup variant="flush">
										{decks[player.id]?.map((deck) =>
											renderDeckItem(deck, player.id)
										)}
									</ListGroup>
								</Accordion.Body>
							</Accordion.Item>
						</Accordion>
					</Col>
				</Row>
			))}
		</Container>
	);
};

export default DeckViewer;
