package blocksworld.modelling ;
import java.util.*;
import modelling.*;

/**
 * Générateur de contraintes de croissance pour un monde de blocs.
 * <p>
 * Cette classe permet de créer automatiquement un ensemble de contraintes
 * de type {@link CroissanceConstraint} pour tous les blocs d'un {@link BlockWorld}.
 * </p>
 * <p>
 * Les contraintes de croissance imposent qu'un bloc ne peut être posé que sur un bloc
 * de numéro inférieur ou directement sur la table, évitant ainsi les configurations circulaires.
 * </p>
 */
public class CroissanceGenerator {

    /** Le monde de blocs pour lequel générer les contraintes */
    private BlockWorld blockWorld;

    /**
     * Construit un générateur de contraintes de croissance pour un monde de blocs donné.
     *
     * @param blockWorld le monde de blocs pour lequel générer les contraintes
     */
    public CroissanceGenerator(BlockWorld blockWorld) {
        this.blockWorld = blockWorld;
    }

    /**
     * Génère l'ensemble des contraintes de croissance pour tous les blocs du monde.
     *
     * @return un ensemble de {@link CroissanceConstraint} pour chaque bloc
     */
    public Set<Constraint> generateCroissanceConstraint() {
        Set<Constraint> constraints = new HashSet<>();
        int nbBlocs = blockWorld.getNbBlocs();
        for (int bloc = 0; bloc < nbBlocs; bloc++) {
            Variable onVar = blockWorld.worldConfig.getOnVariable(bloc);
            constraints.add(new CroissanceConstraint(onVar));
        }
        return constraints;
    }
}
