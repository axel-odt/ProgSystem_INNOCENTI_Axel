public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {

        memory[offset]     = (byte) (value >>> 24);
        memory[offset + 1] = (byte) (value >>> 16);
        memory[offset + 2] = (byte) (value >>> 8);
        memory[offset + 3] = (byte) value;

        return 4;
    }

    public static int readInt(byte[] memory, int offset) {

        byte b1 = memory[offset];
        byte b2 = memory[offset + 1];
        byte b3 = memory[offset + 2];
        byte b4 = memory[offset + 3];

        return ((b1 & 0xFF) << 24)
            | ((b2 & 0xFF) << 16)
            | ((b3 & 0xFF) << 8)
            | (b4 & 0xFF);
    }


    public static int writeShort(byte[] memory, int offset, short value) {
        memory[offset]     = (byte) (value >>> 8);
        memory[offset + 1] = (byte) value;
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        byte b1 = memory[offset];
        byte b2 = memory[offset + 1];

        return (short) (((b1 & 0xFF) << 8)
                      | (b2 & 0xFF));
    }

    public static int writeLong(byte[] memory, int offset, long value) {
                memory[offset]     = (byte) (value >>> 56);
                memory[offset + 1] = (byte) (value >>> 48);
                memory[offset + 2] = (byte) (value >>> 40);
                memory[offset + 3] = (byte) (value >>> 32);
                memory[offset + 4] = (byte) (value >>> 24);
                memory[offset + 5] = (byte) (value >>> 16);
                memory[offset + 6] = (byte) (value >>> 8);
                memory[offset + 7] = (byte) value;

                return 8;
    }

    public static long readLong(byte[] memory, int offset) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result = (result << 8) | (memory[offset + i] & 0xFFL);
        }
        return result;
    }

    public static int writeString(
            byte[] memory,
            int offset,
            String str,
            int maxLength) {

        byte[] bytes = str.getBytes();
        
        int bytesToWrite = bytes.length;
        if (bytesToWrite > maxLength) {
            bytesToWrite = maxLength;
        }

        for (int i = 0; i < bytesToWrite; i++) {
            memory[offset + i] = bytes[i];
        }

        for (int i = bytesToWrite; i < maxLength; i++) {
            memory[offset + i] = 0;
        }

        return maxLength;
    }

    public static String readString(
            byte[] memory,
            int offset,
            int maxLength) {

        int length = 0;
        while (length < maxLength && memory[offset + length] != 0) {
            length++;
        }

        return new String(memory, offset, length);
    }

}