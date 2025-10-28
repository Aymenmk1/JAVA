package srcc;

/**
 * Gestion minimale de codes promo basé sur tableau.
 */
public class PromoManager {
    private PromoCode[] codes;
    private int taille;

    public PromoManager(int capacity) {
        if (capacity <= 0) capacity = 5;
        codes = new PromoCode[capacity];
        taille = 0;
    }

    public PromoManager() { this(5); }

    public void ajouter(PromoCode p) {
        if (p == null) throw new IllegalArgumentException("promo null");
        if (taille >= codes.length) {
            PromoCode[] tmp = new PromoCode[codes.length + 5];
            for (int i = 0; i < codes.length; i++) tmp[i] = codes[i];
            codes = tmp;
        }
        codes[taille++] = p;
    }

    public PromoCode trouverParCode(String code) {
        if (code == null) return null;
        for (int i = 0; i < taille; i++) if (codes[i].getCode().equals(code)) return codes[i];
        return null;
    }

    public void afficher() {
        System.out.println("[CODES PROMO]");
        if (taille == 0) { System.out.println(" (aucun)"); return; }
        for (int i = 0; i < taille; i++) System.out.println(" - " + codes[i]);
    }
}
