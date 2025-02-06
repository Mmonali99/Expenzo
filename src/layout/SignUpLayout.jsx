import React from 'react';
import { Form, Button, Row, Col } from 'react-bootstrap';

const SignUpLayout = ({
  username, password, confirmPassword, phoneNumber,
  handleUsernameChange, handlePasswordChange, handleConfirmPasswordChange,
  handlePhoneNumberChange, handleSubmit, error
}) => {
  return (
    <Form className="custom-form" onSubmit={handleSubmit}>
      <Row className="mb-3 align-items-center">
        <Col sm={3}>
          <Form.Label className="text-left">
            Username
          </Form.Label>
        </Col>
        <Col sm={9}>
          <Form.Control
            type="text"
            placeholder="Enter username"
            value={username}
            onChange={handleUsernameChange}
          />
        </Col>
      </Row>
      
      <Row className="mb-3 align-items-center">
        <Col sm={3}>
          <Form.Label className="text-left">
            Password
          </Form.Label>
        </Col>
        <Col sm={9}>
          <Form.Control
            type="password"
            placeholder="Password"
            value={password}
            onChange={handlePasswordChange}
          />
        </Col>
      </Row>
      
      <Row className="mb-3 align-items-center">
        <Col sm={3}>
          <Form.Label className="text-left">
            Confirm Password
          </Form.Label>
        </Col>
        <Col sm={9}>
          <Form.Control
            type="password"
            placeholder="Confirm password"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
          />
        </Col>
      </Row>
      
      <Row className="mb-3 align-items-center">
        <Col sm={3}>
          <Form.Label className="text-left">
            Phone Number
          </Form.Label>
        </Col>
        <Col sm={9}>
          <Form.Control
            type="text"
            placeholder="Enter phone number"
            value={phoneNumber}
            onChange={handlePhoneNumberChange}
          />
        </Col>
      </Row>
      
      {error && (
        <Row className="mb-3">
          <Col>
            <div className="alert alert-danger">{error}</div>
          </Col>
        </Row>
      )}
      
      <Row>
        <Col className="text-center">
          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Col>
      </Row>
    </Form>
  );
};

export default SignUpLayout;
