# Cahier des charges — KFOKAM48 Présence & Relecture

Auteur : 205 · Version 1 · Frontend choisi : React, parce que l'application ne comporte que trois écrans, sans besoin de SEO ni de rendu serveur, et que React permet une mise en place rapide avec Vite.

## 1. Contexte et objectif

La direction de la formation KFOKAM48 organise des sessions de cours pour plusieurs promotions d'étudiants. Aujourd'hui, le suivi des présences, la collecte des exercices et leur relecture par les pairs se font de manière dispersée : feuilles de présence papier, liens envoyés par messagerie, notes dans un tableur. Résultat : le formateur perd du temps à consolider les informations, et certains étudiants ne sont jamais relus.

L'application **KFOKAM48 Présence & Relecture** centralise tout cela. Pour chaque session de cours, elle permet :

- au **formateur** d'ouvrir une session et d'obtenir un code de présence temporaire ;
- à l'**étudiant** de marquer sa présence avec ce code, puis de déposer le lien de son exercice ;
- au **système** d'assigner automatiquement un relecteur pair à chaque exercice déposé ;
- au **relecteur** de noter l'exercice (note entière sur 20) et de laisser un commentaire ;
- au **formateur** de consulter un tableau de bord par promotion : présences, exercices déposés, moyenne des notes, relectures en attente.

**Objectif :** donner au formateur une vision fiable et en temps réel du suivi pédagogique d'une promotion, et garantir que chaque exercice déposé est relu par un pair, de manière traçable.

## 2. Acteurs et rôles

| Acteur | Ce qu'il peut faire |
|---|---|
| **Formateur** | Ouvrir une session et obtenir un code. Clôturer une session (fige les notes). Consulter le tableau de bord d'une promotion. Ajouter manuellement une présence (marquée « ajouté par le formateur »). Réassigner une relecture en souffrance. |
| **Étudiant** | Marquer sa présence avec le code. Déposer le lien de son exercice. Consulter la note et le commentaire reçus (sans voir le nom du relecteur). |
| **Relecteur** | C'est un étudiant à qui le système a assigné l'exercice d'un pair. Il consulte l'exercice, saisit une note entière de 0 à 20 et un commentaire, puis valide. Il peut modifier sa note tant que la session n'est pas clôturée. |
| **Système** | Assigne automatiquement un relecteur à chaque exercice déposé, au hasard parmi les étudiants présents. Gère l'expiration du code (15 min). Bloque un étudiant après 5 erreurs de code pendant 2 minutes. |

**Remarque :** un même étudiant peut être **étudiant** (pour son propre exercice) et **relecteur** (pour l'exercice d'un pair) dans la même session. Ce ne sont pas des comptes différents, ce sont des rôles qui s'activent selon l'action.

## 3. Périmètre

**Inclus dans le périmètre :**

- Gestion des sessions de cours : ouverture par le formateur, génération d'un code de présence, clôture de session.
- Pointage des présences : par code pour l'étudiant, ajout manuel pour le formateur.
- Dépôt d'un exercice par l'étudiant, sous forme de lien (URL).
- Assignation automatique d'un relecteur pair, au hasard, parmi les étudiants présents à la session.
- Relecture d'un exercice : note entière de 0 à 20, commentaire, validation, et modification tant que la session n'est pas clôturée.
- Tableau de bord formateur par promotion : présences, exercices déposés, moyenne des notes reçues, relectures en attente.
- Réassignation manuelle d'une relecture en souffrance par le formateur.
- API REST conforme au contrat `api/contrat.yaml`.
- Interface web à trois écrans : formateur, étudiant, relecteur.

**Explicitement exclu du périmètre :**

