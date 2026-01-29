package cp;

import modelling.*;            // Importe Variable, Constraint, UnaryConstraint, etc.
import java.util.*;
 // Requis pour trier l'affichage de la solution

/**
 * Classe principale pour tester les différents solveurs de CSP.
 * 1. Teste le BacktrackSolver simple sur un problème de base.
 * 2. Teste le MACSolver sur un problème plus complexe.
 * 3. Teste le HeuristicMACSolver sur ce même problème complexe.
 */
public class Main {

    public static void main(String[] args) {

        // --- 1. Définition commune des variables et du domaine ---
        // Les variables et leur domaine sont partagés par tous les tests.
        Set<Object> domain = Set.of(1, 2, 3); 
        Variable varX = new Variable("X", domain);
        Variable varY = new Variable("Y", domain);
        Variable varZ = new Variable("Z", domain);
        Set<Variable> variables = Set.of(varX, varY, varZ);

        
        // ====================================================================
        System.out.println("========= TEST 1 : BacktrackSolver =========");
        // ====================================================================

        // Problème 1: X=2, X!=Y, Y=1 -> Z=1
        Constraint c1_1 = new UnaryConstraint(varX, Set.of(2));
        Constraint c1_2 = new DifferenceConstraint(varX, varY);
        Constraint c1_3 = new Implication(varY, Set.of(1), varZ, Set.of(1));
        Set<Constraint> constraints1 = Set.of(c1_1, c1_2, c1_3);

        // Affichage des contraintes pour ce test
        System.out.println("Contraintes à résoudre :");
        constraints1.forEach(c -> System.out.println("  - " + c));

        // Création et lancement du solveur 1
        Solver solver1 = new BacktrackSolver(variables, constraints1);
        Map<Variable, Object> solution1 = solver1.solve();

        // Affichage du résultat (via la méthode utilitaire)
        printSolution(solution1);


        // ====================================================================
        System.out.println("\n========= TEST 2 : MACSolver =========");
        // ====================================================================

        // Problème 2: Toutes différentes, Y \in {1, 2}, X=1 -> Z=2
        Constraint c2_1 = new DifferenceConstraint(varX, varY); // X != Y
        Constraint c2_2 = new DifferenceConstraint(varY, varZ); // Y != Z
        Constraint c2_3 = new DifferenceConstraint(varX, varZ); // X != Z
        Constraint c2_4 = new UnaryConstraint(varY, Set.of(1, 2)); // Y \in {1, 2}
        Constraint c2_5 = new Implication(varX, Set.of(1), varZ, Set.of(2)); // X=1 -> Z=2

        Set<Constraint> constraints2 = Set.of(c2_1, c2_2, c2_3, c2_4, c2_5);

        // Affichage des contraintes pour ce test
        System.out.println("Contraintes à résoudre :");
        constraints2.forEach(c -> System.out.println("  - " + c));

        // Création et lancement du solveur 2 (MACSolver)
        Solver solver2 = new MACSolver(variables, constraints2);
        Map<Variable, Object> solution2 = solver2.solve();

        // Affichage du résultat
        printSolution(solution2);

        
        // ====================================================================
        System.out.println("\n========= TEST 3 : HeuristicMACSolver  =========");
        // ====================================================================

        // Le problème est le même que le Test 2 (constraints2)
        System.out.println("Contraintes à résoudre :");
        constraints2.forEach(c -> System.out.println("  - " + c));
        
        System.out.println("Configuration des heuristiques :");

        // Heuristique de variable : "Minimum Remaining Values" (MRV)
        // On choisit la variable avec le plus petit domaine (false)
        VariableHeuristic varHeuristic = new DomainSizeVariableHeuristic(false);
        System.out.println("  - Heuristique Variable: DomainSizeVariableHeuristic (plus petit domaine)");

        // Heuristique de valeur : Ordre aléatoire
        ValueHeuristic valHeuristic = new RandomValueHeuristic(new Random());
        System.out.println("  - Heuristique Valeur: RandomValueHeuristic");

        // Création et lancement du solveur 3 (HeuristicMACSolver)
        Solver solver3 = new HeuristicMACSolver(variables, constraints2, varHeuristic, valHeuristic);
        Map<Variable, Object> solution3 = solver3.solve();

        // Affichage du résultat
        printSolution(solution3);
    }

    /**
     * Méthode utilitaire pour afficher une solution (ou l'échec).
     * L'affichage est trié par nom de variable pour plus de lisibilité.
     * @param solution La map de solution retournée par un solveur, ou null.
     */
    private static void printSolution(Map<Variable, Object> solution) {
        if (solution != null) {
            System.out.println("  ->  Solution trouvée !");
            
            // Trie l'affichage par nom de variable pour un résultat cohérent
            solution.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Variable::getName)))
                .forEach(entry -> 
                    System.out.println("     " + entry.getKey().getName() + " = " + entry.getValue())
                );

        } else {
            System.out.println("  ->  Aucune solution n'a été trouvée.");
        }
    }
}