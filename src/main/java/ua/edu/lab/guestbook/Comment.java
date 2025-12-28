package ua.edu.lab.guestbook;

import java.time.LocalDateTime;

public record Comment(
        long id,
        String author,
        String text,
        LocalDateTime createdAt
) {}
