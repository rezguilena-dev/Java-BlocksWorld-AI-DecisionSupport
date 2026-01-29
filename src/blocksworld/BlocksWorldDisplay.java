package blocksworld;
import blocksworld.modelling.*;

import modelling.*;
import planning.Action;
import java.awt.Dimension;
import java.util.Map;
import java.util.List;
import javax.swing.*;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;
import bwui.BWComponent;

/**
 * Classe utilitaire pour la visualisation graphique du Monde des Blocs.
 *
 * Cette classe fait l'interface entre la représentation interne (Map de variables)
 * et la librairie graphique externe fournie (bwmodel et bwui).
 */
public class BlocksWorldDisplay {

    /**
     * Convertit un état interne (Map de Variables) en un objet BWState
     *
     * @param instanciation L'état actuel (valeurs des variables on_b).
     * @param nbBlocs Le nombre total de blocs.
     * @param config La configuration pour récupérer les noms des variables.
     * @return Un objet BWState prêt à être affiché.
     */
    public static BWState<Integer> makeBWState(Map<Variable, Object> instanciation, int nbBlocs, WorldConfig config) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(nbBlocs);
        for (int b = 0; b < nbBlocs; b++) {
            Variable onB = config.getOnVariable(b); 
            if (instanciation.containsKey(onB)) {
                Object val = instanciation.get(onB);
                if (val instanceof Integer) {
                    int under = (Integer) val;
                    // Si la valeur est positive, c'est un bloc. Si négative, c'est une pile (table).
                    if (under >= 0) { 
                        builder.setOn(b, under);
                    }
                }
            }
        }
        return builder.getState();
    }

    /**
     * Affiche une fenêtre contenant la représentation graphique d'un état donné.
     *
     * @param state L'état à afficher (Map de variables).
     * @param nbBlocs Le nombre de blocs.
     * @param config La configuration du monde.
     * @param title Le titre de la fenêtre.
     * @param x La position horizontale de la fenêtre sur l'écran.
     * @param y La position verticale de la fenêtre sur l'écran.
     */
    public static void displayState(Map<Variable, Object> state, int nbBlocs, WorldConfig config, String title, int x, int y) {
        SwingUtilities.invokeLater(() -> {
            try {
                BWIntegerGUI gui = new BWIntegerGUI(nbBlocs);
                JFrame frame = new JFrame(title);
                
                BWState<Integer> bwState = makeBWState(state, nbBlocs, config);
                BWComponent<Integer> component = gui.getComponent(bwState);
                component.setPreferredSize(new Dimension(500,500));

                frame.add(component);
                frame.pack();
                frame.setLocation(x, y); 
           
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
                frame.setVisible(true);
            } catch (Exception e) {
                System.err.println("Erreur lors de l'affichage GUI : " + e.getMessage());
            }
        });
    }

    /**
     * Lance une animation pas-à-pas d'un plan d'actions dans une fenêtre.
     *
     * @param plan La liste des actions à exécuter.
     * @param initialState L'état de départ.
     * @param nbBlocs Le nombre de blocs.
     * @param config La configuration du monde.
     * @param title Le titre de la fenêtre.
     */
    public static void simulatePlan(List<Action> plan, Map<Variable, Object> initialState, int nbBlocs, WorldConfig config, String title) {
        new Thread(() -> {
            try {
                System.out.println("\n--- Lancement de la simulation GUI ---");
                BWIntegerGUI gui = new BWIntegerGUI(nbBlocs);
                JFrame frame = new JFrame(title);
                
                // On copie l'état initial pour pouvoir le modifier au fur et à mesure
                Map<Variable, Object> currentState = new java.util.HashMap<>(initialState);
                
                BWState<Integer> bwState = makeBWState(currentState, nbBlocs, config);
                BWComponent<Integer> component = gui.getComponent(bwState);
                component.setPreferredSize(new Dimension(800,800));
                frame.add(component);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Boucle d'animation
                for (Action a : plan) {
                    try { Thread.sleep(800); } catch (InterruptedException e) {} // Pause de 0.8s
                    
                    // Calcul de l'état suivant
                    currentState = a.successor(currentState);
                    
                    // Mise à jour du composant graphique
                    component.setState(makeBWState(currentState, nbBlocs, config));
                    component.repaint();
                }
                System.out.println("Simulation terminée.");
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}