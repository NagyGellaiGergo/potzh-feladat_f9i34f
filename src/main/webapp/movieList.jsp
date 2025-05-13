<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:page>
    <style type="text/css">
        td {
            border: 1px solid black;
        }
    </style>
    <div>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Cím</th>
                <th>Rendező</th>
                <th>Megjelenés éve</th>
                <th>IMDb</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${movies}" var="movie">
                <tr>
                    <td><c:out value="${movie.id}"/></td>
                    <td><c:out value="${movie.title}"/></td>
                    <td><c:out value="${movie.directorName}"/></td>
                    <td><c:out value="${movie.releaseYear}"/></td>
                    <td>
                        <button type="button" onclick="getMovieImdb('${movie.id}')">IMDb</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</t:page>
<script>
    function getMovieImdb(id){
        let request = new XMLHttpRequest();
        request.open("GET",
            "${pageContext.request.contextPath}/api/movie/" + id + "/imdb");
        request.setRequestHeader("Accept", "application/json");
        request.onload = function () {
            if (request.status === 200) {
                let imdb = request.responseText;
                window.alert("IMDb értékelés: " + imdb);
            } else {
                window.alert("Hiba történt az értékelés során!");
            }
        };
        request.onerror = function () {
            window.alert("Hálózati hiba történt!");
        };
        request.send();
    }
</script>