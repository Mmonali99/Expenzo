// components/Menu.jsx
import React, { useState } from 'react';
import MenuLayout from '../layout/MenuLayout';

const Menu = () => {
  const [show, setShow] = useState(false);
  const [subMenu, setSubMenu] = useState({
    expenses: false,
    goals: false,
    budget: false,
  });

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const toggleSubMenu = (menu) => {
    setSubMenu({
      expenses: menu === 'expenses' ? !subMenu.expenses : false,
      goals: menu === 'goals' ? !subMenu.goals : false,
      budget: menu === 'budget' ? !subMenu.budget : false,
    });
  };

  return (
    <MenuLayout
      show={show}
      handleClose={handleClose}
      handleShow={handleShow}
      subMenu={subMenu}
      toggleSubMenu={toggleSubMenu}
    />
  );
};

export default Menu;
