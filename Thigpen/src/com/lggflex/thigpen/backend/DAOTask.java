package com.lggflex.thigpen.backend;

import com.lggflex.thigpen.R;
import com.lggflex.thigpen.fragment.RecyclerViewFragment;

import android.os.AsyncTask;
import android.util.Log;

public class DAOTask extends AsyncTask<Void, Void, Void>{
	
	private RecyclerViewFragment[] fragments;
	
	public DAOTask(RecyclerViewFragment[] fragments){
		super();
		this.fragments = fragments;
	}

	@Override
	protected Void doInBackground(Void... params) {
		FileDAO.init();
		
		if(fragments[0].adapter != null){
			if(DAO.get(R.string.pref_loading_snackbar, false)){
				fragments[0].showSnackbar("0");
			}
			fragments[0].adapter.notifyDataSetChanged();
		}
		if(fragments[1].adapter != null){
			fragments[1].adapter.notifyDataSetChanged();
			if(DAO.get(R.string.pref_loading_snackbar, false)){
				fragments[0].showSnackbar("1");
			}
		}
		SharedPrefsDAO.init();
		return null;
	}

}
