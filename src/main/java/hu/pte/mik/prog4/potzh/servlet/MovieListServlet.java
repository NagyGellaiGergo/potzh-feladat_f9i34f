package hu.pte.mik.prog4.potzh.servlet;

import hu.pte.mik.prog4.potzh.service.MovieService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MovieListServlet extends HttpServlet {

    private final MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("movies", this.movieService.listAll());
        req.getRequestDispatcher("/movieList.jsp")
                .forward(req, resp);
    }
}
