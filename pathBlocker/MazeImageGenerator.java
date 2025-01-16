package pathblocker;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class MazeImageGenerator {
    private char[][] maze;
    private int cellSize = 10;
    private int levelName;

    public MazeImageGenerator(char[][] maze, int levelName) {
        this.maze = maze;
        this.levelName = levelName;
    }

    // Generates images that show the path step by step
    public void generatePathImages(List<int[]> path, String filename) {
        char[][] tempMaze = new char[maze.length][maze[0].length];
     // Create a copy of the original maze to manipulate
        for (int i = 0; i < maze.length; i++) {
            System.arraycopy(maze[i], 0, tempMaze[i], 0, maze[i].length);
        }

        int pathStepCount = 1;// Keep step number for png filenames
        generateMazeImage(String.format(filename+"_%03d.png", pathStepCount++), tempMaze, path, 0);

        int lastDirection = -1;
        int startIndex = 0;

        for (int i = 1; i < path.size(); i++) {
            int[] prev = path.get(i - 1);
            int[] curr = path.get(i);
            int direction = getDirection(prev, curr);

            //// If the direction changes, generate an image for the current segment
            if (direction != lastDirection) {
                if (lastDirection != -1) {
                    for (int j = startIndex - 1; j < i; j++) {
                        int[] pos = path.get(j);
                        tempMaze[pos[0]][pos[1]] = 'a';
                    }
                    generateMazeImage(String.format(filename+"_%03d.png", pathStepCount++), tempMaze, path, i - 1);
                }
                startIndex = i;// Update startIndex to current index
                lastDirection = direction;
            }
        }
        for (int j = startIndex; j < path.size(); j++) {
            int[] pos = path.get(j);
            tempMaze[pos[0]][pos[1]] = 'a';
        }
        generateMazeImage(String.format("%s_%03d.png", filename, pathStepCount++), tempMaze, path, path.size() - 1);
    }

    /// Determines the direction of movement between two points in the path
    private int getDirection(int[] prev, int[] curr) {
        int dx = curr[0] - prev[0];
        int dy = curr[1] - prev[1];
        for (int i = 0; i < 4; i++) {
            if (dx == BFS.dx[i] && dy == BFS.dy[i]) {
                return i;
            }
        }
        return -1;
    }

    // Generates an image of the maze with the current path status
    private void generateMazeImage(String filename, char[][] mazeState, List<int[]> path, int currentStep) {
        int width = mazeState[0].length * cellSize;
        int height = mazeState.length * cellSize;

     // Create a buffered image with the calculated width and height
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

     // Set the background color
        g2d.setColor(new Color(153, 153, 255));
        g2d.fillRect(0, 0, width, height);

     // Color the maze based on the characters 'P' for start, 'G' for goal, 'W' and 'a' for wall)
        for (int i = 0; i < mazeState.length; i++) {
            for (int j = 0; j < mazeState[i].length; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                switch (mazeState[i][j]) {
                    case 'P':
                        g2d.setColor(Color.YELLOW);
                        g2d.fillRect(x, y, cellSize, cellSize);
                        break;
                    case 'G':
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(x, y, cellSize, cellSize);
                        break;
                    case 'W':
                    case 'a':
                        g2d.setColor(new Color(51, 0, 102));
                        g2d.fillRect(x, y, cellSize, cellSize);
                        break;
                    default:
                        break;
                }
            }
        }
     // Highlight the current step in the path
        if (path != null && currentStep >= 0 && currentStep < path.size()) {
            int[] currentPos = path.get(currentStep);
            int x = currentPos[1] * cellSize;
            int y = currentPos[0] * cellSize;
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(x, y, cellSize, cellSize);
        }
        
     // Display the level name as a title on the image
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 8));
        g2d.drawString("Level: " + levelName, 4, 8);

        g2d.dispose();
        
        /// Save the PNG file
        try {
            ImageIO.write(image, "png", new File(filename));
        } catch (IOException e) {
            System.out.println( e.getMessage());
        }
    }
    
    public void generateNoSolutionImage(String filename) {
        int width = maze.length*cellSize; 
        int height = maze[0].length*cellSize; 

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(new Color(153, 153, 255)); 
        g2d.fillRect(0, 0, width, height);

        // Add warning message
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        String message = "Target unreachable"; 
        int messageWidth = g2d.getFontMetrics().stringWidth(message);
        g2d.drawString(message, (width - messageWidth) / 2, height / 2);

        // Add level information
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 8));
        g2d.drawString("Level: " + levelName, 4, 8);

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File(String.format("%s_no_solution.png", filename)));
        } catch (IOException e) {
            System.out.println( e.getMessage());
        }
    }
}


