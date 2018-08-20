// Benjamin Arbibe - 323795633

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * A copier thread.
 * Reads files to copy from a queue and copies them to the given destination.
 */
public class Copier implements Runnable {

    public static final int	COPY_BUFFER_SIZE = 4096;
    private File destination;
    private SynchronizedQueue<File> resultsQueue;

    /**
     * Constructor. Initializes the worker with a destination directory and a queue of files to copy.
     * @param destination - Destination directory
     * @param resultsQueue - Queue of files found, to be copied
     */
    public Copier(java.io.File destination,
                  SynchronizedQueue<File> resultsQueue) {
        this.destination = destination;
        this.resultsQueue = resultsQueue;
    }


    /**
     * Runs the copier thread. Thread will fetch files from queue and copy them, one after each other, to the
     * destination directory. When the queue has no more files, the thread finishes.
     *
     * Specified by: run in interface java.lang.Runnable
     */
    // https://www.journaldev.com/861/java-copy-file # Using Method 1
    public void run() {
        File source;
        while ((source = resultsQueue.dequeue()) != null) {
            File fileOutput = new File(destination.getAbsolutePath() + File.separator + source.getName());
            try {
                copyFileUsingStream(source, fileOutput);
            } catch (IOException e) {
                System.err.println("Error when copy - check if already exist?");;
            }
        }
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(dest);
            byte[] bufferOfByte = new byte[COPY_BUFFER_SIZE];
            int length;
            while ((length = inputStream.read(bufferOfByte)) > 0) {
                outputStream.write(bufferOfByte, 0, length);
            }
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

}
