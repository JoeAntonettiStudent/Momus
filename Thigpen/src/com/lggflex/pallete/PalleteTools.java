package com.lggflex.pallete;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

public class PalleteTools {
	
	public static Drawable getSingleDrawable(Context c, Drawable d){
		 
		 LayerDrawable layerDrawable = (LayerDrawable) d;

        int resourceBitmapHeight = 136, resourceBitmapWidth = 153;

        float widthInInches = 0.9f;

        int widthInPixels = (int)(widthInInches * c.getResources().getDisplayMetrics().densityDpi);
        int heightInPixels = (int)(widthInPixels * resourceBitmapHeight / resourceBitmapWidth);

        int insetLeft = 10, insetTop = 10, insetRight = 10, insetBottom = 10;

        layerDrawable.setLayerInset(1, insetLeft, insetTop, insetRight, insetBottom);     

        Bitmap bitmap = Bitmap.createBitmap(widthInPixels, heightInPixels, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        layerDrawable.setBounds(0, 0, widthInPixels, heightInPixels);
        layerDrawable.draw(canvas);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(c.getResources(), bitmap);
        bitmapDrawable.setBounds(0, 0, widthInPixels, heightInPixels);

        return bitmapDrawable;
	}

}
