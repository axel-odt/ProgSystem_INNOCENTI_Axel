public class CompteurSecurise {
    private static int compteur = 0;
    private static final Object verrou = new Object();

    static class Incrementeur implements Runnable {
        private final String nom;
        private final int nombreInc;

        public Incrementeur(String nom, int nombreInc) {
            this.nom = nom;
            this.nombreInc = nombreInc;
        }

        @Override
        public void run() {
            for (int i = 0; i < nombreInc; i++) {
                synchronized (verrou) {
                    compteur++;
                }
            }
            System.out.println(nom + " terminé. Compteur vu : " + getCompteur());
        }
    }

    public static int getCompteur() {
        synchronized (verrou) {
            return compteur;
        }
    }

    public static void resetCompteur() {
        synchronized (verrou) {
            compteur = 0;
        }
    }
}