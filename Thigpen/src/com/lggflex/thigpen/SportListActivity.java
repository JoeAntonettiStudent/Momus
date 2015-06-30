package com.lggflex.thigpen;

import java.util.ArrayList;
import java.util.Locale;

import org.json.MyLinearLayoutManager;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.adapter.ChatAdapter;
import com.lggflex.thigpen.adapter.ListAdapter;
import com.lggflex.thigpen.backend.DAO;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SportListActivity extends LollipopActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initActivityTransitions();
		setContentView(R.layout.activity_sports_category);
		ActivityCompat.postponeEnterTransition(this);
		
		String name = getIntent().getStringExtra(EXTRA_TITLE);
		final int drawableID = getIntent().getIntExtra(EXTRA_IMAGE, 0);
		
		initUIFlourishes(false, -1, -1);
		setTitle(name);
		makeChildOfRoot();
        
        int resId = getResources().getIdentifier(name.toLowerCase(Locale.ENGLISH) + "_list", "array", getPackageName());
	    if(resId == 0){
	    	startChatActivity(name);
	    	finish();
	    }else{
	    	OnItemClickListener listener = new OnItemClickListener(){

	    		@Override
	    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    				long arg3) {
	    			TextView text = (TextView) arg1.findViewById(android.R.id.text1);
					startChatActivity(text.getText());
	    		}
			
	    	};
	    	ArrayList<ChatroomModel> teams = new ArrayList<ChatroomModel>();
	    	for(String team : DAO.getListForSportName(name)){
	    		teams.add(new ChatroomModel(team));
	    	}
	    	adapter = new ListAdapter(teams);
			initRecyclerView(R.id.main_list, 1);
	    }
        
	    Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(drawableID)).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                applyPalette(palette, getResources().getDrawable(drawableID));
            }
        });
        //ViewCompat.setTransitionName(image, "Sports Header Transition");
        ActivityCompat.startPostponedEnterTransition(this);
	}
	
	 private void applyPalette(Palette palette, Drawable image) {
	        int primaryDark = getResources().getColor(R.color.primary_dark);
	        primary = getResources().getColor(R.color.primary);
	        toolbar.setBackgroundColor(palette.getVibrantColor(primary));
	        primary = palette.getVibrantColor(primary);
	        accent = palette.getLightMutedColor(primary);
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	        	themeToColors();
	            getWindow().setStatusBarColor(palette.getDarkMutedColor(primaryDark));
	        }
	    }
	 
	private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }
	
	protected void makeList(int id, int layout, String[] data, OnItemClickListener listener){
		ListView list = (ListView) findViewById(id);
		list.setAdapter(new ArrayAdapter<String>(this, layout, data));
		if(listener != null)
			list.setOnItemClickListener(listener);
	}
	
	public void startChatActivity(CharSequence charSequence){
		Intent i = new Intent(this, ChatActivity.class);
		i.putExtra("name", charSequence);
		i.putExtra(EXTRA_PRIMARY_COLOR, primary);
		i.putExtra(EXTRA_ACCENT_COLOR, accent);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(i);
	}

	@Override
	public <T> void onItemClick(View view, T m) {
		Intent i = new Intent(this, ChatActivity.class);
		ChatroomModel viewModel = (ChatroomModel) m;
		i.putExtra("name", (CharSequence) viewModel.getTitle());
		i.putExtra(EXTRA_PRIMARY_COLOR, viewModel.getColor()[0]);
		i.putExtra(EXTRA_ACCENT_COLOR, viewModel.getColor()[1]);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(i);
	}
}
