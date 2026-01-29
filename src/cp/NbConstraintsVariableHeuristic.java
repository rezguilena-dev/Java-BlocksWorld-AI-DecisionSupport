package cp;

import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;
/**
 * Implémentation de {@link VariableHeuristic} (Exercice 12).
 * <p>
 * Sélectionne une variable en fonction du nombre de contraintes dans lesquelles
 * elle est impliquée. Il s'agit d'une heuristique statique (le nombre
 * de contraintes ne change pas pendant la recherche).
 * </p>
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic  {
    private Set<Constraint> constraints;
    private boolean bool ;

    /**
     * Construit une nouvelle heuristique basée sur le nombre de contraintes.
     *
     * @param constraints L'ensemble de *toutes* les contraintes du problème.
     * @param bool {@code true} pour choisir la variable impliquée dans le *plus*
     * de contraintes (heuristique "deg"), {@code false} pour 
     * choisir celle impliquée dans le *moins* de contraintes.
     */
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean bool){
        this.constraints=constraints;
        this.bool=bool;
    }
    /**
     * Sélectionne une variable en se basant sur le nombre de contraintes.
     * <p>
     * {@inheritDoc}
     * </p>
     * <p>Trouve la variable (parmi l'ensemble {@code variables}) qui maximise ou minimise
     * le nombre de contraintes la mentionnant, en fonction du booléen du constructeur.
     * </p>
     *
     * @param variables L'ensemble des variables non encore instanciées.
     * @param domaines La map des domaines actuels (non utilisée par cette heuristique statique).
     * @return La {@link Variable} sélectionnée.
     */
    @Override
    public Variable best (Set<Variable> variables , Map<Variable, Set<Object>> domaines){
        Variable bestVariable = null;
        int bestcount = bool? -1 :Integer.MAX_VALUE;
        for (Variable variable :variables){
            int count = 0;
            for (Constraint c : constraints) {
            if (c.getScope().contains(variable)) {
                count++;
            }}
            if (bool){
                if (count > bestcount){
                    bestcount=count;
                    bestVariable=variable;
                }
            }
            else {
                if (count < bestcount){
                    bestcount=count;
                    bestVariable=variable;
                }
            }
        }
        
        return bestVariable;
    }
}
