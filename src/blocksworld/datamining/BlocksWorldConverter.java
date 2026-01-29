package blocksworld.datamining;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modelling.*;

/**
 * Exercice 11 : Convertisseur (Propositionnalisation).
 *
 * Cette classe transforme un état du Monde des Blocs en un ensemble de variables booléennes,
 * en respectant strictement les définitions de l'énoncé.
 *
 * Variables générées :
 * - on_b,b' : Vrai si le bloc b est posé sur le bloc b'.
 * - on-table_b,p : Vrai si le bloc b est sur la table dans la pile p.
 * - fixed_b : Vrai si le bloc b est indéplaçable (il y a un bloc sur lui).
 * - free_p : Vrai si la pile p est libre (vide).
 */
public class BlocksWorldConverter {

    private int nbBlocs;
    private int nbPiles;
    private Map<String, BooleanVariable> variableMap;

    /**
     * Construit le convertisseur et génère l'univers de toutes les variables possibles.
     *
     * @param nbBlocs Le nombre total de blocs.
     * @param nbPiles Le nombre total de piles.
     */
    public BlocksWorldConverter(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;
        this.variableMap = new HashMap<>();
        this.createAllBooleanVariables();
    }

    /**
     * Crée et stocke toutes les variables booléennes possibles dans la variableMap.
     */
    private void createAllBooleanVariables() {
        
        // Créer fixed_b
        for (int b = 0; b < this.nbBlocs; b++) { 
            String name = "fixed_" + b;
            variableMap.put(name, new BooleanVariable(name));
        }
        
        // Créer free_p
        for (int p = 1; p <= this.nbPiles; p++) { 
            String name = "free_" + p;
            variableMap.put(name, new BooleanVariable(name));
        }

        // Créer on_b,b' et on-table_b,p
        for (int b = 0; b < this.nbBlocs; b++) { 
            
            // on_b,b' : b est sur b'
            for (int bPrime = 0; bPrime < this.nbBlocs; bPrime++) { 
                if (b != bPrime) {
                    String name = "on_" + b + "," + bPrime;
                    variableMap.put(name, new BooleanVariable(name));
                }
            }
            
            // on-table_b,p : b est sur la table dans p
            for (int p = 1; p <= this.nbPiles; p++) { 
                String name = "on-table_" + b + "," + p;
                variableMap.put(name, new BooleanVariable(name));
            }
        }
    }

    /**
     * Retourne l'ensemble de toutes les variables (items) possibles.
     * Nécessaire pour initialiser la BooleanDatabase.
     *
     * @return Un Set contenant toutes les variables booléennes.
     */
    public Set<BooleanVariable> getAllItems() {
        return new HashSet<>(this.variableMap.values());
    }

    /**
     * Convertit un état donné en une transaction (ensemble de variables vraies).
     *
     * @param state L'état (liste de piles).
     * @return L'ensemble des variables booléennes qui sont VRAIES dans cet état.
     */
    public Set<BooleanVariable> convertStateToTransaction(List<List<Integer>> state) {
        Set<BooleanVariable> transaction = new HashSet<>();
        Set<Integer> topBlocks = new HashSet<>();
        boolean[] pileIsOccupied = new boolean[nbPiles + 1];

        for (int p_idx = 0; p_idx < state.size(); p_idx++) {
            List<Integer> pile = state.get(p_idx);
            int pileNumber = p_idx + 1; 

            if (pile.isEmpty()) continue;
            
            pileIsOccupied[pileNumber] = true;
            // Le dernier élément de la liste est en haut de la pile
            topBlocks.add(pile.get(pile.size() - 1));
            
            for (int i = 0; i < pile.size(); i++) {
                int bloc = pile.get(i);
                
                if (i == 0) {
                    // C'est le bloc du bas : on-table_b,p
                    transaction.add(variableMap.get("on-table_" + bloc + "," + pileNumber));
                } else {
                    // C'est un bloc posé sur un autre
                    // bloc (i) est sur blocUnder (i-1)
                    int blocUnder = pile.get(i - 1);
                    
                    // On active la variable on_b,b' (b sur b')
                    // Donc on_bloc,blocUnder
                    transaction.add(variableMap.get("on_" + bloc + "," + blocUnder));
                }
            }
        }
        
        for (int p = 1; p <= nbPiles; p++) {
            if (!pileIsOccupied[p]) {
                transaction.add(variableMap.get("free_" + p));
            }
        }
        
        for (int b = 0; b < nbBlocs; b++) {
            if (!topBlocks.contains(b)) {
                transaction.add(variableMap.get("fixed_" + b));
            }
        }
        return transaction;
    }
}