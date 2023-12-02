package assingment08;

import java.io.*;
import java.util.Scanner;

public class PathFinder {

    public static NodeGraph graph_;

    public static Maze maze_;

    /**
     * reads in a file of a maze
     * takes the file and creates a 2d array in a Maze object which is then turned into a Graph
     * finds a path from the S to the G
     * if a path exists , set the path and write out the file
     * else, will return the file without any path
     *
     * @param inputFile  the file of the unsolved maze
     * @param outputFile file with a solved maze if possible
     */
    public static void solveMaze(String inputFile, String outputFile) {
        maze_ = readInFile(inputFile);
        graph_ = new NodeGraph(maze_, 'S', 'G');
        boolean pathFound = graph_.findPathBFS();
        if (pathFound) {
            graph_.setPath();
            writeOutFile(outputFile);
        } else if (!pathFound) {
            writeOriginalFile(outputFile);
        }
    }

    /**
     * reads in the file to create a Maze object
     *
     * @param inputFile reading in the maze file to be parsed
     * @return return a Maze object, 2d array
     */
    public static Maze readInFile(String inputFile) {
        Maze maze;
        try {
            File file = new File(inputFile);
            Scanner scanner = new Scanner(file);

            // Read the dimensions
            String dimensionsLine = scanner.nextLine();
            String[] dimensions = dimensionsLine.split(" ");
            //first two ints in the file determine the dimensions of hte maze
            int height = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);

            maze = new Maze(height, width, scanner);

            // Close the scanner
            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return maze;

    }

    /**
     * writes out a solved maze
     * @param outputFile the file to write out
     */
    private static void writeOutFile(String outputFile) {
        //Create file to write to
        try (PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {
            int height = maze_.getHeight_();
            int width = maze_.getWidth();
            //write graph dimensions
            output.println(height + " " + width);

            //Loop through the inputted maze
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    char currentChar = graph_.nodes_[i][j].data_;
                    //if the graph node (in same location as maze elements) has a period, print out a period
                    if (currentChar == '.') {
                        output.print('.');
                    } else {
                        //else print out the contents of the Maze
                        output.print(maze_.getCharAtCell(i, j));
                    }
                }
                output.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method called if the maze is unsolvable
     * copies the exact maze passed in
     * @param outputFile file writing out
     */
    private static void writeOriginalFile(String outputFile) {
        try (PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {
            int height = maze_.getHeight_();
            int width = maze_.getWidth();
            //write graph dimensions
            output.println(height + " " + width);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    output.print(maze_.getCharAtCell(i, j));
                }
                output.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


