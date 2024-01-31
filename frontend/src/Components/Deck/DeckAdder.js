import React from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";

const DeckAdder = () => {
	return (
		<Container>
			<Row>
				<Col>
					<Card>
						<Card.Title>Deck Adder</Card.Title>
						<Card.Body>
							<Form>
								<Row>
									<Col>Player Name</Col>
								</Row>
								<Row>
									<Col>Commander Name</Col>
								</Row>
								<Row>
									<Col>Deck Name</Col>
								</Row>
								<Row>
									<Button>Submit</Button>
								</Row>
							</Form>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default DeckAdder;
