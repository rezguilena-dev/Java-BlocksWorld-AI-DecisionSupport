package blocksworld.modelling ;
import java.util.HashSet;
import java.util.Set;
import modelling.*;

/**
 * Représente un monde de blocs avec un nombre donné de blocs et de piles.
 * <p>
 * Cette classe encapsule la configuration du monde ({@link WorldConfig}) et
 * génère automatiquement l'ensemble des variables et contraintes du monde.
 * </p>
 * <p>
 * Les contraintes générées incluent :
 * <ul>
 *     <li>Les contraintes d'unicité (aucun bloc ne peut être au même endroit qu'un autre)</li>
 *     <li>Les contraintes "fixed" (un bloc sous un autre doit être indéplaçable)</li>
 *     <li>Les contraintes "free" (si un bloc est sur une pile, la pile n'est pas libre)</li>
 * </ul>
 * </p>
 */
public class BlockWorld {

    /** Nombre de blocs dans le monde */
    private int nbBlocs;

    /** Nombre de piles dans le monde */
    private int nbPiles;

    /** Configuration du monde (variables et domaines) */
    public WorldConfig worldConfig;

    /**
     * Construit un monde de blocs avec le nombre de blocs et de piles spécifié.
     *
     * @param nbBlocs nombre de blocs
     * @param nbPiles nombre de piles
     */
    public BlockWorld(int nbBlocs, int nbPiles) {
        this.nbBlocs = nbBlocs;
        this.nbPiles = nbPiles;
        this.worldConfig = new WorldConfig(nbBlocs, nbPiles);
    }

    /**
     * Retourne l'ensemble de toutes les variables du monde.
     *
     * @return un ensemble de {@link Variable} représentant toutes les variables
     */
    public Set<Variable> getVariables() {
        return this.worldConfig.getVariables();
    }

    /**
     * Retourne l'ensemble de toutes les contraintes du monde.
     * <p>
     * Cela inclut les contraintes d'unicité, les contraintes "fixed" et les contraintes "free".
     * </p>
     *
     * @return un ensemble de {@link Constraint} représentant toutes les contraintes du monde
     */
    public Set<Constraint> getConstraints() {
        getVariables();
        Set<Constraint> constraints = new HashSet<>();
        constraints.addAll(generateUniquenessConstraint());
        constraints.addAll(generateFixedConstraint());
        constraints.addAll(generateFreeConstraint());
        return constraints;
    }

    /**
     * Génère les contraintes d'unicité pour tous les blocs.
     *
     * @return un ensemble de {@link UniquenessConstraint}
     */
    private Set<Constraint> generateUniquenessConstraint() {
        Set<Constraint> constraints = new HashSet<>();
        for (int i = 0; i < nbBlocs; i++) {
            for (int j = i + 1; j < nbBlocs; j++) {
                Variable onI = worldConfig.getOnVariable(i);
                Variable onJ = worldConfig.getOnVariable(j);
                constraints.add(new UniquenessConstraint(onI, onJ));
            }
        }
        return constraints;
    }

    /**
     * Génère les contraintes "fixed" pour tous les blocs.
     *
     * @return un ensemble de {@link FixedConstraint}
     */
    private Set<Constraint> generateFixedConstraint() {
        Set<Constraint> constraints = new HashSet<>();
        for (int b = 0; b < nbBlocs; b++) {
            for (int bPrime = 0; bPrime < nbBlocs; bPrime++) {
                if (b != bPrime) {
                    Variable onB = worldConfig.getOnVariable(b);
                    BooleanVariable fixedBPrime = (BooleanVariable) worldConfig.getFixedVariable(bPrime);
                    constraints.add(new FixedConstraint(onB, fixedBPrime));
                }
            }
        }
        return constraints;
    }

    /**
     * Génère les contraintes "free" pour toutes les piles.
     *
     * @return un ensemble de {@link FreeConstraint}
     */
    private Set<Constraint> generateFreeConstraint() {
        Set<Constraint> constraints = new HashSet<>();
        for (int b = 0; b < nbBlocs; b++) {
            for (int p = 1; p <= nbPiles; p++) {
                Variable onB = worldConfig.getOnVariable(b);
                BooleanVariable freeP = (BooleanVariable) worldConfig.getFreeVariable(p);
                constraints.add(new FreeConstraint(onB, freeP));
            }
        }
        return constraints;
    }

    /**
     * Retourne le nombre de blocs dans le monde.
     *
     * @return le nombre de blocs
     */
    public int getNbBlocs() {
        return nbBlocs;
    }

    /**
     * Retourne le nombre de piles dans le monde.
     *
     * @return le nombre de piles
     */
    public int getNbPiles() {
        return nbPiles;
    }
}
