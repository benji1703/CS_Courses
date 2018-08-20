import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  A scouter thread This thread lists all sub-directories from a given root path.
 *  Each sub-directory is enqueued to be searched for files by Searcher threads.
 */
public class Scouter implements Runnable {

    private SynchronizedQueue<File> directoryQueue;
    private File root;

    /**
     * Constructor. Initializes the scouter with a queue for the directories to be searched and a root directory to start from.
     * @param directoryQueue - A queue for directories to be searched
     * @param root - Root directory to start from
     */
    public Scouter(SynchronizedQueue<File> directoryQueue, File root) {
        this.directoryQueue = directoryQueue;
        this.root = root;
        this.directoryQueue.registerProducer();
    }

    /**
     * Starts the scouter thread. Lists directories under root directory and adds them to queue,
     * then lists directories in the next level and enqueues them and so on. This method begins by
     * registering to the directory queue as a producer and when finishes, it unregisters from it.
     */
    public void run() {

        Queue<File> directories = new LinkedList<>();
        directories.add(this.root);

        // Even though this sound weird - We were asked to search only SUBDIRECTORY and not to add the ROOT folder
        // https://piazza.com/class/jf2orluk8yz5hx?cid=67
        // this.directoryQueue.enqueue(this.root);

        while (!directories.isEmpty()){

            // http://javaconceptoftheday.com/list-all-files-in-directory-in-java/
            File[] allDirectories = directories.poll().listFiles();

            if (allDirectories != null) {
                for (File directory : allDirectories) {
                    if (directory.isDirectory()) {
                        directories.add(directory);
                        this.directoryQueue.enqueue(directory);
                    }
                }
            }
        }

        this.directoryQueue.unregisterProducer();
    }
}
