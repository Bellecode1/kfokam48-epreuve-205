import { useEffect, useState } from 'react';
import { promotionsApi, presencesApi, sessionsApi, tableauApi } from '../api/endpoints';
import { Erreur, SelecteurPromotion } from '../components';

export default function Formateur() {
  const [promotionId, setPromotionId] = useState('');
  const [titre, setTitre] = useState('Cours du jour');
  const [session, setSession] = useState(null);
  const [tableau, setTableau] = useState(null);
  const [erreur, setErreur] = useState(null);
  const [loading, setLoading] = useState(false);
  const [modaleCloture, setModaleCloture] = useState(false);

  const [etudiants, setEtudiants] = useState([]);
  const [selection, setSelection] = useState([]);
  const [sessionPourAjout, setSessionPourAjout] = useState('');

  useEffect(() => {
    if (!promotionId) { setEtudiants([]); return; }
    promotionsApi.etudiants(promotionId).then(setEtudiants).catch(() => {});
  }, [promotionId]);

  const ouvrir = async () => {
    setErreur(null); setLoading(true);
    try {
      const s = await sessionsApi.ouvrir(titre, promotionId);
      setSession(s);
      setSessionPourAjout(String(s.id));
      setTableau(await tableauApi.pourPromotion(promotionId));
    } catch (e) { setErreur(e); } finally { setLoading(false); }
  };

  const rafraichir = async () => {
    if (!promotionId) return;
    setErreur(null); setLoading(true);
    try { setTableau(await tableauApi.pourPromotion(promotionId)); }
    catch (e) { setErreur(e); } finally { setLoading(false); }
  };

  const cloturer = async () => {
    if (!session) return;
    setErreur(null);
    try {
      await sessionsApi.cloturer(session.id);
      setSession(null);
      setModaleCloture(false);
      rafraichir();
    } catch (e) { setErreur(e); setModaleCloture(false); }
  };

  const toggleEtudiant = (id) => {
    setSelection((prev) =>
        prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    );
  };

  const ajouterSelection = async () => {
    if (!sessionPourAjout || selection.length === 0) return;
    setErreur(null);
    try {
      for (const id of selection) {
        await presencesApi.manuelle(Number(sessionPourAjout), id);
      }
      alert(`${selection.length} présence(s) ajoutée(s).`);
      setSelection([]);
      rafraichir();
    } catch (e) { setErreur(e); }
  };

  const totalEtudiants = tableau?.length || 0;
  const totalPresences = tableau?.reduce((s, l) => s + l.presences, 0) || 0;
  const totalExercices = tableau?.reduce((s, l) => s + l.exercicesDeposes, 0) || 0;
  const totalEnAttente = tableau?.reduce((s, l) => s + l.relecturesEnAttente, 0) || 0;

  return (
      <>
        <h1 className="page-titre">Tableau de bord formateur</h1>
        <p className="page-sous-titre">Ouvrez une session et suivez votre promotion en temps réel.</p>

        {session && (
            <div className="session-code">
              <div className="label">Code de présence — valable 15 minutes</div>
              <span className="code">{session.code}</span>
              <div className="expiration">
                Expire à {new Date(session.expirationAt).toLocaleTimeString()}
              </div>
              <button
                  className="btn-danger"
                  style={{ marginTop: 16 }}
                  onClick={() => setModaleCloture(true)}
              >
                Clôturer la session
              </button>
            </div>
        )}

        <div className="card">
          <h3>Ouvrir une session</h3>
          <div className="champ-ligne">
            <div style={{ flex: 1 }}>
              <label>Promotion</label>
              <SelecteurPromotion value={promotionId} onChange={setPromotionId} />
            </div>
            <div style={{ flex: 1 }}>
              <label>Titre de la session</label>
              <input value={titre} onChange={(e) => setTitre(e.target.value)} />
            </div>
            <button onClick={ouvrir} disabled={!promotionId || loading}>
              Ouvrir la session
            </button>
            <button className="btn-secondaire" onClick={rafraichir} disabled={!promotionId || loading}>
              Rafraîchir le tableau
            </button>
          </div>
          <Erreur erreur={erreur} />
        </div>

        {tableau && (
            <>
              <div className="stats">
                <div className="stat">
                  <div className="label-icone">👥 Étudiants</div>
                  <div className="valeur-grande">{totalEtudiants}</div>
                  <div className="sous">dans la promotion</div>
                </div>
                <div className="stat">
                  <div className="label-icone">✅ Présences</div>
                  <div className="valeur-grande">{totalPresences}</div>
                  <div className="sous">toutes sessions</div>
                </div>
                <div className="stat">
                  <div className="label-icone">📄 Exercices</div>
                  <div className="valeur-grande">{totalExercices}</div>
                  <div className="sous">déposés</div>
                </div>
                <div className="stat">
                  <div className="label-icone">⏳ En attente</div>
                  <div className="valeur-grande">{totalEnAttente}</div>
                  <div className="sous">relectures</div>
                </div>
              </div>

              <div className="card">
                <h3>Suivi des étudiants</h3>
                <table>
                  <thead>
                  <tr>
                    <th>Étudiant</th>
                    <th>Présences</th>
                    <th>Exercices</th>
                    <th>Moyenne</th>
                    <th>Relectures en attente</th>
                  </tr>
                  </thead>
                  <tbody>
                  {tableau.map((l) => (
                      <tr key={l.etudiantId}>
                        <td><strong>{l.nom}</strong></td>
                        <td>{l.presences}</td>
                        <td>{l.exercicesDeposes}</td>
                        <td>
                          {l.moyenne != null
                              ? <span className="badge badge-vert">{l.moyenne.toFixed(1)} / 20</span>
                              : <span className="badge badge-neutre">—</span>}
                        </td>
                        <td>
                          {l.relecturesEnAttente > 0
                              ? <span className="badge badge-orange">{l.relecturesEnAttente} à faire</span>
                              : <span className="badge badge-vert">à jour</span>}
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>
            </>
        )}

        <div className="card">
          <h3>Ajouter des présences manuellement</h3>
          <p className="page-sous-titre" style={{ marginTop: 0 }}>
            Pour les étudiants qui ont un problème de téléphone. Elles seront marquées « ajouté par le formateur ».
          </p>

          <div className="champ-ligne">
            <div style={{ flex: 1 }}>
              <label>ID de la session</label>
              <input
                  placeholder="1"
                  value={sessionPourAjout}
                  onChange={(e) => setSessionPourAjout(e.target.value)}
              />
            </div>
            <button
                onClick={ajouterSelection}
                disabled={!sessionPourAjout || selection.length === 0}
            >
              Ajouter {selection.length > 0 ? `(${selection.length})` : ''}
            </button>
          </div>

          <table>
            <thead>
            <tr>
              <th style={{ width: 40 }}></th>
              <th>Étudiant</th>
            </tr>
            </thead>
            <tbody>
            {etudiants.map((e) => (
                <tr key={e.id} style={{ cursor: 'pointer' }} onClick={() => toggleEtudiant(e.id)}>
                  <td>
                    <input
                        type="checkbox"
                        checked={selection.includes(e.id)}
                        onChange={() => toggleEtudiant(e.id)}
                        style={{ width: 'auto', marginBottom: 0 }}
                    />
                  </td>
                  <td>{e.prenom} {e.nom}</td>
                </tr>
            ))}
            {etudiants.length === 0 && (
                <tr>
                  <td colSpan="2" className="chargement">
                    Sélectionnez d'abord une promotion.
                  </td>
                </tr>
            )}
            </tbody>
          </table>
        </div>

        {modaleCloture && (
            <div className="modale-fond" onClick={() => setModaleCloture(false)}>
              <div className="modale" onClick={(e) => e.stopPropagation()}>
                <h2>Clôturer la session ?</h2>
                <p>
                  Cette action est <strong>définitive</strong>. Les notes deviennent figées
                  et ne pourront plus être modifiées. Les étudiants ne pourront plus
                  marquer leur présence ni déposer d'exercice.
                </p>
                <div className="actions">
                  <button className="btn-secondaire" onClick={() => setModaleCloture(false)}>
                    Annuler
                  </button>
                  <button className="btn-danger" onClick={cloturer}>
                    Confirmer la clôture
                  </button>
                </div>
              </div>
            </div>
        )}
      </>
  );
}