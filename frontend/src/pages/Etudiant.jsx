import { useState } from 'react';
import { exercicesApi, presencesApi, relecturesApi } from '../api/endpoints';
import { Erreur, SelecteurEtudiant, SelecteurPromotion } from '../components';

export default function Etudiant() {
  const [promotionId, setPromotionId] = useState('');
  const [etudiantId, setEtudiantId] = useState('');
  const [code, setCode] = useState('');
  const [lien, setLien] = useState('');
  const [sessionId, setSessionId] = useState('');
  const [notes, setNotes] = useState(null);
  const [erreur, setErreur] = useState(null);
  const [message, setMessage] = useState(null);

  const marquer = async () => {
    setErreur(null); setMessage(null);
    try {
      await presencesApi.marquer(code, etudiantId);
      setMessage('Presence enregistree.');
    } catch (e) { setErreur(e); }
  };

  const deposer = async () => {
    setErreur(null); setMessage(null);
    try {
      await exercicesApi.deposer(Number(sessionId), etudiantId, lien);
      setMessage('Exercice depose.');
    } catch (e) { setErreur(e); }
  };

  const voirNotes = async () => {
    setErreur(null); setNotes(null);
    try { setNotes(await relecturesApi.notesRecues(etudiantId)); }
    catch (e) { setErreur(e); }
  };

  return (
    <section>
      <h2>Espace etudiant</h2>
      <p>
        Promotion : <SelecteurPromotion value={promotionId} onChange={setPromotionId} />
        {' '}
        Je suis : <SelecteurEtudiant promotionId={promotionId} value={etudiantId} onChange={setEtudiantId} />
      </p>

      <Erreur erreur={erreur} />
      {message && <p style={{ color: 'green' }}>{message}</p>}

      <fieldset>
        <legend>Marquer ma presence</legend>
        <input placeholder="Code de presence" value={code} onChange={(e) => setCode(e.target.value)} />
        <button onClick={marquer} disabled={!etudiantId || !code}>Valider</button>
      </fieldset>

      <fieldset>
        <legend>Deposer mon exercice</legend>
        <input placeholder="ID de session" value={sessionId} onChange={(e) => setSessionId(e.target.value)} />
        <input placeholder="https://..." value={lien} onChange={(e) => setLien(e.target.value)} />
        <button onClick={deposer} disabled={!etudiantId || !sessionId || !lien}>Deposer</button>
      </fieldset>

      <fieldset>
        <legend>Mes notes recues</legend>
        <button onClick={voirNotes} disabled={!etudiantId}>Afficher</button>
        {notes && (
          <ul>
            {notes.map((n) => (
              <li key={n.exerciceId}>
                Exercice {n.exerciceId} : {n.note ?? '-'} / 20 — {n.commentaire || '(sans commentaire)'}
              </li>
            ))}
          </ul>
        )}
      </fieldset>
    </section>
  );
}
