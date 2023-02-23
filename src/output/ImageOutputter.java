package output;


import utilities.Image;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ImageOutputter {
    void outputImage(Image image, String filename) throws IOException;
}
