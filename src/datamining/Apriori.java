package datamining;
import cp.*;
import modelling.*;
import planning.*;
import java.util.*;

/**
 * Classe représentant un extracteur d'itemsets basé sur l'algorithme Apriori.
 * L'algorithme Apriori permet d'extraire des itemsets fréquents à partir d'une base de données transactionnelle.
 * Cette classe étend {@link AbstractItemsetMiner} et implémente la méthode d'extraction des itemsets fréquents
 */


public class Apriori extends AbstractItemsetMiner{
    public BooleanDatabase database;
    
    public Apriori(BooleanDatabase database){
        super(database);
    }
    /**
     * Extrait l'ensemble des itemsets fréquents de la base de données, en appliquant l'algorithme Apriori.
     * Cet algorithme fonctionne par niveaux, en commençant par les itemsets simples (singletons)
     * puis en combinant ces itemsets pour générer des itemsets plus grands, tout en vérifiant
     * que la condition de fréquence minimale est satisfaite  à chaque étape.
     * 
     * @param minFrequency La fréquence minimale d'apparition des itemsets dans la base de données.
     * @return Un ensemble d'itemsets fréquents, dont la fréquence est supérieure ou égale à minFrequency.
     */
    @Override
    public Set<Itemset> extract(float minFrequency) {
        
        Set<Itemset> allFrequentItemsets = new HashSet<>();

        // Extraire les itemsets fréquents de taille 1 (singletons)
        Set<Itemset> frequentSingletons = this.frequentSingletons(minFrequency);
        
        allFrequentItemsets.addAll(frequentSingletons);

         // Liste des itemsets fréquents de taille k-1
        List<SortedSet<BooleanVariable>> frequentSetsKMinus1 = new ArrayList<>();
        for (Itemset itemset : frequentSingletons) {

            SortedSet<BooleanVariable> sortedSet = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            sortedSet.addAll(itemset.getItems());
            frequentSetsKMinus1.add(sortedSet);
        }

        while (!frequentSetsKMinus1.isEmpty()) {

            Set<SortedSet<BooleanVariable>> candidatesK = new HashSet<>();

            Set<SortedSet<BooleanVariable>> frequentSetLookup = new HashSet<>(frequentSetsKMinus1);

            // Combiner les itemsets de taille k-1 pour générer des candidats de taille k
            for (int i = 0; i < frequentSetsKMinus1.size(); i++) {
                for (int j = i + 1; j < frequentSetsKMinus1.size(); j++) {
                    
                    SortedSet<BooleanVariable> s1 = frequentSetsKMinus1.get(i);
                    SortedSet<BooleanVariable> s2 = frequentSetsKMinus1.get(j);

                    SortedSet<BooleanVariable> candidate = combine(s1, s2);

                    if (candidate != null) {

                        if (allSubsetsFrequent(candidate, frequentSetLookup)) {
                            candidatesK.add(candidate);
                        }
                    }
                }
            }

            // Vérifier les fréquences des candidats de taille k
            List<SortedSet<BooleanVariable>> frequentSetsK = new ArrayList<>();
            for (SortedSet<BooleanVariable> candidate : candidatesK) {

                float frequency = this.frequency(candidate);
                if (frequency >= minFrequency) {

                    frequentSetsK.add(candidate);
                    allFrequentItemsets.add(new Itemset(candidate, frequency));

                }
            }
            // Passer à la prochaine itération avec les itemsets de taille k
            frequentSetsKMinus1 = frequentSetsK;
        }
        return allFrequentItemsets;
    }

    /**
     * Extrait les itemsets fréquents de taille 1 (singletons) de la base de données, dont la fréquence
     * est supérieure ou égale à la fréquence minimale donnée.
     * 
     * @param minFrequency La fréquence minimale d'apparition des itemsets.
     * @return Un ensemble d'itemsets de taille 1 (singletons) dont la fréquence est supérieure ou égale à minFrequency.
     */

    public Set<Itemset> frequentSingletons(float minFrequency){
        Set<Itemset> frequentSingletons = new HashSet<>();
        Set<BooleanVariable> allItems = this.getDatabase().getItems();

        for (BooleanVariable item : allItems) {
            Set<BooleanVariable> singleton = new HashSet<>();
            singleton.add(item);
            float frequency = this.frequency(singleton);
            if (frequency >= minFrequency){
                frequentSingletons.add(new Itemset(singleton, frequency));
            }
        }
        return frequentSingletons;
    }

    /**
     * Combine deux ensembles d'items de même taille k, sous condition que :
     * - les ensembles ont les mêmes k - 1 premiers items,
     * - les ensembles ont des éléments différents pour le dernier item.
     * 
     * Si ces conditions sont remplies, retourne un ensemble combiné trié de taille k ; sinon, retourne null.
     * 
     * @param s1 Le premier ensemble d'items.
     * @param s2 Le deuxième ensemble d'items.
     * @return Un ensemble combiné d'items de taille k, ou null si les conditions de combinaison ne sont pas remplies.
     */

    public static SortedSet<BooleanVariable> combine(
            SortedSet<BooleanVariable> s1,
            SortedSet<BooleanVariable> s2) {

        int k = s1.size();
        if (k < 1 || k != s2.size()) {
            return null;
        }

        List<BooleanVariable> list1 = new ArrayList<>(s1);
        List<BooleanVariable> list2 = new ArrayList<>(s2);

        for (int i = 0; i < k - 1; i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return null;
            }
        }

        BooleanVariable last1 = list1.get(k - 1);
        BooleanVariable last2 = list2.get(k - 1);


        if (last1.equals(last2)) {
            return null;
        }

        SortedSet<BooleanVariable> combined = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        
        combined.addAll(s1);
        combined.add(last2);

        return combined;
    }
    /**
     * Vérifie si tous les sous-ensembles d'un ensemble d'items de taille k sont fréquents dans la collection donnée.
     * Cette méthode exploite la propriété d'antimonotonie de l'algorithme Apriori, qui garantit que si un itemset
     * de taille k est fréquent, alors tous ses sous-ensembles de taille k-1 doivent également l'être.
     * 
     * @param items L'ensemble d'items de taille k.
     * @param collections La collection d'itemsets fréquents de taille k-1.
     * @return true si tous les sous-ensembles de items sont fréquents, false sinon.
     */
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items , Collection<SortedSet<BooleanVariable>> collections ){
        if (items.size() <= 1) {
            return true;
        }
        for (BooleanVariable itemToRemove : items) {

            SortedSet<BooleanVariable> subset = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            
  
            subset.addAll(items); 

            subset.remove(itemToRemove); 


            if (!collections.contains(subset)) {
                return false;
            }
        }
        return true;
    }
}
