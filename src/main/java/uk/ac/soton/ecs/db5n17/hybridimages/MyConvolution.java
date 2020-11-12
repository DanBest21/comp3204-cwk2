package uk.ac.soton.ecs.db5n17.hybridimages;

import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage>
{
    private float[][] kernel;

    public MyConvolution(float[][] kernel)
    {
        //note that like the image pixels kernel is indexed by [row][column]
        this.kernel = kernel;
    }

    @Override
    public void processImage(FImage image)
    {
        // convolve image with kernel and store result back in image
        //
        // hint: use FImage#internalAssign(FImage) to set the contents
        // of your temporary buffer image to the image.

        FImage buffer = image.clone();
        float[][] pixels = buffer.pixels;

        for (int i = 0; i < pixels.length; i++)
        {
            for (int j = 0; j < pixels[i].length; j++)
            {
                pixels[i][j] = calculateValue(i, j, image.pixels);
            }
        }

        image.internalAssign(buffer);
    }

    private float calculateValue(int x, int y, float[][] pixels)
    {
        int xOffset = x - (kernel.length / 2);
        int yOffset = y - (kernel[0].length / 2);

        float accum = 0;

        for (int i = 0; i < kernel.length; i++)
        {
            for (int j = 0; j < kernel[i].length; j++)
            {
                int xPos = xOffset + i;
                int yPos = yOffset + j;

                if (xPos < 0)
                    xPos = Math.abs(xPos);
                if (yPos < 0)
                    yPos = Math.abs(yPos);
                if (xPos >= pixels.length)
                    xPos = xOffset - i;
                if (yPos >= pixels[i].length)
                    yPos = yOffset - j;

                accum += kernel[kernel.length - i - 1][kernel[i].length - j - 1] * pixels[xPos][yPos];
            }
        }

        return accum;
    }

    private float calculateValue(float[][] snapshot)
    {
        float accum = 0;

        for (int i = 0; i < snapshot.length; i++)
        {
            for (int j = 0; j < snapshot[i].length; j++)
            {
                accum += snapshot[i][j] * kernel[kernel.length - i - 1][kernel[i].length - j - 1];
            }
        }

        return accum;
    }
}