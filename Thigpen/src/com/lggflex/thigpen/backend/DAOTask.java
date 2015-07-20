package com.lggflex.thigpen.backend;

import android.os.AsyncTask;

public class DAOTask extends AsyncTask<Void, Void, Void>{

	@Override
	protected Void doInBackground(Void... params) {
		FileDAO.init();
		SharedPrefsDAO.init();
		return null;
	}

}
