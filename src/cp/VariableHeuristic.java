package cp;

import java.util.*;

import modelling.Variable;
/**
 * Interface  définissant une stratégie (heuristique)
 * pour sélectionner la "meilleure" variable à instancier ensuite.
 * <p>
 * Une heuristique de variable est utilisée par un solveur pour décider
 * de l'ordre dans lequel les variables sont assignées.
 * </p>
 */
public interface VariableHeuristic {
    /**
     * Sélectionne la "meilleure" variable parmi un ensemble de candidats.
     *
     * @param variables L'ensemble des variables non encore instanciées.
     * @param domaines La map des domaines actuels (potentiellement filtrés) 
     * pour toutes les variables.
     * @return La {@link Variable} sélectionnée comme étant la "meilleure" 
     * à instancier, selon la stratégie de l'heuristique.
     */
    Variable best(Set<Variable> variables , Map<Variable, Set<Object>> domaines);
}
