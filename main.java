import java.io.FileNotFoundException;

public class main {

    public static void main(String[] args) throws InterruptedException {
        int number_of_vertices = 9;
        int number_of_threads = 4;
        WorkerThread[] threads = new WorkerThread[number_of_threads];

        //create Master
        Master master;
        try {
            master = new Master("./graph_files/graphs_9.txt", number_of_vertices);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        for (int i=0;i<number_of_threads;i++){
            System.out.printf("Thread %d created\n", i);
            threads[i] = new WorkerThread(i, master, number_of_vertices);
            threads[i].start();
        }
        for (int i=0; i<number_of_threads; i++){
            threads[i].join();
        }
    }

}
