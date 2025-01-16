package pathblocker;

import java.util.*;

public class BFS {
    static final int[] dx = {0, 1, 0, -1};
    static final int[] dy = {1, 0, -1, 0};
    private Maze maze;

    public BFS(Maze maze) {
        this.maze = maze;
    }

    public List<int[]> findPath() {
        char[][] mazeArray = maze.getMaze();
        int[] startPos = maze.getStartPosition();
        if (startPos == null) {
            return null;
        }
        int startX = startPos[0];
        int startY = startPos[1];

        
        Queue<PathState> queue = new LinkedList<>();// BFS queue
        boolean[][] initialVisited = new boolean[mazeArray.length][mazeArray[0].length];
        initialVisited[startX][startY] = true;// Mark the starting cell as visited
        List<int[]> initialPath = new ArrayList<>();
        initialPath.add(new int[]{startX, startY});// Add the start position to the path
        queue.add(new PathState(initialPath, initialVisited));

        while (!queue.isEmpty()) {
            PathState currentState = queue.poll();
            List<int[]> currentPath = currentState.path;
            boolean[][] currentVisited = currentState.visited;
            int[] current = currentPath.get(currentPath.size() - 1);
            int x = current[0];
            int y = current[1];

         // Explore in all four directions
            for (int i = 0; i < 4; i++) {
                int newX = x;
                int newY = y;
                List<int[]> newPath = new ArrayList<>(currentPath);
                boolean[][] newVisited = copyVisited(currentVisited);

                
                // Move in the current direction until hitting a wall or visited cell
                while (isValid(newX + dx[i], newY + dy[i], newVisited)) {
                    newX += dx[i];
                    newY += dy[i];
                    newPath.add(new int[]{newX, newY});
                    newVisited[newX][newY] = true;
                    if (mazeArray[newX][newY] == 'G') {
                        return newPath;
                    }
                }

             // Only add new state if we moved
                if (!currentPath.equals(newPath)) {
                    queue.add(new PathState(newPath, newVisited));
                }
            }
        }
        return null;
    }
   // checks whether a given cell is valid:The cell must be within the bounds of the maze and It must not be a wall ('W') and It must not have been visited before.
    private boolean isValid(int x, int y, boolean[][] visited) {
        char[][] mazeArray = maze.getMaze();
        return x >= 0 && x < mazeArray.length && y >= 0 && y < mazeArray[0].length && mazeArray[x][y] != 'W' && !visited[x][y];
    }

    private boolean[][] copyVisited(boolean[][] original) {
        boolean[][] copy = new boolean[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }

    private static class PathState {
        List<int[]> path;
        boolean[][] visited;

        PathState(List<int[]> path, boolean[][] visited) {
            this.path = path;
            this.visited = visited;
        }
    }
}
