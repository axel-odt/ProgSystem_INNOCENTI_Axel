import java.util.ArrayList;
import java.util.List;

public class TestProducteurConsommateur {

    public static void main(String[] args) throws InterruptedException {

        executerScenario(2, 9, 3, 6);
        Thread.sleep(1000);
        executerScenario(5, 4, 2, 10);
    }

    private static void executerScenario(int nbProd, int prodArticles, int nbCons, int consArticles) throws InterruptedException {
        ProducteurConsommateur pc = new ProducteurConsommateur();
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= nbProd; i++) {
            Thread t = new Thread(pc.creerProducteur("Producteur " + i, prodArticles));
            threads.add(t);
        }

        for (int i = 1; i <= nbCons; i++) {
            Thread t = new Thread(pc.creerConsommateur("Consommateur " + i, consArticles));
            threads.add(t);

        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Articles produits : " + pc.getTotalProduit());
        System.out.println("Articles consommés : " + pc.getTotalConsomme());
    }
}