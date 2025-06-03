package ihm;
import java.util.HashMap;
import java.util.Map;
import modèle.Tomate;

public class Panier {
    private Map<Tomate, Integer> lignesCommande = new HashMap<>();

 // Classe Panier.java
    public void ajouterTomate(Tomate tomate, int quantite) {
        if (quantite <= 0 || tomate.getStock() < quantite) return;

        // Ajouter ou incrémenter la quantité
        int actuelle = lignesCommande.getOrDefault(tomate, 0);
        lignesCommande.put(tomate, actuelle + quantite);

        // Décrémenter le stock
        tomate.setStock(tomate.getStock() - quantite);
    }


    public void viderPanier() {
        for (Map.Entry<Tomate, Integer> entry : lignesCommande.entrySet()) {
            Tomate t = entry.getKey();
            Integer qte = entry.getValue();
            if (qte != null) {
                t.setStock(t.getStock() + qte);
            }
        }
        lignesCommande.clear();
    }


    public float getTotal() {
        float total = 0f;
        for (Map.Entry<Tomate, Integer> entry : lignesCommande.entrySet()) {
            total += entry.getKey().getPrixTTC() * entry.getValue();
        }
        return total;
    }

    public Map<Tomate, Integer> getLignesCommande() {
        return lignesCommande;
    }
    
    public void retirerTomate(Tomate tomate) {
        Integer quantite = lignesCommande.remove(tomate);
        if (quantite != null) {
            tomate.setStock(tomate.getStock() + quantite);
        }
    }
    public void resetQuantites() {
        for (Map.Entry<Tomate, Integer> entry : lignesCommande.entrySet()) {
            Tomate tomate = entry.getKey();
            int ancienneQuantite = entry.getValue();
            int nouvelleQuantite = 1;

            // réajuster le stock
            tomate.setStock(tomate.getStock() + (ancienneQuantite - nouvelleQuantite));

            // mettre à jour le panier
            lignesCommande.put(tomate, nouvelleQuantite);
        }
    }



    

}
