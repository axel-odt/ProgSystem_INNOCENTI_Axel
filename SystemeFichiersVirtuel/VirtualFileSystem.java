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
}
