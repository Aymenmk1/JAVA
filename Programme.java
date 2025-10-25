import java.util.*;

public class Program {
    public static void main(String[] args) {
        // Commandes
        Commande c1 = new Commande(1, new Date(), "Fournisseur A");
        Commande c2 = new Commande(2, new Date(), "Fournisseur B");
        Commande c3 = new Commande(3, new Date(), "Fournisseur C");
        Commande c4 = new Commande(1, new Date(), "Fournisseur D"); 

        // Clients
        Client cl1 = new Client("C1", "Soukaina", "Casa", "0611223344");
        Client cl2 = new Client("C2", "Imane", "Fès", "0677889900");
        ClientFidel cl3 = new ClientFidel("C3", "Youssef", "Rabat", "0655667788", "FID123", 10);

        // Ajout commandes
        cl1.EnregistrerCommande(c1);
        cl1.EnregistrerCommande(c2);
        cl2.EnregistrerCommande(c3);

        // Affichage simple
        System.out.println("Avant suppression : ");
        System.out.println(cl1);

        cl1.SupprimerCommande(1);
        System.out.println("\nAprès suppression : ");
        System.out.println(cl1);

        // List de clients
        List<Client> clients = Arrays.asList(cl1, cl2, cl3);

        // Tri commandes
        Collections.sort(cl1.getListeCommandes());
        System.out.println("\nCommandes triées de cl1 : " + cl1.getListeCommandes());

        // Set
        Set<Commande> commandesSansDoublons = new HashSet<>();
        commandesSansDoublons.add(c1);
        commandesSansDoublons.add(c2);
        commandesSansDoublons.add(c4); 

        System.out.println("\nSet commandes sans doublons : " + commandesSansDoublons);

        // Map client
        Map<String, Client> mapClients = new HashMap<>();
        for (Client c : clients) {
            mapClients.put(c.getCodeClient(), c);
        }

        System.out.println("\nMap des clients : " + mapClients);
    }
}
