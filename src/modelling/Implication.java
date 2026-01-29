package modelling;

import java.util.*;
public class Implication implements Constraint{
    private final Variable v1;
    private Set<Object> S1;
    private final Variable v2;
    private Set<Object> S2;

    public Implication(Variable v1 , Set<Object> S1 , Variable v2 , Set<Object> S2){
        this.v1 = v1;
        this.S1 = S1;
        this.v2 = v2 ;
        this.S2 = S2;
    }
    @Override
    public Set<Variable> getScope(){
        Set<Variable> scope = new HashSet<>();
        scope.add(this.v1);
        scope.add(this.v2);
        return scope;
    }
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation){
        if (!instanciation.containsKey(v1) || !instanciation.containsKey(v2)){
            throw new IllegalArgumentException("toutes les variables doivent avoir une valeur donnée");
        }
        Object val1 = instanciation.get(v1);
        Object val2 = instanciation.get(v2);
        if(!S1.contains(val1)){
              return true;
        }
        return S2.contains(val2);
      
    }
    @Override
    public String toString() {
        return "(" + this.v1.getName() + " ∈ " + this.S1 + ") → (" + this.v2.getName() + " ∈ " + this.S2 + ")";
    }






}