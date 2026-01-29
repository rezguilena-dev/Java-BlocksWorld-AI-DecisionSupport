package blocksworld.modelling ;
import java.util.*;
import modelling.*;

/**
 * Représente une contrainte de croissance pour un bloc dans le monde des blocs.
 * <p>
 * Une contrainte de croissance impose qu'un bloc ne peut être posé que sur un bloc
 * ayant un numéro inférieur au sien, ou directement sur la table.
 * </p>
 * <p>
 * Cette contrainte permet de garantir qu'aucune configuration circulaire
 * n'est possible et que les blocs sont empilés de manière croissante.
 * </p>
 */
public class CroissanceConstraint implements Constraint {

    /** La variable représentant la position du bloc */
    private final Variable onVar;

    /**
     * Construit une contrainte de croissance pour un bloc donné.
     *
     * @param onVar la variable représentant la position du bloc
     */
    public CroissanceConstraint(Variable onVar) {
        this.onVar = onVar;
    }

    /**
     * Retourne l'ensemble des variables impliquées dans la contrainte.
     *
     * @return un ensemble contenant uniquement la variable "on" du bloc
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(onVar);
        return scope;
    }

    /**
     * Vérifie si la contrainte de croissance est satisfaite par une instanciation donnée.
     * <p>
     * La contrainte est satisfaite si le bloc est posé sur la table (valeur -1),
     * ou si le bloc est posé sur un autre bloc ayant un numéro strictement inférieur au sien.
     * </p>
     *
     * @param instanciation une map associant les variables à leurs valeurs
     * @return true si la contrainte est satisfaite, false sinon
     * @throws IllegalArgumentException si la variable n'a pas de valeur attribuée
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation) {
        if (!instanciation.containsKey(onVar)) {
            throw new IllegalArgumentException("Toutes les variables doivent avoir une valeur assignée");
        }
        Object onVal = instanciation.get(onVar);
        int numeroBloc = Integer.parseInt(onVar.getName().substring(3));
        // Si le bloc est sur la table, la contrainte est toujours respectée
        if (onVal.equals(-1)) {
            return true;
        }
        if (onVal instanceof Integer) {
            return (Integer) onVal < numeroBloc;
        }
        return false;
    }
    @Override
    public String toString() {
        return "CroissanceConstraint sur " + onVar.getName();
    }
}
