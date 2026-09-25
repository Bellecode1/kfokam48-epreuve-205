import { Link, Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import Formateur from './pages/Formateur';
import Etudiant from './pages/Etudiant';
import Relecteur from './pages/Relecteur';

const TITRES = {
  '/': 'Accueil',
  '/formateur': 'Espace formateur',
  '/etudiant': 'Espace étudiant',
  '/relecteur': 'Espace relecteur',
};

function Sidebar() {
  const { pathname } = useLocation();
  const navigate = useNavigate();
  return (
      <aside className="sidebar">
        <div className="logo">
          KFOKAM48
          <span>Présence &amp; Relecture</span>
        </div>
        <nav>
          <Link to="/" className={pathname === '/' ? 'active' : ''}>🏠 Accueil</Link>
          <Link to="/formateur" className={pathname === '/formateur' ? 'active' : ''}>🎓 Formateur</Link>
          <Link to="/etudiant" className={pathname === '/etudiant' ? 'active' : ''}>📝 Étudiant</Link>
          <Link to="/relecteur" className={pathname === '/relecteur' ? 'active' : ''}>✍️ Relecteur</Link>
        </nav>
        <div className="footer">
          Épreuve finale · matricule 205
          <button className="btn-deconnexion" onClick={() => navigate('/')}>
            ← Retour à l'accueil
          </button>
        </div>
      </aside>
  );
}

function TopBar() {
  const { pathname } = useLocation();
  return (
      <div className="topbar">
        <div className="breadcrumb">
          Campus KFOKAM48 <span className="sep">/</span> {TITRES[pathname] || ''}
        </div>
        <div className="topbar-actions">
          <span className="notif">🔔</span>
          <Link to="/" className="btn-secondaire btn-petit">Déconnexion</Link>
        </div>
      </div>
  );
}

function Accueil() {
  const navigate = useNavigate();
  return (
      <div className="accueil">
        <h1>Bienvenue sur KFOKAM48</h1>
        <p className="sous-titre">Choisissez votre espace pour commencer.</p>
        <div className="roles">
          <div className="role-card" onClick={() => navigate('/formateur')}>
            <div className="icone">🎓</div>
            <div className="titre">Formateur</div>
            <div className="desc">Ouvrir une session, voir le tableau, ajouter des présences</div>
          </div>
          <div className="role-card" onClick={() => navigate('/etudiant')}>
            <div className="icone">📝</div>
            <div className="titre">Étudiant</div>
            <div className="desc">Marquer sa présence, déposer un exercice, voir ses notes</div>
          </div>
          <div className="role-card" onClick={() => navigate('/relecteur')}>
            <div className="icone">✍️</div>
            <div className="titre">Relecteur</div>
            <div className="desc">Relire un exercice, saisir une note et un commentaire</div>
          </div>
        </div>
      </div>
  );
}

export default function App() {
  const { pathname } = useLocation();
  const sansSidebar = pathname === '/';

  if (sansSidebar) {
    return (
        <Routes>
          <Route path="/" element={<Accueil />} />
        </Routes>
    );
  }

  return (
      <div className="layout">
        <Sidebar />
        <main className="contenu">
          <TopBar />
          <Routes>
            <Route path="/formateur" element={<Formateur />} />
            <Route path="/etudiant" element={<Etudiant />} />
            <Route path="/relecteur" element={<Relecteur />} />
          </Routes>
        </main>
      </div>
  );
}