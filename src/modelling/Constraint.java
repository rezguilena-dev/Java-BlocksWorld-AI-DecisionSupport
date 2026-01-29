package modelling;

import java.util.Map;
import java.util.Set;

/**
 * Représente une contrainte dans le modèle d'un monde de blocs.
 * <p>
 * Une contrainte définit un ensemble de variables (son domaine ou scope)
 * et fournit une méthode pour vérifier si une instanciation particulière
 * de ces variables satisfait la contrainte.
 * </p>
 */
public interface Constraint {

    /**
     * Retourne l'ensemble des variables sur lesquelles cette contrainte porte.
     *
     * @return un ensemble de {@link Variable} représentant le scope de la contrainte
     */
    Set<Variable> getScope();

    /**
     * Vérifie si la contrainte est satisfaite par une instanciation donnée des variables.
     * <p>
     * L'instanciation est représentée par une map associant chaque variable à sa valeur.
     * </p>
     *
     * @param instanciation une map associant les variables à leurs valeurs
     * @return true si la contrainte est satisfaite, false sinon
     */
    boolean isSatisfiedBy(Map<Variable, Object> instanciation);
}
