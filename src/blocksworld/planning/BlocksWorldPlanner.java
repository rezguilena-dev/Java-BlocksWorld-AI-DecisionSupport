package blocksworld.planning ;
import blocksworld.modelling.WorldConfig;
import java.util.*;
import modelling.Variable;
import planning.*;

/**
* Génère toutes les actions possibles dans le problème du Monde des Blocs.
* Cette classe sert de générateur d'actions génériques en fonction de la
* configuration du monde et du nombre de blocs et de piles.
*/
public class BlocksWorldPlanner {
    
    private int nbBlocs;
    private int nbPiles;
    private WorldConfig worldConfig;

    /**
    * Construit un planificateur pour le Monde des Blocs.
    * @param nbBlocs nombre total de blocs
    * @param nbPiles nombre de piles disponibles
    * @param worldConfig configuration du monde (variables utilisées)
    */
    public BlocksWorldPlanner(int nbBlocs, int nbPiles, WorldConfig worldConfig) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;
        this.worldConfig = worldConfig;
    }
    /**
    * Retourne l'ensemble des variables du modèle.
    * @return l'ensemble des variables
    */
    public Set<Variable> getVariables() {
        return this.worldConfig.getVariables();
    }

    /**
    * Génère l'ensemble complet des actions possibles pour cette configuration.
    * @return un ensemble d'actions génériques
    */
    public Set<Action> getAllActions() {
        Set<Action> actions = new HashSet<>();
        actions.addAll(generateBlocToBlocActions());
        actions.addAll(generateBlocToPileActions());
        actions.addAll(generatePileToBlocActions());
        actions.addAll(generatePileToPileActions());
        return actions;
    }

    /**
    * Génère les actions permettant de déplacer un bloc posé sur un bloc vers un autre bloc.
    * @return ensemble des actions possibles pour ce type de déplacement
    */
    private Set<Action> generateBlocToBlocActions() {
        Set<Action> actions = new HashSet<>();
        
        // b : le bloc à déplacer
        for (int b = 0; b < nbBlocs; b++) {
            // bPrime : le bloc sur lequel bloc b est positionné
            for (int bPrime = 0; bPrime < nbBlocs; bPrime++) {
                if (b == bPrime) continue; 
                
                // b2 : le bloc de destination 
                for (int b2 = 0; b2 < nbBlocs; b2++) {
                    if (b2 == b || b2 == bPrime) continue; 

                    // --- Préconditions ---
                    Map<Variable, Object> pre = new HashMap<>();
                    pre.put(worldConfig.getOnVariable(b), bPrime);      // b est sur bPrime
                    pre.put(worldConfig.getFixedVariable(b), false);    // b est libre 
                    pre.put(worldConfig.getFixedVariable(b2), false);   // b2 est libre 

                    // --- Effets ---
                    Map<Variable, Object> eff = new HashMap<>();
                    eff.put(worldConfig.getOnVariable(b), b2);          // b va sur b2
                    eff.put(worldConfig.getFixedVariable(bPrime), false); // bPrime devient libre
                    eff.put(worldConfig.getFixedVariable(b2), true);      // b2 devient occupé 

                    actions.add(new BasicAction(pre, eff, 1));
                }
            }
        }
        return actions;
    }
    /** 
    * Génère les actions permettant de déplacer un bloc posé sur un bloc vers une pile vide.
    * @return ensemble des actions possibles pour ce type de déplacement
    */
    private Set<Action> generateBlocToPileActions() {
        Set<Action> actions = new HashSet<>();
        
        for (int b = 0; b < nbBlocs; b++) {
            for (int bPrime = 0; bPrime < nbBlocs; bPrime++) {
                if (b == bPrime) continue;
                
                // p : la pile de destination
                for (int p = 0; p < nbPiles; p++) {
                    int pileIdx = p + 1; 

                    // --- Préconditions ---
                    Map<Variable, Object> pre = new HashMap<>();
                    pre.put(worldConfig.getOnVariable(b), bPrime);        // b est sur bPrime
                    pre.put(worldConfig.getFixedVariable(b), false);      // b est libre
                    pre.put(worldConfig.getFreeVariable(pileIdx), true);  // la pile p est vide

                    // --- Effets ---
                    Map<Variable, Object> eff = new HashMap<>();
                    eff.put(worldConfig.getOnVariable(b), -pileIdx);      // b va dans la pile p 
                    eff.put(worldConfig.getFixedVariable(bPrime), false); // bPrime devient libre
                    eff.put(worldConfig.getFreeVariable(pileIdx), false); // la pile p n'est plus vide

                    actions.add(new BasicAction(pre, eff, 1));
                }
            }
        }
        return actions;
    }

    /**
    * Génère les actions permettant de déplacer un bloc provenant d’une pile vers un bloc libre.
    * @return ensemble des actions possibles pour ce type de déplacement
    */
    private Set<Action> generatePileToBlocActions() {
        Set<Action> actions = new HashSet<>();
        // b : le bloc à déplacer
        for (int b = 0; b < nbBlocs; b++) {
            // p : la pile d'origine
            for (int p = 0; p < nbPiles; p++) {
                int pileIdx = p + 1;

                // bPrime : le bloc de destination
                for (int bPrime = 0; bPrime < nbBlocs; bPrime++) {
                    if (b == bPrime) continue;

                    // --- Préconditions ---
                    Map<Variable, Object> pre = new HashMap<>();
                    pre.put(worldConfig.getOnVariable(b), -pileIdx);      // b est dans la pile p
                    pre.put(worldConfig.getFixedVariable(b), false);      // b est libre 
                    pre.put(worldConfig.getFixedVariable(bPrime), false); // bPrime est libre

                    // --- Effets ---
                    Map<Variable, Object> eff = new HashMap<>();
                    eff.put(worldConfig.getOnVariable(b), bPrime);        // b va sur bPrime
                    eff.put(worldConfig.getFreeVariable(pileIdx), true);  // la pile p devient vide
                    eff.put(worldConfig.getFixedVariable(bPrime), true);  // bPrime devient occupé

                    actions.add(new BasicAction(pre, eff, 1));
                }
            }
        }
        return actions;
    }
    /**
    * Génère les actions permettant de déplacer un bloc d’une pile vers une autre pile vide.
    * @return ensemble des actions possibles pour ce type de déplacement
    */
    private Set<Action> generatePileToPileActions() {
        Set<Action> actions = new HashSet<>();
        // b : bloc à déplacer
        for (int b = 0; b < nbBlocs; b++) {
            // p : pile d'origine
            for (int p = 0; p < nbPiles; p++) {
                int pileOrigine = p + 1;

                // pPrime : pile de destination
                for (int pPrime = 0; pPrime < nbPiles; pPrime++) {
                    int pileDest = pPrime + 1;
                    if (pileOrigine == pileDest) continue;

                    // --- Préconditions ---
                    Map<Variable, Object> pre = new HashMap<>();
                    pre.put(worldConfig.getOnVariable(b), -pileOrigine);   // b est dans pileOrigine
                    pre.put(worldConfig.getFixedVariable(b), false);       // b est libre
                    pre.put(worldConfig.getFreeVariable(pileDest), true);  // pileDest est vide

                    // --- Effets ---
                    Map<Variable, Object> eff = new HashMap<>();
                    eff.put(worldConfig.getOnVariable(b), -pileDest);      // b va dans pileDest
                    eff.put(worldConfig.getFreeVariable(pileOrigine), true); // pileOrigine devient vide
                    eff.put(worldConfig.getFreeVariable(pileDest), false); // pileDest n'est plus vide

                    actions.add(new BasicAction(pre, eff, 1));
                }
            }
        }
        return actions;
    }
    /**
    * Retourne la configuration du monde utilisée.
    * @return l'objet WorldConfig associé
    */
    public WorldConfig getWorldConfig() {
         return this.worldConfig;
    }
}