package hu.pte.mik.prog4.potzh.repository;

import hu.pte.mik.prog4.potzh.entity.MovieEntity;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MovieRepository extends Repository {

    private static final Logger LOGGER = Logger.getLogger( MovieRepository.class );

    public MovieEntity save(MovieEntity movie) {
        try (Connection conn = this.getConnection()) {
            PreparedStatement stmt;
            if (movie.getId() == null) {
                stmt = conn.prepareStatement(
                        "INSERT INTO movie (title, director_name, release_year, genre) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getDirectorName());
                stmt.setString(3, movie.getReleaseYear());
                stmt.setString(4, movie.getGenre());
            } else {
                stmt = conn.prepareStatement(
                        "INSERT INTO movie (ID, title, director_name, release_year, genre) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                stmt.setLong(1, movie.getId());
                stmt.setString(2, movie.getTitle());
                stmt.setString(3, movie.getDirectorName());
                stmt.setString(4, movie.getReleaseYear());
                stmt.setString(5, movie.getGenre());
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating movie failed, no rows affected.");
            }

            if (movie.getId() == null) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        movie.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating movie failed, no ID obtained.");
                    }
                }
            }

            return movie;
        } catch (SQLException e) {
            LOGGER.error("Adathozzáférési hiba: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (NamingException e) {
            LOGGER.error("Valamilyen hiba történt: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public MovieEntity update(MovieEntity movie) {
        try (Connection conn = this.getConnection(); PreparedStatement stmt = conn.prepareStatement("UPDATE movie SET title, director_name, release_year, genre WHERE ID = ?")){
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDirectorName());
            stmt.setString(3, movie.getReleaseYear());
            stmt.setString(4, movie.getGenre());

            stmt.executeUpdate();

            return this.findById(movie.getId());
        }catch (SQLException e) {
            LOGGER.error("Adathozzáférési hiba: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (NamingException e) {
            LOGGER.error("Valamilyen hiba történt: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public MovieEntity findById(Long id) {
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ID, title, director_name, release_year, genre FROM movie WHERE ID = ?")){

            stmt.setLong(1, id);

            ResultSet rs =stmt.executeQuery();

            rs.next();

            return this.mapMovie(rs);

        }catch (SQLException e) {
            LOGGER.error("Adathozzáférési hiba: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (NamingException e) {
            LOGGER.error("Valamilyen hiba történt: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<MovieEntity> listAll() {
        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs =
                    stmt.executeQuery("SELECT ID, title, director_name, release_year, genre FROM movie");

            List<MovieEntity> movies = new ArrayList<>();
            while(rs.next()){
                movies.add(this.mapMovie(rs));
            }

            return movies;
        }catch (SQLException e) {
            LOGGER.error("Adathozzáférési hiba: " + e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (NamingException e) {
            LOGGER.error("Valamilyen hiba történt: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private MovieEntity mapMovie(ResultSet rs) throws SQLException {
        MovieEntity movie = new MovieEntity();
        movie.setId(rs.getLong("ID"));
        movie.setTitle(rs.getString("title"));
        movie.setDirectorName(rs.getString("director_name"));
        movie.setReleaseYear(rs.getString("release_year"));
        movie.setGenre(rs.getString("genre"));
        return movie;
    }
}
