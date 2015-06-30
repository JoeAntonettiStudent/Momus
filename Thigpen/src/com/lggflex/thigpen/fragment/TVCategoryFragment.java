package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import java.util.List;
import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.ChatActivity;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.adapter.ListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TVCategoryFragment extends RecyclerViewFragment{
	
    protected static final String TAG = "TVCategoryFragment";
    private List<ChatroomModel> shows = new ArrayList<ChatroomModel>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	view = inflater.inflate(R.layout.fragment_list, container, false);
        view.setTag(TAG);
        String[] tvShowNames = getResources().getStringArray(R.array.home_screen_television_list);
        shows.clear();
        for(final String show : tvShowNames){
        		shows.add(new ChatroomModel(show));
        }
 
        adapter = new ListAdapter(shows);
        initRecyclerView(R.id.content, 1);
        
        return view;
    }
    
    
	@Override
	public <T> void onItemClick(View view, T viewModel) {
		Intent i = new Intent(getActivity(), ChatActivity.class);
		i.putExtra("name", ((ChatroomModel) viewModel).getTitle());
		i.putExtra(EXTRA_PRIMARY_COLOR, ((ChatroomModel) viewModel).getColor()[0]);
		i.putExtra(EXTRA_ACCENT_COLOR, ((ChatroomModel) viewModel).getColor()[1]);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getActivity().startActivity(i);
	}
}
