import java.util.List;
import java.util.ArrayList;

public class Client {

    private String CodeClient;
    private String NomClient;
    private String AdrClient;
    private String TelClient;

    private int maxCommandes=100;

    List<Commande> ListeCommandes;

    public Client(String codeClient, String nomClient, String adrClient, String telClient) {
        this.CodeClient = codeClient;
        this.NomClient = nomClient;
        this.AdrClient = adrClient;
        this.TelClient = telClient;
        this.ListeCommandes=new ArrayList<>();
    }

    public void EnregistrerCommande(Commande commande){
        if(ListeCommandes.size()<maxCommandes){
            ListeCommandes.add(commande);
            System.out.println("Commande enregistree: " + commande);
        }
        else{
            System.out.println("Limite maximale de commandes atteinte pour le client: " + this.CodeClient);
        }
    }

    public boolean SupprimerCommande(int NumCommande) {
        for (int i = 0; i < ListeCommandes.size(); i++) {
            if (ListeCommandes.get(i).getNumCommande() == NumCommande) {
                System.out.println("Commande supprimée: " + ListeCommandes.get(i));
                ListeCommandes.remove(i);
                return true;
            }
        }
        System.out.println("Commande non trouvée avec le numéro: " + NumCommande);
        return false;
    }


    @Override
    public String toString() {
        return "Client [CodeClient=" + CodeClient + ", NomClient=" + NomClient + ", AdrClient=" + AdrClient
                + ", TelClient=" + TelClient + ", NombreCommandes=" + ListeCommandes.size() + "]";
    }


}
