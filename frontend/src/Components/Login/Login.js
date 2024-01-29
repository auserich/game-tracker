import { Form, Button, Container, Card, Col, Row } from "react-bootstrap";
import React, { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";

const Login = () => {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");

	const navigate = useNavigate();

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			const response = await fetch("http://localhost:8080/authenticate", {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					username: username,
					password: password,
				}),
			});

			if (response.ok) {
				const data = await response.json();
				const { jwt } = data;
				localStorage.setItem("jwtToken", jwt);
				console.log("User authenticated successfully");
				navigate("/dashboard");
			} else {
				console.error("Failed to authenticate user");
			}
		} catch (error) {
			console.error("Error during user authentication", error);
		}
	};

	return (
		<Container className="mt-5">
			<Row className="justify-content-center">
				<Col md={6}>
					<Card>
						<Card.Body>
							<Form onSubmit={handleSubmit}>
								<h3 className="text-center mb-4">Log In</h3>

								<Form.Group
									className="mb-3"
									controlId="formBasicEmail"
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
									variant="primary"
									type="submit"
									className="w-100"
								>
									Submit
								</Button>

								<div className="text-center mt-2">
									Don't have an account?{" "}
									<Link to="/signup">Sign Up</Link>
								</div>
							</Form>
						</Card.Body>
					</Card>
				</Col>
			</Row>
		</Container>
	);
};

export default Login;
