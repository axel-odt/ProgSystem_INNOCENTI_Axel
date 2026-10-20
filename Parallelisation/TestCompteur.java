public class TestCompteur {
    private static final int NOMBRE_TESTS = 50;
    private static final int NOMBRE_THREADS = 9;
    private static final int INCREMENTS_THREAD = 111111;
    private static final int VALEUR_ATTENDUE = NOMBRE_THREADS * INCREMENTS_THREAD;
    
    public static void main(String[] args) {
        System.out.println("Valeur attendue par compteur : " + valeurAttendue);
        System.out.println("----------------------------------------");

        for (int t = 1; t <= NOMBRE_TESTS; t++) {
            CompteurDangereux.resetCompteur();
            
            Thread[] threads = new Thread[NOMBRE_THREADS];
            for (int i = 0; i < NOMBRE_THREADS; i++) {
                threads[i] = new Thread(new CompteurDangereux.Incrementeur("nom", INCREMENTS_THREAD));
            }
            
            for (Thread thread : threads) {
                thread.start();
            }
            
            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                System.err.println("Interruption lors du test " + t);
                Thread.currentThread().interrupt();
            }
            
            System.out.println("Test " + t + " - Résultat final : " + CompteurDangereux.getCompteur());
        }
    }
}
