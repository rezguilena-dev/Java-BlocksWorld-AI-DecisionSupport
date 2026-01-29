package datamining;
import cp.*;
import modelling.*;
import planning.*;
import java.util.*;

/**
 * Interface définissant les méthodes nécessaires pour l'extraction de règles d'association
 * à partir d'une base de données de transactions.
 */
public interface AssociationRuleMiner{
    /**
     * Retourne l'instance de la base de données utilisée .
     * 
     * @return La base de données (BooleanDatabase) utilisée .
     */
    BooleanDatabase getDatabase();
    /**
     * Extrait les règles d'association à partir de la base de données, en fonction des seuils de fréquence et de confiance.
     * 
     * @param frequency Le seuil minimum de fréquence pour les règles à extraire.
     * @param confidence Le seuil minimum de confiance pour les règles à extraire.
     * @return Un ensemble de règles d'association  dont la fréquence et la confiance sont supérieures ou égales aux seuils donnés.
     */
    Set<AssociationRule> extract(float frequency , float confidence);
}