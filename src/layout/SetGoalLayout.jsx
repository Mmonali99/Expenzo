import React from 'react';
import { FaChevronDown } from 'react-icons/fa';

const SetGoalLayout = ({
  goalTypes, selectedGoalType, newGoal, title, description, amount, targetDate,
  handleGoalTypeChange, handleNewGoalChange, handleAddGoal, handleTitleChange,
  handleDescriptionChange, handleAmountChange, handleTargetDateChange, handleSubmit
}) => {
  return (
    <div className="container">
      <h2 className="main-heading">Set Goal</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="title" className="form-label">Title:</label>
          <input type="text" className="form-control" id="title" value={title} onChange={handleTitleChange} placeholder="Enter title" />
        </div>
        <div className="form-group">
          <label htmlFor="description" className="form-label">Description:</label>
          <textarea className="form-control" id="description" rows={4} value={description} onChange={handleDescriptionChange} placeholder="Enter description"></textarea>
        </div>
        <div className="form-group">
          <label htmlFor="goalType" className="form-label">Goal Type:</label>
          <div className="d-flex align-items-center">
            <select className="form-control" id="goalType" value={selectedGoalType} onChange={handleGoalTypeChange} style={{ flex: 1 }}>
              {goalTypes.map((type, index) => (
                <option key={index} value={type}>{type}</option>
              ))}
            </select>
            <FaChevronDown style={{ marginLeft: '-1.5rem', pointerEvents: 'none' }} />
          </div>
        </div>
        {selectedGoalType === 'Other' && (
          <div className="form-group">
            <label htmlFor="newGoal" className="form-label">New Goal:</label>
            <input type="text" className="form-control" id="newGoal" value={newGoal} onChange={handleNewGoalChange} placeholder="Enter new goal" />
            <button type="button" className="btn btn-primary mt-2" onClick={handleAddGoal}>Add Goal</button>
          </div>
        )}
        <div className="form-group">
          <label htmlFor="amount" className="form-label">Amount:</label>
          <input type="number" className="form-control" id="amount" value={amount} onChange={handleAmountChange} placeholder="Enter amount" />
        </div>
        <div className="form-group">
          <label htmlFor="targetDate" className="form-label">Target Date:</label>
          <input type="date" className="form-control" id="targetDate" value={targetDate} onChange={handleTargetDateChange} />
        </div>
        <div className="button-group text-right">
          <button type="submit" className="btn btn-primary">Set</button>
        </div>
      </form>
    </div>
  );
};

export default SetGoalLayout;
