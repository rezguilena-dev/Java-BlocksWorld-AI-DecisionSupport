package blocksworld.planning ;
import java.util.*;
import modelling.*;
import planning.*;


/**
*Cette heuristique, est conçue afin d'estimer dans le monde des blocs le coût restant pour atteindre un état but .
* Elle compte le nombre de variables "on_" qui ne correspondent pas
* à la configuration finale souhaitée.
*/
public class MisplacedGoalHeuristic implements Heuristic{
    

    /** État but, associant chaque variable à sa valeur cible. */
    private Map<Variable, Object> but;
    /**
    * Construit l'heuristique avec un  but donné.
    * @param but une map représentant la configuration finale attendue
    */
    public MisplacedGoalHeuristic(Map<Variable, Object> but) {
        this.but = but;
    }

   /**
    * Estime le coût heuristique en comptant le nombre de blocs mal placés.
    * Seules les variables "on_" sont prises en compte.
    * @param etat état courant
    * @return le nombre de blocs dont la position ne correspond pas au but
    */
    @Override 
    public float estimate (Map<Variable,Object> etat){
        int cout =0 ;
        for(Variable var : etat.keySet()){
            if(var.getName().startsWith("on_")){
                if(but.get(var) != null && !etat.get(var).equals(but.get(var))){
                    cout++;
                }
            }
        }
        return cout;
    }

}