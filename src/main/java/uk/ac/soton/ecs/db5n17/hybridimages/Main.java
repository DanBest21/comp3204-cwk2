package uk.ac.soton.ecs.db5n17.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.IOException;
import java.net.URL;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        float[][] edgeKernel = new float[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        float[][] average2Kernel = new float[][]{{(float)1/9, (float)1/9}, {(float)1/9, (float)1/9}};
        float[][] average3Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}};
        float[][] average53Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9, (float)1/9, (float)1/9}};
        float[][] average5Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}};
        float[][] average7Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}};

        MBFImage image = ImageUtilities.readMBF(new URL("https://cdn.discordapp.com/attachments/706994621388947506/768591382427467797/Screenshot_4505.png"));
        MBFImage clone = image.clone();

        image.processInplace(new MyConvolution(average2Kernel));
        clone.processInplace(new MyConvolution(average3Kernel));

        DisplayUtilities.display(image);
        DisplayUtilities.display(clone);
    }
}
