package com.lggflex.thigpen;

import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lggflex.model.ChatroomModel;
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
import android.support.v7.graphics.Palette;
import android.transition.Slide;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends LollipopActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initActivityTransitions();
		setContentView(R.layout.activity_sports_category);
		ActivityCompat.postponeEnterTransition(this);
		initUIFlourishes(false, -1, -1);
		setTitle("Search Results");
		
		ArrayList<ChatroomModel> results = new ArrayList<ChatroomModel>();
		makeChildOfRoot();
		
		String result = getIntent().getStringExtra(EXTRA_TITLE);
		JSONObject responses = new JSONObject(result);
		if(responses.getInt("total_results") == 0){
			results.add(new ChatroomModel("No Results Found"));
		}else{
			JSONArray array = responses.getJSONArray("results");
			for(int i = 0; i < array.length(); i++){
				JSONObject object = array.getJSONObject(i);
				results.add(new ChatroomModel(object.getString("original_name")));
				OnItemClickListener listener = new OnItemClickListener(){

			    		@Override
			    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			    				long arg3) {
			    			TextView text = (TextView) arg1.findViewById(android.R.id.text1);
							startChatActivity(text.getText());
			    		}
					
			    };
			}
		}
		adapter = new ListAdapter(results);
		initRecyclerView(R.id.main_list, 1);
        ActivityCompat.startPostponedEnterTransition(this);
	}
	
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
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
