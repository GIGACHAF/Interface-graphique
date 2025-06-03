package modèle;

/**
 * Enumération représentant différents types de tomates.
 */
public enum TypeTomate {

    /**
     * Type de tomates cerises et cocktails.
     */
    TOMATES_CERISES("Cerises & Cocktails (16)"),

    /**
     * Autres types de tomates.
     */
    TOMATES("Autres Tomates (47)");

    private final String dénomination;

    /**
     * Constructeur privé pour initialiser un type de tomate avec une
     * dénomination spécifique.
     *
     * @param dénomination la dénomination du type de tomate
     */
    private TypeTomate(String dénomination) {
        this.dénomination = dénomination;
    }

    /**
     * Retourne la dénomination du type de tomate.
     *
     * @return la dénomination du type de tomate
     */
    public String getDénomination() {
        return this.dénomination;
    }

    /**
     * Retourne le type de tomate correspondant à une dénomination donnée.
     *
     * @param dénomination la dénomination à rechercher
     * @return le type de tomate correspondant, ou null si aucun type ne
     *         correspond
     */
    public static TypeTomate getTypeTomate(String dénomination) {
        for (TypeTomate t : TypeTomate.values()) {
            if (t.getDénomination().equals(dénomination)) {
                return t;
            }
        }
        return null;
    }
}
