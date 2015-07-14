package com.lggflex.thigpen.backend;

import com.lggflex.thigpen.R;
import com.lggflex.thigpen.fragment.RecyclerViewFragment;

import android.os.AsyncTask;
import android.util.Log;

public class DAOTask extends AsyncTask<Void, Void, Void>{

	@Override
	protected Void doInBackground(Void... params) {
		FileDAO.init();
		SharedPrefsDAO.init();
		return null;
	}

}
