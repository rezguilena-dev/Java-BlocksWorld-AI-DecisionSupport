package blocksworld.modelling ;
import java.util.*;
import modelling.*;

/**
 * Représente la configuration d'un monde de blocs avec un nombre donné de blocs et de piles.
 * <p>
 * Cette classe permet de générer les variables représentant les positions des blocs,
 * ainsi que les variables booléennes "fixed" et "free" correspondant respectivement
 * à l'indéplaçabilité des blocs et à la disponibilité des piles.
 * </p>
 * <p>
 * Elle fournit des méthodes pour obtenir l'ensemble des variables, ainsi que
 * des méthodes utilitaires pour récupérer des variables spécifiques ou filtrer
 * par type.
 * </p>
 */
public class WorldConfig {

    /** Nombre de blocs dans le monde */
    private int nbBlocs;

    /** Nombre de piles dans le monde */
    private int nbPiles;

    /** Map associant le nom de la variable à la variable correspondante */
    private Map<String, Variable> variableMap;

    /**
     * Construit une configuration de monde avec un nombre donné de blocs et de piles.
     *
     * @param nbBlocs nombre de blocs
     * @param nbPiles nombre de piles
     */
    public WorldConfig(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;
        this.variableMap = new HashMap<>();
    }

    /**
     * Génère et retourne l'ensemble de toutes les variables du monde.
     * <p>
     * Chaque bloc possède une variable "on_b" et une variable booléenne "fixed_b".
     * Chaque pile possède une variable booléenne "free_p".
     * </p>
     *
     * @return l'ensemble des variables
     */
    public Set<Variable> getVariables() {
        Set<Variable> variables = new HashSet<>();

        for (int nb = 0; nb < nbBlocs; nb++) {
            Variable onVar = new Variable("on_" + nb, createDomain(nb));
            variables.add(onVar);
            variableMap.put(onVar.getName(), onVar);

            BooleanVariable fixedVar = new BooleanVariable("fixed_" + nb);
            variables.add(fixedVar);
            variableMap.put(fixedVar.getName(), fixedVar);
        }

        for (int nb = 1; nb <= nbPiles; nb++) {
            BooleanVariable freeVar = new BooleanVariable("free_" + nb);
            variables.add(freeVar);
            variableMap.put(freeVar.getName(), freeVar);
        }

        return variables;
    }

    /**
     * Crée le domaine de valeurs possibles pour une variable "on_b" correspondant au bloc spécifié.
     * <p>
     * Les valeurs négatives représentent les piles, et les valeurs positives représentent
     * les autres blocs (à l'exclusion du bloc lui-même).
     * </p>
     *
     * @param nb le numéro du bloc
     * @return l'ensemble des valeurs possibles pour la variable "on_b"
     */
    private Set<Object> createDomain(int nb) {
        Set<Object> domain = new HashSet<>();
        for (int stack = 1; stack <= nbPiles; stack++) {
            domain.add(-stack);
        }
        for (int i = 0; i < nbBlocs; i++) {
            if (i != nb) {
                domain.add(i);
            }
        }
        return domain;
    }

    /**
     * Retourne la variable "on_b" correspondant au bloc spécifié.
     *
     * @param block le numéro du bloc
     * @return la variable "on_b" associée au bloc
     * @throws IllegalArgumentException si la variable n'existe pas
     */
    public Variable getOnVariable(int block) {
        Variable var = variableMap.get("on_" + block);
        if (var == null) {
            throw new IllegalArgumentException("Variable on_" + block + " not found");
        }
        return var;
    }

    /**
     * Retourne la variable booléenne "fixed_b" correspondant au bloc spécifié.
     *
     * @param block le numéro du bloc
     * @return la variable booléenne "fixed_b" associée au bloc
     * @throws IllegalArgumentException si la variable n'existe pas ou n'est pas booléenne
     */
    public BooleanVariable getFixedVariable(int block) {
        Variable var = variableMap.get("fixed_" + block);
        if (var == null || !(var instanceof BooleanVariable)) {
            throw new IllegalArgumentException("Variable fixed_" + block + " not found or not boolean");
        }
        return (BooleanVariable) var;
    }

    /**
     * Retourne la variable booléenne "free_p" correspondant à la pile spécifiée.
     *
     * @param stack le numéro de la pile
     * @return la variable booléenne "free_p" associée à la pile
     * @throws IllegalArgumentException si la variable n'existe pas ou n'est pas booléenne
     */
    public BooleanVariable getFreeVariable(int stack) {
        Variable var = variableMap.get("free_" + stack);
        if (var == null || !(var instanceof BooleanVariable)) {
            throw new IllegalArgumentException("Variable free_" + stack + " not found or not boolean");
        }
        return (BooleanVariable) var;
    }

    /**
     * Retourne l'ensemble des variables "on_b" pour tous les blocs.
     *
     * @return l'ensemble des variables "on_b"
     */
    public Set<Variable> getOnVariables() {
        Set<Variable> onVars = new HashSet<>();
        for (String key : variableMap.keySet()) {
            if (key.startsWith("on_")) {
                onVars.add(variableMap.get(key));
            }
        }
        return onVars;
    }

    /**
     * Retourne l'ensemble des variables booléennes "fixed_b" pour tous les blocs.
     *
     * @return l'ensemble des variables "fixed_b"
     */
    public Set<BooleanVariable> getFixedVariables() {
        Set<BooleanVariable> fixedVars = new HashSet<>();
        for (String key : variableMap.keySet()) {
            if (key.startsWith("fixed_")) {
                fixedVars.add((BooleanVariable) variableMap.get(key));
            }
        }
        return fixedVars;
    }

    /**
     * Retourne l'ensemble des variables booléennes "free_p" pour toutes les piles.
     *
     * @return l'ensemble des variables "free_p"
     */
    public Set<BooleanVariable> getFreeVariables() {
        Set<BooleanVariable> freeVars = new HashSet<>();
        for (String key : variableMap.keySet()) {
            if (key.startsWith("free_")) {
                freeVars.add((BooleanVariable) variableMap.get(key));
            }
        }
        return freeVars;
    }
}
