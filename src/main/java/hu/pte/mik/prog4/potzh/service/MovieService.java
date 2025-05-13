package hu.pte.mik.prog4.potzh.service;

import hu.pte.mik.generated.ws.MovieDataRequest;
import hu.pte.mik.generated.ws.MovieImdb;
import hu.pte.mik.prog4.potzh.entity.MovieEntity;
import hu.pte.mik.prog4.potzh.repository.MovieRepository;

import java.util.List;

public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService() {
        this.movieRepository = new MovieRepository();
    }

    public List<MovieEntity> listAll(){
        return this.movieRepository.listAll();
    }

    public MovieEntity findById(Long id){
        return this.movieRepository.findById(id);
    }

    public MovieEntity save(Long id, String title, String director_name, String release_year, String genre) {
        return this.movieRepository.save(new MovieEntity(id, title, director_name, release_year, genre));
    }

    public long getMovieImdb(String movieId) {
        MovieImdb service = new hu.pte.mik.generated.ws.MovieImdb();
        MovieDataRequest request = new hu.pte.mik.generated.ws.MovieDataRequest();
        request.setMovieId(movieId);

        var port = service.getSoapMovieDataServicePort();
        var resp = port.getMovieData(request);

        var imdb = resp.getImdb();
        return imdb;
    }
}
