public class Graph {

    private final boolean[][] edges;
    boolean[] seenVertices;
    byte[] newVertices; //-1 signalizes that vertex was added in current round, 1 signalizes that vertex was added last round, 0 = not added

    public Graph(boolean[][] edges){
        this.edges = edges;
        seenVertices = new boolean[edges.length];
        newVertices  = new byte[edges.length];
    }

    private int get_eccentricity(int vertex){
        //set seen and new vertices to false except for vertex
        for (int i=0;i<edges.length;i++){
            if (i==vertex){
                seenVertices[i] = true;
                newVertices[i] = 1;
            }
            else {
                seenVertices[i] = false;
                newVertices[i] = 0;
            }
        }
        int eccentricity = 0;

        while (includesFalse(seenVertices)){
            if (!includesTrue(newVertices)){
                return -1;
            }
            eccentricity++;
            for (int v=0; v<edges.length; v++){
                if (!(newVertices[v]==1))continue;

                newVertices[v] = 0;
                //add all reachable vertices from v to seenVertices and newVertices
                for (int u=0; u<edges.length; u++){
                    if (edges[v][u]){
                        if (!seenVertices[u]){
                            seenVertices[u] = true;
                            newVertices[u] = -1;
                        }
                    }
                }
            }
            //make all newvertices = -1 to 1
            for (int i=0;i<edges.length; i++){
                if (newVertices[i] == -1){
                    newVertices[i] = 1;
                }
            }
        }
        return eccentricity;
    }

    private boolean includesTrue(byte[] array) {
        for (byte b: array){
            if (b==1){
                return true;
            }
        }
        return false;
    }

    private boolean includesFalse(boolean[] array) {
        for (int i=0;i<array.length;i++){
            if (!array[i]){
                return true;
            }
        }
        return false;
    }

    public int[] diameter_and_radius(){
        int diameter = 0;
        int radius = 0;
        for (int v = 0; v<edges.length; v++) {
            int e = get_eccentricity(v);
            if (e < 0){
                return new int[]{-1, -1};
            }
            if (e > diameter) {
                diameter = e;
            }
            if (e < radius || radius == 0) {
                radius = e;
            }

        }
        return new int[]{diameter, radius};
    }

    public int oriented_diameter(int number_of_edges, boolean end_early, int diameter){
        int odiam = 0;

        //if end_early is true, calculation stops if orientation below that threshold was reached
        int upper = (int) Math.floor(0.5* Math.pow(diameter,2) + diameter);

        String binary;
        //create every possible orientation
        //NOTE: since the oriented diameter remains the same when flipping all orientations, we can set the last digit in
        //binary to always be 1
        for (int i=0;i<Math.pow(2,number_of_edges-1);i++){
            binary = Integer.toBinaryString(i) + '1';
            int bl = binary.length();
            int row = 0;
            int column = 0;
            int binary_index = 0;
            while (row < edges.length){
                column = row + 1;
                while (column < edges.length){
                    if (edges[row][column]){
                        if (binary_index >= (number_of_edges-bl) && binary.charAt(binary_index-number_of_edges+bl) == '1' ){
                            edges[row][column] = false;
                        }
                        else {
                            edges[column][row] = false;
                        }
                        binary_index++;
                    }
                    column++;
                }
                row++;
            }

            //now we have an orientation of the graph
            int d= diameter_and_radius()[0];

            if (end_early && ((d < upper && d > 0) || (d<=7 && d>0))){
                return upper;
            }
            else if (d < 0){
                //not a strong orientation
            }
            else if (d < odiam || odiam == 0){
                odiam = d;
            }

            //reset edges
            int r = 0;
            while (r<edges.length){
                int c = 0;
                while (c<edges.length){
                    if (edges[r][c]){
                        edges[c][r] = true;
                    }
                    c++;
                }
                r++;
            }
        }
        return odiam;
    }

}
