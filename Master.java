import java.io.*;
import java.util.Scanner;

public class Master {

    int number_of_vertices;
    Scanner reader;
    int start = 0;
    File graphs;


    public Master(String filename, int number_of_vertices) throws FileNotFoundException {
        graphs = new File(filename);
        reader = new Scanner(graphs);
        for (int i=0; i<start*(number_of_vertices+2); i++){
            reader.nextLine();
        }
        this.number_of_vertices = number_of_vertices;
    }

    public synchronized boolean[][] get_next(){
        String s = null;

        if (!reader.hasNextLine())return null;

        //the first two lines are junk
        reader.nextLine();

        if (!reader.hasNextLine())return null;
        boolean[][] edges = new boolean[number_of_vertices][number_of_vertices];
        int i = 0;

        while (i<number_of_vertices) {
            //if end of file is reached, return null
            if (!reader.hasNextLine())return null;

            String line = reader.nextLine();
            int row = i;
            for (int column=0; column<number_of_vertices;column++){
                if (line.charAt(column) == '1'){
                    edges[row][column] = true;
                }
                else {
                    edges[row][column] = false;
                }
            }
            i++;
        }
        return edges;
    }


}
