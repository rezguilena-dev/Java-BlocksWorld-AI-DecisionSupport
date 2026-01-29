package datamining;
import cp.*;
import modelling.*;
import planning.*;
import java.util.*;

/**
 * Représente une règle d'association de type <i>prémisse -> conclusion</i>, avec une fréquence et une confiance.

 */
public class AssociationRule{
    // Prémisse de la règle (les items qui impliquant la conclusion)
    private Set<BooleanVariable> premise;
    // Conclusion de la règle (les items qui sont prédits par la prémisse)
    private Set<BooleanVariable> conclusion;
    // Fréquence de la règle
    private float frequency;
     // Confiance de la règle
    private float confidence;

        /**
     * Constructeur de la classe AssociationRule.
     * 
     * @param premise L'ensemble des items qui forment la prémisse de la règle.
     * @param conclusion L'ensemble des items qui forment la conclusion de la règle.
     * @param frequency La fréquence de la règle (probabilité que la règle apparaisse).
     * @param confidence La confiance de la règle (probabilité que la conclusion soit vraie lorsque la prémisse est vraie).
     */
    public AssociationRule(Set<BooleanVariable>premise,Set<BooleanVariable> conclusion , float frequency, float confidence){
        this.premise=premise;
        this.conclusion=conclusion;
        this.frequency=frequency;
        this.confidence=confidence;
    }
    /**
     * Accesseur pour la prémisse de la règle.
     * 
     * @return La prémisse de la règle.
     */
    public Set<BooleanVariable> getPremise() {
        return this.premise;
    }
    /**
     * Accesseur pour la conclusion de la règle.
     * 
     * @return La conclusion de la règle.
     */
    public Set<BooleanVariable> getConclusion() {
        return this.conclusion;
    }
    /**
     * Accesseur pour la fréquence de la règle.
     * 
     * @return La fréquence de la règle.
     */
    public float getFrequency() {
        return this.frequency;
    }
    /**
     * Accesseur pour la confiance de la règle.
     * 
     * @return La confiance de la règle.
     */
    public float getConfidence() {
        return this.confidence;
    }
}