package pathblocker;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        String[] levels = {"level01.txt", "level02.txt", "level03.txt", "level04.txt", "level05.txt","level06.txt","level07.txt", "level08.txt", "level09.txt", "level10.txt"};

        int levelName = 1;
        for (String level : levels) {
            System.out.println(level);

            //create the class that creates the maze
            Maze maze = new Maze(level);

            // Provide the solution by sending it to the BFS class
            BFS bfs = new BFS(maze);
            List<int[]> path = bfs.findPath();
            MazeImageGenerator imageGenerator = new MazeImageGenerator(maze.getMaze(), levelName);
            if (path != null) {
                // Create png files
                imageGenerator.generatePathImages(path, level);
            } else {
                imageGenerator.generateNoSolutionImage(level);
            }

            
            levelName++;
        }
    }
}
