package blocksworld;

import blocksworld.datamining.*;
import modelling.*;

import java.util.List;
import java.util.Set;
import datamining.*;
import java.util.Random;
import bwgeneratordemo.Demo; 
/**
 * Classe exécutable principale pour la Partie 4 : Extraction de Connaissances (Data Mining).
 * 
 * Génération de données : Création d'une base de données d'états aléatoires (transactions).
 * Propositionnalisation : Conversion des états complexes en ensembles de variables booléennes via { BlocksWorldConverter}.
 * Extraction de motifs (Étape 1) : Utilisation de l'algorithme { Apriori} pour trouver les combinaisons fréquentes d'items.
 * Génération de règles (Étape 2) : Utilisation de { BruteForceAssociationRuleMiner} pour déduire des règles d'association (Si X alors Y) avec une confiance élevée.</li>
 *
 * 
 */
public class MainMining {

    public static void main(String[] args) {
        
        System.out.println("!!!!!!!!!! TEST DE COMPILATION  !!!!!!!!!!");

        // === Paramètres ===
        int NB_BLOCS = 5;
        int NB_PILES = 5;
        int DB_SIZE = 10000; 


        float MIN_FREQUENCY = (float) (2.0 / 3.0); 
        float MIN_CONFIDENCE = (float) (95.0 / 100.0); 
        System.out.println("Lancement de l'extraction de connaissances...");
        System.out.println("Paramètres : " + NB_BLOCS + " blocs, " + NB_PILES + " piles.");
        System.out.println("Base de données : " + DB_SIZE + " instances.");
        System.out.println("Fréquence Min : " + String.format("%.2f%%", MIN_FREQUENCY * 100));
        System.out.println("Confiance Min : " + String.format("%.2f%%", MIN_CONFIDENCE * 100));

        // --- 1. Préparation ---
        BlocksWorldConverter converter = new BlocksWorldConverter(NB_BLOCS, NB_PILES);
        BooleanDatabase db = new BooleanDatabase(converter.getAllItems());
        Random random = new Random();

        System.out.println("\n1. Génération de la base de données...");
        for (int i = 0; i < DB_SIZE; i++) {
            List<List<Integer>> state = Demo.getState(random);
            Set<BooleanVariable> transaction = converter.convertStateToTransaction(state);
            db.add(transaction);
        }
        System.out.println("Base de données générée (" + db.getTransactions().size() + " transactions).");

        // --- 2. Étape 1: Trouver les Motifs Fréquents ---
        System.out.println("\n2. Lancement d'Apriori (Étape 1)...");
        ItemsetMiner miner = new Apriori(db);
        Set<Itemset> frequentItemsets = miner.extract(MIN_FREQUENCY);

        // --- 3. Étape 2: Générer les Règles ---
        System.out.println("\n3. Lancement de la génération de règles (Étape 2)...");
        AssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(db);
        Set<AssociationRule> rules = ruleMiner.extract(MIN_FREQUENCY, MIN_CONFIDENCE);
        
        System.out.println(rules.size() + " règles trouvées avec confiance >= " + String.format("%.0f%%", MIN_CONFIDENCE * 100));

        // --- 4. Afficher les résultats ---
        for (AssociationRule rule : rules) {
            System.out.println("RÈGLE : " + rule.getPremise() + " --> " + rule.getConclusion() 
                             + " (Freq: " + String.format("%.2f%%", rule.getFrequency()*100) 
                             + ", Conf: " + String.format("%.2f%%", rule.getConfidence()*100) + ")");
        }
        
        System.out.println("\nExtraction terminée.");
    }
}