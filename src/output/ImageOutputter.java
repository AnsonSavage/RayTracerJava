package output;


import utilities.image.Image;

import java.io.IOException;

public interface ImageOutputter {
    void outputImage(Image image, String filename) throws IOException;
}
