package cp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;
/**
 * Un solveur de CSP qui implémente l'algorithme MAC (Maintaining Arc Consistency)
 * mais qui utilise des heuristiques pour guider la recherche.
 * <p>
 * (Exercice 15)
 * </p>
 * Il étend {@link AbstractSolver} et utilise :
 * 1. Une {@link VariableHeuristic} pour choisir la *prochaine variable* à instancier.
 * 2. Une {@link ValueHeuristic} pour choisir l'ordre des *valeurs* à tester pour cette variable.
 */
public class HeuristicMACSolver extends AbstractSolver {

    private final VariableHeuristic varHeuristic;
    private final ValueHeuristic valueHeuristic;
    /**
     * Construit un nouveau solveur MAC heuristique.
     *
     * @param variables L'ensemble des variables du CSP.
     * @param constraints L'ensemble des contraintes du CSP.
     * @param varHeuristic L'heuristique pour la sélection de la prochaine variable.
     * @param valueHeuristic L'heuristique pour l'ordonnancement des valeurs.
     */
    public HeuristicMACSolver(Set<Variable> variables,
                              Set<Constraint> constraints,
                              VariableHeuristic varHeuristic,
                              ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.varHeuristic = varHeuristic;
        this.valueHeuristic = valueHeuristic;
    }
    /**
     * Lance le processus de résolution.
     * <p>
     * {@inheritDoc}
     * </p>
     * <p>Initialise les domaines et lance la recherche récursive MAC.</p>
     */
    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> ED = new HashMap<>();
        for (Variable x : variables) {
            ED.put(x, new HashSet<>(x.getDomain()));
        }

        Map<Variable, Object> partialSolution = new HashMap<>();
        List<Variable> notAssigned = new ArrayList<>(variables);

        return MAC(partialSolution, notAssigned, ED);
    }
    /**
     * Méthode récursive (coeur de l'algorithme Heuristic-MAC).
     * <p>
     * 1. Applique AC-1 sur les domaines courants (ED).
     * 2. Si AC-1 réussit, utilise la {@link VariableHeuristic} pour choisir la variable `xi`.
     * 3. Utilise la {@link ValueHeuristic} pour ordonner les valeurs `vi` du domaine de `xi`.
     * 4. Tente d'assigner `xi = vi` et continue récursivement.
     * </p>
     *
     * @param partialSolution L'affectation partielle actuellement construite.
     * @param notAssigned La liste des variables restant à assigner.
     * @param ED La map des domaines "élagués".
     * @return Une solution complète et valide si elle est trouvée, ou {@code null}.
     */
    private Map<Variable, Object> MAC(Map<Variable, Object> partialSolution,
                                      List<Variable> notAssigned,
                                      Map<Variable, Set<Object>> ED) {

        if (notAssigned.isEmpty()) {
            return partialSolution;
        }

        ArcConsistency arc = new ArcConsistency(constraints);
        if (!arc.ac1(ED)) {
            return null;
        }

        Variable xi = varHeuristic.best(new HashSet<>(notAssigned), ED);
        notAssigned.remove(xi);
        List<Object> orderedValues = valueHeuristic.ordering(xi, ED.get(xi));

        for (Object vi : orderedValues) {
            Map<Variable, Object> newSolution = new HashMap<>(partialSolution);
            newSolution.put(xi, vi);

            if (isConsistent(newSolution)) {
                Map<Variable, Object> result = MAC(newSolution, notAssigned, ED);
                if (result != null) {
                    return result;
                }
            }
        }
        notAssigned.add(xi);
        return null;
    }  
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> assignment) {
        for (Constraint c : constraints) {
            if (!c.isSatisfiedBy(assignment)) {
                return false;
            }
        }
        return true;
    }
}
