import assign01.GrayscaleImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {

        GrayscaleImage image = new GrayscaleImage(new URL("https://www.utah.edu/_resources/images/visitors/banner-visitor.jpg"));

        File test = new File("test.png");
        image.normalized().savePNG(test);


    }
}