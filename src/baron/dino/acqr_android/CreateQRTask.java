package baron.dino.acqr_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import baron.dino.animalxingqr.AnimalXingDecoder;
import baron.dino.animalxingqr.AnimalXingEncoder;
import baron.dino.animalxingqr.AnimalXingQR;

public class CreateQRTask extends AsyncTask<Bitmap, Void, Bitmap[]>{

	Context context;
	ProgressDialog waitSpinner;

	public CreateQRTask(Context context) {
		this.context = context;
		waitSpinner = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		waitSpinner = ProgressDialog.show(context, "Please Wait ...", "Creating QR Code ...", true);
	}
	
	@Override
	protected Bitmap[] doInBackground(Bitmap... params) {
		Bitmap[] result = new Bitmap[2];

		AnimalXingEncoder encoder = new AnimalXingEncoder(context);
		AnimalXingDecoder decoder = new AnimalXingDecoder(context);
		AnimalXingQR qr = encoder.encodeFromBitmap(params[0], "ACQR");

		result[0] = decoder.writeAnimalXingQR(qr);
		result[1] = encoder.writeToBitmap(qr);

		return result;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		waitSpinner = ProgressDialog.show(context, "Please Wait ...", "Loading QR Codes ...", true);
	}

	@Override
	protected void onPostExecute(Bitmap[] result) {
		super.onPostExecute(result);
		
		Activity activity = (Activity)context;
		ImageView image1 = (ImageView)activity.findViewById(R.id.imageView1);
		ImageView image2 = (ImageView)activity.findViewById(R.id.imageView2);
		
		image1.setImageBitmap(result[0]);
		image2.setImageBitmap(result[1]);
		waitSpinner.cancel();
	}

}
