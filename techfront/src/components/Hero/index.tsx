import React from 'react';
import './styles.css';

const Hero = () => {
  return (
    <div className="hero">
      <div className="hero__content">
        <h1 className="hero__title">Stranger Things</h1>
        <p className="hero__description">
          Quando um menino desaparece, a cidade toda participa nas buscas. Mas o que encontram são segredos, forças sobrenaturais e uma menina.
        </p>
        <div className="hero__buttons">
          <button className="hero__button hero__button--play">
            <i className="fas fa-play"></i> Assistir
          </button>
          <button className="hero__button hero__button--info">
            <i className="fas fa-info-circle"></i> Mais Informações
          </button>
        </div>
      </div>
      <div className="hero--fadeBottom"></div>
    </div>
  );
};

export default Hero;
