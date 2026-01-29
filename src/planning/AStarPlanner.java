package planning;
import modelling.Variable; 
import planning.Goal; 
import java.util.* ;

public class AStarPlanner implements Planner{
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private Heuristic heuristique;
    private boolean nodeCountActive;
    private int nodeExplored;
    
    public AStarPlanner(Map<Variable,Object> initialState,Set<Action> actions,Goal goal,Heuristic heuristique){
        this.initialState= initialState;
        this.actions =actions;
        this.goal = goal ;
        this.heuristique=heuristique;
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
        //va stocker le noeud et sa distance 
        Map<Map<Variable ,Object>,Double > distance = new HashMap<>();
        //stocke chaque noeud et son pere
        Map<Map<Variable,Object >, Map<Variable,Object>> father = new HashMap<>();
        //stocke chaque noeud et l'action qui l'a engendre 
        Map<Map<Variable,Object >,Action> plan = new HashMap<>();
        Map<Map<Variable ,Object>,Float > value  = new HashMap<>();
        //permet de trier les noeuds selon la distance totale (heuristique +distance)
        PriorityQueue<Map<Variable,Object>> open = new PriorityQueue <>(
            Comparator.comparingDouble(value::get)
        );
        open.add(initialState);
        distance.put(initialState,0.0);
        father.put(initialState,null);
        value.put(initialState,heuristique.estimate(initialState));
        while(!open.isEmpty()){
            Map<Variable,Object> current = open.poll();
            if(this.nodeCountActive){
                this.nodeExplored++;
            }
            if(goal.isSatisfiedBy(current)){
                return getAstarPlan(father,plan,current);
            }
          
                
            for(Action action : actions){
                if(action.isApplicable(current)){
                    //on genere le prochain etat
                    Map<Variable,Object> next = action.successor(current);
                    
                    if( !distance.containsKey(next) ){
                        distance.put(next,Double.POSITIVE_INFINITY);       
                    }

                    double newCost =distance.get(current) + action.getCost();
                    if(distance.get(next) > newCost){
                            distance.put(next,newCost);
                            father.put(next,current);
                            value.put(next,(float)newCost+heuristique.estimate(next));
                            plan.put(next,action);
                            open.add(next);  
                        }
                    }
                }
            }
         return null;   }   
        
    private List<Action> getAstarPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
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

     

    
