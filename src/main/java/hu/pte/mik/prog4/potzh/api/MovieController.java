package hu.pte.mik.prog4.potzh.api;

import hu.pte.mik.prog4.potzh.service.MovieService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movie")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieController {

    private final MovieService movieService;

    public MovieController( MovieService movieService ) {
        this.movieService = movieService;
    }

    public MovieController() {
        movieService = new MovieService();
    }

    @GET
    @Path("/{id}/imdb")
    public Response getMovieImdb( @PathParam("id") String movieId ) {
        long imdb = movieService.getMovieImdb( movieId );
        return Response.ok( imdb ).build();
    }

    @GET
    public Response listAll(){
        var movies = movieService.listAll();
        return Response.status( Response.Status.OK ).entity( movies ).build();

    }

    @GET
    @Path("/{id}")
    public Response findById( @PathParam("id") Long id ) {
        var movie = movieService.findById( id );
        return Response.status( Response.Status.OK ).entity( movie ).build();
    }
}
