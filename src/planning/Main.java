package planning;

import modelling.Variable;
import java.util.*;
public class Main {

    public static void main(String[] args) {
    //Déclaration des variables et leur domaine 
    Set<Object> domaine1 = Set.of(1,2,3,4);
    Set<Object> domaine2 = Set.of(2,3);
    Variable x= new Variable("x",domaine1);
    Variable y = new Variable("y",domaine2);
    Variable z = new Variable("z",domaine1);

    //Etat initial(x:1,y:2,z:3)
    Map<Variable,Object> etat = new HashMap<>();
    etat.put(x,1);
    etat.put(y,2);
    etat.put(z,3);
    
    //Décalaration des preconditions , effets

    //1- (x=1 , y=2) -> (x=3 , z=4)
    Map<Variable,Object> pre1 = Map.of(x,1,y,2);
    Map<Variable,Object> eff1 = Map.of(x,3,z,4);

    //2- (x=3 , z=4) -> (x=4 , z=3)
    Map<Variable,Object> pre2 =Map.of(x,3,z,4);
    Map<Variable,Object> eff2 =Map.of(x,4,z,3);

    //3- (x=1 , z=3) -> (y=3)
    Map<Variable,Object> pre3 = Map.of(x,1,z,3);
    Map<Variable,Object> eff3 =Map.of(y,3);

    //4- (x=1 , y=2) -> (x=2,z=2)
    Map<Variable,Object> pre4 = Map.of(x,1,y,2);
    Map<Variable,Object> eff4 = Map.of(x,2,z,2);

    //5- (x=2 , z=2) -> (x=4,z=3)
    Map<Variable,Object> pre5 = Map.of(x,2,z,2);
    Map<Variable,Object> eff5 = Map.of(x,4,z,3);



    //ensemble d'actions
    Set<Action> actions = new HashSet<>();
    actions.add(new BasicAction(pre5,eff5,3));
    actions.add(new BasicAction(pre2,eff2,1));
    actions.add(new BasicAction(pre1,eff1,1));
    actions.add(new BasicAction(pre4,eff4,2));
    actions.add(new BasicAction(pre3,eff3,2));



    //Définition du but 
    Map<Variable,Object> but = new HashMap<>();
    but.put(x,4);
    but.put(y,2);
    but.put(z,3);

    Goal goal = new BasicGoal(but);

    //Lancement des algorithmes 
    //1-BFS
    BFSPlanner bfs=new BFSPlanner(etat,actions,goal);
    bfs.activateNodeCount(true);
    List<Action> plan = bfs.plan();
    int nbNode = bfs.getNodesExplored();
    if(plan== null){
        System.out.println("aucun plan trouvé pour BFS");
    }
    else{
        System.out.println("plan trouvé pour BFS");
        System.out.println("état initial : " + etat);
        for(Action a :plan){
            System.out.println(a);
        }

        System.out.println("Nombre de noeuds explorés :" + nbNode);
    }

    System.out.println("************************************");
    //2 DFS
    DFSPlanner dfs=new DFSPlanner(etat,actions,goal);
    dfs.activateNodeCount(true);
    List<Action> plan2 = dfs.plan();
    int nbNode2 = dfs.getNodesExplored();
    if(plan== null){
        System.out.println("aucun plan trouvé pour DFS");
    }
    else{
        System.out.println("plan trouvé pour DFS");
        for(Action a :plan2){
            System.out.println(a);
        }
        System.out.println("Nombre de noeuds explorés :" + nbNode2);
    }

    //3 Djikstra
    System.out.println("************************************");
    DijkstraPlanner djikstra = new DijkstraPlanner(etat,actions,goal);
    djikstra.activateNodeCount(true);
    List<Action> plan3 = djikstra.plan();
    int nbNode3 = djikstra.getNodesExplored();
    if(plan== null){
        System.out.println("aucun plan trouvé pour Djikstra");
    }
    else{
        System.out.println("plan trouvé pour Djikstra");
        for(Action a :plan3){
            System.out.println(a);
        }
        System.out.println("Nombre de noeuds explorés :" + nbNode3);
    }

    System.out.println("************************************");

    Heuristic h= new Heuristic(){
        @Override
        public float estimate(Map<Variable,Object> state){
            float count =0;
            for(Map.Entry<Variable,Object> entry : but.entrySet()){
                Variable variable =entry.getKey();
                Object GoalValue = entry.getValue();
                Object value = state.get(variable);
                if(value == null || !value.equals(GoalValue)){
                    count++;
                }
            }
            return count;
        }
    };








    AStarPlanner AStar = new AStarPlanner(etat, actions, goal, h);
    AStar.activateNodeCount(true);
    List<Action> plan4 = AStar.plan();
    int nbNode4 = AStar.getNodesExplored();
    if(plan== null){
        System.out.println("aucun plan trouvé pour AStar");
    }
    else{
        System.out.println("plan trouvé pour Astar");
        for(Action a :plan4){
            System.out.println(a);
        }
        System.out.println("Nombre de noeuds explorés :" + nbNode4);
    }



   
   
    }
}
