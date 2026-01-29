package datamining ;
import cp.*;
import modelling.*;
import planning.*;

import java.util.*;

/**
 * Représente une base de données transactionnelle contenant des transactions avec des variables booléennes.
 * Cette classe permet d'ajouter et d'accéder à l'ensemble des items et transactions.
 */



public class BooleanDatabase{
    private Set<BooleanVariable> items;
    private  List<Set<BooleanVariable>> transactions;

    /**
     * Constructeur de la base de données.
     * 
     * @param items L'ensemble des variables booléennes représentées dans la base de données.
     *              Chaque item peut être associé à une ou plusieurs transactions.
     */
    public BooleanDatabase(Set<BooleanVariable> items ){
        this.items=new HashSet<>(items);
        this.transactions = new ArrayList<>();
    }
    /**
     * Ajoute une transaction à la base de données.
     * 
     * @param transaction Un ensemble  de variables booléennes(les items) représentant la transaction à ajouter.
     */
    public void add (Set<BooleanVariable> transaction){
        this.transactions.add(transaction);
    }
    /**
     * Retourne l'ensemble des items présents dans la base de données.
     * 
     * @return L'ensemble des items.
     */
    public Set<BooleanVariable> getItems() {
        return this.items;
        
    }
    /**
     * Retourne la liste des transactions enregistrées dans la base de données.
     * Chaque transaction est représentée par un ensemble de variables booléennes.
     * 
     * @return La liste des transactions.
     */
    public List<Set<BooleanVariable>> getTransactions() {
        return this.transactions;
    }
    
}