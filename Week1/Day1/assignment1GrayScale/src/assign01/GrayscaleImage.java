package assign01;


import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * Represents a grayscale (black and white) image as a 2D array of "pixel" brightnesses
 * 255 is "white" 127 is "gray" 0 is "black" with intermediate values in between
 * Author: Ben Jones and ***STUDENT FILL YOUR NAME IN***
 */
public class GrayscaleImage {
    private double[][] imageData; // the actual image data


    /**
     * Initialize an image from a 2D array of doubles
     * This constructor creates a copy of the input array
     *
     * @param data initial pixel values
     * @throws IllegalArgumentException if the input array is empty or "jagged" meaning not all rows are the same length
     */
    public GrayscaleImage(double[][] data) {
        if (data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Image is empty");
        }

        imageData = new double[data.length][data[0].length];
        for (var row = 0; row < imageData.length; row++) {
            if (data[row].length != imageData[row].length) {
                throw new IllegalArgumentException("All rows must have the same length");
            }
            for (var col = 0; col < imageData[row].length; col++) {
                imageData[row][col] = data[row][col];
            }
        }
    }

    /**
     * Fetches an image from the specified URL and converts it to grayscale
     * Uses the AWT Graphics2D class to do the conversion, so it may add
     * an item to your dock/menu bar as if you're loading a GUI program
     *
     * @param url where to download the image
     * @throws IOException if the image can't be downloaded for some reason
     */
    public GrayscaleImage(URL url) throws IOException {
        var inputImage = ImageIO.read(url);
        //convert input image to grayscale
        //based on (https://stackoverflow.com/questions/6881578/how-to-convert-between-color-models)
        var grayImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, null);
        g2d.dispose();
        imageData = new double[grayImage.getHeight()][grayImage.getWidth()];