- Authentification par mot de passe (Q1 : l'étudiant choisit son nom dans une liste).
- Upload de fichiers : on ne stocke qu'un lien vers un exercice externe.
- Messagerie interne entre utilisateurs.
- Notifications par email ou SMS.
- Paiement, facturation, gestion administrative des promotions.
- Application mobile native (l'interface web doit être utilisable sur mobile, mais on ne développe pas d'app iOS/Android).
- Génération de bulletins PDF ou exports Excel.
- Gestion multi-établissements : une seule instance pour KFOKAM48.

## 4. Exigences fonctionnelles

| Réf | Exigence | Critère d'acceptation | Priorité |
|---|---|---|---|
| EF1 | Le formateur ouvre une session de cours | Quand je crée une session avec un titre et une promotion, l'API me renvoie un code de présence et une date d'expiration | Must |
| EF2 | L'étudiant marque sa présence avec un code | Quand je saisis un code valide et non expiré, ma présence est enregistrée et apparaît dans le tableau du formateur | Must |
| EF3 | Le formateur peut ajouter une présence manuellement | Quand le formateur ajoute une présence, elle est enregistrée avec la source `FORMATEUR` et visible comme telle | Must |
| EF4 | L'étudiant dépose le lien de son exercice | Quand je soumets une URL valide pour une session, l'exercice est créé avec le statut `DEPOSE` | Must |
| EF5 | L'étudiant peut remplacer le lien de son exercice | Tant que personne n'a commencé à relire, je peux modifier le lien de mon exercice | Must |
| EF6 | Le système assigne un relecteur à chaque exercice déposé | Dès qu'un exercice est déposé, un relecteur est choisi au hasard parmi les étudiants présents à la session, différent de l'auteur | Must |
| EF7 | Le relecteur consulte l'exercice qui lui est assigné | Quand une relecture m'est assignée, je vois le lien de l'exercice et le nom de son auteur | Must |
| EF8 | Le relecteur note et commente un exercice | Quand je saisis une note entière entre 0 et 20 et un commentaire, la relecture est enregistrée | Must |
| EF9 | Le relecteur peut modifier sa note avant clôture | Tant que la session n'est pas clôturée, je peux modifier ma note et mon commentaire | Must |
| EF10 | L'étudiant relu consulte sa note et son commentaire | Quand je consulte mon exercice, je vois la note et le commentaire, mais pas le nom du relecteur | Must |
| EF11 | Le formateur consulte le tableau de bord d'une promotion | Quand je consulte le tableau, je vois par étudiant : présences, exercices déposés, moyenne, relectures en attente | Must |
| EF12 | Le formateur clôture une session | Quand je clôture une session, les notes deviennent définitives et ne sont plus modifiables | Must |
| EF13 | Le formateur réassigne une relecture en souffrance | Quand une relecture n'est pas rendue, je peux la réassigner à un autre étudiant présent | Should |
| EF14 | Le formateur voit les relectures en attente dans son tableau | Dans le tableau, une colonne indique le nombre de relectures que chaque étudiant doit encore faire | Must |
| EF15 | Le système bloque un étudiant après 5 erreurs de code | Après 5 saisies de code erronées, l'étudiant est bloqué pendant 2 minutes | Must |
| EF16 | Le système refuse un code expiré | Quand je saisis un code dont la date d'expiration est dépassée, l'API renvoie une erreur `CODE_EXPIRE` (HTTP 410) | Must |
| EF17 | Le système refuse une présence en double | Quand je saisis un code alors que je suis déjà présent, l'API renvoie une erreur `DEJA_PRESENT` (HTTP 409) | Must |
| EF18 | Le système refuse un exercice déjà déposé | Quand je dépose un exercice alors que j'en ai déjà déposé un pour cette session, l'API renvoie une erreur (HTTP 409) | Must |
| EF19 | Le système refuse l'auto-relecture | Quand un étudiant tente de relire son propre exercice, l'API renvoie une erreur `AUTO_RELECTURE_INTERDITE` (HTTP 403) | Must |
| EF20 | Le système refuse une note invalide | Quand le relecteur saisit une note hors 0–20 ou non entière, l'API renvoie une erreur (HTTP 400) | Must |
| EF21 | Le système refuse une relecture déjà rendue | Quand le relecteur tente de modifier une relecture déjà validée après clôture, l'API renvoie une erreur (HTTP 409) | Must |
| EF22 | Le système refuse un dépôt après clôture | Quand un étudiant tente de déposer un exercice après clôture de la session, l'API refuse | Must |
| EF23 | Le système refuse une présence après clôture | Quand un étudiant tente de marquer sa présence après clôture, l'API refuse | Must |
| EF24 | Le formateur voit les exercices en attente de relecture | Dans le tableau, je vois clairement les exercices dont la relecture n'a pas été rendue | Must |

## 5. Exigences non fonctionnelles

| Réf | Exigence | Comment on la vérifie |
|---|---|---|
| ENF1 | L'API doit répondre en moins de 500 ms en usage normal (moins de 100 étudiants par promotion) | Test de charge simple ou mesure manuelle sur les endpoints principaux |
| ENF2 | L'interface web doit être utilisable sur mobile (téléphone) | Test manuel sur un écran de largeur 375 px |
| ENF3 | Les appels API doivent être centralisés dans une couche dédiée côté frontend | Revue de code : aucun `fetch` ou `axios` dispersé dans les composants |
| ENF4 | Les états de chargement et d'erreur doivent être gérés dans l'interface | Test manuel : couper le backend, l'interface affiche un message d'erreur clair |
| ENF5 | Aucune règle métier ne doit être dupliquée dans le frontend | La moyenne affichée vient de l'API, elle n'est pas recalculée côté client |
| ENF6 | Les entrées doivent être validées côté backend | Test : envoyer une note de 25, l'API répond 400 avec un message d'erreur structuré |
| ENF7 | Les erreurs doivent être renvoyées dans un format unique | Toutes les erreurs respectent `{ "code": "...", "message": "..." }` |
| ENF8 | Aucune stack trace ne doit être renvoyée au client | Test : provoquer une erreur serveur, la réponse ne contient pas de trace Java |
| ENF9 | Le schéma de base de données doit être versionné | Présence de migrations Flyway dans le dépôt |
| ENF10 | L'application doit démarrer depuis un clone vierge | Test : cloner le dépôt sur une machine neuve, suivre le README, l'application démarre |
| ENF11 | Des données de démonstration doivent être chargées au démarrage | Au premier lancement, une promotion, des étudiants et une session existent déjà |
| ENF12 | Le code doit être séparé en couches contrôleur / service / repository | Revue de code : aucun accès base dans un contrôleur, aucune entité JPA exposée en JSON |

## 6. Règles de gestion

| Réf | Règle | Source |
|---|---|---|
| RG1 | L'étudiant n'a pas de mot de passe : il choisit son nom dans une liste | Q1 |
| RG2 | Le code de présence expire 15 minutes après l'ouverture de la session | Q2 |
| RG3 | Un étudiant ne peut pas marquer sa présence après la clôture de la session | Q3 |
| RG4 | Après 5 erreurs de code, l'étudiant est bloqué pendant 2 minutes | Q4 |
| RG5 | Un étudiant ne peut jamais relire son propre exercice | Q5 |
| RG6 | Un exercice a un seul relecteur à un instant donné | Q6 |
| RG7 | Le relecteur est choisi au hasard par le système, parmi les étudiants présents à la session | Q7 |
| RG8 | L'étudiant relu voit sa note et son commentaire, mais pas le nom du relecteur | Q8 |
| RG9 | La note est un nombre entier de 0 à 20 | Q9 |
| RG10 | Le relecteur peut modifier sa note tant que la session n'est pas clôturée | Q10 |
| RG11 | Une relecture non rendue laisse l'exercice au statut « en attente », visible dans le tableau du formateur | Q11 |
| RG12 | L'étudiant peut déposer son exercice après la fin de la session, tant que le formateur ne l'a pas clôturée | Q12 |
| RG13 | L'étudiant peut remplacer le lien de son exercice tant que personne n'a commencé à le relire | Q13 |
| RG14 | Le formateur peut ajouter une présence manuellement ; elle est marquée « ajouté par le formateur » | Q14 |
| RG15 | La clôture de la session par le formateur fige définitivement les notes (décision tranchée, voir section 7) | Q10 / Q15 |
| RG16 | Le formateur peut réassigner une relecture en souffrance à un autre étudiant présent (décision tranchée, voir section 7) | Trou identifié (Q6, Q7, Q11) |

## 7. Zones d'ombre, hypothèses et contradictions

| Point | Réponse client (Qx) ou hypothèse | Décision retenue | Pourquoi |
|---|---|---|---|
| Contradiction Q10 / Q15 | Q10 : la note est modifiable tant que la session n'est pas clôturée. Q15 : la note est définitive une fois envoyée. | On tranche en faveur de Q10. La note est modifiable tant que la session n'est pas clôturée. La clôture de session est l'événement qui fige définitivement les notes. | Q10 décrit un usage réel et conditionnel (« tant que le formateur n'a pas clôturé »). Q15 exprime une intention morale mais rend impossible la correction d'une erreur honnête. Q10 est plus précise, plus opérationnelle et plus testable. |
| Trou : relecteur qui ne rend jamais | Q11 dit que l'exercice reste « en attente » et doit être visible dans le tableau. Mais aucune réponse ne dit ce qu'on fait ensuite. Q6 dit « un seul relecteur », ce qui interdit la réassignation. | Le formateur peut réassigner manuellement une relecture en souffrance à un autre étudiant présent. L'unicité du relecteur (Q6) s'applique à un instant donné : un exercice a un seul relecteur **actif** à la fois. La réassignation est tracée. | Sans cette règle, un exercice dont le relecteur disparaît reste bloqué indéfiniment, ce qui contredit l'objectif pédagogique. On ajoute RG16 pour couvrir ce cas. |
| Q3 / Q12 : présence et dépôt après la session | Q3 : pas de présence après la fin de la session. Q12 : dépôt possible après la fin, tant que le formateur n'a pas clôturé. | La présence est bloquée à la clôture de la session. Le dépôt d'exercice reste possible tant que la session n'est pas clôturée. | Les deux réponses ne se contredisent pas : Q3 parle de la « fin » de la session, Q12 parle de la « clôture » par le formateur. On distingue les deux moments. La clôture est l'acte administratif du formateur. |
| Q14 : ajout manuel de présence | Le formateur peut ajouter une présence manuellement, marquée « ajouté par le formateur ». | Le champ `source` de la présence vaut `ETUDIANT` ou `FORMATEUR`. | Imposé par le contrat d'API. Permet de tracer l'origine de chaque présence. |
| Q4 : blocage après 5 erreurs | L'étudiant est bloqué 2 minutes après 5 erreurs. | Le blocage est géré côté serveur, par étudiant et par session. | Un blocage côté client serait contournable. Le blocage est compté par étudiant, pas par adresse IP. |
| Q7 : relecteur choisi « parmi les étudiants présents » | Le relecteur est choisi au hasard parmi les étudiants présents à la session. | Si un seul étudiant est présent, aucun relecteur ne peut être assigné (il ne peut pas se relire lui-même, RG5). L'exercice reste en attente et le formateur est prévenu. | Q5 interdit l'auto-relecture. Si un seul étudiant est présent, il n'y a pas de solution automatique. Le formateur doit pouvoir intervenir. |
| Q8 : anonymat du relecteur | L'étudiant relu voit la note et le commentaire, mais pas le nom du relecteur. | L'API ne renvoie jamais l'identité du relecteur dans les réponses destinées à l'étudiant relu. | Imposé par Q8. Le relecteur reste anonyme pour l'étudiant relu. |

