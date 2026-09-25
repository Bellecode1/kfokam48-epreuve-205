# KFOKAM48 — Présence & Relecture

Application de gestion des présences et de la relecture par les pairs.
Épreuve finale fullstack — matricule 205.

## Frontend choisi : React

React (Vite) parce que l'application ne comporte que trois écrans, sans besoin de SEO ni de rendu serveur, et que React permet une mise en place rapide avec Vite.

## Stack

- Backend : Java 17, Spring Boot 4.0.8, Maven (wrapper `mvnw`)
- Frontend : React 18 + Vite, axios, react-router-dom
- Base : H2 en dev, PostgreSQL en Docker (profil `docker`)
- Migrations : Flyway
- Conteneurs : Docker Compose (optionnel)

## Démarrage

### Option 1 — Manuel (recommandé, testé)

**Prérequis :** Java 17+ et Node 18+.

**Backend :**

    cd backend
    ./mvnw spring-boot:run

Le backend démarre sur http://localhost:8080. Les migrations Flyway s'appliquent au démarrage et les données de démonstration sont chargées automatiquement.

**Frontend :**

    cd frontend
    npm install
    npm run dev

Ouvrir http://localhost:5173 dans le navigateur.

### Option 2 — Docker Compose

**Prérequis :** Docker et Docker Compose.

    docker compose up

- Frontend : http://localhost:5173
- Backend : http://localhost:8080
- PostgreSQL : localhost:5432

## Comptes de démonstration

Aucun mot de passe (voir Q1 du client). L'utilisateur choisit son nom dans une liste.

Promotions chargées au démarrage :

- KF48-YAO (Sara ALIOUM, Karim BELLO, Léa CHOUPO)
- KF48-DOU (Yann DUPONT, Paul EYENGA)

## Documentation

- `docs/CAHIER_DES_CHARGES.md` — cahier des charges complet (10 sections)
- `docs/JOURNAL.md` — journal de bord
- `docs/diagrammes/` — diagrammes Mermaid (D1 cas d'usage, D2 classes, D3 séquence, D4 états)
- `api/contrat.yaml` — contrat d'API OpenAPI
- `CHANGELOG.md` — historique

## Endpoints principaux

- `POST /api/sessions` — ouvrir une session
- `POST /api/presences` — marquer sa présence
- `POST /api/exercices` — déposer un exercice
- `POST /api/relectures/{id}` — rendre une relecture
- `GET /api/tableau?promotionId=` — tableau formateur

Format d'erreur : `{ "code": "...", "message": "..." }`

## Tests

    cd backend
    ./mvnw test

Trois tests : unitaire (note 0–20), intégration (tableau 200/404), concurrence (marquage simultané).
