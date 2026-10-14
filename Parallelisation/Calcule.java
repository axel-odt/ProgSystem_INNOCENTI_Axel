public class Calcule extends Thread {

    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private int valeur;
    private int multiplicateur;
    private int index;
    private int resultat;

    public Calcule(int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
    }

    @Override
    public void run() {
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
        }
        System.out.println("Traitement de " + valeur + " terminé : " + resultat);
    }

    public int getResultat() {
        return resultat;
    }

    public static void main(String[] args) {
        Calcule th1 = new Calcule(DONNEES[0], 1000, 0);
        th1.start();
    }
}