package com.lggflex.thigpen.fragment.sportscategory;

import java.util.ArrayList;
import java.util.List;
import com.lggflex.pallete.PalleteTools;
import com.lggflex.thigpen.R;
import com.lggflex.thigpen.SportListActivity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SportsCategoryFragment extends Fragment implements SportsCategoryOnItemClickListener{
	
    protected static final String TAG = "SportsCategoryFragment";
    
	private static final String EXTRA_IMAGE = "com.lggflex.thigpen.extraImage";
	private static final String EXTRA_TITLE = "com.lggflex.thigpen.extraTitle";
	
	public static String[] NFL_TEAMS = {
			"New England Patriots",
			"New York Jets",
			"Miami Dolphins",
			"Buffalo Bills",
			"Pittsburgh Steelers",
			"Baltimore Ravens",
			"Cincinnati Bengals",
			"Cleveland Browns",
			"Indianapolis Colts",
			"Houston Texans",
			"Tennessee Titans",
			"Jacksonville Jaguars",
			"Denver Broncos",
			"Kansas City Chiefs",
			"San Diego Chargers",
			"Oakland Raiders",
			"New York Giants",
			"Philadelphia Eagles",
			"Dallas Cowboys",
			"Washington Redskins",
			"Detroit Lions",
			"Green Bay Packers",
			"Minnesota Vikings",
			"Chicago Bears",
			"New Orleans Saints",
			"Carolina Panthers",
			"Atlanta Falcons",
			"Tampa Bay Buccaneers",
			"Seattle Seahawks",
			"San Francisco 49ers",
			"Arizona Cardinals",
			"St. Louis Rams"
	};
	
	public static String[] MLB_TEAMS = {
			"New York Yankees",
			"Boston Red Sox",
			"Baltimore Orioles",
			"Tampa Bay Rays",
			"Toronto Blue Jays",
			"Detroit Tigers",
			"Minnesota Twins",
			"Cleveland Indians",
			"Chicago White Sox",
			"Kansas City Royals",
			"Houston Astros",
			"Texas Rangers",
			"Los Angeles Angels",
			"Seattle Mariners",
			"Oakland Athletics",
			"New York Mets",
			"Atlanta Braves",
			"Washington Nationals",
			"Miami Marlins",
			"Philadelphia Phillies",
			"St. Louis Cardinals",
			"Pittsburgh Pirates",
			"Chicago Cubs",
			"Milwaukee Brewers",
			"Cincinnati Reds",
			"Los Angeles Dodgers",
			"San Diego Padres",
			"San Francisco Giants",
			"Colorado Rockies",
			"Arizona Diamondbacks"
	};
    
	public static String[] NBA_TEAMS = {
			"Toronto Raptors",
			"Boston Celtics",
			"Brooklyn Nets",
			"Philadelphia 76ers",
			"New York Knicks",
			"Cleveland Cavaliers",
			"Chicago Bulls",
			"Miami Heat",
			"Milwaukee Bucks",
			"Indiana Pacers",
			"Detroit Pistons",
			"Atlanta Hawks",
			"Washington Wizards",
			"Charlotte Hornets",
			"Orlando Magic",
			"Portland Trailblazers",
			"Oklahoma City Thunder",
			"Utah Jazz",
			"Denver Nuggets",
			"Minnesota Timberwolves",
			"Golden State Warriors",
			"Los Angeles Clippers",
			"Phoenix Suns",
			"Sacramento Kings",
			"Los Angeles Lakers",
			"Houston Rockets",
			"Memphis Grizzlies",
			"San Antonio Spurs",
			"Dallas Mavericks",
			"New Orleans Pelicans"
	};
	
	public static String[] NHL_TEAMS = {
			"Montreal Canadiens",
			"Tampa Bay Lightning",
			"Detroit Red Wings",
			"Ottowa Senators",
			"Boston Bruins",
			"Florida Panthers",
			"Toronto Maple Leafs",
			"Buffalo Bisons",
			"New York Rangers",
			"Washington Capitals",
			"New York Islanders",
			"Pittsburgh Penguins",
			"Columbus Blue Jackets",
			"Philadelphia Flyers",
			"New Jersey Devils",
			"Carolina Hurricanes",
			"St. Louis Blues",
			"Nashville Predators",
			"Chicago Blackhawks",
			"Minnesota Wild",
			"Winnipeg Jets",
			"Dallas Stars",
			"Colorado Avalanche",
			"Anahiem Ducks",
			"Vancouver Canucks",
			"Calgary Flames",
			"Los Angeles Kings",
			"San Jose Sharks",
			"Edmonton Oilers",
			"Arizona Coyotes"
	};
	
    private static String packageName;
    
    private List<SportsCategoryViewModel> categories = new ArrayList<SportsCategoryViewModel>();
    
    private View view;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //OnCreate Stuff Here
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	view = inflater.inflate(R.layout.fragment_sports_category, container, false);
        view.setTag(TAG);
        
        packageName = getActivity().getPackageName();
        
        String[] categoryNames = getResources().getStringArray(R.array.home_screen_sports_list);
        for(final String category : categoryNames){
        	final int drawableID = getResources().getIdentifier(category.toLowerCase(), "drawable", packageName);
        	if(drawableID != 0){
        		/*Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(drawableID)).getBitmap();
        		 Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        	            public void onGenerated(Palette p) {
        	            	applyPalleteToCard(p, drawableID, category);
        	            }
        	        });	*/
        		boolean isRepeat = false;
            	for(SportsCategoryViewModel model : categories){
            		if(model.getTitle().equals(category))
            			isRepeat = true;
            	}
            	if(!isRepeat)
            		categories.add(new SportsCategoryViewModel(category, getResources().getDrawable(drawableID), drawableID, getResources().getColor(R.color.primary)));
        	}else{
        		categories.add(new SportsCategoryViewModel(category, null, 0, 0));
        	}
        }
 
        initCategoryView();
        
        return view;
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void applyPalleteToCard(Palette pallete, int drawableID, String name){
    	int primary = getResources().getColor(R.color.primary);
    	int mutedPrimary = pallete.getMutedColor(primary);
    	boolean isRepeat = false;
    	for(SportsCategoryViewModel model : categories){
    		if(model.getTitle().equals(name))
    			isRepeat = true;
    	}
    	if(!isRepeat)
    		categories.add(new SportsCategoryViewModel(name, getResources().getDrawable(drawableID), drawableID, mutedPrimary));
    }
    
    public void initCategoryView(){
    	RecyclerView categoryView = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);
    	categoryView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(), 2));
    	SportsCategoryViewAdapter adapter = new SportsCategoryViewAdapter(categories);
    	adapter.setOnCategoryClickListener(this);
    	adapter.notifyDataSetChanged();
    	categoryView.setAdapter(adapter);
    }

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public void onItemClick(View view, SportsCategoryViewModel viewModel) {
    	Intent i = new Intent(getActivity(), SportListActivity.class);
    	i.putExtra(EXTRA_IMAGE, viewModel.getDrawableID());
		i.putExtra(EXTRA_TITLE, viewModel.getTitle());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.category_image), "Sports Header Transition");
			ActivityCompat.startActivity(getActivity(), i, transitionActivityOptions.toBundle());
		}else{
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getActivity().getApplicationContext().startActivity(i);
		}
	}
}
