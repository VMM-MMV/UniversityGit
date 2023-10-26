package com.project.ngit;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageFile extends GeneralFile{
    public static Dimension getImageProportions(String imagePath) {
        if (!Files.exists(Paths.get(imagePath))) {
            System.out.println("No such path");
            return new Dimension(-1,-1);
        }

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));

            if (image == null) {
                throw new IOException("Failed to read image file: " + imagePath);
            }

            int width = image.getWidth();
            int height = image.getHeight();

            return new Dimension(width, height);
        } catch (IOException e) {
            e.printStackTrace();
            return new Dimension(-1, -1);
        }
    }

    public static void main(String[] args) {
        Dimension dimension = getImageProportions("C:\\Users\\Miguel\\Downloads\\1.txt");
        System.out.println(dimension.width + " x " + dimension.height);
    }
}
