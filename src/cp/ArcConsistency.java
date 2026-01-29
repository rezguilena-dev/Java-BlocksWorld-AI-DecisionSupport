package cp;
import java.util.*;
import modelling.*;
/**
 * Fournit des algorithmes pour la gestion de l'arc-consistance 
 * dans un Problème de Satisfaction de Contraintes .
 * <p>
 * Cette classe implémente la "node consistency" 
 * et l'algorithme d'arc-consistance .
 * Elle ne gère que les contraintes unaires et binaires, conformément
 * à l'Exercice 5.
 * </p>
 */
public class ArcConsistency  {
  /**
     * L'ensemble des contraintes (unaires ou binaires) du problème.
     */
    private Set<Constraint> constraints;
  /**
     * Construit un nouvel utilitaire d'arc-consistance.
     *
     * @param constraints L'ensemble des contraintes du CSP.
     * @throws IllegalArgumentException si une des contraintes n'est
     * ni unaire (scope de taille 1) ni binaire (scope de taille 2).
     */    public ArcConsistency(Set<Constraint> constraints) {
        for (Constraint constraint : constraints) {
            Set<Variable> scope = constraint.getScope();
            int size = scope.size();
            if (size != 1 && size != 2) {
                throw new IllegalArgumentException("Contrainte non unaire ni binaire détectée : " + constraint);
            }
        }
        this.constraints = constraints;
    }
    /**
     * Applique la cohérence de nœud (Node Consistency) sur un ensemble de domaines.
     * <p>
     * Cette méthode supprime, en place, les valeurs des domaines qui ne
     * satisfont pas les contraintes *unaires*.
     * </p>
     *
     * @param domaines La map des domaines (Variable -> Set de valeurs) à filtrer.
     * Cette map est modifiée directement (en place).
     * @return {@code false} si au moins un domaine est vidé, {@code true} sinon.
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domaines){
        boolean allDomainsValid=true;
        for (Constraint constraint : constraints) {
        Set<Variable> scope=constraint.getScope();
        if (scope.size()==1) {
        Variable var = scope.iterator().next();
        Set<Object> domain = domaines.get(var);
        Set<Object> toRemove = new HashSet<>();
        for (Object value : domain) {
        Map<Variable, Object> assignment = new HashMap<>();
        assignment.put(var, value);

        if (!constraint.isSatisfiedBy(assignment)) {
        toRemove.add(value);
        }
        }
        domain.removeAll(toRemove);
        if (domain.isEmpty()) {
                    allDomainsValid = false;
        }
        }
    }
    return allDomainsValid;
}
/**
     * Opération "Revise" de l'arc-consistance.
     * <p>
     * Supprime de D1 (domaine de V1) toutes les valeurs (val1) pour lesquelles il n'existe
     * aucune valeur "support" (val2) dans D2 (domaine de V2) qui satisfait toutes les
     * contraintes *binaires* portant sur {V1, V2}.
     * </p>
     *
     * @param v1 La variable dont le domaine est révisé.
     * @param d1 Le domaine de v1 (modifié en place).
     * @param v2 La variable "support".
     * @param d2 Le domaine de v2.
     * @return {@code true} si au moins une valeur a été supprimée de d1, {@code false} sinon.
     */
public boolean revise(Variable v1, Set<Object> d1, Variable v2, Set<Object> d2) {
  boolean revised = false;
  Set<Object> toRemove = new HashSet<>();
  for (Object val1 : d1) {
    boolean aumoinsUne = false;
    for (Object val2 : d2) {
        
      boolean allConstraintsSatisfied = true;
      for (Constraint constraint : constraints) {
        Set<Variable> scope = constraint.getScope();
        if (scope.size() == 2) {
          Iterator<Variable> it = scope.iterator();
          Variable var1 = it.next();
          Variable var2 = it.next();
          if ((var1.equals(v1) && var2.equals(v2)) ||
              (var1.equals(v2) && var2.equals(v1))) {
            Map<Variable, Object> assignment = new HashMap<>();
            assignment.put(v1, val1);
            assignment.put(v2, val2);
            if (!constraint.isSatisfiedBy(assignment)) {
              allConstraintsSatisfied = false;
              break;
            }
          }
        }
        
      }
    
    if (allConstraintsSatisfied) {
          aumoinsUne = true;
          break;
    }
    }
    if (!aumoinsUne) {
      toRemove.add(val1);
    }
  }
  if (!toRemove.isEmpty()) {
    d1.removeAll(toRemove);
    revised = true;
  }
  return revised;
}
/**
     * Exécute l'algorithme d'arc-consistance AC-1.
     * <p>
     * 1. Applique d'abord la cohérence de nœud (enforceNodeConsistency).
     * 2. Boucle et applique {@link #revise(Variable, Set, Variable, Set)} sur
     * chaque paire de variables (Xi, Xj) jusqu'à ce qu'aucune
     * modification de domaine n'ait lieu (stabilité).
     * </p>
     *
     * @param domains La map des domaines (Variable -> Set de valeurs) à filtrer.
     * Cette map est modifiée directement (en place).
     * @return {@code false} si au moins un domaine est vidé pendant le processus,
     * {@code true} sinon.
     */
public boolean ac1(Map<Variable, Set<Object>> domains) {
    if (!enforceNodeConsistency(domains)) 
        return false;
    
    boolean changed = true;

    while (changed) {
        changed = false;
        for (Variable Xi : domains.keySet())
          {
            for (Variable Xj: domains.keySet()){
          
            if (!Xi.equals(Xj)){
              if (revise(Xi, domains.get(Xi), Xj, domains.get(Xj))) {
                    changed = true;
                }
            }
            }
          }

        for (Variable x : domains.keySet()) {
            if (domains.get(x).isEmpty()) {
                return false;
            }
        }
    }

    return true;
}




}