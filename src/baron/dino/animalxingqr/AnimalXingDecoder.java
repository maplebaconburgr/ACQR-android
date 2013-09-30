package baron.dino.animalxingqr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import baron.dino.acqr_android.R;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;

public class AnimalXingDecoder {

	public AnimalXingDecoder(Context context) {
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
	}
	
	/**
	 * This method attempts to decode a QR code from an image
	 * It returns null if the attempt fails, or an AnimalXingQR object otherwise
	 * @param image
	 * @return
	 */
	public AnimalXingQR decodeQRBitmap(Bitmap bitmap)
	{
		BitMatrix rawMatrix = AnimalXingUtils.loadBitMatrixFromBitmap(bitmap);
		com.google.zxing.qrcode.detector.Detector detector = new com.google.zxing.qrcode.detector.Detector(rawMatrix);
		try {
			DetectorResult detectorResult = detector.detect();
			BitMatrix detectedMatrix = detectorResult.getBits();
			com.google.zxing.qrcode.decoder.Decoder decoder = new com.google.zxing.qrcode.decoder.Decoder();
			DecoderResult decoderResult = decoder.decode(detectedMatrix);
			AnimalXingQR acResult = new AnimalXingQR(decoderResult.getByteSegments().get(0));
			return acResult;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method generates an bitmap from an AnimalXingQR object
	 * @param qr
	 * @return
	 */
	public Bitmap writeAnimalXingQR(AnimalXingQR qr)
	{
		byte[][] colourCodes = qr.getPattern().getColourCodes();
		Bitmap bitmap = Bitmap.createBitmap(colourCodes[0].length, colourCodes.length, Bitmap.Config.ARGB_8888);
		for(int y = 0; y<colourCodes.length; y++){
			for(int x = 0; x<colourCodes[y].length; x++)
			{
				AnimalXingColour axColour = qr.getPalette().getColour(colourCodes[y][x]);
				int colour;
				if(axColour != null){
					colour = axColour.getRgbValue();
				} else {
					colour = Color.WHITE;
				}
				
				bitmap.setPixel(x, y, colour);
			}
		}
		return bitmap;
	}
}

