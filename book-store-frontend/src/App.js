import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import "semantic-ui-css/semantic.min.css";
import Books from './components/Books/Books';
import Nav from './components/Nav';

function App() {
  return (
    <Router>
      <div>
        <Nav/>
        <Routes>
          <Route path="/*" element={<Books />} />
          <Route path="/books" element={<Books />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
