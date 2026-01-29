package datamining;
import cp.*;
import modelling.*;
import planning.*;
import java.util.*;

/**
 * Classe abstraite implémentant l'interface {@link ItemsetMiner}
 * Cette classe permet de calculer la fréquence d'un ensemble d'items dans la base et fournit un mécanisme de
 * comparaison pour les itemsets triés.
 */
public abstract class AbstractItemsetMiner implements ItemsetMiner{
    
    private BooleanDatabase database;
    /**
     * Comparator statique pour comparer des variables booléennes par leur nom.
     * Ce comparateur sera  utilisé pour trier des ensembles d'items de type {@link SortedSet<BooleanVariable>}.
     */
    public static final Comparator<BooleanVariable> COMPARATOR =
(var1, var2) -> var1.getName().compareTo(var2.getName());

    /**
     * Constructeur de la classe {@link AbstractItemsetMiner}.
     * 
     * @param database La base de données transactionnelle contenant les transactions et les items.
     *                 elle sera  utilisée pour l'extraction des itemsets et le calcul de leurs fréquences.
     */
    public AbstractItemsetMiner(BooleanDatabase database){
        this.database=database;
    }
    /**
     * Retourne la base de données transactionnelle .
     * 
     * @return L'objet {@link BooleanDatabase} représentant la base de données.
     */
    public BooleanDatabase getDatabase(){
        return this.database;
    }
    /**
     * Calcule la fréquence d'un ensemble donné d'items dans la base de données.
     * La fréquence est définie comme la proportion de transactions contenant tous les items de l'ensemble
     * par rapport au nombre total de transactions.
     * 
     * @param items L'ensemble d'items dont on souhaite calculer la fréquence.
     * @return La fréquence de l'ensemble d'items.
    */
    public float frequency(Set<BooleanVariable> items){
        List<Set<BooleanVariable>> transactions = this.database.getTransactions();
        int totalTransactions = transactions.size();
        if (totalTransactions==0){
            return 0.0f;
        }
        int count = 0;
        for (Set<BooleanVariable> transaction : transactions){
            if (transaction.containsAll(items)){
                count++;
            }
        }
    return (float) count / (float) totalTransactions;
    }   
}