package cp;

import java.util.Map;
import java.util.Set;

import modelling.Variable;
/**
 * Implémentation de {@link VariableHeuristic} (Exercice 13).
 * <p>
 * Sélectionne une variable en fonction de la taille de son domaine actuel.
 * Si le booléen est à {@code false}, cela implémente l'heuristique
 * "Minimum Remaining Values" (MRV) ou "fail-first", qui est très répandue.
 * </p>
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic {
    private boolean bool ;
    /**
     * Construit une nouvelle heuristique basée sur la taille du domaine.
     *
     * @param bool {@code true} pour choisir la variable avec le *plus grand* domaine,
     * {@code false} pour choisir la variable avec le *plus petit* domaine (MRV).
     */
    public DomainSizeVariableHeuristic( boolean bool){
        this.bool=bool;
    }
    /**
     * Sélectionne une variable en se basant sur la taille de son domaine.
     * <p>
     * {@inheritDoc}
     * </p>
     *
     * @param variables L'ensemble des variables non encore instanciées.
     * @param domaines La map des domaines actuels (utilisée pour trouver la taille).
     * @return La {@link Variable} sélectionnée.
     */
    @Override
    public Variable best (Set<Variable> variables , Map<Variable, Set<Object>> domaines){
        Variable bestVariable = null;
        int bestcount = bool? -1 :Integer.MAX_VALUE;
        for (Variable variable :variables){
            int size = domaines.get(variable).size();
            if (bool){
                if (size > bestcount){
                    bestcount=size;
                    bestVariable=variable;
                }
            }
            else {
                if (size < bestcount){
                    bestcount=size;
                    bestVariable=variable;
                }
            }
        }
        
        return bestVariable;
    }
}
