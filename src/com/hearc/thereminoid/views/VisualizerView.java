package com.hearc.thereminoid.views;

import java.util.Random;

import com.hearc.thereminoid.utils.Waves;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.view.View;

public class VisualizerView extends View implements Runnable {

	final int PARTICLES_DISTANCE = 40;
	final int background = Color.argb(10, 0, 0, 0);

	Bitmap bitmap;
	Canvas canvas;
	Paint paintBackground;
	Paint paintParticles;
	Paint paintSignal;
	Random random = new Random();
	float[] input;
	int y;
	float[] hsv;

	public VisualizerView(Context context) {
		super(context);

		hsv = new float[3];
		hsv[0] = 0.0f;
		hsv[1] = 1.0f;
		hsv[2] = 1.0f;
				
		setLayerType(LAYER_TYPE_HARDWARE, paintParticles);
		setLayerType(LAYER_TYPE_HARDWARE, paintSignal);
		
		paintBackground = new Paint();
		paintBackground.setColor(background);
		
		paintSignal = new Paint();
		paintSignal.setColor(Color.WHITE);
		paintSignal.setXfermode(new PorterDuffXfermode(Mode.ADD));
		
		paintParticles = new Paint();
		paintParticles.setXfermode(new PorterDuffXfermode(Mode.ADD));

		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		if (bitmap == null) {
			bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(),
					Config.ARGB_8888);
		}
		if (this.canvas == null) {
			this.canvas = new Canvas(bitmap);
		}
		
		
		canvas.drawBitmap(bitmap, 0, 0, null);
		this.canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paintBackground);
		drawSignal(this.canvas, input);

		postDelayed(this, 10);
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		y = getHeight() / 2;
		
		input = null;
		input = Waves.makeSquare(10.0f, w, 0.5f);
		//input = Waves.makeSinus(5, w, 0.5f);
	}

	private void drawSignal(Canvas canvas, float[] signal) {

		float ya, yb;

		hsv[0] += 1.0f;
		hsv[0] %= 360.0f;
		paintParticles.setColor(Color.HSVToColor(hsv));

		int maxDistance = PARTICLES_DISTANCE * 2;

		// Draw the line
		for (int i = 1; i < signal.length; i++) {
			ya = y - signal[i - 1] * y;
			yb = y - signal[i] * y;

			canvas.drawLine(i - 1, ya, i, yb, paintSignal);

			// Draw particles
			int px, py;
			for (int j = 1; j < 3; j++) {
				px = (int) ((-PARTICLES_DISTANCE + Math.random() * maxDistance) * j * 2);
				py = (int) ((-PARTICLES_DISTANCE + Math.random() * maxDistance) * j * 2);
				canvas.drawPoint(i + px, yb + py, paintParticles);
			}
		}
		
		hsv[0] += 20.0f;
		hsv[1] = 0.6f;
		paintParticles.setColor(Color.HSVToColor(hsv));
		
		for (int i = 1; i < signal.length; i++) {
			ya = y - signal[i - 1] * y;
			yb = y - signal[i] * y;


			// Draw particles
			int px, py;
			for (int j = 1; j < 3; j++) {
				
				px = (int) (Math.random() * j * 60 - j * 30);
				py = (int) (Math.random() * j * 60 - j * 30);
				canvas.drawPoint(i + px, yb + py, paintParticles);
			}
		}
		
		hsv[0] -= 20.0f;
		hsv[1] = 1.0f;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		invalidate();
	}

	public void makeWave(float[] wave)
	{
		input = null;
		input = wave;
	}
}
