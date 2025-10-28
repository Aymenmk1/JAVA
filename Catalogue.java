package srcc;

import java.io.*;

/**
 * Catalogue : tableau d'Article redimensionnable + tableau 2D minimal pour stats
 */
public class Catalogue {
    private Article[] articles;
    private int taille; // nombre d'articles effectivement présents

    // minimal 2-col stats: [i][0] = ventesDuJour, [i][1] = retoursDuJour
    private int[][] stats;

    public Catalogue(int capacity) {
        if (capacity <= 0) capacity = 10;
        this.articles = new Article[capacity];
        this.taille = 0;
        this.stats = new int[capacity][2];
    }

    public Catalogue() { this(10); }

    public int taille() { return taille; }
    public int capacite() { return articles.length; }

    public void afficher() {
        System.out.println("[CATALOGUE]");
        if (taille == 0) {
            System.out.println(" (vide)");
            return;
        }
        for (int i = 0; i < taille; i++) {
            System.out.println(" - " + articles[i].toString());
        }
    }

    public void ajouterArticle(Article a) {
        if (a == null) throw new IllegalArgumentException("article null");
        if (taille >= articles.length) agrandir(articles.length + 10); // expand by +10
        articles[taille++] = a;
        // stats row already present (if resized it's handled in agrandir)
    }

    public int trouverIndexParId(String id) {
        if (id == null) return -1;
        for (int i = 0; i < taille; i++) {
            if (id.equals(articles[i].getId())) return i; // valeur textuelle
        }
        return -1;
    }

    public Article getArticleByIndex(int idx) {
        if (idx < 0 || idx >= taille) return null;
        return articles[idx];
    }

    private void agrandir(int nouvelleCapacite) {
        if (nouvelleCapacite <= articles.length) return;
        Article[] nouveau = new Article[nouvelleCapacite];
        for (int i = 0; i < taille; i++) nouveau[i] = articles[i];
        this.articles = nouveau;

        // copy/expand stats accordingly
        int[][] nouveauStats = new int[nouvelleCapacite][2];
        for (int i = 0; i < stats.length && i < nouveauStats.length; i++) {
            nouveauStats[i][0] = stats[i][0];
            nouveauStats[i][1] = stats[i][1];
        }
        this.stats = nouveauStats;
    }

    /**
     * Charger depuis fichier : format par ligne id;libelle;prixCentimes;stock
     * Retourne nombre d'articles chargés (0 si aucun). Lance IOException si problème d'accès.
     */
    public int chargerDepuisFichier(String chemin) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(chemin))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("[;,\t]");
                if (parts.length < 4) continue; // ligne mal formée : on saute
                String id = parts[0].trim();
                String lib = parts[1].trim();
                try {
                    int prix = Integer.parseInt(parts[2].trim());
                    int stock = Integer.parseInt(parts[3].trim());
                    try {
                        Article a = new Article(id, lib, prix, stock);
                        ajouterArticle(a);
                        count++;
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ligne ignorée (règles): " + line + " -> " + e.getMessage());
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("Ligne ignorée (nombre): " + line);
                }
            }
        }
        return count;
    }

    /**
     * Sauvegarder le catalogue vers fichier (texte simple)
     */
    public int sauvegarderVersFichier(String chemin) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chemin))) {
            for (int i = 0; i < taille; i++) {
                Article a = articles[i];
                // id;libelle;prixCentimes;stock
                bw.write(String.format("%s;%s;%d;%d", a.getId(), a.getLibelle(), a.getPrixCentimes(), a.getStock()));
                bw.newLine();
            }
        }
        return taille;
    }

    // Optional accessors for stats (simple)
    public int[][] getStatsArray() { return stats; }
    public void incrementVentes(int index, int qte) {
        if (index < 0 || index >= taille) return;
        stats[index][0] += qte;
    }
    public void incrementRetours(int index, int qte) {
        if (index < 0 || index >= taille) return;
        stats[index][1] += qte;
    }
}
