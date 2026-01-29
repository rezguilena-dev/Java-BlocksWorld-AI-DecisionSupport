package datamining;
import cp.*;
import modelling.*;
import planning.*;
import java.util.*;
/**
 * Cette interface déclare les méthodes nécessaires pour accéder à la base de données et extraire des itemsets en fonction d'une fréquence minimale.
 */
public interface ItemsetMiner{
    /**
     * Retourne la base de données transactionnelle .
     * 
     * @return L'objet représentant la base de données transactionnelle, de type {@link BooleanDatabase}.
     */
    BooleanDatabase getDatabase();

    /**
     * Extrait l'ensemble des itemsets ayant une fréquence supérieure ou égale à la fréquence minimale donnée.
     * 
     * @param frequency La fréquence minimale d'apparition des itemsets dans les transactions.
     *                  Les itemsets ayant une fréquence plus faible seront exclus.
     * @return Un ensemble d'itemsets dont la fréquence d'apparition dans la base de données est supérieure ou égale
     *         à la fréquence minimale spécifiée.
     */
    Set<Itemset> extract(float frequency);
}