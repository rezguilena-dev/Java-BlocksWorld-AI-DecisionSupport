package cp;

import java.util.*;
import modelling.*;
/**
 * Un solveur de CSP qui implémente l'algorithme MAC .
 * <p>
 * Il s'agit d'un solveur de type backtracking  qui, à chaque
 * étape de l'exploration, relance un algorithme d'arc-consistance
 * (ici {@link ArcConsistency#ac1(Map)}) pour filtrer les domaines des variables
 * restantes avant de continuer la récursion.
 * </p>
 */
public  class MACSolver extends AbstractSolver{
    /**
     * Construit un nouveau solveur MAC.
     *
     * @param variables L'ensemble des variables du CSP.
     * @param constraints L'ensemble des contraintes du CSP.
     * (doivent être unaires ou binaires pour ArcConsistency).
     */
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }
    /**
     * Point d'entrée public pour lancer la résolution MAC.
     * <p>
     * Initialise une copie des domaines (ED), une solution partielle vide,
     * et la liste des variables à assigner, puis appelle la méthode
     * récursive privée {@link #MAC(Map, List, Map)}.
     * </p>
     *
     * @return Une solution complète (Map) ou {@code null} si aucune n'est trouvée.
     */
    @Override
    public  Map<Variable, Object> solve(){
        Map<Variable, Set<Object>> ED = new HashMap<>();
        for (Variable x : variables) {
            ED.put(x, new HashSet<>(x.getDomain()));
        }

        Map<Variable,Object> partielsolution= new  HashMap<>();
        List<Variable> notAssigned=new ArrayList<>(variables);
        return MAC(partielsolution,notAssigned,ED);
    }
    /**
     * Méthode récursive privée (coeur de l'algorithme MAC).
     * <p>
     * 1. Applique AC-1 sur les domaines courants (ED). Si échec, retourne null.
     * 2. Sélectionne la prochaine variable (xi) à assigner.
     * 3. Itère sur les valeurs (vi) du domaine filtré de xi.
     * 4. Crée une nouvelle affectation (N) avec (xi = vi).
     * 5. Vérifie si N est localement cohérente ({@link #isConsistent(Map)}).
     * 6. Si cohérente, appelle récursivement MAC avec N, le reste des variables,
     * et les domaines ED (qui ont été filtrés par AC-1 au début de cet appel).
     * </p>
     *
     * @param partielsolution L'affectation partielle actuellement construite.
     * @param notAssigned La liste des variables restant à assigner.
     * @param ED La map des domaines "élagués" (filtrée par AC-1 au début de l'appel).
     * @return Une solution complète et valide si elle est trouvée, ou {@code null}.
     */
    public  Map<Variable, Object> MAC(Map<Variable,Object> partielsolution , List<Variable> notAssigned,Map<Variable, Set<Object>> ED){

        if (notAssigned.isEmpty()){
            return partielsolution;
        }
        ArcConsistency arc = new ArcConsistency(constraints);
        if (!arc.ac1(ED)){
            return null ;
        }
        Variable xi = notAssigned.remove(0);
        for (Object vi : new HashSet<>(ED.get(xi))) {
        Map<Variable, Object> N = new HashMap<>(partielsolution);
        N.put(xi, vi);
        if (isConsistent(N)){
            Map<Variable, Object> R = MAC(N,notAssigned,ED);
            if (R!=null){
                return R;
            }

        }
        }
        notAssigned.add(0,xi);
        return null;
    }
    /**
     * Vérifie si une affectation donnée satisfait l'intégralité des contraintes.
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
