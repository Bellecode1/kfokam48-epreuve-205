import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api/endpoints';

export default function SelectionUtilisateur() {
  const [utilisateurs, setUtilisateurs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    api.get('/utilisateurs')
      .then(res => setUtilisateurs(res.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  const handleSelect = (utilisateur) => {
    setSelected(utilisateur);
    localStorage.setItem('utilisateur', JSON.stringify(utilisateur));
    
    if (utilisateur.role === 'FORMATEUR') navigate('/');
    else if (utilisateur.role === 'ETUDIANT') navigate('/etudiant');
    else if (utilisateur.role === 'RELECTEUR') navigate('/relecteur');
  };

  if (loading) return <div className="loading">Chargement...</div>;

  return (
    <div className="selection-page">
      <div className="selection-container">
        <h1>Bienvenue sur KFOKAM48</h1>
        <p className="subtitle">Sélectionnez votre profil pour continuer</p>
        
        <div className="utilisateurs-grid">
          {utilisateurs.map(u => (
            <div 
              key={u.id} 
              className={`utilisateur-card ${selected?.id === u.id ? 'selected' : ''}`}
              onClick={() => handleSelect(u)}
            >
              <div className="avatar">{u.nom.charAt(0)}</div>
              <div className="info">
                <h3>{u.nom} {u.prenom}</h3>
                <span className={`role ${u.role.toLowerCase()}`}>{u.role}</span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
