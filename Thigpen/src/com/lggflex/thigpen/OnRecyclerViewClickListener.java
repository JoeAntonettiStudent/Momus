package com.lggflex.thigpen;
import android.view.View;

public interface OnRecyclerViewClickListener {
	
	<T> void onItemClick(View view, T viewModel);

}
