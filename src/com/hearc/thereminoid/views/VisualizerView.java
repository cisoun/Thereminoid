package com.hearc.thereminoid.views;

import java.util.Random;

import com.hearc.thereminoid.utils.Waves;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.preference.PreferenceManager;
import android.view.View;

/**
 * Visualizer view
 * 
 * Draws an animated signal.
 * 
 * @author Cyriaque Skrapits
 *
 */
public class VisualizerView extends View implements Runnable {

	final int PARTICLES_DISTANCE = 40;
	int background;

	Bitmap bitmap;
	Canvas canvas;
	Paint paintBackground;
	Paint paintParticles;
	Paint paintSignal;
	Random random = new Random();
	float[] input;
	int y;
	float[] hsv;
	int waveSize;
	
	SharedPreferences settings;
	boolean hasAnimations;
	boolean hasColorAnimation;
	boolean hasParticles;

	public VisualizerView(Context context) {
		super(context);

		// Check if animations were enabled.
		settings = PreferenceManager.getDefaultSharedPreferences(context);
		
		// Hue animation variables.
		hsv = new float[3];
		hsv[0] = 0.0f;
		hsv[1] = 1.0f;
		hsv[2] = 1.0f;
				
		// Active hardware acceleration.
		setLayerType(LAYER_TYPE_HARDWARE, paintParticles);
		setLayerType(LAYER_TYPE_HARDWARE, paintSignal);
		
		paintBackground = new Paint();
		//paintBackground.setColor(background);
		
		paintSignal = new Paint();
		paintSignal.setColor(Color.WHITE);
		paintSignal.setXfermode(new PorterDuffXfermode(Mode.ADD));
		
		paintParticles = new Paint();
		paintParticles.setXfermode(new PorterDuffXfermode(Mode.ADD));
		
		update();
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
		waveSize = w;
	}

	private void drawSignal(Canvas canvas, float[] signal) {

		float ya, yb;

		if (hasColorAnimation) {
			hsv[0] += 1.0f;
			hsv[0] %= 360.0f;
			paintParticles.setColor(Color.HSVToColor(hsv));
		}
		
		int maxDistance = PARTICLES_DISTANCE * 2;

		// Draw the line
		for (int i = 1; i < signal.length; i++) {
			ya = y + signal[i - 1] * y;
			yb = y + signal[i] * y;

			canvas.drawLine(i - 1, ya, i, yb, paintSignal);

			if (!hasParticles)
				continue;

			// Draw particles
			int px, py;
			for (int j = 1; j < 3; j++) {
				px = (int) ((-PARTICLES_DISTANCE + Math.random() * maxDistance) * j * 2);
				py = (int) ((-PARTICLES_DISTANCE + Math.random() * maxDistance) * j * 2);
				canvas.drawPoint(i + px, yb + py, paintParticles);
			}
		}

		if (hasParticles) {
			hsv[0] += 20.0f;
			hsv[1] = 0.6f;

			paintParticles.setColor(Color.HSVToColor(hsv));

			for (int i = 1; i < signal.length; i++) {
				ya = y + signal[i - 1] * y;
				yb = y + signal[i] * y;

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
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		invalidate();
	}

	public void drawWave(int waveType, float frequency, float amplitude)
	{
		int length = this.getWidth();
		if (length == 0)
			return;

		if (waveType == Waves.SINUS)
			input = Waves.sinus(waveSize, frequency / 10.0f, amplitude);
		else if (waveType == Waves.SQUARE)
			input = Waves.square(waveSize, frequency / 10.0f, amplitude);
		else
			input = Waves.saw(waveSize, frequency / 10.0f, amplitude);
	}

	public synchronized void update() {
		hasAnimations = settings.getBoolean("animations_enable", true);
		hasColorAnimation = settings.getBoolean("animations_hue", true);
		hasParticles = settings.getBoolean("animations_particles", true);
		
		background = hasAnimations ? Color.argb(10, 0, 0, 0) : Color.BLACK;
		paintBackground.setColor(background);
	}
}
