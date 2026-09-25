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

## Étape 2 — Construire la v0.1
Fait : backend Spring Boot complet (8 entités, Flyway V1/V2, services, contrôleurs, erreurs, CORS), frontend React 3 écrans, 2 tests, Docker Compose. Jalon v0.1 posé.
Bloqué : 15 min CORS, 20 min packages Spring Boot 4 déplacés, 10 min starter Flyway.
IA : traduction EF/RG en code. Vérifié par curl et tests d'intégration.

## Étape 3 — Ouvrir l'enveloppe
Fait : bug race condition (#14) avec issue AVANT code, test qui reproduit, fix, PR #15. Changement de besoin (#16) : deux relecteurs, migration V3 ajoutée (pas modifiée), RG17, PR #17. Sacrifice : EF13 passe en Could.
IA : identification de la cause du bug. Vérifié par test de concurrence.

## Étape 4 — Livrer la v1.0
Fait : CHANGELOG, README d'installation, jalon v1.0 posé.

## Étape 2 — Construire la v0.1
Fait : backend Spring Boot complet (8 entités, Flyway V1/V2, services, contrôleurs, erreurs, CORS), frontend React 3 écrans, 2 tests, Docker Compose. Jalon v0.1 posé.
Bloqué : 15 min CORS, 20 min packages Spring Boot 4 déplacés, 10 min starter Flyway.
IA : traduction EF/RG en code. Vérifié par curl et tests d'intégration.

## Étape 3 — Ouvrir l'enveloppe
Fait : bug race condition (#14) avec issue AVANT code, test qui reproduit, fix, PR #15. Changement de besoin (#16) : deux relecteurs, migration V3 ajoutée (pas modifiée), RG17, PR #17. Sacrifice : EF13 passe en Could.
IA : identification de la cause du bug. Vérifié par test de concurrence.

## Étape 4 — Livrer la v1.0
Fait : CHANGELOG, README d'installation, jalon v1.0 posé.
