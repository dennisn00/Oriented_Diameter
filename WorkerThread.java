public class WorkerThread extends Thread {

    int number_of_vertices;
    int edges_count = 0;
    int thread_id;
    Master master;

    public WorkerThread(int thread_id, Master master, int number_of_vertices){
        this.thread_id = thread_id;
        this.master = master;
        this.number_of_vertices = number_of_vertices;
    }

    public void run(){
        while(true){
            boolean[][] graph = master.get_next();
            if (graph == null){
                break;
            }
            edges_count=0;
            //update edgescount
            for (boolean[] row: graph){
                for (boolean entry: row){
                    if (entry){
                        edges_count++;
                    }
                }
            }
            edges_count /= 2;

            Graph g = new Graph(graph);
            int[] dr = g.diameter_and_radius();
            int diam = dr[0];
            if (diam > 2){
                int odiam = g.oriented_diameter(edges_count, true, diam);
                if (odiam > 0.5 * Math.pow(diam, 2) + diam && odiam>= 8){
                    System.out.println("f("+diam+") = " + odiam);
                }
            }
        }
    }
}
