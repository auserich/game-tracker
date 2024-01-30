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

const CommanderAdder = () => {
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

			if (response.ok) {
				const data = await response.json();
				// Using a Set to ensure uniqueness based on card name
				const uniqueResultsSet = new Set(
					data.cards.map((card) => card.name)
				);
				// Converting Set back to an array for rendering
				const uniqueResults = [...uniqueResultsSet].map((name) =>
					data.cards.find((card) => card.name === name)
				);

				setSearchResults(uniqueResults);
			} else {
				console.error("Failed to fetch commanders");
			}
		} catch (error) {
			console.error("Error fetching commanders", error);
		}
	};

	return (
		<Container>
			<Row>
				<Col>
					<Card>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<Row className="mb-3">
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
							<ListGroup>
								{searchResults.map((result) => (
									<ListGroup.Item key={result.id}>
										{result.name}
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
