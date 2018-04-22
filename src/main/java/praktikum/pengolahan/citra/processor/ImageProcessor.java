package praktikum.pengolahan.citra.processor;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import praktikum.pengolahan.citra.utils.ColorOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageProcessor {

  public static int[][][] imageToColors(File imageFile) {
    Image image = null;
    try {
      image = new Image(new FileInputStream(imageFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    int[][][] colors = new int[(int) image.getHeight()][(int) image.getWidth()][4];
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        Color color = image.getPixelReader().getColor(x, y);
        color.grayscale();
        colors[y][x] = new int[]{
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255),
            (int) (color.getOpacity())};
      }
    }
    return colors;
  }

  public static Image colorsToImage(int[][][] colors) {
    int width = colors[0].length;
    int height = colors.length;
    WritableImage writableImage = new WritableImage(width, height);
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        Color color = new Color(
            ColorOperations.getRed(colors, row, column) / 255d,
            ColorOperations.getGreen(colors, row, column) / 255d,
            ColorOperations.getBlue(colors, row, column) / 255d,
            ColorOperations.getAlpha(colors, row, column));
        pixelWriter.setColor(column, row, color);
      }
    }
    return writableImage;
  }
}