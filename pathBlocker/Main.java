package pathblocker;

import java.util.List;
/*21050111032 Serhat Erdoğan
21050111049 Asude Dila Açıkgöz

1) Why you prefer the search algorithm you choose?

	We chose to use the BFS algorithm because BFS guarantees the shortest solution. While DFS might reach a solution faster when there are many possible paths, BFS performs better in levels with only a few correct paths, providing a quicker result. 
	Additionally, BFS systematically explores all possible directions, ensuring that the path found is indeed the optimal solution. Since BFS expands all nodes until it reaches the shallowest solution, the search time depends on the depth of the solution.
	This allows for quick scanning of large areas, speeding up the process of reaching the goal. As the game's difficulty increases, the number of alternative solutions decreases, meaning the entire map needs to be thoroughly explored.
	Even though BFS's memory usage might seem like a disadvantage, it is a logical choice for this game.
	  
2) Can you achieve the optimal result? Why? Why not?

	Yes, the BFS algorithm is optimal. The reason for this is that BFS is a breadth-first search algorithm; it explores all paths systematically from the starting point, step by step. 
	This approach ensures that when the algorithm reaches the target, the path found is guaranteed to be the shortest. The step-by-step checking further confirms that it will find the shortest path. 
	When the algorithm locates the target endpoint, it terminates immediately, preventing unnecessary searches.

3) How you achieved efficiency for keeping the states?

	PathState Class: The algorithm uses a special PathState class to represent each state. This class contains the path that has been taken (path) and the visited cells (visited).
	In the BFS algorithm, a queue (Queue) is used to sequentially process the paths to be explored. At each new step, a PathState object containing the current state is stored in the queue and processed in order. 
	During BFS, each path is stored in a List<int[]> structure. These lists contain the x and y coordinates of the cells and are updated at each step.
	When a new state is created, if this new path is the same as the current path (i.e., no movement has occurred), the state is not added to the queue. 
	This ensures that a continuously updated path is maintained in memory, preventing unnecessary memory usage.
	
4) If you prefer to use DFS (tree version) then do you need to avoid cycles?

	We did not use DFS because the time to find a solution was longer, and the solutions found did not represent the shortest path.

5) What will be the path-cost for this problem?

	Even though there are different paths in the maze, the BFS algorithm finds the first solution that represents the shortest path, which means that the path cost is equal to the lowest number of steps. 
	Each cell traversed counts as one step, and the first valid path found by the BFS algorithm is the lowest cost path. In this case, the path cost will equal the total number of steps in the shortest path.
	The code continues moving in one direction until it encounters a wall or a visited cell. This optimizes the path cost by minimizing unnecessary turns.
	Additionally, the algorithm terminates immediately when it reaches the target cell. This prevents unnecessary searches.
	*/

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
