import { useState } from 'react';
import { sessionsApi, tableauApi } from '../api/endpoints';
import { Chargement, Erreur, SelecteurPromotion } from '../components';

export default function Formateur() {
  const [promotionId, setPromotionId] = useState('');
  const [titre, setTitre] = useState('Cours du jour');
  const [session, setSession] = useState(null);
  const [tableau, setTableau] = useState(null);
  const [erreur, setErreur] = useState(null);
  const [loading, setLoading] = useState(false);

  const ouvrir = async () => {
    setErreur(null); setLoading(true);
    try {
      const s = await sessionsApi.ouvrir(titre, promotionId);
      setSession(s);
      const t = await tableauApi.pourPromotion(promotionId);
      setTableau(t);
    } catch (e) { setErreur(e); } finally { setLoading(false); }
  };

  const rafraichirTableau = async () => {
    setErreur(null); setLoading(true);
    try { setTableau(await tableauApi.pourPromotion(promotionId)); }
    catch (e) { setErreur(e); } finally { setLoading(false); }
  };

  const cloturer = async () => {
    if (!session) return;
    setErreur(null);
    try { await sessionsApi.cloturer(session.id); alert('Session cloturee.'); }
    catch (e) { setErreur(e); }
  };

  return (
    <section>
      <h2>Espace formateur</h2>

      <p>
        Promotion : <SelecteurPromotion value={promotionId} onChange={setPromotionId} />
        {' '}
        Titre : <input value={titre} onChange={(e) => setTitre(e.target.value)} />
        {' '}
        <button onClick={ouvrir} disabled={!promotionId || loading}>Ouvrir une session</button>
        <button onClick={rafraichirTableau} disabled={!promotionId || loading}>Rafraichir le tableau</button>
      </p>

      <Erreur erreur={erreur} />
      {loading && <Chargement />}

      {session && (
        <div style={{ background: '#eef', padding: 12, marginTop: 12 }}>
          <strong>Code de presence : {session.code}</strong>
          <div>Expire a {new Date(session.expirationAt).toLocaleTimeString()}</div>
          <button onClick={cloturer}>Cloturer la session</button>
        </div>
      )}

      {tableau && (
        <table border="1" cellPadding="6" style={{ marginTop: 12, borderCollapse: 'collapse' }}>
          <thead>
            <tr>
              <th>Etudiant</th><th>Presences</th><th>Exercices</th>
              <th>Moyenne</th><th>Relectures en attente</th>
            </tr>
          </thead>
          <tbody>
            {tableau.map((l) => (
              <tr key={l.etudiantId}>
                <td>{l.nom}</td>
                <td>{l.presences}</td>
                <td>{l.exercicesDeposes}</td>
                <td>{l.moyenne ?? '-'}</td>
                <td>{l.relecturesEnAttente}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
