import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        // Parcourir les inodes de 0 à MAX_INODES - 1.
        // Identifier le premier inode libre.
        // Retourner son numéro.
        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            Inode inode = new Inode(memoryManager, i);
            
            if (inode.getFileType() == 0) {
                return i;
            }
        }

        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // Construire l'inode
        // L'initialiser comme fichier vide
        Inode inode = new Inode(memoryManager, inodeNum);
        long currentTime = System.currentTimeMillis();
        int[] directPointers = new int[Inode.DIRECT_POINTERS];
        Arrays.fill(directPointers, -1);
        inode.writeToMemory(0, 0, currentTime, currentTime, directPointers, 0, (short) 0644, 1);

        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public boolean writeFile(
        int inodeNum,
        byte[] data) {

        int blocksNeeded =
                (data.length
                + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers = new int[Inode.DIRECT_POINTERS];
        Arrays.fill(blockPointers, -1);

        for (int i = 0; i < blocksNeeded; i++) {
            int allocatedBlock = memoryManager.allocateBlock();
            if (allocatedBlock == -1) {
                for (int j = 0; j < i; j++) {
                    memoryManager.setBlockUsed(blockPointers[j], false);
                }
                return false;
            }
            blockPointers[i] = allocatedBlock;
        }

        byte[] memory = memoryManager.getFilesystemMemory();

        int bytesRemaining =
                data.length;

        int dataSrcOffset = 0;

        for (int i = 0; i < blocksNeeded; i++) {
             int bytesToWrite = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
             int blockNum = blockPointers[i];
             int physicalOffset = blockNum * MemoryManager.BLOCK_SIZE;

             System.arraycopy(data, dataSrcOffset, memory, physicalOffset, bytesToWrite);

             dataSrcOffset += bytesToWrite;
             bytesRemaining -= bytesToWrite;
        }

        Inode inode = new Inode(memoryManager, inodeNum);
        long currentTime = System.currentTimeMillis();

        inode.writeToMemory(
            1,                  
            data.length,        
            currentTime,     
            currentTime,      
            blockPointers,      
            -1,               
            (short) 0644,      
            1                  
        );

        return true;
    }

    public byte[] readFile(int inodeNum) {

        Inode inode =
                new Inode(memoryManager, inodeNum);

        int fileSize =
                inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData =
                new byte[fileSize];

        byte[] memory =
                memoryManager.getFilesystemMemory();

        int[] blockPointers =
                inode.getDirectPointers();

        int bytesRemaining = fileSize;
        int destOffset = 0;

        for (int i = 0; i < Inode.DIRECT_POINTERS && bytesRemaining > 0; i++) {
            int blockNum = blockPointers[i];
            if (blockNum == -1) {
                break; 
            }

            int bytesToRead = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int physicalOffset = blockNum * MemoryManager.BLOCK_SIZE;

            System.arraycopy(memory, physicalOffset, fileData, destOffset, bytesToRead);

            destOffset += bytesToRead;
            bytesRemaining -= bytesToRead;
        }

        return fileData;
    }

}
