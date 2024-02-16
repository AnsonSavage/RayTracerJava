package output;

import utilities.Image;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PPMOutputterTest {
    @org.junit.jupiter.api.Test
    void ensureImageOutputPNG() {
        Image image;
        try {
            image = new Image("/home/anson/Desktop/spider_man.png");
            PPMOutputter outputter = new PPMOutputter();
            outputter.outputImage(image, "/home/anson/Desktop/spider_man.ppm");
        } catch (IOException e) {
            fail("Image not found");
        }
    }

    @org.junit.jupiter.api.Test
    void ensureImageOutputJPEG() {
        Image image;
        try {
            image = new Image("/home/anson/Desktop/anson.jpeg");
            PPMOutputter outputter = new PPMOutputter();
            outputter.outputImage(image, "/home/anson/Desktop/anson.ppm");
        } catch (IOException e) {
            fail("Image not found");
        }
    }
}