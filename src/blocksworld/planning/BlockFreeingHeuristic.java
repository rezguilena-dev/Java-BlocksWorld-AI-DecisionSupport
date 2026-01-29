package blocksworld.planning ;
import java.util.*;
import modelling.*;
import planning.*;

/**
 * Cette heuristique, est conçue afin d'estimer dans le monde des blocs le coût restant pour atteindre un état but en se basant sur le nombre de blocs
 * qui ne sont pas à leur position finale (selon l'état but) et en pénalisant ceux qui sont
 * actuellement "bloqués" par un autre bloc au-dessus.
 * * L'estimation est calculée comme suit :
 * <ul>
 * <li>Chaque bloc {@code B} qui n'est pas à sa position but contribue pour {@code 1} au coût.</li>
 * <li>Si un tel bloc {@code B} est bloqué (c'est-à-dire qu'un autre bloc est posé dessus),
 * il ajoute une pénalité de {@code 0.5f} au coût.</li>
 * </ul>
 * Le coût total est la somme de ces contributions.
 */
public class  BlockFreeingHeuristic implements Heuristic{

    /** État but, associant chaque variable à sa valeur cible. */
    private Map<Variable, Object> but;
    /**
    * Constructeur de  BlockFreeingHeuristic prenant en paramètre  un état but donné.
    * @param but l'état but est représenté par une association de variables et leurs valeurs à l'état final .
    */
    public BlockFreeingHeuristic(Map<Variable, Object> but) {
        this.but = but;
    }

    /**
    * Estime le coût nécessaire pour atteindre le but depuis l'état courant.
    * Le coût est basé sur le nombre de blocs mal positionnés ainsi que sur
    * leur état de blocage.
    * @param etat l'état courant représenté par une association de variables et de valeurs
    * @return le coût heuristique estimé
    */
    @Override 
    public float estimate(Map<Variable,Object> etat) {
        int cout = 0;
        // Renvoie tous les numéros de blocs qui ne sont pas à leur position finale
        Set<Object> blocsToMove = getBlocsToMove(etat,but);
        //On parcourt chaque bloc et on vérifie s'il ya quelque chose en dessous
        for(Object bloc : blocsToMove){
            cout +=1;
            if(isBlocBlocked(bloc,etat)){
                cout +=0.5f;
            }
        }
        return cout ;
            

        
    }


    /**
    * Identifie les blocs qui ne sont pas à leur position finale.
    * @param etat l'état courant
    * @param but le but
    * @return l'ensemble des blocs mal placés
    */
    private Set<Object> getBlocsToMove(Map<Variable,Object> etat ,Map<Variable,Object> but){
        Set<Object> blocsToMove = new HashSet<>();
        for(Variable var : but.keySet()){
            if(var.getName().startsWith("on_")){
                Object actualVal = etat.get(var);
                Object objectifVal = but.get(var);
                if(!actualVal.equals(objectifVal)){
                    //On récupere le numéro du bloc qui n'est pas à sa place
                    int bloc = Integer.parseInt(var.getName().substring(3)); 
                    //On ajoute ce dernier à la liste des blocs a déplacer
                    blocsToMove.add(bloc);

                }
            }
        }
        return blocsToMove;
    }
        /**
    * Vérifie si un bloc est bloqué par un autre.
    * @param block le bloc à analyser
    * @param etat l'état courant
    * @return vrai si le bloc est bloqué, faux sinon
    */
    private boolean isBlocBlocked(Object block,Map<Variable,Object> etat){
        for (Variable var : etat.keySet()) {
            if (var.getName().startsWith("on_")) {
                if (Objects.equals(etat.get(var), block)) {
                    return true;
                }
            }
        }
        return false;
    }

}
