package ihm;

import java.util.ArrayList;
import java.util.List;
import modèle.Tomate;

public class Panier {
    private List<LignePanier> lignesCommande = new ArrayList<>();

    public void ajouterTomate(Tomate tomate, int quantite) {
        if (quantite <= 0 || tomate.getStock() < quantite) return;

        // Cherche si la tomate est déjà présente
        for (LignePanier ligne : lignesCommande) {
            if (ligne.getTomate().equals(tomate)) {
                // Récupère l'ancienne quantité pour ajuster le stock
                int ancienneQuantité = ligne.getQuantité();
                tomate.setStock(tomate.getStock() + ancienneQuantité); // on "libère" l'ancien stock

                if (tomate.getStock() < quantite) {
                    // Stock insuffisant après libération
                    tomate.setStock(tomate.getStock() - ancienneQuantité);
                    return;
                }

                ligne.addQuantité(quantite); // modifie la quantité
                tomate.setStock(tomate.getStock() - (quantite+ancienneQuantité));
                return;
            }
        }

        // Sinon, ajoute une nouvelle ligne
        lignesCommande.add(new LignePanier(tomate, quantite));
        tomate.setStock(tomate.getStock() - quantite);
    }

    public void retirerTomate(Tomate tomate) {
        lignesCommande.removeIf(ligne -> {
            if (ligne.getTomate().equals(tomate)) {
                tomate.setStock(tomate.getStock() + ligne.getQuantité());
                return true;
            }
            return false;
        });
    }

    public void modifierQuantité(LignePanier ligne, int quantitéVoulue) {
        Tomate t = ligne.getTomate();
        int quantitéInitiale = ligne.getQuantité();

        // Re-libère l'ancienne quantité dans le stock
        t.setStock(t.getStock() + quantitéInitiale);

        // Vérifie s'il y a assez de stock pour la nouvelle quantité
        if (quantitéVoulue > t.getStock()) {
            // Rétablit l'ancien état si pas assez de stock
            t.setStock(t.getStock() - quantitéInitiale);
            return;
        }

        // Met à jour la ligne et le stock
        ligne.setQuantité(quantitéVoulue);
        t.setStock(t.getStock() - quantitéVoulue);
    }

    public void viderPanier() {
        for (LignePanier ligne : lignesCommande) {
            Tomate t = ligne.getTomate();
            int qte = ligne.getQuantité();
            t.setStock(t.getStock() + qte);
        }
        lignesCommande.clear();
    }

    public void resetQuantites() {
        for (LignePanier ligne : lignesCommande) {
            Tomate tomate = ligne.getTomate();
            int ancienneQuantite = ligne.getQuantité();
            int nouvelleQuantite = 1;
            tomate.setStock(tomate.getStock() + (ancienneQuantite - nouvelleQuantite));
            ligne.setQuantité(nouvelleQuantite);
        }
    }

    public float getTotal() {
        float total = 0f;
        for (LignePanier ligne : this.lignesCommande) {
            total += ligne.getMontant();
        }
        return total;
    }

    public List<LignePanier> getLignesCommande() {
        return lignesCommande;
    }
}