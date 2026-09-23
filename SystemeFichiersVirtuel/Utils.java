public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {

        memory[offset]     = (byte) (value >>> 24);
        memory[offset + 1] = (byte) (value >>> 16);
        memory[offset + 2] = (byte) (value >>> 8);
        memory[offset + 3] = (byte) value;

        return 4;
    }

    public static int readInt(byte[] memory, int offset) {
          return ((memory[offset] & 0xFF) << 24)
             | ((memory[offset + 1] & 0xFF) << 16)
             | ((memory[offset + 2] & 0xFF) << 8)
             | (memory[offset + 3] & 0xFF);

    }

    public static int writeShort(byte[] memory, int offset, short value) {
        memory[offset]     = (byte) (value >>> 8);
        memory[offset + 1] = (byte) value;
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
         return (short) (((memory[offset] & 0xFF) << 8)
                      | (memory[offset + 1] & 0xFF));
    }

    public static int writeLong(byte[] memory, int offset, long value) {
    // TODO: Écrire les 8 octets du long en big-endian.
    return 8;
    }

    public static long readLong(byte[] memory, int offset) {
        // TODO: Reconstituer le long.
        return 0L;
    }

    public static int writeString(
            byte[] memory,
            int offset,
            String str,
            int maxLength) {

        // TODO:
        // 1. Convertir la chaîne en octets.
        // 2. Copier les octets sans dépasser maxLength.
        // 3. Nettoyer le reste de la zone avec des zéros.

        return maxLength;
    }

    public static String readString(
            byte[] memory,
            int offset,
            int maxLength) {

        // TODO:
        // Lire jusqu'au premier octet nul
        // ou jusqu'à maxLength.

        return "";
    }

}
