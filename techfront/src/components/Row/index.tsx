import React, { useState, useRef } from 'react';
import './styles.css';

interface RowProps {
  title: string;
  items: Array<{
    id: number;
    title: string;
    backdrop_path: string;
  }>;
}

const Row: React.FC<RowProps> = ({ title, items }) => {
  const [scrollX, setScrollX] = useState(0);
  const rowRef = useRef<HTMLDivElement>(null);

  const handleScroll = (direction: 'left' | 'right') => {
    if (rowRef.current) {
      const { scrollLeft, clientWidth } = rowRef.current;
      const scrollTo = direction === 'left' 
        ? scrollLeft - clientWidth 
        : scrollLeft + clientWidth;
      
      rowRef.current.scrollTo({
        left: scrollTo,
        behavior: 'smooth'
      });
      setScrollX(scrollTo);
    }
  };

  return (
    <div className="row">
      <h2 className="row__title">{title}</h2>
      <div className="row__container">
        <button 
          className="row__arrow row__arrow--left"
          onClick={() => handleScroll('left')}
          style={{ display: scrollX <= 0 ? 'none' : 'flex' }}
        >
          <i className="fas fa-chevron-left"></i>
        </button>
        
        <div className="row__posters" ref={rowRef}>
          {items.map((item) => (
            <div key={item.id} className="row__poster">
              <img
                src={`https://image.tmdb.org/t/p/w300${item.backdrop_path}`}
                alt={item.title}
                className="row__poster-img"
              />
            </div>
          ))}
        </div>
        
        <button 
          className="row__arrow row__arrow--right"
          onClick={() => handleScroll('right')}
        >
          <i className="fas fa-chevron-right"></i>
        </button>
      </div>
    </div>
  );
};

export default Row;
