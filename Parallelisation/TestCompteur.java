public class TestCompteur {
	public static final int NOMBRE_TEST = 50;
	public static final int NOMBRE_THREAD = 9;
	public static final int NOMBRE_INCREMENT = 1111111;

	public static void main(String[] args) {
		int nbMax = NOMBRE_THREAD * NOMBRE_INCREMENT;
		int reussites = 0;
		int minObtenu = Integer.MAX_VALUE;
		int maxObtenu = Integer.MIN_VALUE;

		for (int i = 0; i < NOMBRE_TEST; i++) {
			CompteurDangereux.resetCompteur();

			Thread[] threads = new Thread[NOMBRE_THREAD];
			for (int j = 0; j < NOMBRE_THREAD; j++) {
				threads[j] = new Thread(new CompteurDangereux.Incrementeur("Thread-" + (j + 1), NOMBRE_INCREMENT));
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

			int resultat = CompteurDangereux.getCompteur();
			System.out.println("Test " + (i + 1) + " : " + resultat + " (attendu : " + nbMax + ")");

			if (resultat == nbMax) {
				reussites++;
			}
			if (resultat < minObtenu) {
				minObtenu = resultat;
			}
			if (resultat > maxObtenu) {
				maxObtenu = resultat;
			}
		}

		double pourcentage = ((double) reussites / NOMBRE_TEST) * 100;
		System.out.println("Taux de réussite : " + String.format("%.2f", pourcentage) + " %");
		System.out.println("Valeur minimale obtenue : " + minObtenu);
		System.out.println("Valeur maximale obtenue : " + maxObtenu);
	}
}