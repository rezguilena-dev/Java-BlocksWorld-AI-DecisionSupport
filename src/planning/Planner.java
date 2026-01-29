package planning;
import modelling.Variable;
import java.util.Map;
import java.util.List;
import java.util.Set;
public interface Planner{
    List<Action> plan();
    Map<Variable, Object> getInitialState();
    Set<Action> getActions();
    Goal getGoal();
}