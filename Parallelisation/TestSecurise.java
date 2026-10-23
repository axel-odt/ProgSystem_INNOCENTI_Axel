public class TestSecurise {
    public static final int NOMBRE_TEST = 5;
    public static final int NOMBRE_THREAD = 9;
    public static final int NOMBRE_INCREMENT = 11111;

    public static void main(String[] args) {
        int valeurAttendue = NOMBRE_THREAD * NOMBRE_INCREMENT; // 9 999 999
        int reussites = 0;
        long tempsDebut = System.currentTimeMillis();

        for (int i = 0; i < NOMBRE_TEST; i++) {
            CompteurSecurise.resetCompteur();

            Thread[] threads = new Thread[NOMBRE_THREAD];
            for (int j = 0; j < NOMBRE_THREAD; j++) {
                threads[j] = new Thread(new CompteurSecurise.Incrementeur("Thread : " + (j + 1), NOMBRE_INCREMENT));
            }

            for (Thread thread : threads) {
                thread.start();
            }

            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            int resultat = CompteurSecurise.getCompteur();
            if (resultat == valeurAttendue) {
                reussites++;
            }
        }

        long tempsFin = System.currentTimeMillis();

        System.out.println("Réussites : " + reussites + " / " + NOMBRE_TEST + " (" + (reussites * 100 / NOMBRE_TEST) + "%)");
        System.out.println("Temps d'exécution total : " + (tempsFin - tempsDebut) + " ms");
    }
}