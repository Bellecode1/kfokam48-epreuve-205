import { useEffect, useState } from 'react';

export function Chargement({ children }) {
  return <p style={{ color: '#666' }}>{children || 'Chargement...'}</p>;
}

export function Erreur({ erreur }) {
  if (!erreur) return null;
  return (
    <p style={{ color: 'crimson' }}>
      [{erreur.code}] {erreur.message}
    </p>
  );
}

export function SelecteurPromotion({ value, onChange }) {
  const [promos, setPromos] = useState([]);
  useEffect(() => {
    import('./api/endpoints').then(({ promotionsApi }) => {
      promotionsApi.list().then(setPromos).catch(() => {});
    });
  }, []);
  return (
    <select value={value || ''} onChange={(e) => onChange(Number(e.target.value))}>
      <option value="">-- Choisir une promotion --</option>
      {promos.map((p) => (
        <option key={p.id} value={p.id}>{p.nom}</option>
      ))}
    </select>
  );
}

export function SelecteurEtudiant({ promotionId, value, onChange }) {
  const [etudiants, setEtudiants] = useState([]);
  useEffect(() => {
    if (!promotionId) return;
    import('./api/endpoints').then(({ promotionsApi }) => {
      promotionsApi.etudiants(promotionId).then(setEtudiants).catch(() => {});
    });
  }, [promotionId]);
  return (
    <select value={value || ''} onChange={(e) => onChange(Number(e.target.value))}>
      <option value="">-- Choisir un étudiant --</option>
      {etudiants.map((e) => (
        <option key={e.id} value={e.id}>{e.prenom} {e.nom}</option>
      ))}
    </select>
  );
}
