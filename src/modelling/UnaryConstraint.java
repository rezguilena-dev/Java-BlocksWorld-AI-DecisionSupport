package modelling;

import java.util.*;

public class UnaryConstraint implements Constraint{
    private final Variable v ;
    private final Set<Object> S ;
    public UnaryConstraint(Variable v,Set<Object> S){
        this.v=v;
        this.S = S;
    }
    @Override
    public Set<Variable> getScope(){
        return Set.of(v);
    }
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation){
        if (!instanciation.containsKey(v)){
            throw new IllegalArgumentException("toutes les variables doivent avoir une valeur donnée");
        }
        Object val = instanciation.get(v);
        return S.contains(val);
    }
    @Override 
    public String toString(){
        return this.v.getName() + "∈" + this.S ;
    }
}