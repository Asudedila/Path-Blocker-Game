# Path-Blocker-Game
This project generates images that visually represent the solution to a maze. It uses the Breadth-First Search (BFS) algorithm to find a path through a maze, and then generates step-by-step images showing the path from start to goal. If no solution is found, a special image with a "Target unreachable" message is generated.

Features
Solves mazes using the BFS algorithm.
Generates images showing the path from start to goal step-by-step.
If no solution exists, generates an image indicating that the target is unreachable.
Customizable maze sizes and levels.
Structure 
The project is composed of the following classes:

1. Maze
Responsibility: Reads a maze from a text file and stores it as a 2D character array.
Methods:
readMazeFromFile(filename): Reads the maze from a file and converts it to a 2D array.
getMaze(): Returns the maze as a 2D array.
getStartPosition(): Returns the starting position of the maze marked by 'P'.
2. BFS (Breadth-First Search)
Responsibility: Implements the BFS algorithm to find a path through the maze.
Methods:
findPath(): Searches for the shortest path from the start to the goal ('P' to 'G') using BFS.
isValid(x, y, visited): Checks if a cell is valid to visit.
copyVisited(original): Creates a copy of the visited cells array.
3. MazeImageGenerator
Responsibility: Generates images showing the maze and the solution path.
Methods:
generatePathImages(path, filename): Generates step-by-step images of the solution path.
generateMazeImage(filename, mazeState, path, currentStep): Creates an image for a given state of the maze, highlighting the current step in the path.
generateNoSolutionImage(filename): Generates an image when no solution exists, with a warning message.
4. Main
Responsibility: The main entry point of the program.
Workflow:
Reads levels from text files.
Uses the BFS class to find the solution to each level.
Generates step-by-step images using the MazeImageGenerator class.
If no solution exists, generates an image showing that the target is unreachable.


Example Output
For a maze with a solution, images will be generated with filenames like level01_001.png, level01_002.png, etc., showing the maze with the path drawn at each step.

If the maze is unsolvable, an image named level01_no_solution.png will be created with a red warning message: "Target unreachable".
