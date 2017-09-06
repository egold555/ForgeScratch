package org.golde.java.scratchforge.helpers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * This class can be used to convert images. Note that all the methods of this
 * class are declared as static. Supports the following image operations
 * <ul>
 * <li>Convert between Image and BufferedImage</li>
 * <li>Split images</li>
 * <li>Resize image</li>
 * <li>Create tiled image</li>
 * <li>Create empty transparent image</li>
 * <li>Create a colored image</li>
 * <li>Flip image horizontally</li>
 * <li>Flip image vertically</li>
 * <li>Clone image</li>
 * <li>Rotate image</li>
 * </ul>
 * 
 * @author Sri Harsha Chilakapati
 * 
 * 
 * Modified by Eric
 */
public abstract class ImageTool {

    private ImageTool() {
    }

    /**
     * Converts a given Image into a BufferedImage
     * 
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        // Return the buffered image
        return bimage;
    }

    /**
     * Splits an image into a number of rows and columns
     * 
     * @param img The image to be split
     * @param rows The number of rows
     * @param cols The number of columns
     * @return The array of split images in the vertical order
     */
    public static BufferedImage[] splitImage(Image img, int rows, int cols){
        // Determine the width of each part
        int w = img.getWidth(null) / cols;
        // Determine the height of each part
        int h = img.getHeight(null) / rows;
        // Determine the number of BufferedImages to be created
        int num = rows * cols;
        // The count of images we'll use in looping
        int count = 0;
        // Create the BufferedImage array
        BufferedImage[] imgs = new BufferedImage[num];
        // Start looping and creating images [splitting]
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                // The BITMASK type allows us to use bmp images with coloured
                // text and any background
                imgs[count] = new BufferedImage(w, h, BufferedImage.BITMASK);
                // Get the Graphics2D object of the split part of the image
                Graphics2D g = imgs[count++].createGraphics();
                // Draw only the required portion of the main image on to the
                // split image
                g.drawImage(img, 0, 0, w, h, w * y, h * x, w * y + w, h * x + h, null);
                // Now Dispose the Graphics2D class
                g.dispose();
            }
        }
        return imgs;
    }

    /**
     * Converts a given BufferedImage into an Image
     * 
     * @param bimage The BufferedImage to be converted
     * @return The converted Image
     */
    public static Image toImage(BufferedImage bimage){
        // Casting is enough to convert from BufferedImage to Image
        Image img = (Image) bimage;
        return img;
    }

    /**
     * Resizes a given image to given width and height
     * 
     * @param img The image to be resized
     * @param width The new width
     * @param height The new height
     * @return The resized image
     */
    public static Image resize(Image img, int width, int height){
        // Create a null image
        Image image = null;
        // Resize into a BufferedImage
        BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimg.createGraphics();
        bGr.drawImage(img, 0, 0, width, height, null);
        bGr.dispose();
        // Convert to Image and return it
        image = toImage(bimg);
        return image;
    }

    /**
     * Creates a tiled image with an image upto given width and height
     * 
     * @param img The source image
     * @param width The width of image to be created
     * @param height The height of the image to be created
     * @return The created image
     */
    public static Image createTiledImage(Image img, int width, int height){
        // Create a null image
        Image image = null;
        BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // The width and height of the given image
        int imageWidth = img.getWidth(null);
        int imageHeight = img.getHeight(null);
        // Start the counting
        int numX = (width / imageWidth) + 2;
        int numY = (height / imageHeight) + 2;
        // Create the graphics context
        Graphics2D bGr = bimg.createGraphics();
        for (int y = 0; y < numY; y++) {
            for (int x = 0; x < numX; x++) {
                bGr.drawImage(img, x * imageWidth, y * imageHeight, null);
            }
        }
        // Convert and return the image
        image = toImage(bimg);
        return image;
    }

    /**
     * Creates an empty image with transparency
     * 
     * @param width The width of required image
     * @param height The height of required image
     * @return The created image
     */
    public static Image getEmptyImage(int width, int height){
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return toImage(img);
    }

    /**
     * Creates a colored image with a specified color
     * 
     * @param color The color to be filled with
     * @param width The width of the required image
     * @param height The height of the required image
     * @return The created image
     */
    public static Image getColoredImage(Color color, int width, int height){
        BufferedImage img = toBufferedImage(getEmptyImage(width, height));
        Graphics2D g = img.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.dispose();
        return img;
    }

    /**
     * Flips an image horizontally. (Mirrors it)
     * 
     * @param img The source image
     * @return The image after flip
     */
    public static Image flipImageHorizontally(Image img){
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage bimg = toBufferedImage(getEmptyImage(w, h));
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return toImage(bimg);
    }

    /**
     * Flips an image vertically. (Mirrors it)
     * 
     * @param img The source image
     * @return The image after flip
     */
    public static Image flipImageVertically(Image img){
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage bimg = toBufferedImage(getEmptyImage(w, h));
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return toImage(bimg);
    }

    /**
     * Clones an image. After cloning, a copy of the image is returned.
     * 
     * @param img The image to be cloned
     * @return The clone of the given image
     */
    public static Image clone(Image img){
        BufferedImage bimg = toBufferedImage(getEmptyImage(img.getWidth(null), img.getHeight(null)));
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return toImage(bimg);
    }

    /**
     * Rotates an image. Actually rotates a new copy of the image.
     * 
     * @param img The image to be rotated
     * @param angle The angle in degrees
     * @return The rotated image
     */
    public static Image rotate(Image img, double angle){
        double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = img.getWidth(null), h = img.getHeight(null);
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h
                * cos + w * sin);
        BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
        Graphics2D g = bimg.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(Math.toRadians(angle), w / 2, h / 2);
        g.drawRenderedImage(toBufferedImage(img), null);
        g.dispose();
        return toImage(bimg);
    }
    
    /**
     * Makes a color in an Image transparent.
     * @param img Input image
     * @param color The color to math the image in
     * @return The new image
     */
    public static Image mask(Image img, Color color){
        BufferedImage bimg = toBufferedImage(getEmptyImage(img.getWidth(null), img.getHeight(null)));
        Graphics2D g = bimg.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        for (int y=0; y<bimg.getHeight(); y++){
            for (int x=0; x<bimg.getWidth(); x++){
                int col = bimg.getRGB(x, y);
                if (col==color.getRGB()){
                    bimg.setRGB(x, y, col & 0x00ffffff);
                }
            }
        }
        return toImage(bimg);
    }
    
}
