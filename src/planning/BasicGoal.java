package planning;
import modelling.Variable;
import java.util.Map;

public class BasicGoal implements Goal{
    private Map<Variable, Object> instanciation;
    public BasicGoal(Map<Variable, Object> instanciation){
        this.instanciation = instanciation;
    }
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> etat){
        for(Map.Entry<Variable,Object>  entry : instanciation.entrySet()){
            Variable cle = entry.getKey();
            Object val = entry.getValue();
            if(!etat.containsKey(cle) || !etat.get(cle).equals(val)){
                return false;
            }

        }
        return true;
    }
    
}