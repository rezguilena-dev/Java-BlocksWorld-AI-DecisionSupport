package cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import modelling.Variable;
/**
 * Implémentation de {@link ValueHeuristic} (Exercice 14).
 * <p>
 * Retourne les valeurs du domaine dans un ordre pseudo-aléatoire uniforme.
 * </p>
 */
public class RandomValueHeuristic implements ValueHeuristic{
     private final Random random;
    /**
     * Construit une nouvelle heuristique de valeur aléatoire.
     *
     * @param random Une instance de {@link Random} à utiliser pour le mélange
     * (permet la reproductibilité des tests).
     */
    public RandomValueHeuristic(Random random) {
        this.random = random;
    }
    /**
     * Crée une liste des valeurs du domaine et la mélange.
     * <p>
     * {@inheritDoc}
     * </p>
     *
     * @param v La {@link Variable} (non utilisée par cette heuristique).
     * @param domain L'ensemble des valeurs du domaine à mélanger.
     * @return Une {@link List} des valeurs du domaine dans un ordre aléatoire.
     */
    @Override
    public List<Object> ordering(Variable v, Set<Object> domain) {
        List<Object> values = new ArrayList<>(domain);

        Collections.shuffle(values, random);

        return values;
    }
    }

