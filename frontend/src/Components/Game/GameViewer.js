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

const GameViewer = () => {
	const [games, setGames] = useState([]);

	useEffect(() => {
		fetchGames();
	}, []);

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

	const formatDateString = (dateString) => {
		const options = { year: "numeric", month: "long", day: "numeric" };
		const formattedDate = new Date(dateString).toLocaleDateString(
			undefined,
			options
		);
		return formattedDate;
	};

	const organizeGamesByDate = () => {
		const gamesByDate = {};
		games.forEach((game) => {
			const date = game.date;
			if (!gamesByDate[date]) {
				gamesByDate[date] = [];
			}
			gamesByDate[date].push(game);
		});
		return gamesByDate;
	};

	return (
		<Container className="mt-5">
			<Accordion defaultActiveKey="0">
				{Object.entries(organizeGamesByDate()).map(
					([date, gamesForDate], index) => (
						<Accordion.Item key={index} eventKey={index.toString()}>
							<Accordion.Header>
								{formatDateString(date)}
							</Accordion.Header>
							<Accordion.Body>
								<ListGroup variant="flush">
									{gamesForDate.map((game) => (
										<ListGroup.Item key={game.id}>
											<h5>Game {game.gameNumber}</h5>
											<p>
												Winner:{" "}
												{game.winner.commander.name}
											</p>
											{game.deck1 && (
												<p
													style={{
														marginLeft: "20px",
													}}
												>
													{game.deck1.commander.name}
												</p>
											)}
											{game.deck2 && (
												<p
													style={{
														marginLeft: "20px",
													}}
												>
													{game.deck2.commander.name}
												</p>
											)}
											{game.deck3 && (
												<p
													style={{
														marginLeft: "20px",
													}}
												>
													{game.deck3.commander.name}
												</p>
											)}
											{game.deck4 && (
												<p
													style={{
														marginLeft: "20px",
													}}
												>
													{game.deck4.commander.name}
												</p>
											)}
										</ListGroup.Item>
									))}
								</ListGroup>
							</Accordion.Body>
						</Accordion.Item>
					)
				)}
			</Accordion>
		</Container>
	);
};

export default GameViewer;
