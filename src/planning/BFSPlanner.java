package planning;
import modelling.Variable; 
import planning.Goal; 
import java.util.*;

public class BFSPlanner implements Planner {
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private boolean nodeCountActive;
    private int nodeExplored;
    public BFSPlanner(Map<Variable,Object> initialState , Set<Action> actions , Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal= goal;
        this.nodeCountActive = false;
        this.nodeExplored=0;
    }
    @Override
    public Map<Variable,Object> getInitialState(){
        return this.initialState;
    }
    
    @Override
    public Set<Action> getActions(){
        return this.actions;

    }
    @Override
    public Goal getGoal(){
        return this.goal;
    }
    @Override
    public List<Action> plan(){
        Map<Map<Variable,Object>,Map<Variable,Object>> father = new HashMap<>();
        Map<Map<Variable,Object>,Action> planMap = new HashMap<>();

        Set<Map<Variable,Object>> closed = new HashSet<>();
        Queue<Map<Variable,Object>> open = new LinkedList<>();

        open.add(initialState);
        closed.add(initialState);
        father.put(initialState,null);

        if(goal.isSatisfiedBy(initialState)){
            return new ArrayList<>();
        }
        while(!open.isEmpty()){
            Map<Variable,Object> current = open.poll();
            if(this.nodeCountActive){
                    this.nodeExplored++;
            }

            for(Action action : actions){
                if(action.isApplicable(current)){
                    Map<Variable,Object> next = action.successor(current);
                    if(!closed.contains(next) && !open.contains(next)){
                        father.put(next,current);
                        planMap.put(next,action);
                        if(goal.isSatisfiedBy(next)){
                            return getBFSPlan(father,planMap,next);
                        }
                        else{
                            open.add(next);
                            closed.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }
    private List<Action> getBFSPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
                                    Map<Map<Variable, Object>, Action> planMap,
                                    Map<Variable, Object> goalState){
    
    List<Action> plan = new ArrayList<>();
    Map<Variable,Object> current = goalState;
    while(father.get(current) != null){
        plan.add(0,planMap.get(current));
        current = father.get(current);

    }
    return plan;
    
    
    }
    public void activateNodeCount(boolean activate){
        this.nodeCountActive = activate;
        if(activate){
            this.nodeExplored=0;
        }

    }
    public int getNodesExplored(){
        return this.nodeExplored;
    }
}
