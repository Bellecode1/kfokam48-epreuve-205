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
      setMessage('Présence enregistrée.');
    } catch (e) { setErreur(e); }
  };

  const deposer = async () => {
    setErreur(null); setMessage(null);
    try {
      await exercicesApi.deposer(Number(sessionId), etudiantId, lien);
      setMessage('Exercice déposé. Un relecteur a été assigné.');
    } catch (e) { setErreur(e); }
  };

  const voirNotes = async () => {
    setErreur(null); setNotes(null);
    try { setNotes(await relecturesApi.notesRecues(etudiantId)); }
    catch (e) { setErreur(e); }
  };

  return (
      <>
        <h1 className="page-titre">Espace étudiant</h1>
        <p className="page-sous-titre">Marquez votre présence, déposez votre exercice, consultez vos notes.</p>

        <div className="card">
          <h3>Qui êtes-vous ?</h3>
          <div className="champ-ligne">
            <div style={{ flex: 1 }}>
              <label>Promotion</label>
              <SelecteurPromotion value={promotionId} onChange={setPromotionId} />
            </div>
            <div style={{ flex: 1 }}>
              <label>Je suis</label>
              <SelecteurEtudiant promotionId={promotionId} value={etudiantId} onChange={setEtudiantId} />
            </div>
          </div>
          <Erreur erreur={erreur} />
          {message && <p className="succes">{message}</p>}
        </div>

        <div className="card">
          <h3>Marquer ma présence</h3>
          <div className="champ-ligne">
            <div style={{ flex: 1 }}>
              <label>Code de présence</label>
              <input
                  placeholder="Ex : K48-742"
                  value={code}
                  onChange={(e) => setCode(e.target.value.toUpperCase())}
              />
            </div>
            <button onClick={marquer} disabled={!etudiantId || !code}>Valider la présence</button>
          </div>
        </div>

        <div className="card">
          <h3>Déposer mon exercice</h3>
          <div className="champ-ligne">
            <div style={{ flex: 0.5 }}>
              <label>ID de session</label>
              <input
                  placeholder="1"
                  value={sessionId}
                  onChange={(e) => setSessionId(e.target.value)}
              />
            </div>
            <div style={{ flex: 2 }}>
              <label>Lien de l'exercice</label>
              <input
                  placeholder="https://github.com/..."
                  value={lien}
                  onChange={(e) => setLien(e.target.value)}
              />
            </div>
            <button onClick={deposer} disabled={!etudiantId || !sessionId || !lien}>Déposer</button>
          </div>
        </div>

        <div className="card">
          <h3>Mes notes reçues</h3>
          <button onClick={voirNotes} disabled={!etudiantId}>Afficher mes notes</button>
          {notes && notes.length === 0 && <p className="chargement">Aucune note pour le moment.</p>}
          {notes && notes.length > 0 && (
              <table style={{ marginTop: 16 }}>
                <thead>
                <tr><th>Exercice</th><th>Note</th><th>Commentaire</th></tr>
                </thead>
                <tbody>
                {notes.map((n) => (
                    <tr key={n.exerciceId}>
                      <td>#{n.exerciceId}</td>
                      <td><span className="badge badge-vert">{n.note ?? '—'} / 20</span></td>
                      <td>{n.commentaire || <em>sans commentaire</em>}</td>
                    </tr>
                ))}
                </tbody>
              </table>
          )}
        </div>
      </>
  );
}