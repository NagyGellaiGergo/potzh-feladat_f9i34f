package hu.pte.mik.prog4.potzh.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        try {
            req.logout();
        } catch (ServletException e) {
            System.err.println("Hiba történt a kijelentkezés során: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath());
    }
}
