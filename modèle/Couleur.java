package modèle;


// SALUT CECI EST UN TEST
/**
 * Enumération représentant différentes couleurs.
 */
public enum Couleur {

    /**
     * Couleur bleue.
     */
    BLEU("Bleu"),

    /**
     * Couleur verte.
     */
    VERT("Vert"),

    /**
     * Couleur rouge.
     */
    ROUGE("Rouge"),

    /**
     * Couleur orange.
     */
    ORANGE("Orange"),

    /**
     * Couleur jaune.
     */
    JAUNE("Jaune"),

    /**
     * Couleur noire.
     */
    NOIR("Noir"),

    /**
     * Couleur multicolore.
     */
    MULTICOLORE("Multicolore");

    private final String dénomination;

    /**
     * Constructeur privé pour initialiser une couleur avec une dénomination
     * spécifique.
     *
     * @param dénomination la dénomination de la couleur
     */
    private Couleur(String dénomination) {
        this.dénomination = dénomination;
    }

    /**
     * Retourne la dénomination de la couleur.
     *
     * @return la dénomination de la couleur
     */
    public String getDénomination() {
        return this.dénomination;
    }

    /**
     * Retourne la couleur correspondant à une dénomination donnée.
     *
     * @param dénomination la dénomination à rechercher
     * @return la couleur correspondante, ou null si aucune couleur ne
     *         correspond
     */
    public static Couleur getCouleur(String dénomination) {
        for (Couleur c : Couleur.values()) {
            if (c.getDénomination().equals(dénomination)) {
                return c;
            }
        }
        return null;
    }
}
