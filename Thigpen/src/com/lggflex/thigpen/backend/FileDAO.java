package com.lggflex.thigpen.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.content.res.AssetManager;
import android.util.Log;

public class FileDAO {
	
	private final static String TAG = "FileDAO";
	
	private static HashMap<String, ArrayList<String>> fileDirectory;
	
	public static void init(){
		AssetManager manager = DAO.DAOContext.getAssets();
		fileDirectory = new HashMap<String, ArrayList<String>>();
		try {
			for(String asset : manager.list("Files")){
				Log.i(TAG, asset);
				ArrayList<String> rooms = getAssetsForFile(asset);
				fileDirectory.put(asset.toLowerCase(Locale.US).replace(".txt", ""), rooms);
			}
		} catch (IOException e) {
			Log.e(TAG, "Failed to create DAO object");
		}
	}
	
	public static ArrayList<String> getFromDirectory(String id){
		ArrayList<String> recs = fileDirectory.get(id.toLowerCase(Locale.US));
		if(recs != null)
			return recs;
		recs = new ArrayList<String>();
		recs.add("No Recs Found");
		return recs;
	}
	
	private static ArrayList<String> getAssetsForFile(String name){
		AssetManager manager = DAO.DAOContext.getAssets();
		try {
			InputStream input = manager.open("Files/" + name.toLowerCase(Locale.US));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			ArrayList<String> items = new ArrayList<String>();
			String line = "";
			do{
				line = reader.readLine();
				if(line != null)
					items.add(line);
			}while(line != null);
			reader.close();
			return items;
		} catch (IOException e) {
			Log.e(TAG, e.toString());
			Log.e(TAG, "Couldn't open resource for file " + name);
			return null;
		}
		
	}

}
