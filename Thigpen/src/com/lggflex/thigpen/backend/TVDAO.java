package com.lggflex.thigpen.backend;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lggflex.model.ChatroomModel;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

public class TVDAO {
	
	private final static String API_KEY = "4e9b213cb3154cc94186d532ab073de5";
	private final static String TAG = "TVDAO";
	private static final String POST_LOC = "http://api.themoviedb.org";
	
	private static HttpClient client;
	private static ArrayList<ChatroomModel> popularTV;
	private static ArrayList<ChatroomModel> liveTV;
	private static ArrayList<ChatroomModel> genres;

	public static void initAPI(){
		client = new DefaultHttpClient();
		liveTV = ChatroomModel.makeFromList(getFromResults("/3/tv/on_the_air"));
		popularTV = ChatroomModel.makeFromList(getFromResults("/3/discover/tv"));
		genres = ChatroomModel.makeFromList(getGenresFromLoc("/3/genre/tv/list"));
	}
	
	public static ArrayList<ChatroomModel> getLiveTV(){
		return liveTV;
	}
	public static ArrayList<ChatroomModel> getPopularTV(){
		return popularTV;
	}
	
	public static ArrayList<ChatroomModel> getGenres(){
		return genres;
	}
	
	private static ArrayList<String> getGenresFromLoc(String postLoc){
		HttpGet post = new HttpGet(POST_LOC + postLoc + "?api_key=" + API_KEY);
		HttpResponse response;
		ArrayList<String> results = new ArrayList<String>();
		try {
			response = client.execute(post);
			JSONObject responses = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
			JSONArray array = responses.getJSONArray("genres");
			for(int i = 0; i < array.length(); i++){
				JSONObject object = array.getJSONObject(i);
				results.add(object.getString("name"));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	private static ArrayList<String> getFromResults(String postLoc){
		HttpGet post = new HttpGet(POST_LOC + postLoc + "?api_key=" + API_KEY);
		HttpResponse response;
		ArrayList<String> results = new ArrayList<String>();
		try {
			response = client.execute(post);
			JSONObject responses = new JSONObject(IOUtils.toString(response.getEntity().getContent()));
			JSONArray array = responses.getJSONArray("results");
			for(int i = 0; i < array.length(); i++){
				JSONObject object = array.getJSONObject(i);
				results.add(object.getString("original_name"));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

}
