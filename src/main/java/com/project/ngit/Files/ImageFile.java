package com.project.ngit.Files;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageFile extends GeneralFile{
    int width;
    int height;

    public ImageFile(String imagePath) {
        super(Path.of(imagePath));
        Path path = Path.of(imagePath);
        if (!Files.exists(Paths.get(imagePath))) {
            System.out.println("No such path");
            return;
        }

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));

            if (image == null) {
                throw new IOException("Failed to read image file: " + imagePath);
            }

            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
