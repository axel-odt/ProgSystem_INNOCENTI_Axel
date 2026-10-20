public class Calcule extends Thread {

    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private int valeur;
    private int multiplicateur;
    private int resultat;

    public Calcule(int valeur, int multiplicateur) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
    }

    @Override
    public void run() {
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
        }
    }

    public int getResultat() {
        return resultat;
    }
}