## 8. Contraintes techniques

**Backend — imposé par le sujet :**

- Java 17 ou plus, Maven, wrapper `mvnw` commité.
- Spring Boot.
- Le contrat `api/contrat.yaml` est respecté à la lettre : chemins, verbes, codes de statut, format d'erreur.
- Séparation des couches contrôleur / service / repository. Aucune requête base dans un contrôleur, aucune entité JPA exposée en JSON (passage par des DTO).
- Validation des entrées et gestion centralisée des erreurs (`@RestControllerAdvice`). Une stack trace renvoyée au client est une faute.
- Schéma versionné par Flyway, migrations commitées. `ddl-auto=update` interdit hors tests.
- Deux tests qui prouvent quelque chose : un test unitaire sur une règle métier réelle, un test d'intégration sur un endpoint.

**Frontend — choix : React**

- React (Vite) comme framework frontend. Justification : trois écrans seulement, pas de besoin de SEO ni de rendu serveur, écosystème mature et mise en place rapide.
- Trois écrans : formateur (ouvrir une session, voir le tableau), étudiant (marquer sa présence, déposer son exercice), relecteur (faire une relecture).
- Appels API dans une couche dédiée, pas de `fetch` dispersé.
- États de chargement et d'erreur gérés.
- Aucune règle métier dupliquée : la moyenne affichée vient de l'API.

