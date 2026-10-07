public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(
            MemoryManager memoryManager,
            int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
        return MemoryManager.INODE_TABLE_OFFSET + (this.inodeNumber * INODE_SIZE);
    }

    public int getFileType() {
       byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset() + 4;
        return Utils.readInt(memory, offset);
    }

    public int getFileSize() {
       byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset() + 8; 
        return Utils.readInt(memory, offset);
    }

    public int[] getDirectPointers() {

        byte[] memory = memoryManager.getFilesystemMemory();
        int[] pointers = new int[DIRECT_POINTERS];
        int startOffset = getInodeOffset() + 28;

        for (int i = 0; i < DIRECT_POINTERS; i++) {
            pointers[i] = Utils.readInt(memory, startOffset + (i * 4));
        }

        return pointers;
    }

    public void writeToMemory(
            int fileType,
            int fileSize,
            long creationTime,
            long modificationTime,
            int[] directPointers,
            int indirectPointer,
            short permissions,
            int linkCount) {

        byte[] memory = memoryManager.getFilesystemMemory();

        int offset = getInodeOffset();

        Utils.writeInt(memory, offset, this.inodeNumber);
        offset += 4;

        Utils.writeInt(memory, offset, fileType);
        offset += 4;

        Utils.writeInt(memory, offset, fileSize);
        offset += 4;

        Utils.writeLong(memory, offset, creationTime);
        offset += 8;

        Utils.writeLong(memory, offset, modificationTime);
        offset += 8;

        for (int i = 0; i < 10; i++) {
            int pointerVal = (directPointers != null && i < directPointers.length) ? directPointers[i] : -1;
            Utils.writeInt(memory, offset, pointerVal);
            offset += 4;
        }

        Utils.writeInt(memory, offset, indirectPointer);
        offset += 4;

        Utils.writeShort(memory, offset, permissions);
        offset += 2;

        Utils.writeInt(memory, offset, linkCount);
    }
}
