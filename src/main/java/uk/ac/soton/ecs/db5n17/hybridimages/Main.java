package uk.ac.soton.ecs.db5n17.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static uk.ac.soton.ecs.db5n17.hybridimages.MyHybridImages.makeHybrid;

public class Main
{
    public static void main(String[] args) throws IOException
    {
//        float[][] edgeKernel = new float[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
//        float[][] average2Kernel = new float[][]{{(float)1/9, (float)1/9}, {(float)1/9, (float)1/9}};
//        float[][] average3Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}};
//        float[][] average53Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9, (float)1/9, (float)1/9}};
//        float[][] average5Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}};
//        float[][] average7Kernel = new float[][]{{(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}, {(float)1/9, (float)1/9, (float)1/9}};
//
//        MBFImage image = ImageUtilities.readMBF(new URL("https://cdn.discordapp.com/attachments/706994621388947506/768591382427467797/Screenshot_4505.png"));
//        MBFImage clone = image.clone();
//
//        image.processInplace(new MyConvolution(average2Kernel));
//        clone.processInplace(new MyConvolution(average3Kernel));
//
//        DisplayUtilities.display(image);
//        DisplayUtilities.display(clone);

//        float[][] k = {{1,2,3},{4,1,6},{1,8,2},{5,4,3},{6,7,8}};
//        float[][] image = {{1,2,1,1},{1,1,2,1},{1,2,2,1}};
//        FImage fimage = new FImage(image);
//        System.out.println("Before image");
//        print(image);
//        MyConvolution mc = new MyConvolution(k);
//        mc.processImage(fimage);
//
//
//        float[][] afterImage = fimage.pixels;
//        System.out.println();
//        System.out.println("After image");
//        print(afterImage);

        // Create the custom hybrid image:
        MBFImage image = makeHybrid(ImageUtilities.readMBF(new File("resources/trump.bmp")), 2.0f, ImageUtilities.readMBF(new File("resources/count.bmp")), 1.25f);
        DisplayUtilities.display(image);

        ImageUtilities.write(image, new File("C://Users/Dan/Desktop/stop_the_count.jpeg"));
    }

//    public static void print(float[][] m)
//    {
//        for(float[] row : m)
//            System.out.println(Arrays.toString(row));
//    }


}
