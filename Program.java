package srcc;

import java.io.IOException;
import java.util.Scanner;

/**
 * Programme principal : menu console minimal pour illustrer le TP.
 * - affiche catalogue, ajoute/supprime panier, applique code, écrit/ lit fichier.
 */
public class Program {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        Catalogue catalogue = new Catalogue(10);
        Panier panier = new Panier(5);
        PromoManager promos = new PromoManager();

        // quelques codes en mémoire (phase 3)
        promos.ajouter(new PromoCode("KENDI10", 10));
        promos.ajouter(new PromoCode("WELCOME5", 5));

        // exemples du catalogue (phase 1)
        catalogue.ajouterArticle(new Article("KIT_BOL1", "Bol végétarien", 5990, 12));
        catalogue.ajouterArticle(new Article("TOMATE3", "Tomates x3", 990, 40));

        System.out.println("Bienvenue KendiFood (console demo).");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1) Afficher catalogue");
            System.out.println("2) Ajouter article au catalogue (manuel)");
            System.out.println("3) Charger catalogue depuis fichier");
            System.out.println("4) Sauvegarder catalogue vers fichier");
            System.out.println("5) Afficher panier");
            System.out.println("6) Ajouter au panier (par id)");
            System.out.println("7) Supprimer du panier (par id)");
            System.out.println("8) Appliquer code & afficher reçu");
            System.out.println("9) Afficher codes promo");
            System.out.println("0) Quitter");
            System.out.print("Choix: ");
            String choix = SC.nextLine().trim();
            try {
                switch (choix) {
                    case "1": catalogue.afficher(); break;
                    case "2": ajouterArticleInteractif(catalogue); break;
                    case "3": chargerFichierInteractif(catalogue); break;
                    case "4": sauvegarderFichierInteractif(catalogue); break;
                    case "5": panier.afficherPanier(); break;
                    case "6": ajouterAuPanierInteractif(catalogue, panier); break;
                    case "7": supprimerDuPanierInteractif(panier); break;
                    case "8": appliquerCodeEtAfficherRecuInteractif(panier, promos); break;
                    case "9": promos.afficher(); break;
                    case "0": running = false; break;
                    default: System.out.println("Choix invalide."); break;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("Erreur métier : " + iae.getMessage());
            } catch (IOException ioe) {
                System.out.println("Erreur fichier : " + ioe.getMessage());
            } catch (Exception e) {
                System.out.println("Erreur inattendue : " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Au revoir.");
    }

    // Helpers with robust numeric input checking
    private static int lireEntier(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SC.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Quantité/prix invalide (non numérique). Réessayez.");
            }
        }
    }

    private static void ajouterArticleInteractif(Catalogue cat) {
        System.out.print("id: "); String id = SC.nextLine().trim();
        System.out.print("libelle: "); String lib = SC.nextLine().trim();
        int prix = lireEntier("prix (centimes): ");
        int stock = lireEntier("stock: ");
        Article a = new Article(id, lib, prix, stock);
        cat.ajouterArticle(a);
        System.out.println("Article ajouté.");
    }

    private static void chargerFichierInteractif(Catalogue cat) throws IOException {
        while (true) {
            System.out.print("Chemin fichier à charger: ");
            String path = SC.nextLine().trim();
            try {
                int n = cat.chargerDepuisFichier(path);
                System.out.println(n + " articles chargés.");
                break;
            } catch (IOException ioe) {
                System.out.println("Chemin introuvable ou erreur lecture: " + path + " — saisissez un autre chemin ou vide pour annuler.");
                System.out.print("Réessayer ? (o/n): ");
                String r = SC.nextLine().trim();
                if (!r.equalsIgnoreCase("o")) break;
            }
        }
    }

    private static void sauvegarderFichierInteractif(Catalogue cat) throws IOException {
        System.out.print("Chemin fichier pour sauvegarder: ");
        String path = SC.nextLine().trim();
        if (path.isEmpty()) { System.out.println("Annulé."); return; }
        int n = cat.sauvegarderVersFichier(path);
        System.out.println("Catalogue sauvegardé (" + n + " articles) dans: " + path);
    }

    private static void ajouterAuPanierInteractif(Catalogue catalogue, Panier panier) {
        System.out.print("id article: ");
        String id = SC.nextLine().trim();
        int qte = lireEntier("quantité: ");
        // controle et exceptions gérés dans Panier.ajouterAuPanier
        panier.ajouterAuPanier(id, qte, catalogue);
        System.out.println("Ajouté au panier.");
    }

    private static void supprimerDuPanierInteractif(Panier panier) {
        System.out.print("id article à supprimer: ");
        String id = SC.nextLine().trim();
        boolean ok = panier.supprimerDuPanier(id);
        if (ok) System.out.println("Supprimé.");
        else System.out.println("Article non trouvé dans le panier.");
    }

    private static void appliquerCodeEtAfficherRecuInteractif(Panier panier, PromoManager promos) {
        if (panier.estVide()) {
            System.out.println("Panier vide.");
            return;
        }
        System.out.print("Entrez code promo (ou ENTER pour aucun): ");
        String code = SC.nextLine().trim();
        if (code.isEmpty()) code = null;
        String recu = panier.genererRecu(code, promos);
        System.out.println("\n" + recu);
    }
}
