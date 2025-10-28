package srcc;

/**
 * Une ligne panier : référence vers un Article existant + quantite > 0
 */
public class LignePanier {
    private Article article;
    private int quantite;

    public LignePanier(Article article, int quantite) {
        if (article == null) throw new IllegalArgumentException("article null");
        if (quantite <= 0) throw new IllegalArgumentException("quantite doit etre > 0");
        this.article = article;
        this.quantite = quantite;
    }

    public Article getArticle() { return article; }
    public int getQuantite() { return quantite; }

    public void ajouterQuantite(int q) {
        if (q <= 0) throw new IllegalArgumentException("quantite invalide");
        this.quantite += q;
    }

    public void setQuantite(int q) {
        if (q <= 0) throw new IllegalArgumentException("quantite invalide");
        this.quantite = q;
    }

    public int ligneTotal() {
        return article.getPrixCentimes() * quantite;
    }

    @Override
    public String toString() {
        return String.format("%-10s x%2d  -> %6d cts", article.getId(), quantite, ligneTotal());
    }
}
