package Util;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaverUtil {

    public static void savePNG(Pane canvas) throws Exception {
        File file = FileChooserUtil.openFileChooser("PNG Files", "*.png");
        if (file != null) {
            try {
                WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                PixelReader pixelReader = image.getPixelReader();

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int argb = pixelReader.getArgb(x, y);
                        bufferedImage.setRGB(x, y, argb);
                    }
                }

                boolean isSaved = ImageIO.write(bufferedImage, "PNG", file);
                if (!isSaved) {
                    throw new Exception("Failed to save PNG image.");
                }
            } catch (Exception e) {
                throw new Exception("Error saving the PNG image: " + e.getMessage());
            }
        } else {
            throw new Exception("No file selected for PNG.");
        }
    }

    // Method to save the screenshot as JPEG
//    public static void saveJPEG(Pane canvas) throws ImageSaveException {
//        File file = FileChooserUtil.openFileChooser("JPEG Files", "*.jpg");
//        if (file != null) {
//            try {
//                WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
//                BufferedImage bufferedImage = ImageConverter.convertToBufferedImage(image);
//
//                boolean isSaved = ImageIO.write(bufferedImage, "JPEG", file);
//                if (!isSaved) {
//                    throw new ImageSaveException("Failed to save JPEG image.");
//                }
//            } catch (IOException e) {
//                throw new ImageSaveException("Error saving the JPEG image: " + e.getMessage());
//            }
//        } else {
//            throw new ImageSaveException("No file selected for JPEG.");
//        }
//    }
}
