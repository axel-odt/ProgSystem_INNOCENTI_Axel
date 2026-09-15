import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class image {
    private int width;
    private int height;
    // pixels[y][x][0=R,1=G,2=B]
    private int[][][] pixels; // pixels[y][x][0=R,1=G,2=B]

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Constructeur : initialise une image vide.
     */
    public image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width][3];
    }

    /**
     * Définit la couleur d'un pixel à la position (x, y)
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }

    /**
     * Sauvegarde l'image au format texte PPM (P3)
     */
    public void save_txt(String filename) throws IOException {
     	FileWriter writer = new FileWriter(filename);
        writer.write("P3\n");
	    writer.write(width + " " + height + "\n");
        writer.write("255\n");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.write(
                pixels[y][x][0] + " " +
                pixels[y][x][1] + " " +
                pixels[y][x][2] + " " 
                );
            }
        }
	    writer.close();
	    System.out.println("Image PPM créée avec succés !");
    }

    static public void read_txt(String filename) throws IOException {
         try {
            FileInputStream fis = new FileInputStream(filename);
            byte[] buffer = new byte[128];
            int bytesRead = fis.read(buffer);
            System.out.println(new String(buffer, 0, bytesRead));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
 * Sauvegarde l'image au format binaire PPM (P6)
 */
public void write_bin(String filename) throws IOException {
    FileOutputStream fos = new FileOutputStream(filename);

    // Header PPM
    String header = "P6\n" + width + " " + height + "\n255\n";
    fos.write(header.getBytes());

    // Pixels : 3 octets par pixel (R, G, B)
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            fos.write(pixels[y][x][0]);
            fos.write(pixels[y][x][1]);
            fos.write(pixels[y][x][2]);
        }
    }

    fos.close();

    System.out.println("Image PPM binaire créée avec succès !");
}

private static String readToken(FileInputStream fis) throws IOException {
    StringBuilder token = new StringBuilder();
    int c;

    // Ignorer les espaces
    do {
        c = fis.read();
    } while (c != -1 && Character.isWhitespace((char) c));

    // Lire le token
    while (c != -1 && !Character.isWhitespace((char) c)) {
        token.append((char) c);
        c = fis.read();
    }

    return token.toString();
}

/**
 * Lit une image au format binaire PPM (P6)
 */
public static image read_bin(String filename) throws IOException {
    FileInputStream fis = new FileInputStream(filename);

    // Lecture du header
    String format = readToken(fis);

    if (!format.equals("P6")) {
        fis.close();
        throw new IOException("Le fichier n'est pas au format P6");
    }

    int width = Integer.parseInt(readToken(fis));
    int height = Integer.parseInt(readToken(fis));
    int maxColor = Integer.parseInt(readToken(fis));

    if (maxColor != 255) {
        fis.close();
        throw new IOException("La valeur maximale doit être 255");
    }

    image img = new image(width, height);

    // Lecture des pixels binaires
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {

            int r = fis.read();
            int g = fis.read();
            int b = fis.read();

            if (r == -1 || g == -1 || b == -1) {
                fis.close();
                throw new IOException("Fichier PPM incomplet");
            }

            img.setPixel(x, y, r, g, b);
        }
    }

    fis.close();

    return img;
}

}


