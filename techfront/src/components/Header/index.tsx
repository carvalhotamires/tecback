import React from 'react';
import { Link } from 'react-router-dom';
import './styles.css';

const Header = () => {
  return (
    <header className="header">
      <div className="header__logo">
        <Link to="/">
          <img src="/images/logo.png" alt="IESPflix" />
        </Link>
      </div>
      <nav className="header__nav">
        <Link to="/browse" className="header__nav-link">Início</Link>
        <Link to="/browse/tv" className="header__nav-link">Séries</Link>
        <Link to="/browse/movies" className="header__nav-link">Filmes</Link>
        <Link to="/browse/my-list" className="header__nav-link">Minha Lista</Link>
      </nav>
      <div className="header__user">
        <div className="header__avatar">
          <img src="/images/avatar.png" alt="Usuário" />
        </div>
      </div>
    </header>
  );
};

export default Header;
