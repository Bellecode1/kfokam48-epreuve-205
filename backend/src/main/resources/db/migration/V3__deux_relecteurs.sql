-- Changement de besoin (etape 3) : chaque exercice est relu par DEUX pairs.
-- La note retenue est la moyenne des deux.
-- Si un seul relecteur a rendu, la note est provisoire.
ALTER TABLE relecture ADD COLUMN provisoire BOOLEAN NOT NULL DEFAULT FALSE;
