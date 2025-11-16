import React from 'react';
import Header from '../../components/Header';
import Hero from '../../components/Hero';
import Row from '../../components/Row';
import './styles.css';

// Dados de exemplo - na aplicação real, isso virá de uma API
const mockData = {
  featured: {
    title: 'Em Destaque',
    items: [
      { id: 1, title: 'Stranger Things', backdrop_path: '/x2RS3uTcsJJ9IfjNPgD1kxhiH4u.jpg' },
      { id: 2, title: 'The Witcher', backdrop_path: '/8WUVHemHFH2ZIP6NWkwlHWsyrEL.jpg' },
      { id: 3, title: 'The Mandalorian', backdrop_path: '/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg' },
      // Adicione mais itens conforme necessário
    ]
  },
  originals: {
    title: 'Originais Netflix',
    items: [
      { id: 4, title: 'La Casa de Papel', backdrop_path: '/reEMJA1uzscCbkpeRJeTT2bjqUp.jpg' },
      { id: 5, title: 'The Crown', backdrop_path: '/zX7N6QQqJ5J9GkxjCkiJml6UYjP.jpg' },
      { id: 6, title: 'Dark', backdrop_path: '/5LoHUgrgacSethjLDeI6vVTjAyZ.jpg' },
    ]
  },
  trending: {
    title: 'Em Alta',
    items: [
      { id: 7, title: 'Squid Game', backdrop_path: '/dDlEmu3EZ0Pgg93K2SVNLCjCSvE.jpg' },
      { id: 8, title: 'The Queen\'s Gambit', backdrop_path: '/zU0htwkhNvBQdVSIKB9s6hgVeFS.jpg' },
      { id: 9, title: 'Bridgerton', backdrop_path: '/mFmXbXQPlCvZ4oJEJXoxhqKsMUM.jpg' },
    ]
  },
  action: {
    title: 'Ação e Aventura',
    items: [
      { id: 10, title: 'The Witcher', backdrop_path: '/8WUVHemHFH2ZIP6NWkwlHWsyrEL.jpg' },
      { id: 11, title: 'The Umbrella Academy', backdrop_path: '/scZlQQYnDVlnpxFTxaIv2g0BWnL.jpg' },
      { id: 12, title: 'Shadow and Bone', backdrop_path: '/n6bUvigpRFqLmIfqCBuwkO53ZpBR.jpg' },
    ]
  }
};

const Home: React.FC = () => {
  return (
    <div className="home">
      <Header />
      <main className="home__main">
        <Hero />
        <div className="home__rows">
          <Row title={mockData.featured.title} items={mockData.featured.items} />
          <Row title={mockData.originals.title} items={mockData.originals.items} />
          <Row title={mockData.trending.title} items={mockData.trending.items} />
          <Row title={mockData.action.title} items={mockData.action.items} />
        </div>
      </main>
    </div>
  );
};

export default Home;
