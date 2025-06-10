package ihm;

import modèle.Tomate;

public class LignePanier {
    private Tomate tomate;
    private int quantité;

    public LignePanier(Tomate t, int q) {
        this.tomate = t;
        this.quantité = q;
    }
    
    public void addQuantité(int q) {
    	this.quantité += q;
    }
    
    public void setQuantité(int q) {
    	this.quantité = q;
    }
    
    public int getQuantité() {
    	return this.quantité;
    }
    
    public Tomate getTomate() {
    	return this.tomate;
    }

    public double getMontant() {
        return this.quantité * this.tomate.getPrixTTC();
    }
}
