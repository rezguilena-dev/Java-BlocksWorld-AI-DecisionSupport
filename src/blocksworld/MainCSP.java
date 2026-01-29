package blocksworld;

import blocksworld.modelling.BlockWorld;
import blocksworld.modelling.CroissanceGenerator;
import blocksworld.modelling.ImplicationGenerator;
import blocksworld.modelling.WorldConfig;
import cp.BacktrackSolver;
import cp.DomainSizeVariableHeuristic;
import cp.HeuristicMACSolver;
import cp.MACSolver;
import cp.RandomValueHeuristic;
import cp.Solver;
import java.util.*;
import modelling.*;

/**
 * Classe exécutable principale pour la Partie 3 : Satisfaction de Contraintes (CSP).
 *
 * Cette classe a pour but de comparer les performances de trois algorithmes de résolution :
 * - BacktrackSolver : Algorithme naïf.
 * - MACSolver : Algorithme utilisant l'Arc-Consistance (AC-1) pour élaguer l'arbre.
 * - HeuristicMACSolver : Algorithme MAC optimisé avec des heuristiques de choix de variable   et de valeur (Aléatoire).
 *
 * Elle exécute séquentiellement les tests demandés dans les exercices 9 et 10 :
 * 1. Configuration Régulière (Exercice 9).
 * 2. Configuration Croissante (Exercice 10a).
 * 3. Configuration Régulière ET Croissante (Exercice 10b).
 */
public class MainCSP {
    public static void main(String[] args) {
        System.out.println("=== PARTIE 3 : SATISFACTION DE CONTRAINTES (CSP) ===");
        
        // 1. Lancer le test de régularité
        lancerTestRegularite(6, 4);

        // 2. Lancer le test Croissant
        lancerTestCroissance(8, 4);

        // 3. Lancer le test Combiné
        lancerTestRegulariteEtCroissance(8, 4);
        
        System.out.println("\n>>> Tous les tests CSP sont terminés.");
    }

    /**
     * Prépare et lance la résolution pour le problème "Configuration Régulière" (Exercice 9).
     *
     * Utilise ImplicationGenerator pour générer les contraintes de régularité
     * (écart constant entre les blocs d'une même pile).
     *
     * @param nbBlocs Le nombre de blocs du monde.
     * @param nbPiles Le nombre de piles du monde.
     */
    public static void lancerTestRegularite(int nbBlocs, int nbPiles) {
        System.out.println("\n>>> Lancement Exercice 9 : Configuration Régulière (" + nbBlocs + " blocs, " + nbPiles + " piles)");
        
        // Initialisation
        BlockWorld bw = new BlockWorld(nbBlocs, nbPiles);
        Set<Constraint> constraints = new HashSet<>(bw.getConstraints()); // Base
        
        // Ajout contraintes Régulière
        ImplicationGenerator regGen = new ImplicationGenerator(bw);
        constraints.addAll(regGen.generateImplicationConstraint());

        // Exécution
        runSolvers(bw.getVariables(), constraints, nbBlocs, bw.worldConfig, "Exercice 9: Régulière", 50, 50);
    }

    /**
     * Prépare et lance la résolution pour le problème "Configuration Croissante" (Exercice 10a).
     *
     * Utilise CroissanceGenerator pour générer les contraintes de croissance
     * (un bloc ne peut être posé que sur un bloc plus petit).
     *
     * @param nbBlocs Le nombre de blocs du monde.
     * @param nbPiles Le nombre de piles du monde.
     */
    public static void lancerTestCroissance(int nbBlocs, int nbPiles) {
        System.out.println("\n>>> Lancement Exercice 10a : Configuration Croissante (" + nbBlocs + " blocs, " + nbPiles + " piles)");

        // Initialisation
        BlockWorld bw = new BlockWorld(nbBlocs, nbPiles);
        Set<Constraint> constraints = new HashSet<>(bw.getConstraints()); 

        // Ajout contraintes Croissante
        CroissanceGenerator croissGen = new CroissanceGenerator(bw);
        constraints.addAll(croissGen.generateCroissanceConstraint());

        // Exécution
        runSolvers(bw.getVariables(), constraints, nbBlocs, bw.worldConfig, "Exercice 10a: Croissante", 700, 50);
    }

    /**
     * Prépare et lance la résolution pour le problème combiné (Exercice 10b).
     *
     * Combine les contraintes de régularité ET de croissance.
     *
     * @param nbBlocs Le nombre de blocs du monde.
     * @param nbPiles Le nombre de piles du monde.
     */
    public static void lancerTestRegulariteEtCroissance(int nbBlocs, int nbPiles) {
        System.out.println("\n>>> Lancement Exercice 10b : Régulière & Croissante (" + nbBlocs + " blocs, " + nbPiles + " piles)");

        // Initialisation
        BlockWorld bw = new BlockWorld(nbBlocs, nbPiles);
        Set<Constraint> constraints = new HashSet<>(bw.getConstraints()); 

        // Ajout contraintes Régulière
        ImplicationGenerator regGen = new ImplicationGenerator(bw);
        constraints.addAll(regGen.generateImplicationConstraint());

        // Ajout contraintes Croissante
        CroissanceGenerator croissGen = new CroissanceGenerator(bw);
        constraints.addAll(croissGen.generateCroissanceConstraint());

        // Exécution
        runSolvers(bw.getVariables(), constraints, nbBlocs, bw.worldConfig, "Exercice 10b: Régulière + Croissante", 1300, 50);
    }

    /**
     * Méthode générique pour exécuter  des solveurs sur un problème donné.
     *
     * Cette méthode :
     * 1. Instancie les solveurs (Backtrack, MAC, HeuristicMAC).
     * 2. Mesure le temps d'exécution pour trouver une solution.
     * 3. Affiche la solution trouvée graphiquement (via BlocksWorldDisplay).
     *
     * @param variables L'ensemble des variables du problème.
     * @param constraints L'ensemble des contraintes à satisfaire.
     * @param nbBlocs Le nombre de blocs (pour l'affichage).
     * @param config La configuration du monde (pour l'affichage).
     * @param testTitle Le titre de la fenêtre graphique.
     * @param x La position X de la fenêtre sur l'écran.
     * @param y La position Y de la fenêtre sur l'écran.
     */

    public static void runSolvers(Set<Variable> variables, Set<Constraint> constraints, 
                                  int nbBlocs, WorldConfig config, String testTitle, 
                                  int x, int y) {
        
        System.out.println("\n" + "=".repeat(20) + " " + testTitle + " " + "=".repeat(20));

        Solver[] solvers = {
            new BacktrackSolver(variables, constraints),
            new MACSolver(variables, constraints),
            new HeuristicMACSolver(variables, constraints, 
                new DomainSizeVariableHeuristic(false), 
                new RandomValueHeuristic(new java.util.Random()))
        };
        String[] names = {"BacktrackSolver", "MACSolver", "HeuristicMACSolver"};

        for (int i = 0; i < solvers.length; i++) {
            System.out.println("--- Lancement de " + names[i] + " ---");
            long start = System.currentTimeMillis();
            
            Map<Variable, Object> solution = solvers[i].solve();
            
            long end = System.currentTimeMillis();

            if (solution == null) {
                System.out.println("PAS DE SOLUTION TROUVÉE.");
            } else {
                System.out.println("Solution trouvée !");
                if (i == solvers.length-1) { 
                    BlocksWorldDisplay.displayState(solution, nbBlocs, config, testTitle + " (" + names[i] + ")", x, y);
                }
            }
            System.out.println("Temps écoulé : " + (end - start) + " ms");
        }
    }
}