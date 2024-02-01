import React, { useState, useEffect } from "react";
import { Button, Card, Col, Container, ListGroup, Row } from "react-bootstrap";

const CommanderViewer = ({ commandersUpdated }) => {
	const [commanders, setCommanders] = useState([]);

	useEffect(() => {
		fetchCommanders();
	}, []);

	useEffect(() => {
		fetchCommanders();
	}, [commandersUpdated]);

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
			console.error("Error fetching data: ", error.message);
		}
	};

	const handleDeleteCommander = async (commanderId) => {
		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/commander/${commanderId}`,
				{
					method: "DELETE",
					headers: {
						Authorization: `Bearer ${jwt}`,
						"Content-Type": "application/json",
					},
				}
			);

			if (!response.ok) {
				console.error(
					`Failed to delete commander with ID: ${commanderId}`
				);
				return;
			}

			setCommanders((prevCommanders) =>
				prevCommanders.filter(
					(commander) => commander.id !== commanderId
				)
			);
		} catch (error) {
			console.error("Error deleting commander: ", error.message);
		}
	};

	return (
		<Container className="mt-5">
			<Row>
				<Col>
					<Card>
						<Card.Header className="text-center">
							Commander Viewer
						</Card.Header>
						<ListGroup variant="flush">
							{commanders.map((commander) => (
								<ListGroup.Item
									key={commander.id}
									className="d-flex justify-content-between align-items-center"
								>
									{commander.name}
									<Button
										variant="danger"
										className="ml-2"
										onClick={() =>
											handleDeleteCommander(commander.id)
										}
									>
										Delete
									</Button>
								</ListGroup.Item>
							))}
						</ListGroup>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default CommanderViewer;
