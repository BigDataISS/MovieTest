package Bean;
/**
 * Start
 * 实体类，用于存储movie的相关信息
 * @author 宁志豪
 **/
public class MovieBean {
	private int MovieId;
	private String Name;
	private String Description;
	private String Scenarist;
	private String RatingNum;
	private String Direction;
	private String Actors;
	private String Type;
	private String Country;
	private String Language;
	private String Tags;
	private String ReleaseDate;
	private String Runtime;
	private String viewTime = null;
	public int getMovieId() {
		return MovieId;
	}
	public void setMovieId(int movieId) {
		MovieId = movieId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String movieName) {
		Name = movieName;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getScenarist() {
		return Scenarist;
	}
	public void setScenarist(String scenarist) {
		Scenarist = scenarist;
	}
	public String getRatingNum() {
		return RatingNum;
	}
	public void setRatingNum(String ratingNum) {
		RatingNum = ratingNum;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	public String getActors() {
		return Actors;
	}
	public void setActors(String actors) {
		Actors = actors;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getTags() {
		return Tags;
	}
	public void setTags(String tags) {
		Tags = tags;
	}
	public String getReleaseDate() {
		return ReleaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		ReleaseDate = releaseDate;
	}
	public String getRuntime() {
		return Runtime;
	}
	public void setRuntime(String runtime) {
		Runtime = runtime;
	}
	public String getViewTime() {
		return viewTime;
	}
	public void setViewTime(String viewtime) {
		viewTime = viewtime;
	}
	
}

/**
 * END
 * @author 宁志豪
 */
