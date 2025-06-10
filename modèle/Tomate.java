package modèle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe Tomate représente une tomate avec ses caractéristiques et ses
 * tomates apparentées.
 */
public class Tomate {

    private TypeTomate type;
    private Couleur couleur;
    private String désignation;
    private String sousTitre;
    private String nomImage;
    private String description;
    private int stock;
    private int nbGrainesParSachet;
    private float prixTTC;
    private List<Tomate> tomatesApparentées;

    /**
     * Constructeur pour créer une nouvelle instance de Tomate.
     *
     * @param type               le type de la tomate
     * @param couleur            la couleur de la tomate
     * @param désignation        la désignation de la tomate
     * @param sousTitre          le sous-titre de la tomate
     * @param nomImage           le nom de l'image associée à la tomate
     * @param description        la description de la tomate
     * @param stock              le stock disponible de sachets de graines
     * @param nbGrainesParSachet le nombre de graines par sachet
     * @param prixTTC            le prix TTC de la tomate
     */
    public Tomate(TypeTomate type, Couleur couleur, String désignation,
            String sousTitre, String nomImage, String description, int stock,
            int nbGrainesParSachet, float prixTTC) {
        this.type = type;
        this.couleur = couleur;
        this.désignation = désignation;
        this.sousTitre = sousTitre;
        this.nomImage = nomImage;
        this.description = description;
        this.stock = stock;
        this.nbGrainesParSachet = nbGrainesParSachet;
        this.prixTTC = prixTTC;
        this.tomatesApparentées = new ArrayList<>();
    }

    /**
     * Retourne le type de la tomate.
     *
     * @return le type de la tomate
     */
    public TypeTomate getType() {
        return this.type;
    }

    /**
     * Retourne la couleur de la tomate.
     *
     * @return la couleur de la tomate
     */
    public Couleur getCouleur() {
        return this.couleur;
    }

    /**
     * Retourne la dénomination de la tomate.
     *
     * @return la dénomination de la tomate
     */
    public String getDésignation() {
        return this.désignation;
    }

    /**
     * Définit le stock de la tomate.
     *
     * @param v le nouveau stock
     */
    public void setStock(int v) {
        this.stock = v;
    }

    /**
     * Retourne le sous-titre de la tomate.
     *
     * @return le sous-titre de la tomate
     */
    public String getSousTitre() {
        return this.sousTitre;
    }

    /**
     * Retourne le nom de l'image associée à la tomate.
     *
     * @return le nom de l'image
     */
    public String getNomImage() {
        return this.nomImage;
    }

    /**
     * Retourne la description de la tomate.
     *
     * @return la description de la tomate
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retourne le stock disponible de sachets de graines.
     *
     * @return le stock disponible
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Retourne le nombre de graines par sachet.
     *
     * @return le nombre de graines par sachet
     */
    public int getNbGrainesParSachet() {
        return this.nbGrainesParSachet;
    }

    /**
     * Retourne le prix TTC de la tomate.
     *
     * @return le prix TTC
     */
    public float getPrixTTC() {
        return this.prixTTC;
    }

    /**
     * Retourne la liste des tomates apparentées.
     *
     * @return la liste des tomates apparentées
     */
    public List<Tomate> getTomatesApparentées() {
        return this.tomatesApparentées;
    }

    /**
     * Ajoute une tomate apparentée à la liste des tomates apparentées.
     *
     * @param tomate la tomate apparentée à ajouter
     */
    public void addTomateApparentée(Tomate tomate) {
        if (!(tomate == null) && !(this == tomate)
                && this.type.equals(tomate.type)
                && !(this.tomatesApparentées.contains(tomate))) {
            this.tomatesApparentées.add(tomate);
        }
    }

    /**
     * Retourne le code de hachage de la tomate.
     *
     * @return le code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.couleur, this.description,
                this.nbGrainesParSachet, this.nomImage, this.prixTTC,
                this.sousTitre, this.stock, this.désignation, this.type);
    }

    /**
     * Vérifie si deux objets Tomate sont égaux.
     *
     * @param obj l'objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tomate)) {
            return false;
        }
        Tomate other = (Tomate) obj;
        return this.couleur == other.couleur
                && Objects.equals(this.description, other.description)
                && this.nbGrainesParSachet == other.nbGrainesParSachet
                && Objects.equals(this.nomImage, other.nomImage)
                && Float.floatToIntBits(this.prixTTC) == Float
                        .floatToIntBits(other.prixTTC)
                && Objects.equals(this.sousTitre, other.sousTitre)
                && this.stock == other.stock
                && Objects.equals(this.désignation, other.désignation)
                && this.type == other.type;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la
     * tomate.
     *
     * @return la représentation sous forme de chaîne de caractères
     */
    @Override
    public String toString() {
        return "Tomate [type=" + this.type + ", couleur=" + this.couleur
                + ", titre=" + this.désignation + ", sousTitre="
                + this.sousTitre + ", nomImage=" + this.nomImage
                + ", description=" + this.description + ", stock=" + this.stock
                + ", nbGrainesParSachet=" + this.nbGrainesParSachet
                + ", prixTTC=" + this.prixTTC + "]";
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la
     * tomate avec ses tomates apparentées.
     *
     * @return la représentation sous forme de chaîne de caractères avec les
     *         tomates apparentées
     */
    public String toStringAvecTomatesApparentées() {
        StringBuffer res = new StringBuffer(this.toString());
        res.append("\n Tomates apparentées : ");
        for (Tomate t : this.getTomatesApparentées()) {
            res.append(t.getDésignation() + " ");
        }
        return res.toString();
    }

}
