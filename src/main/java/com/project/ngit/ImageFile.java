package com.project.ngit;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageFile extends GeneralFile{
    private Dimension dimensions;

    public ImageFile(String imagePath) {
        if (!Files.exists(Paths.get(imagePath))) {
            System.out.println("No such path");
            this.dimensions = new Dimension(-1, -1);
            return;
        }

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));

            if (image == null) {
                throw new IOException("Failed to read image file: " + imagePath);
            }

            int width = image.getWidth();
            int height = image.getHeight();

            this.dimensions = new Dimension(width, height);
        } catch (IOException e) {
            e.printStackTrace();
            this.dimensions = new Dimension(-1, -1);
        }
    }

    public Dimension getDimensions() {
        return this.dimensions;
    }

    public static void main(String[] args) {
        ImageFile imageFile = new ImageFile("C:\\Users\\Miguel\\Downloads\\1.txt");
        Dimension dimension = imageFile.getDimensions();
        System.out.println(dimension.width + " x " + dimension.height);
    }
}
