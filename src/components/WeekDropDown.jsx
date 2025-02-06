import React, { useState, useEffect } from 'react';
import { Form, Container, Row, Col } from 'react-bootstrap';
import axios from 'axios';

const WeekDropDown = ({ year, month }) => {
  const [weeks, setWeeks] = useState([]);
  const [selectedWeek, setSelectedWeek] = useState('');

  useEffect(() => {
    axios.get(`/api/weeks?year=${year}&month=${month}`)
      .then(response => {
        setWeeks(response.data);
      })
      .catch(error => {
        console.error("There was an error fetching the week details!", error);
      });
  }, [year, month]);

  const handleWeekChange = (e) => {
    setSelectedWeek(e.target.value);
    console.log(`Selected: ${e.target.value}`);
  };

  return (
    <Container>
      <Row className="mb-3">
        <Col>
          <Form.Group controlId="weekDropDown">
            <Form.Label>Select Week</Form.Label>
            <Form.Control as="select" value={selectedWeek} onChange={handleWeekChange}>
              <option value="">-- Select Week --</option>
              {weeks.map((week, index) => (
                <option key={index} value={week.weekNumber}>
                  {week.weekNumber}: {week.startDay} - {week.endDay} ({week.daysInWeek} days)
                </option>
              ))}
            </Form.Control>
          </Form.Group>
        </Col>
      </Row>
    </Container>
  );
};

export default WeekDropDown;
