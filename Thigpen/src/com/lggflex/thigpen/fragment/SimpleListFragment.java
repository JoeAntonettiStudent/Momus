package com.lggflex.thigpen.fragment;

import java.util.ArrayList;
import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.ChatActivity;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.adapter.ListAdapter;
import com.lggflex.thigpen.backend.DAO;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

abstract public class SimpleListFragment extends RecyclerViewFragment{
	
	protected static String TAG = "SimpleListFragment";
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	view = inflater.inflate(R.layout.fragment_list, container, false);
        view.setTag(TAG);
        adapter = new ListAdapter(getItems());
        if(DAO.get(R.string.pref_twocolumn, false))
        	initRecyclerView(R.id.content, 2);
        else
        	initRecyclerView(R.id.content, 1);
        
        return view;
    }
    
    abstract protected ArrayList<ChatroomModel> getItems();

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
