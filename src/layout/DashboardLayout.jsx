import React from 'react';
import { Container, Row, Col, Tab, Nav } from 'react-bootstrap';
import SignInForm from '../components/SignIn';
import SignUpForm from '../components/SignUp';

const DashboardLayout = ({ defaultActiveKey }) => {
  return (
    <Container className="dashboard-container mt-5 p-4 bg-light rounded shadow-sm">
      <Row className="mb-4">
        <Col>
          <h1 className="dashboard-title text-center">Welcome To Expense Tracker</h1>
        </Col>
      </Row>

      <Row>
        <Col md={{ span: 6, offset: 3 }}>
          <Tab.Container defaultActiveKey={defaultActiveKey}>
            <Nav variant="pills" className="dashboard-nav justify-content-center">
              <Nav.Item>
                <Nav.Link eventKey="signin" className="px-4">Sign In</Nav.Link>
              </Nav.Item>
              <Nav.Item>
                <Nav.Link eventKey="signup" className="px-4">Sign Up</Nav.Link>
              </Nav.Item>
            </Nav>

            <Tab.Content className="mt-3">
              <Tab.Pane eventKey="signin">
                <SignInForm />
              </Tab.Pane>
              <Tab.Pane eventKey="signup">
                <SignUpForm />
              </Tab.Pane>
            </Tab.Content>
          </Tab.Container>
        </Col>
      </Row>
    </Container>
  );
};

export default DashboardLayout;
