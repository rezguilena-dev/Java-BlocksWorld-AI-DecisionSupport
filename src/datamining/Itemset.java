package datamining ;

import cp.*;
import modelling.*;
import planning.*;
import java.util.*;


/**
 * Représente un itemset dans une base de données transactionnelle, comprenant un ensemble d'items
 * et une fréquence d'apparition de cet itemset dans les transactions.
 */
public class Itemset{
    private Set<BooleanVariable> items;
    private  float frequency;
    /**
     * Constructeur de l'Itemset.
     * 
     * @param items L'ensemble des variables booléennes représentant les items de l'itemset.
     * @param frequency La fréquence d'apparition de l'itemset dans l'ensemble des transactions, comprise entre 0.0 et 1.0
     */
    public Itemset(Set<BooleanVariable> items , float frequency ){
        this.items=new HashSet<>(items);
        this.frequency = frequency;
    }
    /**
     * Retourne la fréquence d'apparition de l'itemset dans les transactions.
     * @return La fréquence de l'itemset, un nombre entre 0.0 et 1.0.
     */
    public float getFrequency(){
        return this.frequency;
    }
    /**
     * Retourne l'ensemble des items qui composent l'itemset.
     * 
     * @return L'ensemble des items de l'itemset.
     */
    public Set<BooleanVariable> getItems(){
        return this.items;
    }
}