package cp;

import java.util.List;
import java.util.Set;

import modelling.Variable;
/**
 * Interface  définissant une stratégie (heuristique)
 * pour ordonner les valeurs du domaine d'une variable.
 * <p>
 * Une heuristique de valeur est utilisée par un solveur pour décider
 * dans quel ordre tester les valeurs pour une variable donnée.
 * </p>
 */
public interface ValueHeuristic {
    /**
     * Ordonne les valeurs du domaine d'une variable donnée.
     *
     * @param V La {@link Variable} dont le domaine doit être ordonné.
     * @param domaine L'ensemble des valeurs du domaine (actuel) de V.
     * @return Une {@link List} contenant les objets du domaine, ordonnés selon
     * l'heuristique (la meilleure valeur à tester est à l'index 0).
     */
    List<Object> ordering(Variable V , Set<Object> domaine );
}
