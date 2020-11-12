package uk.ac.soton.ecs.db5n17.hybridimages;

import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;

public class MyHybridImages
{
    /**
     * Compute a hybrid image combining low-pass and high-pass filtered images
     *
     * @param lowImage
     *            The image to which apply the low pass filter
     * @param lowSigma
     *            The standard deviation of the low-pass filter
     * @param highImage
     *            The image to which apply the high pass filter
     * @param highSigma
     *            The standard deviation of the low-pass component of computing the
     *            high-pass filtered image
     * @return The computed hybrid image
     */
    public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma)
    {
        // Calculate the size of the kernel, making it odd if even.
        int size = (int) (8.0f * lowSigma + 1.0f);

        if (size % 2 == 0)
            size++;

        // Create a kernel based on the provided low sigma (standard deviation) value.
        float[][] kernel = Gaussian2D.createKernelImage(size, lowSigma).pixels;

        // Process the low image with template convolution, utilising the produced kernel.
        lowImage.processInplace(new MyConvolution(kernel));

        // Calculate the size of the new kernel for the high sigma value, again making it odd if even.
        size = (int) (8.0f * highSigma + 1.0f);

        if (size % 2 == 0)
            size++;

        // Replace the old kernel with a new one based on the high sigma value.
        kernel = Gaussian2D.createKernelImage(size, highSigma).pixels;

        // Create a clone and perform template convolution.
        MBFImage clone = highImage.clone();

        clone.processInplace(new MyConvolution(kernel));

        // Subtract the low pass filtered image from the original to form the high pass filtered image we desire.
        highImage.subtractInplace(clone);

        // Add the low and high pass images together to form the hybrid image.
        lowImage.addInplace(highImage);

        return lowImage;
    }
}
