import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import "semantic-ui-css/semantic.min.css";
import Login from 'components/Login/Login';
import { useStore } from 'components/Login/StoreContext';
import { jwtDecode } from "jwt-decode";
import AdminNav from 'components/AdminNav';
import CustomerNav from 'components/CustomerNav';
import GuestNav from './GuestNav';
import Books from './Books/Books';
import Cart from './Carts/Cart';
import BookDetails from './Books/BookDetails';
import Orders from "./Admin/Orders";
import CreateBook from "./Admin/CreateBook";
import Register from './Register/Register';
import Export from "./Admin/Export";
import Forbidden from './Forbidden';
import ProtectedRoute from './ProtectedRoute';

const Root = () => {

  const [initialized, setInitialized] = useState(false);
  const { user, setUser } = useStore();

  useEffect(() => {
    const cookies = document.cookie.split(';');
    let token = null;
    for (let i = 0; i < cookies.length; i++) {
      if (cookies[i].startsWith('Bearer')) {
        console.log(cookies[i])
        token = cookies[i].split('=')[1];
        break;
      }
    }
    if (token) {
      const decodedToken = jwtDecode(token);
      setUser(decodedToken);
    } else {
      console.log("Not logged in!")
    }
    setInitialized(true);
  }, [setUser, setInitialized]);

  if (!initialized) {
    return null;
  }

  return (
    <div>
      <ToastContainer
        position="bottom-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
      <Router>
        {user?.role === "ADMINISTRATOR" && <AdminNav />}
        {user?.role === "BOOK_BUYER" && <CustomerNav />}
        {(user  === undefined || user === null)   && <GuestNav/>}
        <div>
          <Routes>
            <Route path="/*" element={<Login />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/books" element={<Books />} />
            <Route path="/books/details/:id" element={<BookDetails />} />
            <Route path="/forbidden" element={<Forbidden />} />

            <Route
            path="/cart"
            element={(
              <ProtectedRoute
                element={<Cart />}
                allowedRoles={["BOOK_BUYER"]} 
              />
            )}>
            </Route>
            
            <Route
            path="/orders"
            element={(
              <ProtectedRoute
                element={<Orders />}
                allowedRoles={["ADMINISTRATOR","BOOK_BUYER"]} 
              />
            )}>
            </Route>
            
            <Route
            path="/create"
            element={(
              <ProtectedRoute
                element={<CreateBook />}
                allowedRoles={["ADMINISTRATOR"]} 
              />
            )}>
            </Route>

            <Route
            path="/export"
            element={(
              <ProtectedRoute
                element={<Export />}
                allowedRoles={["ADMINISTRATOR"]} 
              />
            )}>
            </Route>


            {/* <Route path="/cart" element={<Cart />} /> */}
            {/* <Route path="/orders" element={<Orders />} /> */}
            {/* <Route path="/create" element={<CreateBook />} /> */}
            {/* <Route path="/export" element={<Export />} /> */}
          </Routes>
        </div>
      </Router>
    </div>
  );
}

export default Root;
