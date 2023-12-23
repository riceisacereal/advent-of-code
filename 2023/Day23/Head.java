public class Head {
    boolean[][] visited;
    int[] coord;
    int distance;
//    int h;

    public Head(int[] coord, int distance, int maxY, int maxX, boolean[][] visited) {
        this.coord = coord;
        this.distance = distance;
        this.visited = new boolean[maxY][maxX];
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                this.visited[i][j] = visited[i][j];
            }
        }
        this.visited[coord[0]][coord[1]] = true;
    }

    public boolean visited(int[] loc) {
        return visited[loc[0]][loc[1]];
    }
}
