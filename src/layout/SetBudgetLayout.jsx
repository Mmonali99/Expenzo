import React, { useState } from 'react';
import { Form, Button, Row, Col, Container, Table, Alert } from 'react-bootstrap';
import { FaCheck } from 'react-icons/fa';

const SetBudgetLayout = ({
  totalBudget,
  categories,
  newCategory,
  handleTotalBudgetChange,
  handleInputChange,
  handleAddCategory,
  setNewCategory,
  overBudget,
  totalMonthlyAmount,
  handleSubmit,
  month,
  year,
  handleMonthChange,
  handleYearChange,
  selectedWeek,
  setSelectedWeek,
  weekOptions
}) => {
  const isOtherFieldsDisabled = !totalBudget || totalBudget <= 0;

  // Handle change for monthly amount
  const handleAmountChange = (index, value) => {
    const cleanedValue = value.replace(/^0+/, ''); // Remove leading zeroes
    handleInputChange(index, 'monthlyAmount', cleanedValue);
  };

  // Adjust the weekly amount display to be a whole number
  const handleWeeklyAmountDisplay = (amount) => {
    return parseInt(amount).toString(); // Always convert to whole number
  };

  // Adjust the monthly percentage display to be a whole number
  const handleMonthlyPercentageDisplay = (percent) => {
    return `${parseInt(percent)}%`; // Always convert to integer and add %
  };

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Col>
            <h2 className="text-center">Set Budget</h2>
          </Col>
        </Row>
        <Row className="mb-3">
          <Col sm={4}>
            <Form.Label>Total Budget Amount:</Form.Label>
            <Form.Control
              type="number"
              name="totalBudget"
              value={totalBudget}
              onChange={handleTotalBudgetChange}
              placeholder="Enter Amount"
            />
          </Col>
          <Col sm={4}>
            <Form.Label>Month:</Form.Label>
            <Form.Control
              as="select"
              name="month"
              value={month}
              onChange={handleMonthChange}
              disabled={isOtherFieldsDisabled}
            >
              {Array.from({ length: 12 }, (_, i) => (
                <option key={i} value={i + 1}>
                  {new Date(0, i).toLocaleString('default', { month: 'long' })}
                </option>
              ))}
            </Form.Control>
          </Col>
          <Col sm={4}>
            <Form.Label>Year:</Form.Label>
            <Form.Control
              type="number"
              name="year"
              value={year}
              onChange={handleYearChange}
              disabled={isOtherFieldsDisabled}
              placeholder="Enter Year"
            />
          </Col>
        </Row>
        {overBudget && (
          <Row className="mb-3">
            <Col>
              <Alert variant="danger">More than your budget</Alert>
            </Col>
          </Row>
        )}
        <Row>
          <Col>
            <Table bordered>
              <thead>
                <tr>
                  <th>Category</th>
                  <th>Weekly</th>
                  <th>Monthly</th>
                  <th>Actions</th>
                </tr>
                <tr>
                  <td></td>
                  <td>
                    <div className="d-flex align-items-center">
                      <Form.Control
                        as="select"
                        value={selectedWeek}
                        onChange={(e) => setSelectedWeek(parseInt(e.target.value))}
                        disabled={isOtherFieldsDisabled}
                        className="me-2"
                      >
                        <option value="">Select Week</option>
                        {weekOptions.map((week) => (
                          <option key={week.value} value={week.value}>
                            {week.label}
                          </option>
                        ))}
                      </Form.Control>
                      <i className="bi bi-chevron-down"></i>
                    </div>
                  </td>
                  <td></td>
                  <td></td>
                </tr>
              </thead>
              <tbody>
                {categories.map((category, index) => (
                  <tr key={index}>
                    <td>{category.name}</td>
                    <td>
                      <Row>
                        <Col>
                          <Form.Control
                            type="text"
                            placeholder="%"
                            value={selectedWeek && category.weeklyPercent[selectedWeek - 1] ? `${parseInt(category.weeklyPercent[selectedWeek - 1])}%` : '0%'}
                            disabled
                          />
                        </Col>
                        <Col>
                          <Form.Control
                            type="text"
                            placeholder="Amt"
                            value={selectedWeek && category.weeklyAmounts[selectedWeek - 1] ? handleWeeklyAmountDisplay(category.weeklyAmounts[selectedWeek - 1]) : '0'} // Show 0 instead of 0.00
                            disabled
                          />
                        </Col>
                      </Row>
                    </td>
                    <td>
                      <Row>
                        <Col>
                          <Form.Control
                            type="text"
                            placeholder="%"
                            value={handleMonthlyPercentageDisplay(category.monthlyPercent)} // Show integer percentage with %
                            disabled // Disable the monthly percentage field
                          />
                        </Col>
                        <Col>
                          <Form.Control
                            type="text"
                            placeholder="Amt"
                            value={category.monthlyAmount !== '' ? `${category.monthlyAmount}` : '0'}
                            onChange={(e) => handleAmountChange(index, e.target.value)}
                          />
                        </Col>
                      </Row>
                    </td>
                    <td>
                      <Button variant="success" aria-label="Set" disabled={isOtherFieldsDisabled}><FaCheck /></Button>
                    </td>
                  </tr>
                ))}
                <tr>
                  <td><strong>Total</strong></td>
                  <td></td>
                  <td>
                    <Form.Control
                      type="number"
                      value={totalMonthlyAmount}
                      readOnly
                    />
                  </td>
                  <td></td>
                </tr>
              </tbody>
            </Table>
          </Col>
        </Row>
        <Row className="mt-3">
          <Col sm={4}>
            <Form.Label>Add Category:</Form.Label>
          </Col>
          <Col sm={6}>
            <Form.Control
              type="text"
              value={newCategory}
              onChange={(e) => setNewCategory(e.target.value)}
            />
          </Col>
          <Col sm={2}>
            <Button type="button" onClick={handleAddCategory}>OK</Button>
          </Col>
        </Row>
        <Row className="mt-3">
          <Col className="text-center">
            <Button type="submit" variant="primary" disabled={isOtherFieldsDisabled}>Save Budget</Button>
          </Col>
        </Row>
      </Form>
    </Container>
  );
};

export default SetBudgetLayout;
