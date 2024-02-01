import React, { useState } from "react";
import {
	Button,
	Card,
	Col,
	Container,
	Form,
	ListGroup,
	Row,
} from "react-bootstrap";

const CommanderAdder = ({ onCommanderAdded }) => {
	const [commanderName, setCommanderName] = useState("");
	const [searchResults, setSearchResults] = useState([]);

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			const jwt = localStorage.getItem("jwt");

			const response = await fetch(
				`http://localhost:8080/api/commander/search/${commanderName}`,
				{
					method: "GET",
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${jwt}`,
					},
				}
			);

			if (response.ok && typeof onCommanderAdded === "function") {
				const data = await response.json();
				// Using a Set to ensure uniqueness based on card name
				const uniqueResultsSet = new Set(
					data.cards.map((card) => card.name)
				);
				// Converting Set back to an array for rendering
				const uniqueResults = [...uniqueResultsSet].map((name) =>
					data.cards.find((card) => card.name === name)
				);
				console.log(uniqueResults);
				setSearchResults(uniqueResults);
			} else {
				console.error("Failed to fetch commanders");
			}
		} catch (error) {
			console.error("Error fetching commanders", error);
		}
	};

	const handleAddCommander = async (result) => {
		try {
			const jwt = localStorage.getItem("jwt");
			const colorIdentityString = result.colorIdentity.join(""); // Convert char array to string

			const response = await fetch(
				"http://localhost:8080/api/commander",
				{
					method: "POST",
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${jwt}`,
					},
					body: JSON.stringify({
						name: result.name,
						colorIdentity: colorIdentityString,
					}),
				}
			);

			if (response.ok) {
				console.log("Commander added successfully");
				onCommanderAdded();
			} else {
				console.error("Failed to add commander");
			}
		} catch (error) {
			console.error("Error adding commander", error);
		}
	};

	return (
		<Container className="mt-5">
			<Row>
				<Col>
					<Card>
						<Card.Header className="text-center">
							Commander Adder
						</Card.Header>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<Row className="mb-2 mt-2">
									<Col>
										<Form.Control
											placeholder="Enter commander name"
											value={commanderName}
											onChange={(e) =>
												setCommanderName(e.target.value)
											}
										/>
									</Col>
									<Col>
										<Button type="submit">Search</Button>
									</Col>
								</Row>
							</Form>
							<ListGroup variant="flush">
								{searchResults.map((result) => (
									<ListGroup.Item
										key={result.id}
										className="d-flex justify-content-between align-items-center"
									>
										{result.name}
										<Button
											variant="primary"
											className="ml-2"
											onClick={() =>
												handleAddCommander(result)
											}
										>
											Add
										</Button>
									</ListGroup.Item>
								))}
							</ListGroup>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default CommanderAdder;
