// layout/MenuLayout.jsx
import React from 'react';
import { Container, Row, Col, Nav, Navbar, Offcanvas, ListGroup } from 'react-bootstrap';
import { Routes, Route, Link, Navigate } from 'react-router-dom';
import { FaBars, FaTachometerAlt } from 'react-icons/fa'; // Import the Dashboard icon
import AddExpense from '../components/AddExpense';
import TrackExpense from '../components/TrackExpense';
import SetGoal from '../components/SetGoal';
import TrackGoal from '../components/TrackGoal';
import SetBudget from '../components/SetBudget';
import TrackBudget from '../components/TrackBudget';
import Dashboard from '../components/Dashboard'; // Import the Dashboard component

const MenuLayout = ({ show, handleClose, handleShow, subMenu, toggleSubMenu }) => {
  return (
    <Container fluid>
      <Navbar bg="light" expand={false}>
        <Container fluid>
          <Navbar.Toggle aria-controls="offcanvasNavbar" onClick={handleShow}>
            <FaBars />
          </Navbar.Toggle>
          <Navbar.Brand className="mx-auto">
            <h1 className="brand-title">Expense Tracker</h1>
          </Navbar.Brand>
          <Navbar.Offcanvas
            id="offcanvasNavbar"
            aria-labelledby="offcanvasNavbarLabel"
            placement="start"
            show={show}
            onHide={handleClose}
          >
            <Offcanvas.Header closeButton>
              <Offcanvas.Title id="offcanvasNavbarLabel">Menu</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
              <Nav className="flex-column">
                <Nav.Item>
                  <Nav.Link as={Link} to="/menu/dashboard" onClick={handleClose}>
                    <FaTachometerAlt /> Dashboard
                  </Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link onClick={() => toggleSubMenu('expenses')}>Expenses</Nav.Link>
                  {subMenu.expenses && (
                    <ListGroup>
                      <ListGroup.Item as={Link} to="/menu/add-expense" onClick={handleClose}>Add Expense</ListGroup.Item>
                      <ListGroup.Item as={Link} to="/menu/track-expense" onClick={handleClose}>Track Expense</ListGroup.Item>
                    </ListGroup>
                  )}
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link onClick={() => toggleSubMenu('goals')}>Goals</Nav.Link>
                  {subMenu.goals && (
                    <ListGroup>
                      <ListGroup.Item as={Link} to="/menu/set-goal" onClick={handleClose}>Set Goal</ListGroup.Item>
                      <ListGroup.Item as={Link} to="/menu/track-goal" onClick={handleClose}>Track Goal</ListGroup.Item>
                    </ListGroup>
                  )}
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link onClick={() => toggleSubMenu('budget')}>Budget</Nav.Link>
                  {subMenu.budget && (
                    <ListGroup>
                      <ListGroup.Item as={Link} to="/menu/set-budget" onClick={handleClose}>Set Budget</ListGroup.Item>
                      <ListGroup.Item as={Link} to="/menu/track-budget" onClick={handleClose}>Track Budget</ListGroup.Item>
                    </ListGroup>
                  )}
                </Nav.Item>
              </Nav>
            </Offcanvas.Body>
          </Navbar.Offcanvas>
        </Container>
      </Navbar>
      <Row>
        <Col md={{ span: 10, offset: 1 }} className="content">
          <Routes>
            <Route path="dashboard" element={<Dashboard />} /> {/* Add the Dashboard route */}
            <Route path="add-expense" element={<AddExpense />} />
            <Route path="track-expense" element={<TrackExpense />} />
            <Route path="set-goal" element={<SetGoal />} />
            <Route path="track-goal" element={<TrackGoal />} />
            <Route path="set-budget" element={<SetBudget />} />
            <Route path="track-budget" element={<TrackBudget />} />
            <Route path="*" element={<Navigate to="dashboard" />} /> {/* Change default route to Dashboard */}
          </Routes>
        </Col>
      </Row>
    </Container>
  );
};

export default MenuLayout;
