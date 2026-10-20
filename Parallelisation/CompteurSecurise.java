public class CompteurSecurise {

    private static final Object verrou = new Object();

    @Override
    public void run() {
        for (int i = 0; i < nombreIncrements; i++) {
            synchronized(verrou)
            compteurGlobal++;

        }
    }
}

