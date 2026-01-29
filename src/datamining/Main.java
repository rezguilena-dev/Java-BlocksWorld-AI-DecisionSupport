package datamining;

import modelling.BooleanVariable;


import java.util.Set;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        //On déclare les items (chaque item représentant un produit )
        BooleanVariable pantalon = new BooleanVariable("Pantalon");
        BooleanVariable tshirt = new BooleanVariable("Tshirt");
        BooleanVariable echarpe = new BooleanVariable("echarpe ");
        BooleanVariable chaussettes = new BooleanVariable("Chaussettes");
        BooleanVariable chaussures = new BooleanVariable("Chaussures");

        //Création de l'ensemble des items 
        Set<BooleanVariable> allItems = Set.of(pantalon, tshirt, echarpe, chaussettes, chaussures);

        //Instanciation d'une base de donnée contenant l'ensemble des items
        System.out.println("Création de la base de données...");
        BooleanDatabase db = new BooleanDatabase(allItems);

        // Ajout des transactions à la base de données 
        
        db.add(Set.of(pantalon, tshirt, echarpe));   
        db.add(Set.of(pantalon, chaussettes, chaussures)); 
        db.add(Set.of(pantalon, tshirt, chaussures));  
        db.add(Set.of(chaussettes, chaussures));      
        db.add(Set.of(pantalon, tshirt));        

        System.out.println("Base de données créée avec " 
                           + db.getTransactions().size() + " transactions.");

        
        // Test de l'algorithme : APRIORI
        
        // On défini une fréquence minimale de 40%
        float minFrequency = 0.4f; 
        
        System.out.println("\n---  Test de Apriori (minFreq = " + minFrequency + ") ---");

      
        ItemsetMiner apriori = new Apriori(db);

        // On lance l'extraction 
        Set<Itemset> frequentItemsets = apriori.extract(minFrequency);

        System.out.println("Résultat : " + frequentItemsets.size() + " itemsets fréquents trouvés.");

        // Affichage des résultats
        for (Itemset itemset : frequentItemsets) {
            
            System.out.println("  > " + itemset.getItems() 
                               + "\t (fréq: " + itemset.getFrequency() + ")");
        }


        // Test de : BRUTE FORCE
        
        //Confiance minimale à 60%
        float minConfidence = 0.6f; 

        System.out.println("\n--- Test du RuleMiner (minConf = " + minConfidence + ") ---");
        
        
        AssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(db);

        // On lance l'extraction
        Set<AssociationRule> rules = ruleMiner.extract(minFrequency, minConfidence);

        System.out.println("Résultat : " + rules.size() + " règles valides trouvées.");

        // Affichage des regles trouvées
        for (AssociationRule rule : rules) {
            System.out.println("  > " + rule.getPremise() + "  ==>  " + rule.getConclusion()
                               + "\t (conf: " + rule.getConfidence() 
                               + ", freq: " + rule.getFrequency() + ")");
        }

        System.out.println("\n---  Test terminé ---");
    }
}