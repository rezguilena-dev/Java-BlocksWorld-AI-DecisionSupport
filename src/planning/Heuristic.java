package planning;
import modelling.Variable;
import java.util.Map;

public interface Heuristic{
    float estimate(Map<Variable, Object> etat);
}