// Benjamin Arbibe - 323795633

import java.io.File;

/**
 *  Main application class. This application searches for all files under some given path that contain a given
 *  textual pattern. All files found are copied to some specific directory.
 */
public class DiskSearcher {

    /**
     *     Capacity of the queue that holds the directories to be searched
     */
    static int DIRECTORY_QUEUE_CAPACITY = 50;

    /**
     *     Capacity of the queue that holds the files found
     */
    static int RESULTS_QUEUE_CAPACITY = 50;


    /**
     * Main method. Reads arguments from command line and starts the search.
     * @param args - Command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 5) {
            System.err.println("> java DiskSearcher <pattern> <root directory> <destination directory> " +
                    "<# of searchers> <# of copiers>");
            return;
        }

        String pattern = args[0];
        String root_dir = args[1];
        String dest_dir = args[2];
        String num_search = args[3];
        String num_copy = args[4];

        int searcher = Integer.parseInt(num_search);
        int copier = Integer.parseInt(num_copy);

        if (searcher < 0 || copier < 0) {
            throw new NumberFormatException();
        }

        startSearch(pattern, root_dir, dest_dir, searcher, copier);

    }

    private static void startSearch(String pattern, String root_dir, String dest_dir, int searcher, int copier) {

        Searcher[] searchers = new Searcher[searcher];
        Copier[] copiers = new Copier[copier];

        Thread[] searchThreads = new Thread[searcher];
        Thread[] copyThreads = new Thread[copier];

        SynchronizedQueue<File> directoryQueue = new SynchronizedQueue<>(DIRECTORY_QUEUE_CAPACITY);
        SynchronizedQueue<File> resultsQueue = new SynchronizedQueue<>(RESULTS_QUEUE_CAPACITY);

        File rootDir = new File(root_dir);
        File destinationFile = new File(dest_dir);

        if (!destinationFile.isDirectory()){
            destinationFile.mkdir();
        }

        Thread scouter = new Thread (new Scouter(directoryQueue, rootDir));

        scouter.start();

        for (int i = 0; i < searcher; i++) {
            searchers[i] = new Searcher(pattern, directoryQueue, resultsQueue);
            searchThreads[i] = new Thread(searchers[i]);
            searchThreads[i].start();
        }

        for (int i = 0; i < copier; i++) {
            copiers[i] = new Copier(destinationFile, resultsQueue);
            copyThreads[i] = new Thread(copiers[i]);
            copyThreads[i].start();
        }

        try {
            scouter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < searcher; i++) {
            try {
                searchThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < copier; i++) {
            try {
                copyThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
