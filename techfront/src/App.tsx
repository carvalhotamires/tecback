import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Planos from './pages/Planos';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/planos" element={<Planos />} />
          {/* Adicione mais rotas conforme necessário */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
