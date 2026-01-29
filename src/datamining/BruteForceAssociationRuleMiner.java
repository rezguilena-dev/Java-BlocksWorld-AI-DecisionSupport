package datamining;
import modelling.BooleanVariable;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {
     // Constructeur prenant la base de données en argument
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }
    /**
     * Génère tous les sous-ensembles d'un ensemble d'items, excepté l'ensemble vide et l'ensemble lui-même.
     * @param items l'ensemble d'items
     * @return l'ensemble de tous les sous-ensembles possibles
     */
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        List<BooleanVariable> itemList = new ArrayList<>(items);
        int n = itemList.size();     
        Set<Set<BooleanVariable>> allPremises = new HashSet<>();
        long max = (1L << n);
        for (long i = 1; i < max - 1; i++) {
            
            Set<BooleanVariable> premise = new HashSet<>();
            

            for (int j = 0; j < n; j++) {
                

                if (((i >> j) & 1L) == 1L) {
                    premise.add(itemList.get(j));
                }
            }
            allPremises.add(premise);
        }
        return allPremises;
    }

    /**
     * Extrait toutes les règles d'association avec une fréquence et une confiance supérieures aux seuils donnés.
     * @param minFrequency la fréquence minimale pour qu'une règle soit considérée
     * @param minConfidence la confiance minimale pour qu'une règle soit considérée
     * @return l'ensemble des règles d'association valides
     */
    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        
        Set<AssociationRule> allValidRules = new HashSet<>();

        ItemsetMiner itemsetMiner = new Apriori(this.getDatabase());
        
        Set<Itemset> allFrequentItemsets = itemsetMiner.extract(minFrequency);

        for (Itemset frequentItemset : allFrequentItemsets) {
            
            Set<BooleanVariable> itemsetItems = frequentItemset.getItems();

          
            if (itemsetItems.size() <= 1) {
                continue;
            }
            
            float freqItemset = frequentItemset.getFrequency();

            Set<Set<BooleanVariable>> allPremises = allCandidatePremises(itemsetItems);

            for (Set<BooleanVariable> premise : allPremises) {
                
                
                Set<BooleanVariable> conclusion = new HashSet<>(itemsetItems);
                conclusion.removeAll(premise); 

                
                float confidence = AbstractAssociationRuleMiner.confidence(
                                        premise, conclusion, allFrequentItemsets);

                if (confidence >= minConfidence) {
                    
                    AssociationRule rule = new AssociationRule(
                                            premise, conclusion, freqItemset, confidence);
                    allValidRules.add(rule);
                }
            }
        }

        return allValidRules;
    }
}