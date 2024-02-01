import React, { useState, useEffect } from "react";
import {
	Button,
	Card,
	Col,
	Container,
	Form,
	ListGroup,
	Row,
} from "react-bootstrap";

const PlayerViewer = ({ playersUpdated }) => {
	const [players, setPlayers] = useState([]);
	const [editPlayerId, setEditPlayerId] = useState(null);
	const [editedPlayerName, setEditedPlayerName] = useState("");

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
			}
			const data = await response.json();
			setPlayers(data);
		} catch (error) {
			console.error("Error fetching data: ", error.message);
		}
	};

	useEffect(() => {
		fetchPlayers();
	}, [playersUpdated]);

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
		<Container className="mt-5">
			<Card>
				<Card.Header className="text-center">Player Viewer</Card.Header>
				<ListGroup variant="flush">
					{players.map((player) => (
						<ListGroup.Item
							key={player.id}
							className="d-flex justify-content-between align-items-center mb-2"
						>
							{editPlayerId === player.id ? (
								<Form.Control
									type="text"
									value={editedPlayerName}
									onChange={(e) =>
										setEditedPlayerName(e.target.value)
									}
								/>
							) : (
								player.name
							)}
							<div>
								{editPlayerId === player.id ? (
									<div className="d-flex">
										<Button
											variant="secondary"
											onClick={handleCancelEdit}
										>
											Cancel
										</Button>
										<Button
											variant="success"
											onClick={handleSaveEdit}
										>
											Save
										</Button>
									</div>
								) : (
									<>
										<Button
											variant="primary"
											onClick={() =>
												handleEdit(player.id)
											}
										>
											Edit
										</Button>{" "}
										<Button
											variant="danger"
											onClick={() =>
												handleDelete(player.id)
											}
										>
											Delete
										</Button>
									</>
								)}
							</div>
						</ListGroup.Item>
					))}
				</ListGroup>
			</Card>
		</Container>
	);
};

export default PlayerViewer;
