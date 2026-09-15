import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;

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
}


