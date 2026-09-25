# Journal de bord — KFOKAM48 Présence & Relecture

Auteur : 205
Une entrée par étape. Format : ce que j'ai fait, ce qui m'a bloqué et combien de temps, ce que j'ai demandé à l'IA et comment j'ai vérifié sa réponse.

---

## Étape 1 — Analyser, spécifier, concevoir

**Fait :**
- Cahier des charges complet en 10 sections, avec 24 exigences fonctionnelles (EF1 à EF24), 12 exigences non fonctionnelles (ENF1 à ENF12) et 16 règles de gestion (RG1 à RG16).
- 4 diagrammes Mermaid : D1 cas d'utilisation, D2 classes, D3 séquence « marquer sa présence », D4 états-transitions d'un exercice (bonus).
- Contrat d'API complété : les 5 opérations imposées + 12 opérations ajoutées pour couvrir le cahier des charges.
- 12 issues créées sur GitHub avec critères d'acceptation, priorité Must/Should et renvoi aux EFx / RGx.
- Commit `[JALON] analyse` posé avant tout commit de code.

**Bloqué :**
- 15 min sur la contradiction Q10 / Q15 du client. Tranché en faveur de Q10 : la note est modifiable tant que la session n'est pas clôturée. Justification écrite en section 7 du cahier des charges.
- 10 min sur le trou du sujet : Q6 + Q7 + Q11 laissent un exercice bloqué si le relecteur ne rend jamais. Décision ajoutée en RG16 : le formateur peut réassigner la relecture.
- 5 min sur la distinction entre « fin de session » (Q3) et « clôture par le formateur » (Q12). Les deux moments sont distingués dans le cahier des charges.

**IA :**
- J'ai demandé à l'IA de relire les 16 questions du client et de repérer les contradictions et les trous. Elle a identifié la contradiction Q10 / Q15 et le trou Q6/Q7/Q11. J'ai vérifié en relisant moi-même CLIENT.md ligne par ligne, puis j'ai tranché et justifié chaque décision dans la section 7 du cahier des charges.
- J'ai demandé à l'IA de proposer un découpage en issues. Elle en a proposé 24 (une par exigence). J'en ai retenu 12 en regroupant les exigences qui concernent le même développement. Vérifié en me demandant, pour chaque issue : « est-ce que le client comprendrait ce titre comme un résultat utilisable ? »
- J'ai fait vérifier la cohérence entre D2 (classes) et D3 (séquence) : les entités du diagramme de classes correspondent aux champs utilisés dans le diagramme de séquence. Vérifié manuellement.

**Durée :** environ 2 h 30.

---

## Étape 3 — Ouvrir l'enveloppe

**Fait :**
- Issue #13 créée pour le bug de concurrence sur les présences
- Test PresenceConcurrenceTest écrit pour reproduire le bug
- Correction appliquée dans PresenceService (try/catch DataIntegrityViolationException)
- Test passe après correction
- README.md corrigé (suppression duplication)
- Dockerfile frontend créé avec nginx.conf

**Bloqué :**
- 20 min sur le test de concurrence : l'exception était wrappée, a dû simplifier le test pour vérifier uniquement l'absence de doublon
- Le changement de besoin (2 relecteurs par exercice) n'a pas été implémenté par manque de temps

**IA :**
- J'ai demandé à l'IA de proposer une correction pour le bug de concurrence. Elle a suggéré d'attraper DataIntegrityViolationException. J'ai adapté en vérifiant toute la chaîne de causes car l'exception était wrappée. Vérifié en lançant le test.

**Durée :** environ 45 min.

---

## Étape 4 — Livrer la v1.0

**Fait :**
- README.md corrigé et complété
- Dockerfile frontend créé
- Jalon v1.0 à poser

**IA :** Non utilisé pour cette étape.

**Durée :** environ 15 min.

---
