package blocksworld;

import blocksworld.modelling.BlockWorld;
import blocksworld.modelling.CroissanceGenerator;
import blocksworld.modelling.ImplicationGenerator;
import blocksworld.modelling.WorldConfig;
import java.util.*;
import modelling.*;
/**
 * Classe exécutable pour la Partie 1 (Modélisation).
 *
 * Cette classe teste la création des variables et la validation des contraintes
 * sur deux scénarios distincts.
 *
 * Scénarios testés :
 * - Configuration Valide : Une pile ordonnée respectant toutes les règles.
 * - Configuration Invalide : Une pile violant la contrainte de croissance (petit sur grand).
 */
public class MainModelling {

    public static void main(String[] args) {
        System.out.println("=== PARTIE 1 : MODÉLISATION & CONTRAINTES ===");
        
        int n = 4; // Blocs
        int m = 3; // Piles
        
        System.out.println("Monde : " + n + " blocs, " + m + " piles.");
        
        BlockWorld bw = new BlockWorld(n, m);
        WorldConfig config;
        config = bw.worldConfig;
        
        // 2. Récupération des contraintes de base
        Set<Constraint> constraints = bw.getConstraints();
        
        // 3. Ajout des contraintes spécifiques (Régularité & Croissance)
        ImplicationGenerator regGen = new ImplicationGenerator(bw);
        constraints.addAll(regGen.generateImplicationConstraint());
        
        CroissanceGenerator croissGen = new CroissanceGenerator(bw);
        constraints.addAll(croissGen.generateCroissanceConstraint());
        
        System.out.println(constraints.size() + " contraintes générées au total.");

        // 4. Création d'une configuration VALIDE (Régulière et Croissante)

        Map<Variable, Object> state = new HashMap<>();
        
        // 5. Positions
        state.put(config.getOnVariable(0), -3); 
        state.put(config.getOnVariable(1), 0);  

        state.put(config.getOnVariable(2), 1);  
        state.put(config.getOnVariable(3), 2); 
        
        state.put(config.getFixedVariable(0), true);
        state.put(config.getFixedVariable(1), true);
        state.put(config.getFixedVariable(2), true);
        state.put(config.getFixedVariable(3), false); 
        

        state.put(config.getFreeVariable(1), true);
        state.put(config.getFreeVariable(2), true);
        state.put(config.getFreeVariable(3), false);

        System.out.println("\n=== Test d'une configuration VALIDE ===");
        boolean valid = true;
        for (Constraint c : constraints) {
            if (!c.isSatisfiedBy(state)) {
                System.out.println("VIOLATION : " + c);
                valid = false;
            }
        }
        if (valid){
            System.out.println("\nLa configuration est valide ");

        }

                // ===== 6. Création d'une configuration INVALIDE =====
        System.out.println("\n=== Test d'une configuration INVALIDE ===");

        Map<Variable, Object> badState = new HashMap<>();

        badState.put(config.getOnVariable(1), -1); 
        badState.put(config.getOnVariable(0), 1); 

        badState.put(config.getFreeVariable(1), false); 
        badState.put(config.getFreeVariable(2), true);  
        badState.put(config.getFreeVariable(3), true);  

       
        badState.put(config.getFixedVariable(1), true); 
        badState.put(config.getFixedVariable(0), false);

       
        badState.put(config.getOnVariable(2), -2);
        badState.put(config.getOnVariable(3), -3);
        badState.put(config.getFixedVariable(2), false);
        badState.put(config.getFixedVariable(3), false);

        boolean badValid = true;
        for (Constraint c : constraints) {
            if (!c.isSatisfiedBy(badState)) {
                System.out.println("VIOLATION : " + c);
                badValid = false;
            }
        }
    }
}