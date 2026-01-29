package planning;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import modelling.Variable;

public class BasicAction implements Action{
    private Map<Variable, Object> precondition;
    private Map<Variable, Object> effet;
    private int cout;
    public BasicAction(Map<Variable, Object> precondition,Map<Variable, Object>effet ,int cout){
        this.precondition = precondition;
        this.effet = effet;
        this.cout=cout;
    }
    @Override 
    public boolean isApplicable(Map<Variable,Object>etat){
        for(Map.Entry<Variable,Object> entry : precondition.entrySet()){
            Variable cle=entry.getKey();
            Object val = entry.getValue();
            if(!etat.containsKey(cle)|| !etat.get(cle).equals(val) ){
                return false;
            }

        }
        return true;
    }
    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> etat){
        Map<Variable, Object> nouveau =new HashMap<>(etat);
        if(isApplicable(etat)){
            for(Map.Entry<Variable,Object> entry : effet.entrySet()){
                nouveau.put(entry.getKey(), entry.getValue());
            }
        }
        return nouveau;
    }
    @Override
    public int getCost(){
        return this.cout;
    }
    @Override
    public String toString(){
        return "précondition " + this.precondition +" effet " + this.effet ; 
    }
}