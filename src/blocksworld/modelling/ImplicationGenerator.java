package blocksworld.modelling;

import java.util.*;
import modelling.*;

/**
 * Générateur de contraintes de regularité pour un monde de blocs.
 
 */
public class ImplicationGenerator {

    /** Le monde de blocs pour lequel générer les contraintes */
    private BlockWorld blockWorld;

    /**
     * Construit un générateur d'implications pour un monde de blocs donné.
     *
     * @param blockWorld le monde de blocs pour lequel générer les contraintes
     */
    public ImplicationGenerator(BlockWorld blockWorld) {
        this.blockWorld = blockWorld;
    }

    /**
     * Génère l'ensemble des contraintes de régularité.
     * 1. Anti-cycle (si A sur B, alors B pas sur A).
     * 2. Régularité (interdiction des triplets irréguliers).
     *
     * @return un ensemble de {@link Constraint}
     */
    public Set<Constraint> generateImplicationConstraint() {
        Set<Constraint> constraints = new HashSet<>();
        int nbBlocs = blockWorld.getNbBlocs();
        for (int base = 0; base < nbBlocs; base++) {
            for (int milieu = 0; milieu < nbBlocs; milieu++) {
                if (base == milieu) continue;

                Variable onBase = blockWorld.worldConfig.getOnVariable(base);
                Variable onMilieu = blockWorld.worldConfig.getOnVariable(milieu);

                Set<Object> S_Premise = Set.of(milieu);

                Set<Object> S_Conclusion = new HashSet<>(onMilieu.getDomain());
                S_Conclusion.remove(base);

                constraints.add(new Implication(onBase, S_Premise, onMilieu, S_Conclusion));
            }
        }
        
        // --- 1. Génération des contraintes de régularité par interdiction ---        
        // base : le bloc du bas
        // milieu : le bloc au milieu
        // sommet : le bloc tout en haut
        for (int base = 0; base < nbBlocs; base++) {
            for (int milieu = 0; milieu < nbBlocs; milieu++) {
                if (base == milieu) continue; 
                
                for (int sommet = 0; sommet < nbBlocs; sommet++) {
                    if (sommet == base || sommet == milieu) continue; 

                    // L'écart requis si base, milieu et sommet sont empilés
                    int requiredGap = Math.abs(milieu - base);
                    // L'écart réel s'ils sont empilés sommet -> milieu -> base
                    int actualGap = Math.abs(sommet - milieu);
                    
                    // Si la configuration est NON-RÉGULIÈRE, nous l'interdisons.
                    if (requiredGap != actualGap) {
                        
                        Variable onMilieu = blockWorld.worldConfig.getOnVariable(milieu); // Sur quoi est posé milieu (ne doit pas valoir base)
                        Variable onSommet = blockWorld.worldConfig.getOnVariable(sommet); // Sur quoi est posé sommet (doit valoir milieu)

                        // PRÉMISSE: On_sommet = milieu (Bloc sommet est posé sur Bloc milieu)
                        Set<Object> S_Premise = Set.of(milieu); 
                        
                        // CONCLUSION: On_milieu ne peut pas valoir base (Bloc milieu ne peut pas être posé sur Bloc base)
                        // On construit l'ensemble de toutes les valeurs possibles SAUF base.
                        Set<Object> S_Conclusion = new HashSet<>(onMilieu.getDomain());
                        
                        // On retire la valeur base du domaine de conclusion.
                        S_Conclusion.remove(base); 
                        
                        // Contrainte : Si On_sommet = milieu, ALORS On_milieu != base
                        // Ceci empêche la formation du triplet non régulier sommet -> milieu -> base.
                        constraints.add(new Implication(onSommet, S_Premise, onMilieu, S_Conclusion));
                    }
                }
            }
        }
        return constraints;
    }
}