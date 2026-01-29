package datamining;
import cp.*;
import modelling.*;
import planning.*;
import java.util.*;



public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner{
    private BooleanDatabase database;
    public AbstractAssociationRuleMiner(BooleanDatabase database){
        this.database=database;
    }
    public BooleanDatabase getDatabase(){
        return this.database;
    }
    public static float frequency(Set<BooleanVariable> items,
                                  Set<Itemset> allFrequentItemsets) {
        
        for (Itemset frequentItemset : allFrequentItemsets) {

            if (frequentItemset.getItems().equals(items)) {
                
                return frequentItemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("L'itemset " + items + 
                           " n'a pas été trouvé dans l'ensemble des itemsets fréquents.");
        }
    public static float confidence(Set<BooleanVariable> premise,
                                   Set<BooleanVariable> conclusion,
                                   Set<Itemset> allFrequentItemsets) {


        float freqPremise = frequency(premise, allFrequentItemsets);

        Set<BooleanVariable> union = new HashSet<>(premise);
        union.addAll(conclusion);

        float freqUnion = frequency(union, allFrequentItemsets);
        if (freqPremise == 0.0f) {
            return 0.0f;
        }

        return freqUnion / freqPremise;
    }
    public abstract Set<AssociationRule> extract(float minFrequency, float minConfidence);
}