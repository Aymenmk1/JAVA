npackage srcc;

/**
 * Article : id non vide, libelle non vide, prixCentimes >= 0, stock >= 0
 */
public class Article {
    private String id;
    private String libelle;
    private int prixCentimes;
    private int stock;

    public Article(String id, String libelle, int prixCentimes, int stock) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id vide");
        if (libelle == null || libelle.trim().isEmpty()) throw new IllegalArgumentException("libelle vide");
        if (prixCentimes < 0) throw new IllegalArgumentException("prix negatif");
        if (stock < 0) throw new IllegalArgumentException("stock negatif");
        this.id = id;
        this.libelle = libelle;
        this.prixCentimes = prixCentimes;
        this.stock = stock;
    }

    // getters / setters (setters validate)
    public String getId() { return id; }
    public String getLibelle() { return libelle; }
    public int getPrixCentimes() { return prixCentimes; }
    public int getStock() { return stock; }

    public void setLibelle(String libelle) {
        if (libelle == null || libelle.trim().isEmpty()) throw new IllegalArgumentException("libelle vide");
        this.libelle = libelle;
    }

    public void setPrixCentimes(int prixCentimes) {
        if (prixCentimes < 0) throw new IllegalArgumentException("prix negatif");
        this.prixCentimes = prixCentimes;
    }

    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("stock negatif");
        this.stock = stock;
    }

    public void decrementStock(int qty) {
        if (qty < 0) throw new IllegalArgumentException("quantite negative");
        if (qty > stock) throw new IllegalArgumentException("stock insuffisant");
        stock -= qty;
    }

    public void incrementStock(int qty) {
        if (qty < 0) throw new IllegalArgumentException("quantite negative");
        stock += qty;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-20s | %6d cts | stock=%d", id, libelle, prixCentimes, stock);
    }
}
