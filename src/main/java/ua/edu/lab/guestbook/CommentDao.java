package ua.edu.lab.guestbook;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    private static final String URL =
            "jdbc:h2:file:./data/guest;AUTO_SERVER=TRUE";

    static {
        try (Connection c = DriverManager.getConnection(URL);
             Statement s = c.createStatement()) {

            s.execute("""
                CREATE TABLE IF NOT EXISTS comments(
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    author VARCHAR(64) NOT NULL,
                    text VARCHAR(1000) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comment> findAll() throws SQLException {
        List<Comment> list = new ArrayList<>();

        try (Connection c = DriverManager.getConnection(URL);
             PreparedStatement ps =
                     c.prepareStatement(
                             "SELECT * FROM comments ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Comment(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("text"),
                        rs.getTimestamp("created_at")
                                .toLocalDateTime()
                ));
            }
        }
        return list;
    }

    public long insert(String author, String text) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL);
             PreparedStatement ps =
                     c.prepareStatement(
                             "INSERT INTO comments(author, text) VALUES (?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, author);
            ps.setString(2, text);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        }
    }
}
