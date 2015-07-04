package com.lggflex.thigpen.backend;

import android.os.AsyncTask;

public class SearchTask extends AsyncTask<String, String, String>{

	@Override
	protected String doInBackground(String... params) {
		return NetDAO.search(params[0]);
	}

}
