package srcc;

/** Promo code simple */
public class PromoCode {
    private String code;
    private int pourcentage; // 1..50

    public PromoCode(String code, int pourcentage) {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("code vide");
        if (pourcentage < 1 || pourcentage > 50) throw new IllegalArgumentException("pourcentage invalide");
        this.code = code;
        this.pourcentage = pourcentage;
    }

    public String getCode() { return code; }
    public int getPourcentage() { return pourcentage; }

    @Override
    public String toString() { return code + " (" + pourcentage + "%)"; }
}
