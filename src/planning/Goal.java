package planning;
import modelling.Variable;
import java.util.Map;
public interface Goal{
    boolean isSatisfiedBy(Map<Variable,Object> etat);
}