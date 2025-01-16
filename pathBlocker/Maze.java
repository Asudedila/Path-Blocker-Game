package pathblocker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private char[][] maze;

    public Maze(String filename) {
        readMazeFromFile(filename);
    }
    // Read the txt file from disk and convert it to an array
    private void readMazeFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        maze = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i).toCharArray();
        }
    }

    public char[][] getMaze() {
        return maze;
    }

    // Method to find the start position, the character 'P'
    public int[] getStartPosition() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'P') {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}

