import { Link, Route, Routes } from 'react-router-dom';
import Formateur from './pages/Formateur';
import Etudiant from './pages/Etudiant';
import Relecteur from './pages/Relecteur';

export default function App() {
  return (
    <div style={{ fontFamily: 'system-ui', maxWidth: 900, margin: '0 auto', padding: 16 }}>
      <h1>KFOKAM48 — Presence & Relecture</h1>
      <nav style={{ marginBottom: 16 }}>
        <Link to="/">Formateur</Link> | <Link to="/etudiant">Etudiant</Link> | <Link to="/relecteur">Relecteur</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Formateur />} />
        <Route path="/etudiant" element={<Etudiant />} />
        <Route path="/relecteur" element={<Relecteur />} />
      </Routes>
    </div>
  );
}
