package baron.dino.animalxingqr;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import net.sf.javaml.core.Instance;

import org.apache.commons.imaging.color.ColorCieLab;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.common.BitMatrix;

public class AnimalXingUtils {
	
	/**
	 * Creates an bitmap from a supplied BitMatrix
	 * @param matrix
	 * @return
	 */
	public static Bitmap bitmapFromMatrix(BitMatrix matrix)
	{
		Bitmap bitmap = Bitmap.createBitmap(matrix.getWidth(), matrix.getHeight(), Bitmap.Config.ARGB_8888);
		drawMatrix(matrix, bitmap);
		return bitmap;
	}

	/**
	 * Draws a BitMatrix to a supplied Bitmap object
	 * @param matrix
	 * @param g
	 */
	public static void drawMatrix(BitMatrix matrix, Bitmap bitmap)
	{
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		
		// Draw pixels
		for(int i = 0; i<width; i++)
		{
			for(int j = 0; j<height; j++)
			{
				if(matrix.get(i, j)){
					bitmap.setPixel(i, j, Color.BLACK);
				} else {
					bitmap.setPixel(i, j, Color.WHITE);
				}
			}
		}
	}
	
	/**
	 * Averages a set of CieLab colours
	 * 
	 * @param instances
	 * @return
	 */
	public static ColorCieLab averageLabColor(List<Instance> instances)
	{
		BigDecimal sumL = BigDecimal.ZERO;
		BigDecimal sumA = BigDecimal.ZERO;
		BigDecimal sumB = BigDecimal.ZERO;
		
		for(Instance instance : instances)
		{
			sumL = sumL.add(new BigDecimal(instance.value(0) + ""));
			sumA = sumA.add(new BigDecimal(instance.value(1) + ""));
			sumB = sumB.add(new BigDecimal(instance.value(2) + ""));
		}
		
		sumL = sumL.divide(new BigDecimal(instances.size()), RoundingMode.HALF_UP);
		sumA = sumA.divide(new BigDecimal(instances.size()), RoundingMode.HALF_UP);
		sumB = sumB.divide(new BigDecimal(instances.size()), RoundingMode.HALF_UP);
		ColorCieLab color = new ColorCieLab(sumL.doubleValue(), sumA.doubleValue(), sumB.doubleValue());
		return color;
	}
	
	/**
	 * Method to load a BitMatrix from a bitmap object
	 * Used before we can use a QR Detector
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static BitMatrix loadBitMatrixFromBitmap(Bitmap bitmap)
	{
		BitMatrix matrix = new BitMatrix(bitmap.getWidth(), bitmap.getHeight());
		for(int i = 0; i<bitmap.getWidth(); i++)
		{
			for(int j = 0; j<bitmap.getHeight(); j++)
			{
				if(!isBlack(bitmap.getPixel(i, j)))
				{
					matrix.set(i, j);
				}
			}
		}
		return matrix;
	}
	
	/**
	 * A method to determine if an argb value is black
	 * Used to convert coloured images into black and white
	 * @param argb
	 * @return
	 */
	public static boolean isBlack(int argb)
	{
		int[] colours = rgbIntToBytes(argb);
		if(colours[0] > 127 && colours[1] > 127 && colours[2] > 127 && colours[3] > 127)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * A method to convert an integer into and array of bytes
	 * returns array of the following: {alpha, red, green, blue}
	 * @param integer
	 * @return
	 */
	public static int[] rgbIntToBytes(int argb)
	{
		int[] bytes = new int[4];
		bytes[0] = Color.alpha(argb);
		bytes[1] = Color.red(argb);
		bytes[2] = Color.green(argb);
		bytes[3] = Color.blue(argb);

		return bytes;
	}

}
