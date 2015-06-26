package com.sf.odometersnap.auth;

import android.util.Log;

import com.sf.odometersnap.util.DisjointSets;

public class MatrixChecker extends Test{
	
	public MatrixChecker(){
		super(null, "Adjacency Matrix Test");
	}

	@Override
	public boolean run() {
		Log.i(TEST_TAG_NAME, "Running Matrix Checker");
		DisjointSets test_ds = new DisjointSets(10);
		test_ds.union(0, 2);
		test_ds.union(2, 4);
		test_ds.union(0, 5);
		onPostRun(test_ds.inSameSet(4, 5));
		return test_ds.inSameSet(4, 5);
	}

}
