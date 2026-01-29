package modelling ;
import java.util.*;

public class DifferenceConstraint implements Constraint{
    private final Variable v1;
    private final Variable v2;
    public DifferenceConstraint(Variable v1 ,Variable v2){
        this.v1=v1;
        this.v2=v2;
    }
    @Override
    public Set<Variable> getScope(){
        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);
        return scope;
    }
    public boolean isSatisfiedBy(Map<Variable, Object> instanciation){
        if (!instanciation.containsKey(v1) || !instanciation.containsKey(v2)){
            throw new IllegalArgumentException("toutes les variables doivent avoir une valeur donnée");
        }
        Object val1 = instanciation.get(v1);
        Object val2 =instanciation.get(v2);
        return ! val1.equals(val2);
    }
    @Override
    public String toString(){
        return this.v1.getName() + "≠" +this.v2.getName();
    }
}
