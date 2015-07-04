package com.lggflex.thigpen.backend;

import com.lggflex.thigpen.fragment.RecyclerViewFragment;

import android.os.AsyncTask;

public class DAOTask extends AsyncTask<Void, Void, Void>{
	
	private RecyclerViewFragment[] fragments;
	
	public DAOTask(RecyclerViewFragment[] fragments){
		super();
		this.fragments = fragments;
	}

	@Override
	protected Void doInBackground(Void... params) {
		FileDAO.init();
		if(fragments[0].adapter != null)
			fragments[0].adapter.notifyDataSetChanged();
		if(fragments[1].adapter != null)
			fragments[1].adapter.notifyDataSetChanged();
		NetDAO.init();
		if(fragments[2].adapter != null)
			fragments[2].adapter.notifyDataSetChanged();
		SharedPrefsDAO.init();
		if(fragments[3].adapter != null)
			fragments[3].adapter.notifyDataSetChanged();
		return null;
	}

}
