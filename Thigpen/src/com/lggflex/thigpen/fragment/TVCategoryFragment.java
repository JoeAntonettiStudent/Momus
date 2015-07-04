package com.lggflex.thigpen.fragment;

import java.util.ArrayList;

import com.lggflex.model.ChatroomModel;
import com.lggflex.thigpen.backend.DAO;

public class TVCategoryFragment extends SimpleListFragment{
	
    protected static final String TAG = "TVCategoryFragment";

	@Override
	protected ArrayList<ChatroomModel> getItems() {
		return DAO.getTV();
	}

}
