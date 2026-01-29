package cp;
import java.util.*;
import modelling.*;
import planning.*;
/**
 * Un solveur de CSP qui implémente l'algorithme de backtracking standard.
 *
 * Il explore l'arbre de recherche en assignant une variable à la fois et
 * revient en arrière (backtrack) si une affectation mène à une impasse.
 */
public class BacktrackSolver extends AbstractSolver{
    /**
     * Construit un nouveau solveur de type Backtracking.
     *
     * @param variables L'ensemble des variables du CSP.
     * @param constraints L'ensemble des contraintes du CSP.
     */
    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables,constraints);
    }
    /**
     * Point d'entrée public pour lancer la résolution.
     * <p>
     * Initialise une solution partielle vide et la liste de toutes les variables
     * non assignées, puis appelle la méthode récursive privée.
     * </p>
     *
     * @return Une solution complète (Map) ou {@code null} si aucune n'est trouvée.
     */
    public  Map<Variable, Object> solve(){
        Map<Variable,Object> partielsolution= new  HashMap<>();
        List<Variable> notAssigned=new ArrayList<>(variables);
        return Backtracksolve(partielsolution,notAssigned);
    }
    /**
     * Méthode récursive privée .
     *
     * @param partielsolution L'affectation partielle actuellement construite.
     * @param notAssigned La liste des variables restant à assigner.
     * @return Une solution complète et valide si elle est trouvée à partir de
     * cet état, ou {@code null} si cette branche mène à une impasse.
     */
    private Map<Variable, Object> Backtracksolve(Map<Variable,Object> partielsolution , List<Variable> notAssigned){
        if (notAssigned.isEmpty()){
            if (isConsistent(partielsolution)){
                return partielsolution;
            }
            else {
                return null;
            }
        }
        Variable currentVariable= notAssigned.remove(0);
        for (Object value : currentVariable.getDomain()){
            partielsolution.put(currentVariable, value);
            if (isConsistent(partielsolution)) {
            Map<Variable, Object> result =Backtracksolve(partielsolution,new ArrayList<>(notAssigned));
            if (result != null ){
                return result;
            }
            }
            partielsolution.remove(currentVariable);
        }
        return null;
    }
    /**
     * Vérifie si une affectation donnée satisfait l'intégralité des contraintes.
     * <p>
     * Note : Cette méthode est requise par l'interface Solver.
     * Elle suppose que les méthodes isSatisfiedBy de chaque contrainte
     * peuvent gérer les affectations partielles.
     * </p>
     *
     * @param assignment L'affectation à vérifier.
     * @return {@code true} si toutes les contraintes sont satisfaites, {@code false} sinon.
     */
    public boolean isSatisfiedBy(Map<Variable, Object> assignment) {
        for (Constraint c : constraints) {
            if (!c.isSatisfiedBy(assignment)) {
                return false;
            }
        }
        return true;
    }

}