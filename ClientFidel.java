public class ClientFidel extends Client {
    private String codeFidelite;
    private float tauxReduction;

    // Constructeur d'initialisation
    public ClientFidel(int codeClient, String nomClient, String adrClient, String codeFidelite, float tauxReduction) {
        super(codeClient, nomClient, adrClient);
        this.codeFidelite = codeFidelite;
        this.tauxReduction = tauxReduction;
    }

    // Méthode toString
    @Override
    public String toString() {
        return super.toString() + ", Code Fidélité: " + codeFidelite + 
               ", Taux Réduction: " + tauxReduction + "%";
    }
}
