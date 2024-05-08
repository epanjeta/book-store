import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import "semantic-ui-css/semantic.min.css";
import Login from 'components/Login/Login';
import { useStore } from 'components/Login/StoreContext';
import { getSession } from 'api/users';
import AdminNav from 'components/AdminNav';
import CustomerNav from 'components/CustomerNav';
import Books from './Books/Books';
import Cart from './Carts/Cart';

const Root = () => {

  const [initialized, setInitialized] = useState(false);
  const { user, setUser } = useStore();

  useEffect(() => {
    const fetch = async () => {
      try {
        const response = await getSession();
        setUser(response);
      } catch (e) {
        console.log(e);
      } finally {
        setInitialized(true);
      }
    };

    fetch();
  }, [setUser]);

  if (!initialized) {
    return null;
  }

  return (
      <Router>
        {user?.role === "Admin" && <AdminNav />}
        {user?.role === "Custmer" && <CustomerNav />}
        <div>
          <Routes>
            <Route path="/*" element={<Login />} />
            <Route path="/login" element={<Login />} />
            <Route path="/books" element={<Books />} />
            <Route path="/cart" element={<Cart />} />
          </Routes>
        </div>
      </Router>
  );
}

export default Root;
