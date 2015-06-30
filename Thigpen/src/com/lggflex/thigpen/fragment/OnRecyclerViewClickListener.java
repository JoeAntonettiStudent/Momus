package com.lggflex.thigpen.fragment;
import android.view.View;

public interface OnRecyclerViewClickListener {
	
	<T> void onItemClick(View view, T viewModel);

}
