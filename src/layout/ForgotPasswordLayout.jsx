import React from 'react';
import { Form, Button, Row, Col, Container } from 'react-bootstrap';

const ForgotPasswordLayout = ({
  username, password, confirmPassword,
  handleUsernameChange, handlePasswordChange, handleConfirmPasswordChange,
  handleSubmit
}) => {
  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ height: '100vh' }}>
      <div>
        <h2 className="text-center">Forgot Password</h2>
        <Form className="custom-form" onSubmit={handleSubmit}>
          <Row className="mb-3 align-items-center">
            <Col sm={3}>
              <Form.Label className="text-left">Username</Form.Label>
            </Col>
            <Col sm={9}>
              <Form.Control
                type="text"
                placeholder="Enter username"
                value={username}
                onChange={handleUsernameChange}
                required
              />
            </Col>
          </Row>
          <Row className="mb-3 align-items-center">
            <Col sm={3}>
              <Form.Label className="text-left">Password</Form.Label>
            </Col>
            <Col sm={9}>
              <Form.Control
                type="password"
                placeholder="Enter password"
                value={password}
                onChange={handlePasswordChange}
                required
              />
            </Col>
          </Row>
          <Row className="mb-3 align-items-center">
            <Col sm={3}>
              <Form.Label className="text-left">Confirm Password</Form.Label>
            </Col>
            <Col sm={9}>
              <Form.Control
                type="password"
                placeholder="Confirm password"
                value={confirmPassword}
                onChange={handleConfirmPasswordChange}
                required
              />
            </Col>
          </Row>
          <Row>
            <Col className="text-center">
              <Button variant="primary" type="submit">
                Submit
              </Button>
            </Col>
          </Row>
        </Form>
      </div>
    </Container>
  );
};

export default ForgotPasswordLayout;
