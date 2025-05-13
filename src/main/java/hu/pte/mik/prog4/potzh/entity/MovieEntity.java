package hu.pte.mik.prog4.potzh.entity;

import java.util.Objects;

public class MovieEntity {

    private Long id;
    private String title;
    private String directorName ;
    private String releaseYear;
    private String genre;

    public MovieEntity() {}

    public MovieEntity(Long id, String title, String directorName, String releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.directorName = directorName;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(directorName, that.directorName) && Objects.equals(releaseYear, that.releaseYear) && Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, directorName, releaseYear, genre);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
