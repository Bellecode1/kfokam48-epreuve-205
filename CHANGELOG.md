# Changelog

## v1.0 — Étape 4

- Changement de besoin (étape 3) : deux relecteurs par exercice, note = moyenne. Migration V3.
- Correction du bug de race condition sur marquage simultané de présence (#14).

## v0.1 — Étape 2

- Backend Spring Boot complet : entités, migrations Flyway V1/V2, services, contrôleurs, gestion centralisée des erreurs.
- Frontend React : 3 écrans (formateur, étudiant, relecteur) + couche API dédiée.
- Tests : unitaire (note 0–20), intégration (tableau 200/404), concurrence (présence).
- Docker Compose (backend + frontend + PostgreSQL).
- CORS configuré.

## v0.0 — Étape 1

- Cahier des charges complet (10 sections, 24 EF, 12 ENF, 16 RG).
- 4 diagrammes Mermaid.
- Contrat d'API OpenAPI complété.
- Backlog : 12 issues GitHub.
