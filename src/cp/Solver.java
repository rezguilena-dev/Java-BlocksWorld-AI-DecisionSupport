package cp;
import java.util.*;
import modelling.*;
import planning.*;

public interface Solver{
    /**
     * Lance le processus de résolution pour trouver une solution au CSP.
     *
     * @return Une {@link Map} représentant une affectation complète (Variable -> Objet)
     * qui satisfait toutes les contraintes, ou {@code null} si aucune
     * solution n'est trouvée.
     */
    Map<Variable, Object> solve();
    /**
     * Vérifie si une affectation donnée satisfait l'intégralité des contraintes du problème.
     *
     * @param instanciation Une affectation de variables (potentiellement complète).
     * @return {@code true} si toutes les contraintes sont satisfaites par
     * l'affectation, {@code false} sinon.
     */
    boolean isSatisfiedBy(Map<Variable, Object> instanciation);
}