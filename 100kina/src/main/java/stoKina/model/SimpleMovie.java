package stoKina.model;

public class SimpleMovie {
	
	private Long id;
	
	private String movieTitle;
	
	
	public SimpleMovie(Movie movie){
		this.id = movie.getId();
		this.movieTitle = movie.getTitle();
	}
	
	public String getMovieTitle(){
		return movieTitle;
	}
	
	public Long getId() {
		return id;
	}

}
