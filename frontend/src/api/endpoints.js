import api from './client';

export const promotionsApi = {
  list: () => api.get('/promotions').then((r) => r.data),
  etudiants: (id) => api.get(`/promotions/${id}/etudiants`).then((r) => r.data),
};

export const sessionsApi = {
  ouvrir: (titre, promotionId) => api.post('/sessions', { titre, promotionId }).then((r) => r.data),
  detail: (id) => api.get(`/sessions/${id}`).then((r) => r.data),
  presences: (id) => api.get(`/sessions/${id}/presences`).then((r) => r.data),
  cloturer: (id) => api.post(`/sessions/${id}/cloturer`).then((r) => r.data),
};

export const presencesApi = {
  marquer: (code, etudiantId) => api.post('/presences', { code, etudiantId }).then((r) => r.data),
  manuelle: (sessionId, etudiantId) => api.post('/presences/manuelle', { sessionId, etudiantId }).then((r) => r.data),
};

export const exercicesApi = {
  deposer: (sessionId, etudiantId, lien) => api.post('/exercices', { sessionId, etudiantId, lien }).then((r) => r.data),
  detail: (id) => api.get(`/exercices/${id}`).then((r) => r.data),
};

export const relecturesApi = {
  detail: (id) => api.get(`/relectures/${id}`).then((r) => r.data),
  rendre: (id, note, commentaire) => api.post(`/relectures/${id}`, { note, commentaire }).then((r) => r.data),
  reassigner: (id, nouveauRelecteurId) => api.post(`/relectures/${id}/reassigner`, { nouveauRelecteurId }).then((r) => r.data),
  parEtudiant: (etudiantId) => api.get(`/etudiants/${etudiantId}/relectures`).then((r) => r.data),
  notesRecues: (etudiantId) => api.get(`/etudiants/${etudiantId}/notes`).then((r) => r.data),
};

export const tableauApi = {
  pourPromotion: (promotionId) => api.get('/tableau', { params: { promotionId } }).then((r) => r.data),
};
