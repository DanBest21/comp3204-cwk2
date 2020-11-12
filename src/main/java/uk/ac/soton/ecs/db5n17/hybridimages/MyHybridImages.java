package uk.ac.soton.ecs.db5n17.hybridimages;

import java.io.File;
import java.io.IOException;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;

public class MyHybridImages
{
    /**
     * Compute a hybrid image combining low-pass and high-pass filtered images
     *
     * @param lowImage
     *            the image to which apply the low pass filter
     * @param lowSigma
     *            the standard deviation of the low-pass filter
     * @param highImage
     *            the image to which apply the high pass filter
     * @param highSigma
     *            the standard deviation of the low-pass component of computing the
     *            high-pass filtered image
     * @return the computed hybrid image
     */
    public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma)
    {
        //implement your hybrid images functionality here.
        //Your submitted code must contain this method, but you can add
        //additional static methods or implement the functionality through
        //instance methods on the `MyHybridImages` class of which you can create
        //an instance of here if you so wish.
        //Note that the input images are expected to have the same size, and the output
        //image will also have the same height & width as the inputs.

        int size = (int) (8.0f * lowSigma + 1.0f);

        if (size % 2 == 0)
            size++;

        float[][] kernel = Gaussian2D.createKernelImage(size, lowSigma).pixels;

        lowImage.processInplace(new MyConvolution(kernel));

        size = (int) (8.0f * highSigma + 1.0f);

        if (size % 2 == 0)
            size++;

        kernel = Gaussian2D.createKernelImage(size, highSigma).pixels;

        MBFImage clone = highImage.clone();

        clone.processInplace(new MyConvolution(kernel));

        highImage.subtractInplace(clone);

        lowImage.addInplace(highImage);

        return lowImage;
    }

    public static void main(String[] args) throws IOException
    {
        // Cat Dog:
        // DisplayUtilities.display(makeHybrid(ImageUtilities.readMBF(new File("resources/dog.bmp")), 4.0f, ImageUtilities.readMBF(new File("resources/cat.bmp")), 6.0f));
        // Motorbicycle:
        // DisplayUtilities.display(makeHybrid(ImageUtilities.readMBF(new File("resources/motorcycle.bmp")), 4.0f, ImageUtilities.readMBF(new File("resources/bicycle.bmp")), 2.0f));
        // Marilyn Einstein:
        // DisplayUtilities.display(makeHybrid(ImageUtilities.readMBF(new File("resources/marilyn.bmp")), 4.0f, ImageUtilities.readMBF(new File("resources/einstein.bmp")), 2.0f));
        // Is it a bird? Is it a plane?
        // DisplayUtilities.display(makeHybrid(ImageUtilities.readMBF(new File("resources/plane.bmp")), 4.0f, ImageUtilities.readMBF(new File("resources/bird.bmp")), 2.0f));
        // Finding Nemo
        // DisplayUtilities.display(makeHybrid(ImageUtilities.readMBF(new File("resources/submarine.bmp")), 4.0f, ImageUtilities.readMBF(new File("resources/fish.bmp")), 2.0f));

        // Attempt 1: Jacob Rees-Hogg:
        // DisplayUtilities.display(makeHybrid(ImageUtilities.readMBF(new File("resources/warthog.bmp")), 0.2f, ImageUtilities.readMBF(new File("resources/jacob.bmp")), 3.0f));
        // Attempt 2: Stop the Count!:
        MBFImage image = makeHybrid(ImageUtilities.readMBF(new File("resources/trump.bmp")), 2.0f, ImageUtilities.readMBF(new File("resources/count_count.bmp")), 1.25f);
        DisplayUtilities.display(image);

        ImageUtilities.write(image, new File("C://Users/Dan/Desktop/stop_the_count.jpeg"));
        // Attempt 3: Joe Bidet:
        // Attempt 4: Lightsabers:
        // Maybe more?
    }
}
