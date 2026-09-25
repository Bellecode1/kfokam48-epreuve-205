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
      setMessage('Relecture enregistrée.');
      setDetail(null); setNote(''); setCommentaire(''); charger();
    } catch (e) { setErreur(e); }
  };

  return (
      <>
        <h1 className="page-titre">Espace relecteur</h1>
        <p className="page-sous-titre">Relisez les exercices qui vous sont assignés et rendez votre note.</p>

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
            <button onClick={charger} disabled={!etudiantId}>Voir mes relectures</button>
          </div>
          <Erreur erreur={erreur} />
          {message && <p className="succes">{message}</p>}
        </div>

        {liste && (
            <div className="card">
              <h3>Relectures assignées</h3>
              {liste.length === 0 ? (
                  <p className="chargement">Aucune relecture assignée.</p>
              ) : (
                  <table>
                    <thead>
                    <tr><th>Relecture</th><th>Exercice</th><th>Statut</th><th>Action</th></tr>
                    </thead>
                    <tbody>
                    {liste.map((r) => (
                        <tr key={r.id}>
                          <td>#{r.id}</td>
                          <td>Exercice #{r.exerciceId}</td>
                          <td>
                            {r.statut === 'RENDUE'
                                ? <span className="badge badge-vert">Rendue</span>
                                : <span className="badge badge-orange">Assignée</span>}
                          </td>
                          <td>
                            {r.statut !== 'RENDUE' && (
                                <button className="btn-petit" onClick={() => ouvrir(r.id)}>Ouvrir</button>
                            )}
                          </td>
                        </tr>
                    ))}
                    </tbody>
                  </table>
              )}
            </div>
        )}

        {detail && (
            <div className="card">
              <h3>Relecture #{detail.id}</h3>
              <p>
                <strong>Lien :</strong>{' '}
                <a href={detail.lien} target="_blank" rel="noreferrer">{detail.lien}</a>
              </p>
              <p><strong>Auteur :</strong> {detail.auteurNom}</p>

              <label>Note (0 à 20, entier)</label>
              <input
                  type="number"
                  min="0"
                  max="20"
                  value={note}
                  onChange={(e) => setNote(e.target.value)}
              />

              <label>Commentaire</label>
              <textarea
                  placeholder="Points forts, points à améliorer..."
                  value={commentaire}
                  onChange={(e) => setCommentaire(e.target.value)}
              />

              <button onClick={rendre} disabled={note === ''}>Valider la relecture</button>
            </div>
        )}
      </>
  );
}