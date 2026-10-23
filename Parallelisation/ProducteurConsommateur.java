import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducteurConsommateur {

    private static final int CAPACITE_MAXIMALE = 5;
    private final Queue<Integer> fileProduits = new LinkedList<>();

    private final Semaphore semaphorePlacesLibres = new Semaphore(CAPACITE_MAXIMALE);
    private final Semaphore semaphoreProduitsDisponibles = new Semaphore(0);
    private final Semaphore semaphoreAccesBuffer = new Semaphore(1);

    private int nombreProduitsConsommes = 0;
    private int nombreProduitsFabriques = 0;

    class Producteur implements Runnable {

        private final int quantiteAProduire;
        private final String identifiant;

        public Producteur(String identifiant, int quantiteAProduire) {
            this.identifiant = identifiant;
            this.quantiteAProduire = quantiteAProduire;
        }

        @Override
        public void run() {
            for (int indice = 0; indice < quantiteAProduire; indice++) {
                int nouvelArticle = (int) (Math.random() * 100) + 1;

                try {
                    semaphorePlacesLibres.acquire();
                    semaphoreAccesBuffer.acquire();

                    fileProduits.add(nouvelArticle);
                    nombreProduitsFabriques++;

                    System.out.println(identifiant + " a produit : " + nouvelArticle);

                    semaphoreAccesBuffer.release();
                    semaphoreProduitsDisponibles.release();

                    Thread.sleep(100);

                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    class Consommateur implements Runnable {

        private final int quantiteAConsommer;
        private final String identifiant;

        public Consommateur(String identifiant, int quantiteAConsommer) {
            this.identifiant = identifiant;
            this.quantiteAConsommer = quantiteAConsommer;
        }

        @Override
        public void run() {
            for (int indice = 0; indice < quantiteAConsommer; indice++) {
                try {
                    semaphoreProduitsDisponibles.acquire();
                    semaphoreAccesBuffer.acquire();

                    int articleConsomme = fileProduits.poll();
                    nombreProduitsConsommes++;

                    System.out.println(identifiant + " a consommé : " + articleConsomme);

                    semaphoreAccesBuffer.release();
                    semaphorePlacesLibres.release();

                    Thread.sleep(150);

                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public Producteur creerProducteur(String identifiant, int quantiteAProduire) {
        return new Producteur(identifiant, quantiteAProduire);
    }

    public Consommateur creerConsommateur(String identifiant, int quantiteAConsommer) {
        return new Consommateur(identifiant, quantiteAConsommer);
    }

    public int getNombreProduitsConsommes() {
        return nombreProduitsConsommes;
    }

    public int getNombreProduitsFabriques() {
        return nombreProduitsFabriques;
    }
}