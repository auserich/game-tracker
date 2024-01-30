import React, { useState, useEffect } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";

const PlayerViewer = () => {
	const [players, setPlayers] = useState([]);
	const [editPlayerId, setEditPlayerId] = useState(null);
	const [editedPlayerName, setEditedPlayerName] = useState("");

	useEffect(() => {
		const fetchData = async () => {
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
				setPlayers(data);
			} catch (error) {
				console.error("Error fetching data: ", error.message);
			}
		};

		fetchData();
	}, []);

	const handleEdit = (playerId) => {
		setEditPlayerId(playerId);
		setEditedPlayerName("");
	};

	const handleCancelEdit = () => {
		setEditPlayerId(null);
	};

	const handleSaveEdit = async () => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(`http://localhost:8080/api/player`, {
				method: "PUT",
				headers: {
					Authorization: `Bearer ${jwt}`,
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					id: editPlayerId,
					name: editedPlayerName,
					user: players.find((player) => player.id === editPlayerId)
						?.user,
				}),
			});

			if (!response.ok) {
				console.error(`Failed to edit player with ID: ${editPlayerId}`);
				return;
			}

			// Update state to reflect edited player
			setPlayers((prevPlayers) =>
				prevPlayers.map((player) =>
					player.id === editPlayerId
						? { ...player, name: editedPlayerName }
						: player
				)
			);

			// Clear the player ID and hide the form
			setEditPlayerId(null);
		} catch (error) {
			console.error("Error editing player: ", error.message);
		}
	};

	const handleDelete = async (playerId) => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				// TODO: fix this RequestParam endpoint
				`http://localhost:8080/api/player/id?id=${playerId}`,
				{
					method: "DELETE",
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${jwt}`,
					},
				}
			);

			if (!response.ok) {
				console.error(`Failed to delete player with ID: ${playerId}`);
				return;
			}

			// Update state to reflect deleted player
			setPlayers((prevPlayers) =>
				prevPlayers.filter((player) => player.id !== playerId)
			);
		} catch (error) {
			console.error("Error deleting player: ", error.message);
		}
	};

	return (
		<Container>
			<Row>
				<Col>
					<Card>
						<Card.Title>Player Viewer</Card.Title>
						<Card.Body>
							{players.map((player) => (
								<Row key={player.id} className="mb-2">
									<Col>
										{editPlayerId === player.id ? (
											<Form.Control
												type="text"
												value={editedPlayerName}
												onChange={(e) =>
													setEditedPlayerName(
														e.target.value
													)
												}
											/>
										) : (
											player.name
										)}
									</Col>
									<Col>
										{editPlayerId === player.id ? (
											<>
												<Button
													variant="success"
													onClick={handleSaveEdit}
													className="mr-2"
												>
													Save
												</Button>
												<Button
													variant="secondary"
													onClick={handleCancelEdit}
												>
													Cancel
												</Button>
											</>
										) : (
											<Button
												variant="outline-primary"
												onClick={() =>
													handleEdit(player.id)
												}
											>
												Edit
											</Button>
										)}{" "}
										<Button
											variant="outline-danger"
											onClick={() =>
												handleDelete(player.id)
											}
										>
											Delete
										</Button>
									</Col>
								</Row>
							))}
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default PlayerViewer;
