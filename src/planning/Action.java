package planning;
import modelling.Variable;
import java.util.Map;
public interface Action{
    boolean isApplicable(Map<Variable,Object> etat);
    Map<Variable, Object> successor(Map<Variable, Object> etat);
    int getCost();
}