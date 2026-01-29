package planning;
import modelling.Variable;   
import java.util.Map;         
import java.util.Set;       
import java.util.List;        
import java.util.ArrayList;   
import java.util.HashSet;     
import java.util.HashMap;     
import java.util.Stack;  
import java.util.Collections; 

public class DFSPlanner implements Planner{
    private Map<Variable,Object> etat;
    private Set<Action> ensembleA;
    private Goal but;
    private boolean nodeCountActive;
    private int nodeExplored;
    public DFSPlanner(Map<Variable,Object> etat,Set<Action> ensembleA,Goal but){
        this.etat = etat;
        this.ensembleA = ensembleA;
        this.but = but;
        this.nodeCountActive = false;
        this.nodeExplored=0;
    }
    @Override 
    public List<Action>plan(){
        Stack<Action> plan = new Stack<>();
        Set<Map<Variable, Object>> visited = new HashSet<>();
        visited.add(new HashMap<>(etat));
        return dfs(etat,plan,visited);
    }
    public List<Action> dfs(Map<Variable,Object> etat,Stack<Action> plan ,Set<Map<Variable, Object>> visited){
        if(this.nodeCountActive){
            this.nodeExplored++;
        }
        if(but.isSatisfiedBy(etat)){
            return new ArrayList<>(plan);
        }
        for(Action action : ensembleA){
            if(action.isApplicable(etat)){
                Map<Variable,Object> next = action.successor(etat);
                //si l'etat suivant na pas ete visite 
                if(!visited.contains(next)){
                    plan.push(action); //on ajoute l'action à notre plan
                    visited.add(next); //on ajoute l'etat comme visité
                    List<Action> subplan = dfs(next,plan,visited); //on visite les sous etats
                    if(subplan != null){
                        return subplan;
                    }
                    else {
                        plan.pop();
                    }
                }
                
            }
        }
        return  null;
    }

    @Override
    public Goal getGoal(){
        return but;
    }
    public Map<Variable, Object> getInitialState(){
        return etat;
    }
    public Set<Action> getActions(){
        return ensembleA;
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