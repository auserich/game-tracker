import React, { useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";

const GameAdder = () => {
	const [winningPlayer, setWinningPlayer] = useState(null);

	const handleCheckboxChange = (playerId) => {
		setWinningPlayer((prevWinner) =>
			prevWinner === playerId ? null : playerId
		);
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
							<Form>
								<Row>
									<Col md={9} className="mb-3">
										<Form.Label>Date</Form.Label>
										<Form.Control type="date" />
									</Col>
									<Col md={3} className="mb-3">
										<Form.Label>Game Number</Form.Label>
										<Form.Control type="number" />
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
													winningPlayer === playerId
												}
											/>
										</Col>
										<Col md={12}>
											<Form.Control />
										</Col>
									</Row>
								))}
								<Button>Submit</Button>
							</Form>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default GameAdder;