        //raster is basically a width x height x 1 3-dimensional array
        var grayRaster = grayImage.getRaster();
        for (var row = 0; row < imageData.length; row++) {
            for (var col = 0; col < imageData[0].length; col++) {
                //getSample parameters are x (our column) and y (our row), so they're "backwards"
                imageData[row][col] = grayRaster.getSampleDouble(col, row, 0);
            }
        }
    }

    public void savePNG(File filename) throws IOException {
        var outputImage = new BufferedImage(imageData[0].length, imageData.length, BufferedImage.TYPE_BYTE_GRAY);
        var raster = outputImage.getRaster();
        for (var row = 0; row < imageData.length; row++) {
            for (var col = 0; col < imageData[0].length; col++) {
                raster.setSample(col, row, 0, imageData[row][col]);
            }
        }
        ImageIO.write(outputImage, "png", filename);
    }

    ///Methods to be filled in by students below

    /**
     * Get the pixel brightness value at the specified coordinates
     * (0,0) is the top left corner of the image, (width -1, height -1) is the bottom right corner
     *
     * @param x horizontal position, increases left to right
     * @param y vertical position, **increases top to bottom**
     * @return the brightness value at the specified coordinates
     * @throws IllegalArgumentException if x, y are not within the image width/height
     */
    public double getPixel(int x, int y) {

        //checking if provided coordinates are within the valid range
        if(x < 0 || x >= imageData[0].length || y < 0 || y>= imageData.length){
            throw new IllegalArgumentException("x and y are not within the same width/height");
        }

        return imageData[y][x];
    }

    /**
     * Two images are equal if they have the same size and each corresponding pixel
     * in the two images is exactly equal
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {

        if(other == null){
            return false;
        }

        //AIDEN: checking if the object passed into the equals method is of the same object as GrayscaleImage
        //if not, return false
        if (!(other instanceof GrayscaleImage)) {
            return false;
        }

        //casting the other image to be a type of GrayscaleImage object
        GrayscaleImage otherImage = (GrayscaleImage) other;

        if(this.imageData.length != otherImage.imageData.length || this.imageData[0].length != otherImage.imageData[0].length){
            return false;
        }

        //implement equals to return true only when all pixels are exactly equal

        //outerloop iterating through the rows of the imageData array
        for (var row = 0; row < imageData.length; row++) {
            //inner loop iterates through columns of imageData
            //ensures that inner loop only iterates through the column of the current row
            for (var col = 0; col < imageData[row].length; col++) {
                //if this pixels and other image pixels are not the same, return false
                if (this.getPixel(col, row) != otherImage.getPixel(col, row)) {
                    return false;
                }
            }
        }
        //else
        return true;
    }


    /**
     * Computes the average of all values in image data
     *
     * @return the average of the imageData array
     */
    public double averageBrightness() {
        //STUDENT FILL ME IN WITH A CORRECT IMPLEMENTATION
        double totalBrightness = 0;
        double pixelCount = 0;

        //outerloop for the rows of imageData, innerloop through each column of each row
        for (var row = 0; row < this.imageData.length; row++) {
            for (var col = 0; col < this.imageData[row].length; col++) {
                double pixel = this.getPixel(col, row); //recieve brightness value at current row
                totalBrightness += pixel; //add to totalbrightness
                pixelCount++; //count pixels
            }
        }
        double averageB = totalBrightness / pixelCount;

        return averageB;
    }

    /**
     * Return a new GrayScale image where the average new average brightness is 127
     * To do this, uniformly scale each pixel (ie, multiply each imageData entry by the same value)
     * Due to rounding, the new average brightness will not be 127 exactly, but should be very close
     * The original image should not be modified
     *
     * @return a GrayScale image with pixel data uniformly rescaled so that its averageBrightness() is 127
     */

    //AIDEN
    public GrayscaleImage normalized() {

        // GrayscaleImage pic = new GrayscaleImage(new double[][]{{1, 1},{2,2}});

        double bright = this.averageBrightness();
        if(bright == 0){
            //bright = 1;
            throw new IllegalArgumentException("image is black");
        }

        double scale = 127 / bright;
        double[][] pic;
        pic = new double[this.imageData.length][this.imageData[0].length];
        GrayscaleImage newImage = new GrayscaleImage(pic);

        for (var row = 0; row < this.imageData.length; row++) {
            for (var col = 0; col < this.imageData[0].length; col++) {
                //getting brightness of each pixel in the image
                double pixel = this.getPixel(col, row);
                //scaling the pixel brightness by multiplying by the scale factor
                double normValue = pixel * scale;
                newImage.imageData[row][col] = normValue;
            }
        }
        return newImage;
    }


    /**
     * Returns a new grayscale image that has been "mirrored" across the y-axis
     * In other words, each row of the image should be reversed
     * The original image should be unchanged
     *
     * @return a new GrayscaleImage that is a mirrored version of the this
     */
    public GrayscaleImage mirrored() {

        //new 2D array to store the mirrored pixel values
        //setting it as same as this that calls the method for now
        double[][] mirrorData = new double[this.imageData.length][this.imageData[0].length];

        for (var row = 0; row < this.imageData.length; row++) {
            for (var col = 0; col < this.imageData[row].length; col++) {
                //for each pixel in the original image, assigns to mirrorData
                //finds new column index for that pixel
                //subtracts current col index from total number of columns in the original image
                //then subtracts one more for the mirror effect, reflects the pixel horizontally
                mirrorData[row][col] = (this.imageData[row][this.imageData[0].length - 1 - col]);
            }
        }
        GrayscaleImage newImage = new GrayscaleImage(mirrorData);

        return newImage;
    }

    /**
     * Returns a new GrayscaleImage of size width x height, containing the part of `this`
     * from startRow -> startRow + height, startCol -> startCol + width
     * The original image should be unmodified
     *
     * @param startRow
     * @param startCol
     * @param width
     * @param height
     * @return A new GrayscaleImage containing the sub-image in the specified rectangle
     * @throws IllegalArgumentException if the specified rectangle goes outside the bounds of the original image
     */
    public GrayscaleImage cropped(int startRow, int startCol, int width, int height) {

        //startRow - row index where cropping begins
        //startCol - column index where cropping begins
        //width - width of the cropped region
        //height - height of cropped region

        //if statements to ensure entered parameters are not outside the image ot be cropped
        if (startRow < 0 || startCol < 0 || startRow + height > this.imageData.length || height < 0 || width < 0
                || startCol + width > this.imageData[0].length) {
            throw new IllegalArgumentException("out of bounds");
        }

        double[][] cropData = new double[height][width];
        //stores new 2D array with pixel values entered

        //the height entered is the number of pixels the user wants
        //it loops over the original image and adds rows for how long the height is
        //iterate through each row nad column within the specified width and height of the cropped region
        for (var row = 0; row < height; row++) {
            for (var col = 0; col < width; col++) {
                //saving the startRow + the amount of rows and the startCol + the amount of columns in new image
                //calcuate corresponding position in the og image
                cropData[row][col] = this.imageData[startRow + row][startCol + col];
            }
        }
        GrayscaleImage croppedImage = new GrayscaleImage(cropData);
        return croppedImage;
    }

    /**
     * Returns a new "centered" square image (new width == new height)
     * For example, if the width is 20 pixels greater than the height,
     * this should return a height x height image, with 10 pixels removed from the left and right
     * edges of the image
     * If the number of pixels to be removed is odd, remove 1 fewer pixel from the left or top part
     * (note this convention should be SIMPLER/EASIER to implement than the alternative)
     * The original image should not be changed
     *
     * @return a new, square, GrayscaleImage
     */
    public GrayscaleImage squarified() {
        int numCols = this.imageData[0].length; //columns of original image
        int numRows = this.imageData.length; //rows of original image
        GrayscaleImage sqaureImage;

        if (numRows == numCols){
            return this;
            //already square
        }

        else {
            //Math.min returns min value between two nums
            int minSide = Math.min(numCols, numRows);
            double[][] squareData = new double[minSide][minSide];
            sqaureImage = new GrayscaleImage(squareData);

            //calculating the number of pixels to remove from top bottom, left and right
            //ensures the square region is centered within the og image
            int removeRows = (numRows - minSide) / 2;
            int removeCols = (numCols - minSide) / 2;

            //for loop iterate through the minside region of og image
            for (var row = 0; row < minSide; row++) {
                for (var col = 0; col < minSide; col++) {
                    sqaureImage.imageData[row][col] = this.imageData[row + removeRows][col + removeCols];

                }
            }
            return sqaureImage;
        }
    }
}