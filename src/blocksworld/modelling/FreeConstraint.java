package blocksworld.modelling ;
import java.util.*;
import modelling.*;

/**
 * Représente une contrainte liant une variable "on" à une variable "free" de pile.
 * <p>
 * Cette contrainte assure que si un bloc est posé sur une pile donnée, 
 * la pile n'est pas libre (freeVar = false). 
 * </p>
 * <p>
 * Concrètement, si la variable "onVar" prend la valeur correspondant à la pile
 * associée à "freeVar", alors "freeVar" doit être false.
 * </p>
 */
public class FreeConstraint implements Constraint {

    /** La variable booléenne représentant si la pile est libre */
    private final BooleanVariable freeVar;

    /** La variable représentant la position du bloc */
    private final Variable onVar;

    /**
     * Construit une contrainte liant un bloc à une pile.
     *
     * @param onVar la variable représentant la position du bloc
     * @param freeVar la variable booléenne indiquant si la pile est libre
     */
    public FreeConstraint(Variable onVar, BooleanVariable freeVar) {
        this.onVar = onVar;
        this.freeVar = freeVar;
    }

    /**
     * Retourne l'ensemble des variables impliquées dans la contrainte.
     *
     * @return un ensemble contenant la variable "on" et la variable "free"
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(onVar);
        scope.add(freeVar);
        return scope;
    }

    /**
     * Vérifie si la contrainte est satisfaite par une instanciation donnée.
     * <p>
     * La contrainte est satisfaite si, lorsque le bloc est posé sur la pile correspondante,
     * la variable freeVar vaut false. Sinon, la contrainte est considérée satisfaite.
     * </p>
     *
     * @param instanciation une map associant les variables à leurs valeurs
     * @return true si la contrainte est satisfaite, false sinon
     * @throws IllegalArgumentException si l'une des variables n'a pas de valeur attribuée
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        if (!instanciation.containsKey(onVar) || !instanciation.containsKey(freeVar)) {
            throw new IllegalArgumentException("Toutes les variables doivent avoir une valeur attribuée");
        }
        Object onValue = instanciation.get(onVar);
        Object freeValue = instanciation.get(freeVar);
        int pileNumber = extractNumber(freeVar.getName());
        if (onValue instanceof Integer && (Integer) onValue == -pileNumber) {
            return freeValue.equals(false);
        }
        return true;
    }
    private int extractNumber(String name) {
        if (name.startsWith("free_")) {
            return Integer.parseInt(name.substring(5));
        }
        throw new IllegalStateException("Not a free variable: " + name);
    }


    /**
     * Retourne une représentation textuelle de la contrainte.
     *
     * @return une chaîne décrivant la contrainte entre la variable "on" et la variable "free"
     */
    @Override
    public String toString() {
        return "FreeConstraint[on=" + onVar.getName() + ", free=" + freeVar.getName() + "]";
    }
}
