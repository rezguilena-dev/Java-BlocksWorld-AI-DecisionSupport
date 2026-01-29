package blocksworld.modelling ;
import java.util.*;
import modelling.*;

/**
 * Représente une contrainte liant une variable "on" à une variable "fixed" de bloc.
 * <p>
 * Cette contrainte assure que si un bloc est posé sur un autre bloc spécifique,
 * ce bloc doit être marqué comme indéplaçable (fixedVar = true).
 * </p>
 * <p>
 * Concrètement, si la variable "onVar" prend la valeur correspondant à un autre bloc,
 * alors la variable "fixedVar" associée à ce bloc doit être true.
 * </p>
 */
public class FixedConstraint implements Constraint {

    /** La variable booléenne représentant si le bloc est indéplaçable */
    private final BooleanVariable fixedVar;

    /** La variable représentant la position du bloc */
    private final Variable onVar;

    /**
     * Construit une contrainte liant un bloc à un autre bloc au-dessus.
     *
     * @param onVar la variable représentant la position du bloc
     * @param fixedVar la variable booléenne indiquant si le bloc est indéplaçable
     */
    public FixedConstraint(Variable onVar, BooleanVariable fixedVar) {
        this.onVar = onVar;
        this.fixedVar = fixedVar;
    }

    /**
     * Retourne l'ensemble des variables impliquées dans la contrainte.
     *
     * @return un ensemble contenant la variable "on" et la variable "fixed"
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(onVar);
        scope.add(fixedVar);
        return scope;
    }

    /**
     * Vérifie si la contrainte est satisfaite par une instanciation donnée.
     * <p>
     * La contrainte est satisfaite si, lorsque le bloc est posé sur le bloc
     * correspondant, la variable fixedVar vaut true. Sinon, la contrainte
     * est considérée satisfaite.
     * </p>
     *
     * @param instanciation une map associant les variables à leurs valeurs
     * @return true si la contrainte est satisfaite, false sinon
     * @throws IllegalArgumentException si l'une des variables n'a pas de valeur attribuée
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        if (!instanciation.containsKey(fixedVar) || !instanciation.containsKey(onVar)) {
            throw new IllegalArgumentException("Toutes les variables doivent avoir une valeur attribuée");
        }
        Object onValue = instanciation.get(onVar);
        Object fixedValue = instanciation.get(fixedVar);
        if (onValue instanceof Integer && (Integer) onValue >= 0) {
            int numBloc = extractNumber(fixedVar.getName());
            if (onValue.equals(numBloc)) {
                return fixedValue.equals(true);
            }
        }
        return true;
    }
    private int extractNumber(String name) {
        if (name.startsWith("fixed_")) {
            return Integer.parseInt(name.substring(6));
        }
        throw new IllegalStateException("Not a fixed variable: " + name);
    }

    /**
     * Retourne une représentation textuelle de la contrainte.
     *
     * @return une chaîne décrivant la contrainte entre la variable "on" et la variable "fixed"
     */
    @Override
    public String toString() {
        return "FixedConstraint[on=" + onVar.getName() + ", fixed=" + fixedVar.getName() + "]";
    }
}
