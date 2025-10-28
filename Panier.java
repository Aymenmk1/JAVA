package srcc;

/**
 * Panier : tableau de lignes de taille initiale 5 ; si plein on agrandit de +5
 */
public class Panier {
    private LignePanier[] lignes;
    private int taille; // nombre effectif de lignes

    public Panier(int initialCapacity) {
        if (initialCapacity <= 0) initialCapacity = 5;
        this.lignes = new LignePanier[initialCapacity];
        this.taille = 0;
    }

    public Panier() { this(5); }

    public int taille() { return taille; }

    private void agrandirSiNecessaire() {
        if (taille < lignes.length) return;
        int nouv = lignes.length + 5;
        LignePanier[] tmp = new LignePanier[nouv];
        for (int i = 0; i < lignes.length; i++) tmp[i] = lignes[i];
        lignes = tmp;
    }

    /**
     * Ajoute au panier par id : recherche l'article dans le catalogue,
     * vérifie stock, décrémente stock, insère ou met à jour la ligne panier.
     * Lance IllegalArgumentException pour erreurs métier (stock insuffisant, id introuvable).
     */
    public void ajouterAuPanier(String id, int qte, Catalogue catalogue) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id vide");
        if (qte <= 0) throw new IllegalArgumentException("quantite invalide");
        int idx = catalogue.trouverIndexParId(id);
        if (idx == -1) throw new IllegalArgumentException("article introuvable: " + id);
        Article art = catalogue.getArticleByIndex(idx);
        if (art.getStock() < qte) throw new IllegalArgumentException("stock insuffisant pour l'article " + id + " (disponible: " + art.getStock() + ")");
        // decrementer stock du catalogue
        art.decrementStock(qte);

        // si ligne existe, augmenter quantité ; sinon ajouter nouvelle ligne
        for (int i = 0; i < taille; i++) {
            if (lignes[i].getArticle().getId().equals(id)) {
                lignes[i].ajouterQuantite(qte);
                return;
            }
        }
        agrandirSiNecessaire();
        lignes[taille++] = new LignePanier(art, qte);
    }

    /**
     * Supprimer du panier par id : restaure le stock catalogue correspondant ;
     * comble le trou par décalage à gauche. Retourne true si supprimé.
     */
    public boolean supprimerDuPanier(String id) {
        if (id == null) return false;
        for (int i = 0; i < taille; i++) {
            if (lignes[i].getArticle().getId().equals(id)) {
                // restaurer stock
                int qte = lignes[i].getQuantite();
                lignes[i].getArticle().incrementStock(qte);
                // decaler à gauche
                for (int j = i; j < taille - 1; j++) lignes[j] = lignes[j + 1];
                lignes[--taille] = null;
                return true;
            }
        }
        return false;
    }

    public void afficherPanier() {
        System.out.println("[PANIER]");
        if (taille == 0) {
            System.out.println(" (vide)");
            return;
        }
        for (int i = 0; i < taille; i++) {
            System.out.println(" - " + lignes[i].toString());
        }
        System.out.println("Total brut : " + totalBrut() + " cts");
    }

    public int totalBrut() {
        int sum = 0;
        for (int i = 0; i < taille; i++) sum += lignes[i].ligneTotal();
        return sum;
    }

    public LignePanier[] getLignes() {
        LignePanier[] copy = new LignePanier[taille];
        for (int i = 0; i < taille; i++) copy[i] = lignes[i];
        return copy;
    }

    public boolean estVide() { return taille == 0; }

    /**
     * Génère un reçu multi-ligne. Si codeOptionnel != null, le code doit exister dans promos,
     * sinon on lance IllegalArgumentException (TP: interdire code inconnu).
     */
    public String genererRecu(String codeOptionnel, PromoManager promos) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== REÇU KendiFood =====").append(System.lineSeparator());
        for (int i = 0; i < taille; i++) {
            sb.append(lignes[i].toString()).append(System.lineSeparator());
        }
        int brut = totalBrut();
        sb.append(System.lineSeparator()).append("Total brut : ").append(brut).append(" cts").append(System.lineSeparator());
        if (codeOptionnel != null) {
            if (promos == null) {
                throw new IllegalArgumentException("Aucun gestionnaire de promos fourni.");
            }
            PromoCode p = promos.trouverParCode(codeOptionnel);
            if (p == null) {
                // TP: interdire l'application d'un code inconnu — signaler par exception
                throw new IllegalArgumentException("Code promo inconnu : \"" + codeOptionnel + "\"");
            }
            int reduc = (p.getPourcentage() * brut) / 100;
            int net = brut - reduc;
            sb.append("Code appliqué : ").append(p.getCode()).append(" (-").append(p.getPourcentage()).append("%)").append(System.lineSeparator());
            sb.append("Total à payer : ").append(net).append(" cts").append(System.lineSeparator());
        } else {
            sb.append("Total à payer : ").append(brut).append(" cts").append(System.lineSeparator());
        }
        return sb.toString();
    }
}
