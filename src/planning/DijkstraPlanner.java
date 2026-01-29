package planning;
import modelling.Variable; 
import planning.Goal; 

import java.util.* ;

public class DijkstraPlanner implements Planner{
    private Map<Variable,Object> initialState;
    private Set<Action> actions;
    private Goal goal;
    private boolean nodeCountActive ; //indique si le compte de noeud est activé
    private int nodeExplored; //indique le nombre de noeud explorés
    public DijkstraPlanner(Map<Variable,Object> initialState , Set<Action> actions , Goal goal){
        this.initialState = initialState;
        this.actions = actions;
        this.goal= goal;
        this.nodeCountActive= false;
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
        Map<Map<Variable ,Object>,Integer > distance = new HashMap<>();
        //stocke chaque noeud et son pere
        Map<Map<Variable,Object >, Map<Variable,Object>> father = new HashMap<>();
        //stocke chaque noeud et laction qui la engendre 
        Map<Map<Variable,Object >,Action> planMap = new HashMap<>();
        //permet de trier les noeuds selon la distance
        PriorityQueue<Map<Variable,Object>> open = new PriorityQueue <>(
            Comparator.comparingInt(distance::get)
        );
        open.add(initialState);
        distance.put(initialState,0);
        father.put(initialState,null);
        while(!open.isEmpty()){
            Map<Variable,Object> current = open.poll();
            if(this.nodeCountActive){
                this.nodeExplored++;
            }
            if(goal.isSatisfiedBy(current)){
                return getDijkstraPlan(father,planMap,current);
            }
            for(Action action : actions){
                if(action.isApplicable(current)){
                    Map<Variable,Object> next = action.successor(current);
                    int newCost = distance.get(current)+ action.getCost() ;
                    if(!distance.containsKey(next) || newCost < distance.get(next) ){
                        distance.put(next,newCost);
                        father.put(next,current);
                        planMap.put(next,action);
                        open.remove(next);
                        open.add(next);                  }
                }
            }
            
        }

        return null;

    }
    private List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
                                         Map<Map<Variable, Object>, Action> planMap,
                                         Map<Variable, Object> goalState){
                                            List<Action> plan = new ArrayList<>();
                                            Map<Variable,Object> current = goalState;
                                            while(father.get(current)!=null){
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