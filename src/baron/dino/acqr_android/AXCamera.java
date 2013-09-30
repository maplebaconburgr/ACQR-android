package baron.dino.acqr_android;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class AXCamera extends Activity {

	public static String CAMERA_RESULT_ID = "CAMERA_RESULT_ID";
	
	private Camera mCamera;
	private CameraPreview mPreview;

	private PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			// Set get bitmap where the overlay goes
			Bitmap photo = BitmapFactory.decodeByteArray(data, 0, data.length);
			double width = photo.getWidth();
			double height = photo.getHeight();
			double difference = (width - height)/2;
			Matrix rotation = new Matrix();
			rotation.setRotate(90);
			Bitmap croppedBitmap = Bitmap.createBitmap(photo, (int)(difference + (height/4)),
					(int)(height/4), (int)(height/2), (int)(height/2), rotation, true);

			// Return the image
			Intent result = new Intent();
			result.putExtra(CAMERA_RESULT_ID, croppedBitmap);
			setResult(RESULT_OK, result);
			finish();
			
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_axcamera);

		// Create an instance of Camera
		mCamera = getCameraInstance();
		
		// Set camera parameters
		Camera.Parameters params = mCamera.getParameters();
		if(params.getMaxNumMeteringAreas() > 0){
			List<Camera.Area> meteringAreas = new LinkedList<Camera.Area>();
			Rect middleRect = new Rect(-333, -333, 667, 667);
			meteringAreas.add(new Camera.Area(middleRect, 1000));
		}

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview, 0);

		Bitmap input = BitmapFactory.decodeResource(getResources(), R.drawable.mask);
		ImageView overlay = (ImageView) findViewById(R.id.overlay);
		overlay.setImageBitmap(input);

		// Add a listener to the Capture button
		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// get an image from the camera
						mCamera.takePicture(null, null, mPicture);
					}
				}
				);
	}


	@Override
	protected void onDestroy() {

		if(mCamera != null){
			mCamera.release();
		}
		
		super.onDestroy();
	}


	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		}
		catch (Exception e){
			Log.e("baron.dino.acqr_Android", "Camera load failed", e);
		}
		return c; // returns null if camera is unavailable
	}
}
