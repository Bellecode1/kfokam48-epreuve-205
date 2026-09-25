import { useState } from 'react';
import { relecturesApi } from '../api/endpoints';
import { Erreur, SelecteurEtudiant, SelecteurPromotion } from '../components';

export default function Relecteur() {
  const [promotionId, setPromotionId] = useState('');
  const [etudiantId, setEtudiantId] = useState('');
  const [liste, setListe] = useState(null);
  const [detail, setDetail] = useState(null);
  const [note, setNote] = useState('');
  const [commentaire, setCommentaire] = useState('');
  const [erreur, setErreur] = useState(null);
  const [message, setMessage] = useState(null);

  const charger = async () => {
    setErreur(null); setMessage(null);
    try { setListe(await relecturesApi.parEtudiant(etudiantId)); }
    catch (e) { setErreur(e); }
  };

  const ouvrir = async (id) => {
    setErreur(null); setDetail(null);
    try { setDetail(await relecturesApi.detail(id)); }
    catch (e) { setErreur(e); }
  };

  const rendre = async () => {
    setErreur(null); setMessage(null);
    try {
      await relecturesApi.rendre(detail.id, Number(note), commentaire);
      setMessage('Relecture enregistree.');
      setDetail(null); charger();
    } catch (e) { setErreur(e); }
  };

  return (
    <section>
      <h2>Espace relecteur</h2>
      <p>
        Promotion : <SelecteurPromotion value={promotionId} onChange={setPromotionId} />
        {' '}
        Je suis : <SelecteurEtudiant promotionId={promotionId} value={etudiantId} onChange={setEtudiantId} />
        {' '}
        <button onClick={charger} disabled={!etudiantId}>Mes relectures</button>
      </p>

      <Erreur erreur={erreur} />
      {message && <p style={{ color: 'green' }}>{message}</p>}

      {liste && (
        <ul>
          {liste.map((r) => (
            <li key={r.id}>
              Relecture #{r.id} (exercice {r.exerciceId}) — {r.statut}
              {' '}
              <button onClick={() => ouvrir(r.id)}>Ouvrir</button>
            </li>
          ))}
          {liste.length === 0 && <li>Aucune relecture assignee.</li>}
        </ul>
      )}

      {detail && (
        <fieldset>
          <legend>Relecture #{detail.id}</legend>
          <p>Lien : <a href={detail.lien} target="_blank" rel="noreferrer">{detail.lien}</a></p>
          <p>Auteur : {detail.auteurNom}</p>
          <p>Note : <input type="number" min="0" max="20" value={note} onChange={(e) => setNote(e.target.value)} /> / 20</p>
          <p>Commentaire : <textarea value={commentaire} onChange={(e) => setCommentaire(e.target.value)} /></p>
          <button onClick={rendre} disabled={note === ''}>Valider la relecture</button>
        </fieldset>
      )}
    </section>
  );
}
