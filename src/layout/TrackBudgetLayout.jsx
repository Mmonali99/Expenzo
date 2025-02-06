import React from 'react';
import { Container, Row, Col, Form, Table, Dropdown, Button } from 'react-bootstrap';
import { FaFilter, FaEdit, FaTrash } from 'react-icons/fa';

const TrackBudgetLayout = ({
  budgetData,
  handleEdit,
  handleDelete,
  period,
  setPeriod,
  selectedMonth,
  setSelectedMonth,
  selectedYear,
  setSelectedYear,
  weekOptions,
  selectedWeek,
  setSelectedWeek,
}) => {
  return (
    <Container>
      <Row className="align-items-center header-row">
        <Col md={3}>
          {period !== '' && (
            <Form.Group controlId="monthSelect">
              <Form.Label>Select Month</Form.Label>
              <Form.Control
                as="select"
                value={selectedMonth}
                onChange={(e) => setSelectedMonth(parseInt(e.target.value))}
              >
                {Array.from({ length: 12 }, (_, index) => (
                  <option key={index + 1} value={index + 1}>
                    {new Date(0, index).toLocaleString('en', { month: 'long' })}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          )}
        </Col>
        {period === 'Weekly' && (
          <Col md={3}>
            <Form.Group controlId="weekSelect">
              <Form.Label>Select Week</Form.Label>
              <Form.Control
                as="select"
                value={selectedWeek}
                onChange={(e) => setSelectedWeek(e.target.value)}
              >
                {weekOptions.map((week) => (
                  <option key={week.value} value={week.value}>
                    {week.label}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
          </Col>
        )}
        <Col>
          <h2 className="text-center">Track Budget</h2>
        </Col>
        <Col className="text-end filter-col">
          <Dropdown onSelect={(e) => setPeriod(e)} defaultValue={period}>
            <Dropdown.Toggle variant="link" id="dropdown-basic" className="filter-icon small-filter-button">
              <FaFilter />
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item eventKey="Weekly">Weekly</Dropdown.Item>
              <Dropdown.Item eventKey="Monthly">Monthly</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Col>
      </Row>
      <Row>
        <Col>
          <Table bordered className="mt-3">
            <thead>
              <tr>
                <th>Categories</th>
                <th>Spent</th>
                <th>Remaining</th>
                <th>Total</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {budgetData.length > 0 ? (
                budgetData.map((data, index) => (
                  <tr key={index}>
                    <td>{data.category}</td>
                    <td>{data.weeklyAmount || 'N/A'}</td>
                    <td>{data.monthlyAmount || 'N/A'}</td>
                    <td>{data.totalAmount || 'N/A'}</td>
                    <td>
                      <Button variant="warning" onClick={() => handleEdit(index)} className="me-2">
                        <FaEdit />
                      </Button>
                      <Button variant="danger" onClick={() => handleDelete(index)}>
                        <FaTrash />
                      </Button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5">No data available</td>
                </tr>
              )}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Container>
  );
};

export default TrackBudgetLayout;
