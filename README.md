# Java-BlocksWorld-AI-DecisionSupport

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![AI](https://img.shields.io/badge/AI-Decision_Support-blue?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Finished-brightgreen?style=for-the-badge)

> **Projet : Aide à la Décision et Intelligence Artificielle (L3 Informatique)** 
> *Université de Caen Normandie — 2025-2026* 

Ce projet consiste en une implémentation d'un système décisionnel complet appliqué au domaine du **Monde des Blocs** (*Blocks World*), capable de modéliser des états formels complexes , de calculer des séquences de déplacements optimaux et de résoudre des problèmes de satisfaction de contraintes (CSP). Le projet inclut un module d'extraction de connaissances, capable d'analyser des bases de données d'états afin d'identifier automatiquement des motifs récurrents et des règles d'association .
---

## Architecture et Modules Techniques

Le projet est divisé en packages modulaires, chacun gérant une facette spécifique de l'intelligence artificielle

| Module | Rôle et Fonctionnalités |
| :--- | :--- |
| **`modelling`** | Gestion des variables (positions, états) et des contraintes (unicité, régularité, croissance). |
| **`planning`** | Moteur de recherche d'états et de séquences d'actions(DFS, BFS, Dijkstra, A*)pour atteindre un but défini. |
| **`cp`** | Solveurs de contraintes génériques (Backtracking, MAC) pour la génération de configurations valides. |
| **`datamining`** | Extraction de motifs fréquents et de règles d'association via l'algorithme Apriori. |
| **`blocksworld`** | Implémentation spécifique au domaine des blocs et interface utilisateur Swing. |

---

## Intelligence Artificielle et Algorithmes

Le projet met en œuvre des stratégies avancées pour la résolution de problèmes complexes :

### 1. Planification et Recherche d'États
Le module de planification explore l'espace des états pour déterminer la séquence d'actions optimale entre un état initial et un but:
* **Stratégies de recherche :** Implémentation d'algorithmes de recherche non-informée (**DFS**, **BFS**) et informée (**Dijkstra**, **A***).


* **Heuristiques Admissibles :** Optimisation de la recherche via `MisplacedGoalHeuristic`  et `BlockFreeingHeuristic`.
* **Actions :** Gestion de 4 types de déplacements de blocs avec préconditions et effets atomiques.

### 2. Satisfaction de Contraintes (CSP)
Utilisation de solveurs dans le but de générer des configurations valides respectant des règles logiques (configurations régulières ou croissantes):
* **Solveurs :** Implémentation d'un solveur **Backtrack**  et d'un solveur **MAC** (*Maintaining Arc Consistency*) .
* **Heuristiques de Variables :** Optimisation de l'ordre d'assignation via `DomainSizeVariableHeuristic`  et `NbConstraintsVariableHeuristic` .
* **Heuristiques de Valeurs :** Stratégie de sélection via `RandomValueHeuristic` pour explorer l'espace des solutions.
* **Filtrage :** Algorithmes de maintien de l'**Arc-Consistance** pour réduire dynamiquement l'espace de recherche.

### 3. Découverte de Connaissances
* **Analyse :** Identification de motifs récurrents et de règles d'association (fréquence et confiance) via l'algorithme **Apriori**.
* **Propositionnalisation :** Traduction des états physiques en variables booléennes pour permettre le minage de données.

---

## Installation & Exécution

Le projet dépend des bibliothèques externes `blocksworld.jar` et `bwgenerator.jar` fournies par l'équipe pédagogique de l'Université de Caen et situées dans le dossier `lib/`.

### 1. Compilation
Les fichiers sources sont compilés et placés dans le répertoire `out/`. Depuis la racine du projet  :
```
javac -d out -cp "lib/blocksworld.jar:lib/bwgenerator.jar" -sourcepath src src/blocksworld/*.java
 ```

### 2.Execution 
Chaque partie possède sa propre classe exécutable :
  #### 2-1 Modelisation : 
```
java -cp "out:lib/blocksworld.jar:lib/bwgenerator.jar" blocksworld.MainModelling
 ```

#### 2-2 Planification :
```
java -cp "out:lib/blocksworld.jar:lib/bwgenerator.jar" blocksworld.MainPlanning
 ```
#### 2-3 Satisfaction de contraintes :
```
java -cp "out:lib/blocksworld.jar:lib/bwgenerator.jar" blocksworld.MainCSP
 ```
#### 2-4 Datamining :
```
java -cp "out:lib/blocksworld.jar:lib/bwgenerator.jar" blocksworld.MainMining
 ```
### 3.Documentation : 
Pour générer la documentation technique du projet dans le dossier doc/ :
```
javadoc -d doc -sourcepath src -subpackages blocksworld cp datamining modelling planning -classpath "lib/*"
 ```

## Organisation et Arborescence: 
   ```
.
├── src/                # Code source Java (.java)
│   ├── blocksworld/    # Classes métier et exécutables (Main)
│   ├── modelling/      # Classes génériques de modélisation
│   ├── planning/       # Moteur de planification générique
│   ├── cp/             # Solveurs de contraintes génériques
│   └── datamining/     # Algorithmes de fouille de données
├── lib/                # Bibliothèques externes (.jar)
├── out/                # Fichiers compilés (.class)
├── doc/                # Javadoc et Rapport PDF
└── README.md
   ```

## Équipe de Développement
    Lena REZGUI
    Mohamed Yassine LAMAIRI



