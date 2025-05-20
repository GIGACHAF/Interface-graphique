package modèle;

import java.util.LinkedList;
import java.util.List;

/**
 * La classe Tomates représente une collection de graines de tomates avec des
 * conseils de culture.
 */
public class Tomates {

    /**
     * Titre des conseils de culture pour les tomates.
     */
    public static final String CONSEILS_DE_CULTURE_TITRE = "Conseils de culture\r\n"
            + "Semis : mars-avril\r\n" + "Repiquage : après les gelées\r\n"
            + "Récolte : juillet à septembre, voire octobre";

    /**
     * Conseils détaillés pour la culture des tomates.
     */
    public static final String CONSEILS_DE_CULTURE = "Les tomates sont frileuses !\r\n"
            + "\r\n"
            + "Exposition : Les tomates sont originaires d’Amérique du Sud…et leurs gènes s’en souviennent !\r\n"
            + "Réservez-leur l’endroit le mieux ensoleillé de votre jardin.\r\n"
            + "\r\n"
            + "Semis : Démarrez vos semis en petite terrine dès mars / avril (15/20° nuit et jour) dans du terreau à semis, "
            + "couvrez vos graines de 0,5 cm, tassez doucement et maintenez humide. "
            + "Repiquez vos semis lorsqu’ils font 5 cm, dans des godets avec du terreau 1/3 de fumier ou du compost , enterrez jusqu’au première feuilles.\r\n"
            + "\r\n"
            + "Après les Saints de glaces, plantez vos pieds de tomates dès qu’ils auront atteint 15 cm, "
            + "enterrez-les jusqu’aux premières feuilles en pleine terre dans un trou avec du fumier, du compost ou quelques feuilles d’ortie si besoin, "
            + "installez vos tuteurs espacés de 70 cm.\r\n"
            + "Arrosez abondamment les 3 premiers jours, arrêtez les 15 jours suivants puis arrosez régulièrement.\r\n"
            + "\r\n"
            + "Pensez à pailler ! ainsi vous garderez beaucoup plus facilement la terre humide et espacerez les désherbages.\r\n"
            + "\r\n"
            + "Maladies pouvant toucher les tomates: mildiou (ne pas arroser les feuilles et supprimer celles qui touchent le sol).\r\n"
            + "\r\n"
            + "Plantez du basilic entre vos pieds de tomates, ils s’entraident l’un l’autre…";

    private List<Tomate> tomates;

    /**
     * Constructeur par défaut initialisant une liste vide de tomates.
     */
    public Tomates() {
        this.tomates = new LinkedList<Tomate>();
    }

    /**
     * Ajoute une liste de tomates à la collection.
     *
     * @param tomates La liste de tomates à ajouter.
     */
    public void addTomates(List<Tomate> tomates) {
        this.tomates.addAll(tomates);
    }

    /**
     * Retourne la liste des tomates.
     *
     * @return La liste des tomates.
     */
    public List<Tomate> getTomates() {
        return this.tomates;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la
     * liste des tomates.
     *
     * @return Une chaîne représentant la liste des tomates.
     */
    @Override
    public String toString() {
        StringBuffer res = new StringBuffer("Liste des tomates : \n\n");
        for (Tomate t : this.tomates) {
            res.append(t.toString() + '\n');
        }
        return res.toString();
    }

    /**
     * Retourne une liste de tomates d'un type spécifique.
     *
     * @param typeTomate Le type de tomate recherché.
     * @return Une liste de tomates du type spécifié.
     */
    public List<Tomate> tomatesDeType(TypeTomate typeTomate) {
        return tomatesDeTypeAvecListe(typeTomate, this.tomates);
    }

    /**
     * Méthode statique pour filtrer une liste de tomates par type.
     *
     * @param typeTomate Le type de tomate recherché.
     * @param lesTomates La liste de tomates à filtrer.
     * @return Une liste de tomates du type spécifié.
     */
    private static List<Tomate> tomatesDeTypeAvecListe(TypeTomate typeTomate,
            List<Tomate> tomates) {
        // A COMPLETER
        return null;
    }

    /**
     * Retourne une liste de tomates d'une couleur spécifique.
     *
     * @param couleur La couleur de tomate recherchée.
     * @return Une liste de tomates de la couleur spécifiée.
     */
    public List<Tomate> tomatesDeCouleur(Couleur couleur) {
        return tomatesDeCouleurAvecListe(couleur, this.tomates);
    }

    /**
     * Méthode statique pour filtrer une liste de tomates par couleur.
     *
     * @param couleur La couleur de tomate recherchée.
     * @param tomates La liste de tomates à filtrer.
     * @return Une liste de tomates de la couleur spécifiée.
     */
    private static List<Tomate> tomatesDeCouleurAvecListe(Couleur couleur,
            List<Tomate> tomates) {
        // A COMPLETER
        return null;
    }

    /**
     * Retourne une liste de tomates d'un type et d'une couleur spécifiques.
     *
     * @param typeTomate Le type de tomate recherché.
     * @param couleur    La couleur de tomate recherchée.
     * @return Une liste de tomates du type et de la couleur spécifiés.
     */
    public List<Tomate> tomatesDetypeDeCouleur(TypeTomate typeTomate,
            Couleur couleur) {
        // A COMPLETER
        return null;
    }

    /**
     * Retourne une tomate spécifique par sa désignation.
     *
     * @param désignation La désignation de la tomate recherchée.
     * @return La tomate correspondant à la désignation, ou null si non trouvée.
     */
    public Tomate getTomate(String désignation) {
        for (Tomate t : this.tomates) {
            if (t.getDésignation().equals(désignation)) {
                return t;
            }
        }
        return null;
    }
}
