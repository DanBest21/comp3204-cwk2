package uk.ac.soton.ecs.db5n17.hybridimages;

import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

import java.lang.reflect.Array;

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
     * @param y
     *          The vertical position of the pixel in the image
     * @param x
     *          The horizontal position of the pixel in the image
     * @param pixels
     *          The original image in the form of its pixels, represented by a two-dimensional array
     * @return The newly calculated value of the pixel matching the passed x and y co-ordinates
     */
    private float calculateValue(int y, int x, float[][] pixels)
    {
        // Offset the x and y positions by half of the kernel length and height dimensions respectively.
        int yOffset = y - (kernel.length / 2);
        int xOffset = x - (kernel[0].length / 2);

        float accum = 0;

        // For each pixel in the kernel:
        for (int i = 0; i < kernel.length; i++)
        {
            for (int j = 0; j < kernel[i].length; j++)
            {
                // Add the respective offset to determine which pixel we want to apply this specific kernel modifier to in the image.
                int yPos = yOffset + i;
                int xPos = xOffset + j;

                // If the calculated position is negative or positive, use a mirroring technique to instead grab the equivalent
                // pixel at the corresponding x and/or y position in the opposite direction from the central (x, y) pixel.
                if (yPos < 0)
                    yPos = Math.abs(yPos);
                if (xPos < 0)
                    xPos = Math.abs(xPos);
                if (yPos >= pixels.length)
                    yPos = Math.abs(pixels.length - 1 - (yPos + 1 - pixels.length));
                if (xPos >= pixels[0].length)
                    xPos = Math.abs(pixels[0].length - 1 - (xPos + 1 - pixels[0].length));

                // Add the value of the product of the kernel operator and appropriate pixel in the original image to an accumulator.
                try
                {
                    accum += kernel[kernel.length - i - 1][kernel[i].length - j - 1] * pixels[yPos][xPos];
                }
                catch (ArrayIndexOutOfBoundsException ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        return accum;
    }
}