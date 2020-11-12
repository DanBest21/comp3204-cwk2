package uk.ac.soton.ecs.db5n17.hybridimages;

import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage>
{
    private final float[][] kernel;

    public MyConvolution(float[][] kernel)
    {
        //note that like the image pixels kernel is indexed by [row][column]
        this.kernel = kernel;
    }

    /**
     * Process an image via template convolution
     *
     * @param image
     *          The image to be processed
     */
    @Override
    public void processImage(FImage image)
    {
        // Create a clone that can be used as the buffer image that is modified.
        FImage buffer = image.clone();
        float[][] pixels = buffer.pixels;

        // For each pixel in the buffer, calculate the new value using the calculateValue method.
        for (int i = 0; i < pixels.length; i++)
        {
            for (int j = 0; j < pixels[i].length; j++)
            {
                pixels[i][j] = calculateValue(i, j, image.pixels);
            }
        }

        // Assign the processed image in the buffer back to the original image.
        image.internalAssign(buffer);
    }

    /**
     * Calculate the value for a given Cartesian position in the image
     *
     * @param x
     *          The horizontal position of the pixel in the image
     * @param y
     *          The vertical position of the pixel in the image
     * @param pixels
     *          The original image in the form of its pixels, represented by a two-dimensional array
     * @return The newly calculated value of the pixel matching the passed x and y co-ordinates
     */
    private float calculateValue(int x, int y, float[][] pixels)
    {
        // Offset the x and y positions by half of the kernel length and height dimensions respectively.
        int xOffset = x - (kernel.length / 2);
        int yOffset = y - (kernel[0].length / 2);

        float accum = 0;

        // For each pixel in the kernel:
        for (int i = 0; i < kernel.length; i++)
        {
            for (int j = 0; j < kernel[i].length; j++)
            {
                // Add the respective offset to determine which pixel we want to apply this specific kernel modifier to in the image.
                int xPos = xOffset + i;
                int yPos = yOffset + j;

                // If the calculated position is negative or positive, use a mirroring technique to instead grab the equivalent
                // pixel at the corresponding x and/or y position in the opposite direction from the central (x, y) pixel.
                if (xPos < 0)
                    xPos = Math.abs(xPos);
                if (yPos < 0)
                    yPos = Math.abs(yPos);
                if (xPos >= pixels.length)
                    xPos = xOffset - i;
                if (yPos >= pixels[i].length)
                    yPos = yOffset - j;

                // Add the value of the product of the kernel operator and appropriate pixel in the original image to an accumulator.
                accum += kernel[kernel.length - i - 1][kernel[i].length - j - 1] * pixels[xPos][yPos];
            }
        }

        return accum;
    }
}