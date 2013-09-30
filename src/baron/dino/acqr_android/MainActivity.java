package baron.dino.acqr_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final int CAMERA_REQUEST_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		ImageView image1 = (ImageView) findViewById(R.id.imageView1);
		ImageView image2 = (ImageView) findViewById(R.id.imageView2);

		Bitmap input = BitmapFactory.decodeResource(getResources(), R.drawable.dino);
		AnimalXingEncoder encoder = new AnimalXingEncoder(this);
		AnimalXingDecoder decoder = new AnimalXingDecoder(this);
		AnimalXingQR qr = encoder.encodeFromBitmap(input, "Dino");

		Bitmap out1 = decoder.writeAnimalXingQR(qr);
		Bitmap out2 = encoder.writeToBitmap(qr);
		image1.setImageBitmap(out1);
		image2.setImageBitmap(out2);

		Log.d("baron.dino", "Bitmap Loaded");*/

		//		Intent intent = new Intent(this, AXCamera.class);		
		//		startActivityForResult(intent, CAMERA_REQUEST_ID);

		// Listener for button
		Button captureButton = (Button) findViewById(R.id.camera_button);
		captureButton.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this, AXCamera.class);		
						startActivityForResult(intent, CAMERA_REQUEST_ID);
					}
				}
				);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(resultCode == RESULT_OK)
		{
			if(requestCode == CAMERA_REQUEST_ID)
			{
				Bitmap result = (Bitmap)data.getParcelableExtra(AXCamera.CAMERA_RESULT_ID);
				CreateQRTask qrtask = new CreateQRTask(this);
				qrtask.execute(result);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}

