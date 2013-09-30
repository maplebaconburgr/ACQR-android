package baron.dino.animalxingqr;

import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

import org.apache.commons.imaging.color.ColorCieLab;
import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.color.ColorXyz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.SparseArray;
import baron.dino.acqr_android.R;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;

public class AnimalXingEncoder {

	public static int test = 0;
	
	public AnimalXingEncoder(Context context) {
		/*
		 * Initialise other classes
		 */
		if(!AnimalXingPalette.initialized){
			try {
				AnimalXingPalette.initColours(context.getResources().openRawResource(R.raw.accolours));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		/*
		 * Create defaults
		 */
		if(AnimalXingDefaults.getInstance() == null)
		{
			try {
				Bitmap defaultsBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.defaultqr);
				AnimalXingDecoder decoder = new AnimalXingDecoder(context);
				AnimalXingQR defaults = decoder.decodeQRBitmap(defaultsBitmap);
				AnimalXingDefaults.setInstance(new AnimalXingDefaults(defaults));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This class creates an AnimalXingQR object given a bitmap
	 * This bitmap should be square, but can be of any non-zero dimension
	 * null is returned should the operation fail
	 * @param image
	 * @return
	 */
	public AnimalXingQR encodeFromBitmap(Bitmap bitmap, String name)
	{		
		// Load non-image data
		AnimalXingQR acqr = new AnimalXingQR();
		AnimalXingDefaults.getInstance().loadIntoAnimalXingQR(acqr);
		acqr.setPatternName(name);
		
		// Crop bitmap
		int minDimension = Math.min(bitmap.getWidth(), bitmap.getHeight());
		Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 0, minDimension, minDimension);
		
		// Downscale bitmap
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(croppedBitmap, 32, 32, true);
		
		//Now comes the tricky part (colouring)
		DefaultDataset dataset = new DefaultDataset();
		SparseArray<Point> pixelMap = new SparseArray<Point>(); // Maps unique ids of instances to coordinates

		// Put every point into our clustering structure
		for(int x = 0; x <scaledBitmap.getWidth(); x++)
		{
			for(int y = 0; y < scaledBitmap.getHeight(); y++)
			{
				ColorXyz xyz = ColorConversions.convertRGBtoXYZ(scaledBitmap.getPixel(x, y));
				ColorCieLab lab = ColorConversions.convertXYZtoCIELab(xyz);
				DenseInstance instance = new DenseInstance(new double[]{lab.L, lab.a, lab.b});
				pixelMap.put(instance.getID(), new Point(x, y));
				dataset.add(instance);
			}
		}
		
		// Run K-Means clustering to find the top 15 colours
		KMeans clusterer = new KMeans(15);

		Dataset[] results = clusterer.cluster(dataset); // 15 long array
		ColorCieLab[] computedColours = new ColorCieLab[results.length]; // Store all the computed colours
		for(byte i = 0; i<results.length; i++)
		{
			ColorCieLab lab = AnimalXingUtils.averageLabColor(results[i]);
			computedColours[i] = lab; // Compute colour to use
			
			for(Instance instance : results[i])
			{
				Point point = pixelMap.get(instance.getID());
				acqr.getPattern().getColourCodes()[point.y][point.x] = i;
				if(point.y == 0 && point.x == 0){
					AnimalXingEncoder.test = i;
				}
			}
		}
		
		acqr.getPalette().createFromLabValues(computedColours);
		
		return acqr;
	}
	
	/**
	 * Attempts to write an AnimalXingQR object out to a QR code bitmap
	 * If it fails, will return null
	 * @param acqr
	 * @return
	 */
	public Bitmap writeToBitmap(AnimalXingQR acqr)
	{
		try {
			QRCode qrCode = com.google.zxing.qrcode.encoder.Encoder.encode(
					new String(acqr.getBytes(), com.google.zxing.qrcode.encoder.Encoder.DEFAULT_BYTE_MODE_ENCODING), 
					AnimalXingDefaults.defautErrorCorrectionLevel);
			BitMatrix resultMatrix = QRCodeWriter.renderResult(qrCode,
					AnimalXingDefaults.defaultQRWidth, AnimalXingDefaults.defaultQRHeight, 0);
			return AnimalXingUtils.bitmapFromMatrix(resultMatrix);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
