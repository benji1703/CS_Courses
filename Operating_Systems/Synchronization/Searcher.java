// Benjamin Arbibe - 323795633

import java.io.File;

/**
 *  A searcher thread. Searches for files with a given pattern in all directories listed in a directory queue.
 */
public class Searcher implements Runnable {
    private String extension;
    private SynchronizedQueue<File> directoryQueue;
    private SynchronizedQueue<File> resultsQueue;
    /**
     * Constructor. Initializes the searcher thread.
     * @param extension - Pattern to look for
     * @param directoryQueue - A queue with directories to search in (as listed by the scouter)
     * @param resultsQueue - A queue for files found (to be copied by a copier)
     */
    public Searcher(String extension, SynchronizedQueue<File> directoryQueue, SynchronizedQueue<File> resultsQueue) {
        this.extension = extension;
        this.directoryQueue = directoryQueue;
        this.resultsQueue = resultsQueue;
    }

    /**
     * Runs the searcher thread. Thread will fetch a directory to search in from the directory queue,
     * then search all files inside it (but will not recursively search subdirectories!).
     * Files that are found to have the given extension are enqueued to the results queue.
     * This method begins by registering to the results queue as a producer and when finishes, it unregisters from it.
     * Specified by: run in interface java.lang.Runnable
     */
    public void run() {

        this.resultsQueue.registerProducer();

        for (;;) {

            File dir = directoryQueue.dequeue();
            if (dir == null) {
                resultsQueue.unregisterProducer();
                return;
            }

            File[] filesArr = dir.listFiles();

            if (filesArr != null) {
                for (File file : filesArr) {
                    // https://stackoverflow.com/questions/35082838/
                    // https://www.tutorialspoint.com/java/io/file_isfile.htm
                    if (file.getName().endsWith("." + extension)  && file.isFile()) {
                        resultsQueue.enqueue(file);
                    }
                }
            }
        }

    }

}