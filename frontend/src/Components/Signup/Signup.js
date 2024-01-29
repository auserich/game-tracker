import React, { useState } from "react";
import { Button, Card, Col, Container, Form, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

const Signup = () => {
	const [email, setEmail] = useState("");
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			const response = await fetch("http://localhost:8080/api/user", {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					username: username,
					password: password,
					role: "ROLE_USER",
					email: email,
				}),
			});

			if (response.ok) {
				console.log("User registered successfully");
			} else {
				console.error("Failed to register user");
			}
		} catch (error) {
			console.error("Error during user registration", error);
		}
	};

	return (
		<Container className="mt-5">
			<Row className="justify-content-center">
				<Col md={6}>
					<Card>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<h3 className="text-center mb-4">Sign Up</h3>

								<Form.Group
									className="mb-3"
									controlId="formBasicEmail"
								>
									<Form.Label>Email address</Form.Label>
									<Form.Control
										type="email"
										placeholder="Enter email"
										value={email}
										onChange={(e) =>
											setEmail(e.target.value)
										}
									/>
								</Form.Group>

								<Form.Group
									className="mb-3"
									controlId="formBasicUsername"
								>
									<Form.Label>Username</Form.Label>
									<Form.Control
										type="username"
										placeholder="Enter username"
										value={username}
										onChange={(e) =>
											setUsername(e.target.value)
										}
									/>
								</Form.Group>

								<Form.Group
									className="mb-3"
									controlId="formBasicPassword"
								>
									<Form.Label>Password</Form.Label>
									<Form.Control
										type="password"
										placeholder="Password"
										value={password}
										onChange={(e) =>
											setPassword(e.target.value)
										}
									/>
								</Form.Group>

								<Button
									block
									variant="primary"
									type="submit"
									className="w-100"
								>
									Submit
								</Button>

								<div className="text-center mt-2">
									Already registered?{" "}
									<Link to="/">Log In</Link>
								</div>
							</Form>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default Signup;
