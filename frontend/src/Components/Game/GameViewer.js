import React, { useState, useEffect } from "react";
import {
	Accordion,
	Button,
	Card,
	Col,
	Container,
	ListGroup,
	Row,
} from "react-bootstrap";
import moment from "moment";
import "moment-timezone";

const GameViewer = ({ gamesUpdated }) => {
	const [games, setGames] = useState([]);

	useEffect(() => {
		fetchGames();
	}, []);

	useEffect(() => {
		fetchGames();
	}, [gamesUpdated]);

	const fetchGames = async () => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch("http://localhost:8080/api/game", {
				method: "GET",
				headers: {
					Authorization: `Bearer ${jwt}`,
					"Content-Type": "application/json",
				},
			});

			if (!response.ok) {
				console.error("Failed to get commanders");
			}
			const data = await response.json();
			setGames(data);
		} catch (error) {
			console.error("Error fetching data: ", error.message);
		}
	};

	const formatDateString = (dateString, timeZone = "UTC") => {
		console.log("Input Date:", dateString);
		const formattedDate = moment
			.tz(dateString, timeZone)
			.format("MMMM D, YYYY");
		console.log("Formatted Date:", formattedDate);
		return formattedDate;
	};

	const organizeGamesByDate = () => {
		const gamesByDate = {};

		// Sort dates in descending order
		const sortedDates = games
			.map((game) => game.date)
			.sort((a, b) => new Date(b) - new Date(a));

		sortedDates.forEach((date) => {
			gamesByDate[date] = games.filter((game) => game.date === date);
		});

		return gamesByDate;
	};

	const handleDelete = async (gameId) => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/game/${gameId}`,
				{
					method: "DELETE",
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${jwt}`,
					},
				}
			);

			if (!response.ok) {
				console.error(`Failed to delete game with ID: ${gameId}`);
				return;
			}
			setGames((prevGames) =>
				prevGames.filter((game) => game.id !== gameId)
			);
		} catch (error) {
			console.error("Error deleting game: ", error.message);
		}
	};

	return (
		<Container className="mt-5 mb-5">
			<h5 className="text-center mb-3">Game History</h5>
			<Accordion defaultActiveKey={null}>
				{games.length === 0 ? (
					<Accordion.Item eventKey="noGames">
						<Accordion.Header>No Game History</Accordion.Header>
						<Accordion.Body>
							<p>No games have been recorded yet.</p>
						</Accordion.Body>
					</Accordion.Item>
				) : (
					Object.entries(organizeGamesByDate()).map(
						([date, gamesForDate], index) => (
							<Accordion.Item
								key={index}
								eventKey={index.toString()}
							>
								<Accordion.Header>
									{formatDateString(date)}
								</Accordion.Header>
								<Accordion.Body>
									<ListGroup variant="flush">
										{gamesForDate.map((game) => (
											<ListGroup.Item key={game.id}>
												<h5>Game {game.gameNumber}</h5>
												<p>
													Winner: {game.winner.name}
												</p>
												{game.deck1 && (
													<p
														style={{
															marginLeft: "20px",
														}}
													>
														{game.deck1.name}
													</p>
												)}
												{game.deck2 && (
													<p
														style={{
															marginLeft: "20px",
														}}
													>
														{game.deck2.name}
													</p>
												)}
												{game.deck3 && (
													<p
														style={{
															marginLeft: "20px",
														}}
													>
														{game.deck3.name}
													</p>
												)}
												{game.deck4 && (
													<p
														style={{
															marginLeft: "20px",
														}}
													>
														{game.deck4.name}
													</p>
												)}

												<Button
													variant="danger"
													className="ml-2"
													onClick={() =>
														handleDelete(game.id)
													}
												>
													Delete
												</Button>
											</ListGroup.Item>
										))}
									</ListGroup>
								</Accordion.Body>
							</Accordion.Item>
						)
					)
				)}
			</Accordion>
		</Container>
	);
};

export default GameViewer;
