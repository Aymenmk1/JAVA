import java.util.Date;

public class Commande implements Comparable<Commande> {
    private int numCommande;
    private Date dateCommande;
    private String nomFournisseur;

    // Constructeur avec arguments
    public Commande(int numCommande, Date dateCommande, String nomFournisseur) {
        this.numCommande = numCommande;
        this.dateCommande = dateCommande;
        this.nomFournisseur = nomFournisseur;
    }

    // Accesseurs (getters)
    public int getNumCommande() {
        return numCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    // Mutateurs (setters)
    public void setNumCommande(int numCommande) {
        this.numCommande = numCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Commande - Numéro: " + numCommande + ", Date: " + dateCommande + ", Fournisseur: " + nomFournisseur;
    }

    // Méthode equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Commande commande = (Commande) obj;
        return numCommande == commande.numCommande;
    }

    // Implémentation de Comparable
    @Override
    public int compareTo(Commande autre) {
        return this.dateCommande.compareTo(autre.dateCommande);
    }
}
