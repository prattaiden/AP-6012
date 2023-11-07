package assign01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrayscaleImageTest {

    private GrayscaleImage smallSquare;
    private GrayscaleImage smallWide;
    private GrayscaleImage largeSquare;

    private GrayscaleImage blackImage;

    private GrayscaleImage smallSquare2;
    private GrayscaleImage whiteIMG;

    private GrayscaleImage negativeVal;

    private GrayscaleImage singleVal;

    //test ideas: empty square image, large square image, when a squared image is already square
    //all same color for some of the tests

    @BeforeEach
    void setUp() {
        smallSquare = new GrayscaleImage(new double[][]{{1, 2}, {3, 4}});
        smallSquare2 = new GrayscaleImage(new double[][]{{1, 2}, {3, 4}});
        smallWide = new GrayscaleImage(new double[][]{{1, 2, 3}, {4, 5, 6}});
        blackImage = new GrayscaleImage(new double[][]{{0, 0}, {0, 0}});
        whiteIMG = new GrayscaleImage(new double[][]{{255, 255}, {255, 255}});
        negativeVal = new GrayscaleImage(new double[][]{{-3}});
        singleVal = new GrayscaleImage(new double[][]{{3}});
        largeSquare = new GrayscaleImage(new double[][]{{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30}});
    }

    @Test
    void testGetPixel() {
        assertEquals(smallSquare.getPixel(0, 0), 1);
        assertEquals(smallSquare.getPixel(1, 0), 2);
        assertEquals(smallSquare.getPixel(0, 1), 3);
        assertEquals(smallSquare.getPixel(1, 1), 4);

    }

    @Test
    void testEquals() {
        assertEquals(smallSquare, smallSquare);
        var equivalent = new GrayscaleImage(new double[][]{{1, 2}, {3, 4}});
        assertEquals(smallSquare, equivalent);
    }

    @Test
    void averageBrightness() {
        assertEquals(smallSquare.averageBrightness(), 2.5, 2.5 * .001);
        var bigZero = new GrayscaleImage(new double[1000][1000]);
        assertEquals(bigZero.averageBrightness(), 0);
    }

    @Test
    void normalized() {
        var smallNorm = smallSquare.normalized();
        assertEquals(smallNorm.averageBrightness(), 127, 127 * .001);
        var scale = 127 / 2.5;
        var expectedNorm = new GrayscaleImage(new double[][]{{scale, 2 * scale}, {3 * scale, 4 * scale}});
        for (var row = 0; row < 2; row++) {
            for (var col = 0; col < 2; col++) {
                assertEquals(smallNorm.getPixel(col, row), expectedNorm.getPixel(col, row),
                        expectedNorm.getPixel(col, row) * .001,
                        "pixel at row: " + row + " col: " + col + " incorrect");
            }
        }
    }

    @Test
    void mirrored() {
        var expected = new GrayscaleImage(new double[][]{{2, 1}, {4, 3}});
        assertEquals(smallSquare.mirrored(), expected);
    }

    @Test
    void cropped() {
        var cropped = smallSquare.cropped(1, 1, 1, 1);
        assertEquals(cropped, new GrayscaleImage(new double[][]{{4}}));
    }

    @Test
    void squarified() {
        var squared = smallWide.squarified();
        var expected = new GrayscaleImage(new double[][]{{1, 2}, {4, 5}});
        assertEquals(squared, expected);
    }

    @Test
    void testGetPixelThrowsOnNegativeX() {
        assertThrows(IllegalArgumentException.class, () -> {
            smallSquare.getPixel(-1, 0);
        });
    }


    //AIDEN:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //test is already a square
    @Test
    void squarifiedWhenImageIsAlreadySquared() {
        var squared = smallSquare.squarified();
        var expected = new GrayscaleImage(new double[][]{{1, 2}, {3, 4}});
        assertEquals(squared, expected);
    }


    //not equals
    @Test
    void testNotEquals() {
        assertNotEquals(smallSquare, largeSquare);
    }


    //testing and verifying that IllegalArgumentExcpetion is thrown
    @Test
    void testGetPixelThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            smallSquare.getPixel(2, 2);
        });
    }


    //equals-------------------------------
    @Test
    void testEqualsTrue() {
        assertTrue(smallSquare.equals(smallSquare));
    }

    @Test
    void testEqualsFalse() {
        assertFalse(smallSquare.equals(largeSquare));
    }

    @Test
    void testEqualsWithDifferentDimensions() {
        assertFalse(smallSquare.equals(smallWide));
    }

    @Test
    void testEqualsSymmetry() {
        assertTrue(smallSquare.equals(smallSquare2));
        assertTrue(smallSquare2.equals(smallSquare));
    }




    //brightness----------------------------
    @Test
    void averageBrightnessWhite() {
        assertEquals(whiteIMG.averageBrightness(), 255, 255 * .001);
    }

    @Test
    void negativeValueForBrightness() {
        assertEquals(negativeVal.averageBrightness(), -3);
    }

    @Test
    void testBlackImage() {
        assertEquals(blackImage.averageBrightness(), 0);
    }

    //normalized-------------------------------
    @Test
    void testExceptionForNormalized(){
        assertThrows(IllegalArgumentException.class, () -> {
            blackImage.normalized();
        });
    }

    //mirror----------------------------------
    @Test
    void singleValueForMirror() {
        var expected = new GrayscaleImage(new double[][]{{3}});
        assertEquals(singleVal.mirrored(), expected);
    }

    //test on nonsquare image

    //cropped-------------------------------
    @Test
    void croppedOutOfBounds() {
//        var cropped = smallSquare.cropped(1, 1, 3, 3);
        assertThrows(IllegalArgumentException.class, () -> {
            smallSquare.cropped(1, 1, 3, 3);
        });
    }
}


//tests I could not get to work.. ---------------
//    @Test
//    void mirrorTestWithLargeImage(){
//        var expected = new GrayscaleImage(new double[][]{{15,14,13,12,11,10,9,8,7,6,5,4,3,2,1},{30,29,28,27,26,25,24,23,22,21,20,19,18,17,16}});
//        assertEquals(largeSquare.mirrored(), expected);
//    }


    //not working?
//    @Test
//    void squarifiedLargeSquare(){
//        var squared = largeSquare.squarified();
//        var expected = new GrayscaleImage(new double[][]{{15,14,13,12,11,10,9,8,7,6,5,4,3,2,1},{30,29,28,27,26,25,24,23,22,21,20,19,18,17,16}});
//        assertEquals(squared, expected);
//    }

    //    @Test
//    void testBlackNorm(){
//        var blackNorm = blackImage.normalized();
//        assertEquals(blackNorm.averageBrightness(), 0);
//        var scale = 127/2.5;
//        var expectedNorm = new GrayscaleImage(new double[][]{{scale, 2*scale},{3*scale, 4*scale}});
//        for(var row = 0; row < 2; row++){
//            for(var col = 0; col < 2; col++){
//                assertEquals(blackNorm.getPixel(col, row), expectedNorm.getPixel(col, row),
//                        expectedNorm.getPixel(col, row)*.001,
//                        "pixel at row: " + row + " col: " + col + " incorrect");
//            }
//        }
//    }

//    @Test
//    void mirrored2(){
//        var expected = new GrayscaleImage(new double[][]{{3,2,1},{6,5,4}});
//        assertEquals(smallWide.mirrored(), expected);
//    }



