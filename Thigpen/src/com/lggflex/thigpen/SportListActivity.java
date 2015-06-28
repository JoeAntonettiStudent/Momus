package com.lggflex.thigpen;

import com.lggflex.pallete.PalleteTools;
import com.lggflex.thigpen.fragment.sportscategory.SportsCategoryViewModel;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SportListActivity extends AppCompatActivity {
	
	private static final String EXTRA_IMAGE = "com.lggflex.thigpen.extraImage";
	private static final String EXTRA_TITLE = "com.lggflex.thigpen.extraTitle";
	private static final String EXTRA_PRIMARY_COLOR = "com.lggflex.thigpen.extraPrimary";
	private static final String EXTRA_ACCENT_COLOR = "com.lggflex.thigpen.extraAccent";
	
	int primaryColor = -1;
	int accentColor = -1;
	
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActivityTransitions();
		
		setContentView(R.layout.list);
		ActivityCompat.postponeEnterTransition(this);
		
		String name = getIntent().getStringExtra(EXTRA_TITLE);
		initToolbar(name);
		
		final ImageView image = (ImageView) findViewById(R.id.headerImage);
        int drawableID = getIntent().getIntExtra(EXTRA_IMAGE, 0);
        if(drawableID != 0){
        	image.setImageDrawable(getResources().getDrawable(drawableID));
        }
        
        int resId = getResources().getIdentifier(name.toLowerCase() + "_list", "array", getPackageName());
	    if(resId == 0){
	    	startChatActivity(name);
	    	finish();
	    }else{
	    	makeList(R.id.main_list, android.R.layout.simple_list_item_1, resId, new OnItemClickListener(){

	    		@Override
	    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    				long arg3) {
	    			TextView text = (TextView) arg1.findViewById(android.R.id.text1);
					startChatActivity(text.getText());
	    		}
			
	    	});
	    }
        
	    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                applyPalette(palette, image);
            }
        });
        ViewCompat.setTransitionName(image, "Sports Header Transition");
        ActivityCompat.startPostponedEnterTransition(this);
	}
	
	 private void applyPalette(Palette palette, ImageView image) {
	        int primaryDark = getResources().getColor(R.color.primary_dark);
	        int primary = getResources().getColor(R.color.primary);
	        toolbar.setBackgroundColor(palette.getVibrantColor(primary));
	        primaryColor = palette.getVibrantColor(primary);
	        accentColor = palette.getLightMutedColor(primary);
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	            getWindow().setStatusBarColor(palette.getDarkMutedColor(primaryDark));
	        }
	    }
	
	private void initToolbar(String title){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setTitle(title);
	}
	
	private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }
	
	public int getStatusBarHeight() {
	    int result = 0;
	    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	        result = getResources().getDimensionPixelSize(resourceId);
	    }
	    return result;
	}
	
	protected void makeList(int id, int layout, int data, OnItemClickListener listener){
		ListView list = (ListView) findViewById(id);
		String[] data_items = getResources().getStringArray(data);
		list.setAdapter(new ArrayAdapter<String>(this, layout, data_items));
		if(listener != null)
			list.setOnItemClickListener(listener);
	}
	
	public void startChatActivity(CharSequence charSequence){
		Intent i = new Intent(this, ChatActivity.class);
		i.putExtra("name", charSequence);
		i.putExtra(EXTRA_PRIMARY_COLOR, primaryColor);
		i.putExtra(EXTRA_ACCENT_COLOR, accentColor);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(i);
		//overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom);
	}
}
