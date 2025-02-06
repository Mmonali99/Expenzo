import React from 'react';
import { FaChevronDown, FaPlus } from 'react-icons/fa';

const AddExpenseLayout = ({
  date, time, amount, description, purchaseLocation, items,
  handleDateChange, handleTimeChange, handleAmountChange,
  handleDescriptionChange, handlePurchaseLocationChange, handleItemChange,
  handleAddRow, handleSubmit, handleReset
}) => {
  return (
    <div className="container">
      <h2>Add Expense</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="amount" className="form-label">Amount:</label>
          <input type="number" className="form-control" id="amount" value={amount} onChange={handleAmountChange} placeholder="Enter Amount" required />
        </div>
        <div className="form-group">
          <label htmlFor="purchaseLocation" className="form-label">Purchase Location:</label>
          <input type="text" className="form-control" id="purchaseLocation" value={purchaseLocation} onChange={handlePurchaseLocationChange} placeholder="Enter Purchase Location" required />
        </div>
        <h4>Items</h4>
        {items.map((item, index) => (
          <div key={index} className="form-group d-flex align-items-center">
            <input type="text" className="form-control" value={item.itemName} onChange={(e) => handleItemChange(index, 'itemName', e.target.value)} placeholder="Item Name" style={{ flex: 2, marginRight: '1rem' }} />
            <div className="d-flex align-items-center" style={{ flex: 2, marginRight: '1rem', position: 'relative' }}>
              <select className="form-control" value={item.itemCategory} onChange={(e) => handleItemChange(index, 'itemCategory', e.target.value)} style={{ flex: 1, paddingRight: '2rem' }}>
                <option value="Groceries">Groceries</option>
                <option value="Utilities">Utilities</option>
                <option value="Entertainment">Entertainment</option>
              </select>
              <FaChevronDown style={{ position: 'absolute', right: '1rem', pointerEvents: 'none' }} />
            </div>
            <input type="number" className="form-control" value={item.itemAmount} onChange={(e) => handleItemChange(index, 'itemAmount', e.target.value)} placeholder="Item Amount" style={{ flex: 1, marginRight: '1rem' }} />
            <FaPlus onClick={handleAddRow} style={{ cursor: 'pointer' }} />
          </div>
        ))}
        <div className="form-group">
          <label htmlFor="date" className="form-label">Date:</label>
          <input type="date" className="form-control" id="date" value={date} onChange={handleDateChange} />
        </div>
        <div className="form-group">
          <label htmlFor="time" className="form-label">Time:</label>
          <input type="time" className="form-control" id="time" value={time} onChange={handleTimeChange} />
        </div>
        <div className="form-group">
          <label htmlFor="description" className="form-label">Description:</label>
          <textarea className="form-control" id="description" value={description} onChange={handleDescriptionChange} placeholder="Enter Description"></textarea>
        </div>
        <div className="button-group text-right">
          <button type="submit" className="btn btn-primary">Set</button>
        </div>
      </form>
    </div>
  );
};

export default AddExpenseLayout;
