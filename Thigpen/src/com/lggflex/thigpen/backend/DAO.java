package com.lggflex.thigpen.backend;

public class DAO {
	
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
	
	public static String[] getListForSportName(String name){
		if(name.equalsIgnoreCase("nfl"))
			return NFL_TEAMS;
		if(name.equalsIgnoreCase("nba"))
			return NBA_TEAMS;
		if(name.equalsIgnoreCase("nhl"))
			return NHL_TEAMS;
		if(name.equalsIgnoreCase("mlb"))
			return MLB_TEAMS;
		return null;
	}

}
