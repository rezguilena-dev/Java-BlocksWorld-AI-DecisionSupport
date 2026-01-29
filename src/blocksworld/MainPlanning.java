package blocksworld;

import blocksworld.modelling.WorldConfig;
import blocksworld.planning.*;
import java.util.*;
import modelling.*;
import planning.*;
/**
 * Classe exécutable principale 
 *
 * Cette classe compare les performances de différents algorithmes de recherche
 * pour résoudre un problème de Monde des Blocs donné.
 *
 * Algorithmes testés :
 * - BFS (Breadth-First Search) : Recherche en largeur (optimal pour coût uniforme).
 * - DFS (Depth-First Search) : Recherche en profondeur (rapide mais pas optimal).
 * - Dijkstra : Recherche de coût uniforme (garantit l'optimalité).
 * - A* (A Star)  : Recherche heuristique 
 *
 * Le programme affiche les statistiques (temps, nombre de noeuds éxplorés ) pour chaque
 * planificateur, puis lance une simulation graphique du plan trouvé.
 */
public class MainPlanning {

    public static void main(String[] args) {

        // === CONFIGURATION ===
        int nbBlocs = 3;
        int nbPiles = 4; 
        
        WorldConfig config = new WorldConfig(nbBlocs, nbPiles);
        Set<Variable> variables = config.getVariables(); 

        // === ETAT INITIAL ===
        Map<Variable, Object> initial = new HashMap<>();
   
        initial.put(config.getOnVariable(0), -1);  
        initial.put(config.getOnVariable(1), 0);   
        initial.put(config.getOnVariable(2), -2);  

        initial.put(config.getFixedVariable(0), true);
        initial.put(config.getFixedVariable(1), false);
        initial.put(config.getFixedVariable(2), false);

        initial.put(config.getFreeVariable(1), false);
        initial.put(config.getFreeVariable(2), false);
        initial.put(config.getFreeVariable(3), true);

        // === BUT ===
        Map<Variable, Object> goalMap = new HashMap<>();
        goalMap.put(config.getOnVariable(1), 2);   
        goalMap.put(config.getOnVariable(2), -1);  
        goalMap.put(config.getOnVariable(0), -2);  

        BasicGoal goal = new BasicGoal(goalMap);

        // === ACTIONS ===
        BlocksWorldPlanner bw = new BlocksWorldPlanner(nbBlocs, nbPiles, config);
        Set<Action> actions = bw.getAllActions();

        // === PLANNERS ===
        BFSPlanner bfs = new BFSPlanner(initial, actions, goal);
        bfs.activateNodeCount(true);
        
        DFSPlanner dfs = new DFSPlanner(initial, actions, goal);
        dfs.activateNodeCount(true);
        
        DijkstraPlanner dijkstra = new DijkstraPlanner(initial, actions, goal);
        dijkstra.activateNodeCount(true);
        
       
        Heuristic hMisplaced = new MisplacedGoalHeuristic(goalMap); 
        AStarPlanner astarMisplaced = new AStarPlanner(initial, actions, goal, hMisplaced);
        astarMisplaced.activateNodeCount(true);

        Heuristic hFreeing = new BlockFreeingHeuristic(goalMap);
        AStarPlanner astarFreeing = new AStarPlanner(initial, actions, goal, hFreeing);
        astarFreeing.activateNodeCount(true);

        Planner[] planners = { bfs, dfs, dijkstra, astarMisplaced, astarFreeing };
        String[] names = {"BFS", "DFS", "Dijkstra", "A* (Misplaced)", "A* (BlockFreeing)"};


        List<Action> foundPlan = null; 
        
        for (int i = 0; i < planners.length; i++) {
            Planner p = planners[i];
            System.out.println("\n===== TEST " + names[i] + " =====");

            long start = System.currentTimeMillis();
            List<Action> plan = p.plan();
            long end = System.currentTimeMillis();

            if (plan == null) {
                System.out.println(" Pas de plan trouvé");
            } else {
                System.out.println(" Plan trouvé en " + plan.size() + " actions :");
                for (Action a : plan) System.out.println("  → " + a);
                
                if (foundPlan == null) foundPlan = plan;
            }
            int nodes = -1;
            if (p instanceof BFSPlanner) {
                nodes = ((BFSPlanner) p).getNodesExplored();
            } else if (p instanceof DFSPlanner) {
                nodes = ((DFSPlanner) p).getNodesExplored();
            } else if (p instanceof DijkstraPlanner) {
                nodes = ((DijkstraPlanner) p).getNodesExplored();
            } else if (p instanceof AStarPlanner) {
                nodes = ((AStarPlanner) p).getNodesExplored();
            }
            
            if (nodes != -1) {
                System.out.println("Noeuds explorés : " + nodes);
            }
            System.out.println("Temps écoulé : " + (end - start) + " ms");
        }
        
        if (foundPlan != null) {
            BlocksWorldDisplay.simulatePlan(foundPlan, initial, nbBlocs, config, "Planification (BFS)");
        } else {
            System.out.println("\nAucun plan trouvé, simulation annulée.");
        }
    }
}