**Base de données :**

- H2 en développement local (base en mémoire, démarrage rapide).
- PostgreSQL en exécution Docker (profil `docker`).
- Schéma versionné par Flyway, migrations commitées.
- Données de démonstration chargées au démarrage.

**Démarrage :**

- `docker compose up` à la racine du dépôt. Le backend, le frontend et PostgreSQL démarrent ensemble. Testé depuis un clone vierge.

## 9. Livrables

**Dans le dépôt `kfokam48-epreuve-205` :**

- `docs/CAHIER_DES_CHARGES.md` — ce document.
- `docs/JOURNAL.md` — journal de bord, une entrée par étape.
- `docs/diagrammes/` — trois diagrammes en Mermaid (cas d'utilisation, classes, séquence) + un bonus états-transitions.
- `api/contrat.yaml` — contrat d'API complété (5 opérations imposées + opérations ajoutées).
- `backend/` — application Spring Boot (Java 17+, Maven, wrapper `mvnw`).
- `frontend/` — application React (Vite).
- `README.md` — instructions d'installation et de démarrage, testées depuis un clone vierge.
- `CHANGELOG.md` — historique cohérent avec l'historique Git.
- `.gitignore` — Java + JS, posé avant le premier commit de code.
- `docker-compose.yml` — orchestration backend + frontend + PostgreSQL.
- `backend/Dockerfile` et `frontend/Dockerfile` — images Docker.
- Issues GitHub — backlog avec critères d'acceptation, priorisation Must/Should/Could, renvoi aux EFx / RGx.
- Pull requests — une par branche, liées aux issues.

**Dans le dépôt `kfokam48-gitlab-205` (étape 5) :**

- Le dépôt de l'épreuve Git, avec ses branches poussées.

**Sur la plateforme :**

- `SOUMISSION.md` — téléversé avant 18h00, avec les liens des deux dépôts et les hash des commits finaux.

## 10. Démarche prévue

Je travaille dans l'ordre des six étapes imposées par le sujet.

1. **Analyser, spécifier, concevoir** — Je rédige le cahier des charges (10 sections), je produis les trois diagrammes en Mermaid (cas d'utilisation, classes, séquence), je crée le backlog en issues GitHub avec critères d'acceptation et priorisation Must/Should/Could, et je complète le contrat d'API. Je pose le commit `[JALON] analyse` avant d'écrire la moindre ligne de code.

2. **Construire la v0.1** — Je développe uniquement les stories Must. Une branche par ticket, une pull request par branche, les issues fermées par les commits. Je pose le commit `[JALON] v0.1` et je pousse.

3. **Ouvrir l'enveloppe** — J'ouvre l'enveloppe qui contient le bug signalé et le changement de besoin. J'ouvre une issue pour le bug, je le reproduis, j'écris une migration versionnée, je mets à jour le contrat d'API, je re-priorise le backlog, et je sépare le correctif de l'évolution. Je mets à jour le cahier des charges et les diagrammes dans un commit qui le dit.

4. **Livrer la v1.0** — Je pose le commit `[JALON] v1.0`, je rédige le `CHANGELOG.md` cohérent avec l'historique Git, je teste le README depuis un clone vierge, je trie le backlog restant.

5. **Épreuve Git** — Je clone le `git-lab.bundle`, je résous les cinq situations, je pousse toutes les branches dans un dépôt séparé `kfokam48-gitlab-205`.

6. **Soumettre** — Je rédige `SOUMISSION.md`, je relève les hash complets des commits finaux, je vérifie les deux liens depuis une fenêtre de navigation privée, et je téléverse sur la plateforme avant 18h00.

**Definition of Done :** un ticket est terminé quand le code est écrit, testé, commité sur une branche, poussé, la pull request est ouverte et liée à l'issue, et la CI (si présente) passe.
