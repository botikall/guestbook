package ua.edu.lab.guestbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {

    private final CommentDao dao = new CommentDao();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private static final Logger log =
            LoggerFactory.getLogger(CommentServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        try {
            mapper.writeValue(resp.getOutputStream(), dao.findAll());
        } catch (SQLException e) {
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String author = req.getParameter("author");
        String text = req.getParameter("text");

        log.info("POST params: author='{}', text='{}'", author, text);

        if (author == null || author.isBlank() || author.length() > 64 ||
                text == null || text.isBlank() || text.length() > 1000) {
            resp.setStatus(400);
            return;
        }

        try {
            long id = dao.insert(author, text);
            log.info("New comment: id={}, author={}, len={}",
                    id, author, text.length());
            resp.setStatus(204);
        } catch (SQLException e) {
            resp.setStatus(500);
        }
    }
}
