import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class image {
    private int width;
    private int height;
    private int[][][] pixels; 

    public int getWidth() { return width; }
    public int getHeight() { return height; }


    public image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width][3];
    }


    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;
        }
    }


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


    public void write_bin(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);

        String header = "P6\n" + width + " " + height + "\n255\n";
        fos.write(header.getBytes());

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


    public static image read_bin(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);

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


