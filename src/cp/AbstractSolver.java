package cp;
import java.util.*;
import modelling.*;
/**
 * Classe abstraite de base pour les solveurs de CSP.
 *
 * Elle implémente l'interface {@link Solver} et stocke l'ensemble
 * des variables et des contraintes du problème.
 * Elle fournit également une méthode de vérification de cohérence locale.
 */
public abstract class AbstractSolver implements Solver{
    /**
     * L'ensemble des variables du problème de satisfaction de contraintes.
     */
    protected Set<Variable> variables;
    /**
     * L'ensemble des contraintes à satisfaire.
     */
    protected Set<Constraint> constraints;
    /**
     * Construit un nouveau solveur avec un ensemble de variables et de contraintes.
     *
     * @param variables L'ensemble des variables du CSP.
     * @param constraints L'ensemble des contraintes du CSP.
     */
    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
    }
    /**
     * Vérifie si une affectation partielle est localement cohérente.
     * <p>
     * Cette méthode ne vérifie que les contraintes pour lesquelles *toutes*
     * les variables impliquées (scope) ont une valeur dans l'affectation partielle.
     * </p>
     *
     * @param partiel L'affectation partielle (Variable -> Valeur) à vérifier.
     * @return {@code true} si aucune contrainte (parmi celles entièrement instanciées)
     * n'est violée, {@code false} sinon.
     */
    public boolean isConsistent(Map<Variable,Object> partiel){
        for (Constraint constraint : constraints ){
            if (partiel.keySet().containsAll(constraint.getScope())){
                if(!constraint.isSatisfiedBy(partiel)){
                    return false;
                }
            }
        }
        return true ;
    }
    /**
         * Lance le processus de résolution.
         * Méthode abstraite à implémenter par les classes concrètes.
         *
         * @return Une solution (Map) ou {@code null} si aucune n'existe.
         */    
    public abstract Map<Variable, Object> solve();
}