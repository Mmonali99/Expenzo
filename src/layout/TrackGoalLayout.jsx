import React from 'react';
import { Container, Row, Col, ProgressBar, Accordion, Card } from 'react-bootstrap';
import { FaChevronDown, FaChevronUp, FaEdit, FaTrash } from 'react-icons/fa';

const TrackGoalLayout = ({ goals, activeKey, toggleDetails, handleEdit, handleDelete }) => {
  return (
    <Container>
      <Row>
        <Col>
          <h2>Track Goal</h2>
        </Col>
      </Row>
      <Row>
        <Col>
          <h4>All Goals</h4>
          <Accordion activeKey={activeKey}>
            {goals.map((goal) => (
              <Card key={goal.id}>
                <Card.Header>
                  <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                    <h5 style={{ marginRight: '10px', flexGrow: 1 }}>{goal.title}</h5>
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                      <ProgressBar
                        now={goal.progress}
                        style={{ width: '100px', marginRight: '10px' }}
                      />
                      <span style={{ marginRight: '10px' }}>{goal.progress}%</span>
                      <button
                        onClick={() => toggleDetails(goal.id)}
                        style={{ background: 'none', border: 'none', cursor: 'pointer', padding: '0' }}
                      >
                        {activeKey === goal.id ? <FaChevronUp /> : <FaChevronDown />}
                      </button>
                    </div>
                  </div>
                </Card.Header>
                <Accordion.Collapse eventKey={goal.id}>
                  <Card.Body>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                      <div>
                        <p><strong>Title:</strong> {goal.title}</p>
                        <p><strong>Description:</strong> {goal.description}</p>
                        <p><strong>Goal Type:</strong> {goal.goalType}</p>
                        <p><strong>Amount:</strong> ${goal.amount}</p>
                        <p><strong>Target Date:</strong> {goal.targetDate}</p>
                      </div>
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        <button
                          onClick={() => handleEdit(goal)}
                          style={{ background: 'none', border: 'none', cursor: 'pointer', color: 'orange', marginRight: '10px', fontSize: '1.2rem' }}
                        >
                          <FaEdit />
                        </button>
                        <button
                          onClick={() => handleDelete(goal.id)}
                          style={{ background: 'none', border: 'none', cursor: 'pointer', color: 'red', fontSize: '1.2rem' }}
                        >
                          <FaTrash />
                        </button>
                      </div>
                    </div>
                  </Card.Body>
                </Accordion.Collapse>
              </Card>
            ))}
          </Accordion>
        </Col>
      </Row>
    </Container>
  );
};

export default TrackGoalLayout;
