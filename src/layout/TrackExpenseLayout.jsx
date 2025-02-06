import React from 'react';
import { Container, Row, Col, Form, Table, Dropdown, Button } from 'react-bootstrap';
import { FaFilter, FaEdit, FaTrash } from 'react-icons/fa';
import { BsChevronDown, BsChevronUp } from 'react-icons/bs';

const TrackExpenseLayout = ({
  expenses, filterType, filterValue, categories, expandedExpenseId,
  handleSelect, handleFilterValueChange, toggleExpand, handleMenuClose, anchorEl,
  handleEdit, handleDelete
}) => {
  return (
    <Container>
      <Row className="align-items-center header-row">
        <Col>
          <h2 className="main-heading text-center">Track Expenses</h2>
        </Col>
        <Col className="text-end filter-col">
          <Dropdown onSelect={handleSelect}>
            <Dropdown.Toggle variant="link" id="dropdown-basic" className="filter-icon small-filter-button">
              <FaFilter />
            </Dropdown.Toggle>
            <Dropdown.Menu>
              <Dropdown.Item eventKey="dateRange">Date Range</Dropdown.Item>
              <Dropdown.Item eventKey="category">Category</Dropdown.Item>
              <Dropdown.Item eventKey="amountRange">Amount Range</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </Col>
      </Row>
      <Row className="mt-3">
        <Col md={{ span: 6, offset: 3 }}>
          {filterType === 'dateRange' && (
            <Form>
              <Row className="date-range-row">
                <Col>
                  <Form.Group controlId="formDateRangeFrom">
                    <Form.Label>From</Form.Label>
                    <Form.Control
                      type="date"
                      placeholder="From date"
                      name="from"
                      value={filterValue.from}
                      onChange={handleFilterValueChange}
                    />
                  </Form.Group>
                </Col>
                <Col>
                  <Form.Group controlId="formDateRangeTo">
                    <Form.Label>To</Form.Label>
                    <Form.Control
                      type="date"
                      placeholder="To date"
                      name="to"
                      value={filterValue.to}
                      onChange={handleFilterValueChange}
                    />
                  </Form.Group>
                </Col>
              </Row>
            </Form>
          )}
          {filterType === 'category' && (
            <Form.Group controlId="formCategoryFilter" className="dropdown-wrapper">
              <Form.Label>Category</Form.Label>
              <div className="custom-select-wrapper">
                <Form.Control as="select" value={filterValue.category} onChange={handleFilterValueChange} name="category" className="custom-select">
                  <option value="">Select Category</option>
                  {categories.map((category, index) => (
                    <option key={index} value={category}>{category}</option>
                  ))}
                </Form.Control>
              </div>
            </Form.Group>
          )}
          {filterType === 'amountRange' && (
            <Form>
              <Row className="amount-range-row">
                <Col>
                  <Form.Group controlId="formMinAmount">
                    <Form.Label>Min Amount</Form.Label>
                    <Form.Control
                      type="number"
                      placeholder="Min amount"
                      name="min"
                      value={filterValue.min}
                      onChange={handleFilterValueChange}
                    />
                  </Form.Group>
                </Col>
                <Col>
                  <Form.Group controlId="formMaxAmount">
                    <Form.Label>Max Amount</Form.Label>
                    <Form.Control
                      type="number"
                      placeholder="Max amount"
                      name="max"
                      value={filterValue.max}
                      onChange={handleFilterValueChange}
                    />
                  </Form.Group>
                </Col>
              </Row>
            </Form>
          )}
        </Col>
      </Row>
      <Row>
        <Col>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Amount</th>
                <th>Purchase Location</th>
                <th>More</th>
              </tr>
            </thead>
            <tbody>
              {expenses.map(expense => (
                <React.Fragment key={expense.id}>
                  <tr>
                    <td>{expense.date}</td>
                    <td>{expense.time}</td>
                    <td>${expense.amount}</td>
                    <td>{expense.purchaseLocation}</td>
                    <td>
                      <Button variant="link" onClick={() => toggleExpand(expense.id)} className="p-0 m-0">
                        {expandedExpenseId === expense.id ? <BsChevronUp /> : <BsChevronDown />}
                      </Button>
                    </td>
                  </tr>
                  {expandedExpenseId === expense.id && (
                    <tr>
                      <td colSpan="5">
                        <div className="d-flex justify-content-between align-items-center">
                          <div>
                            <p>{expense.description}</p>
                            {expense.items.length > 0 && (
                              <Table striped bordered hover size="sm">
                                <thead>
                                  <tr>
                                    <th>Item Name</th>
                                    <th>Category</th>
                                    <th>Amount</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  {expense.items.map((item, index) => (
                                    <tr key={index}>
                                      <td>{item.itemName}</td>
                                      <td>{item.itemCategory}</td>
                                      <td>${item.itemAmount}</td>
                                    </tr>
                                  ))}
                                </tbody>
                              </Table>
                            )}
                          </div>
                          <div>
                            <Button variant="warning" className="mr-2" onClick={() => handleEdit(expense.id)}><FaEdit /></Button>
                            <Button variant="danger" onClick={() => handleDelete(expense.id)}><FaTrash /></Button>
                          </div>
                        </div>
                      </td>
                    </tr>
                  )}
                </React.Fragment>
              ))}
            </tbody>
          </Table>
        </Col>
      </Row>
    </Container>
  );
};

export default TrackExpenseLayout;
