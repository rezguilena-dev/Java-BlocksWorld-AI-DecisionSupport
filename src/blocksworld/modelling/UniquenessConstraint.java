package blocksworld.modelling ;
import java.util.*;
import modelling.*;

/**
 * Représente une contrainte d'unicité entre deux variables.
 * <p>
 * Cette contrainte assure que les deux variables ne peuvent pas prendre
 * la même valeur dans une instanciation donnée.
 * </p>
 * <p>
 * Par exemple, dans le monde des blocs, deux blocs ne peuvent pas être
 * posés sur le même bloc ou dans la même pile simultanément.
 * </p>
 */
public class UniquenessConstraint implements Constraint {

    /** Première variable de la contrainte */
    private final Variable v1;

    /** Deuxième variable de la contrainte */
    private final Variable v2;

    /**
     * Construit une contrainte d'unicité entre deux variables.
     *
     * @param v1 la première variable
     * @param v2 la deuxième variable
     */
    public UniquenessConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    /**
     * Retourne l'ensemble des variables impliquées dans la contrainte.
     *
     * @return un ensemble contenant v1 et v2
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);
        return scope;
    }

    /**
     * Vérifie si la contrainte est satisfaite par une instanciation donnée.
     * <p>
     * La contrainte est satisfaite si et seulement si les deux variables
     * ont des valeurs différentes dans l'instanciation.
     * </p>
     *
     * @param instanciation une map associant les variables à leurs valeurs
     * @return true si les valeurs des deux variables sont différentes, false sinon
     * @throws IllegalArgumentException si l'une des variables n'a pas de valeur attribuée
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        if (!instanciation.containsKey(v1) || !instanciation.containsKey(v2)) {
            throw new IllegalArgumentException("Toutes les variables doivent avoir une valeur attribuée");
        }
        Object val1 = instanciation.get(v1);
        Object val2 = instanciation.get(v2);
        return !val1.equals(val2);
    }

    /**
     * Retourne une représentation textuelle de la contrainte.
     *
     * @return une chaîne décrivant la contrainte d'unicité
     */
    @Override
    public String toString() {
        return "UniquenessConstraint[" + v1.getName() + " != " + v2.getName() + "]";
    }
}
