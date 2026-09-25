import { Link, Route, Routes, useLocation } from 'react-router-dom';
import Formateur from './pages/Formateur';
import Etudiant from './pages/Etudiant';
import Relecteur from './pages/Relecteur';

export default function App() {
  const { pathname } = useLocation();
  return (
    <>
      <header>
        <h1>KFOKAM48 — Présence & Relecture</h1>
      </header>
      <div className="app">
        <nav>
          <Link to="/" className={pathname === '/' ? 'active' : ''}>Formateur</Link>
          <Link to="/etudiant" className={pathname === '/etudiant' ? 'active' : ''}>Étudiant</Link>
          <Link to="/relecteur" className={pathname === '/relecteur' ? 'active' : ''}>Relecteur</Link>
        </nav>
        <Routes>
          <Route path="/" element={<Formateur />} />
          <Route path="/etudiant" element={<Etudiant />} />
          <Route path="/relecteur" element={<Relecteur />} />
        </Routes>
      </div>
    </>
  );
}

