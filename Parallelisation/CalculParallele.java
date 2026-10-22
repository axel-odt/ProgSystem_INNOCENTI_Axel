public class CalculParrallele {

    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MULTIPLICATEUR = 1000;

    public static void main(String[] args) {
        System.out.println("=== Calcul Parallele ===");
        Calcule[] threads = new Calcule[DONNEES.length];
        int somme = 0;
        long debut = System.nanoTime();

        //bocle start
        for (int i = 0; i < DONNEES.length; i++) {
            threads[i] = new Calcule(DONNEES[i], MULTIPLICATEUR)
            threads[i].start();
        }

        //boucle join
        for (int i = 0; i < DONNEES.length; i++) {
            try{
                threads[i].join();
                sommeTotal += threads[i].getResultat();
                
            }catch(InterruptedException e){
                
            }	
        }

        long duree = (System.nanoTime() - debut) / 1_000_000;
		System.out.println("Résultat total : " + sommeTotal);
        System.out.println("Durée : " + duree + " ms");
    }
}