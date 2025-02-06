import React from 'react';
import { Form, Button, Row, Col } from 'react-bootstrap';

const SignInLayout = ({
  username, password, handleUsernameChange, handlePasswordChange, handleSubmit
}) => (
  <Form onSubmit={handleSubmit} className="custom-form mt-4">
    <Row className="mb-3">
      <Col>
        <Form.Label>Username</Form.Label>
        <Form.Control type="text" value={username} onChange={handleUsernameChange} required />
      </Col>
    </Row>
    <Row className="mb-3">
      <Col>
        <Form.Label>Password</Form.Label>
        <Form.Control type="password" value={password} onChange={handlePasswordChange} required />
      </Col>
    </Row>
    <Button type="submit" variant="primary" className="w-100">Sign In</Button>
  </Form>
);

export default SignInLayout;
