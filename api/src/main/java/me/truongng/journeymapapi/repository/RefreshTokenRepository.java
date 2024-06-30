package me.truongng.journeymapapi.repository;

import java.util.List;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import me.truongng.journeymapapi.models.RefreshToken;
import me.truongng.journeymapapi.models.User;

@Repository
public class RefreshTokenRepository implements RefreshTokenRepositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(RefreshToken refreshToken) {
        int res = jdbcTemplate.update(
                "INSERT INTO refresh_tokens (token, user_id) VALUES (?, ?)",
                refreshToken.getToken(),
                refreshToken.getUserID());
        return res == 1 ? true : false;
    }

    @Override
    public List<RefreshToken> findByToken(String token) {
        return jdbcTemplate.query(
                "SELECT token, user_id FROM refresh_tokens WHERE token = ?",
                (ResultSet rs, int rowNum) -> new RefreshToken(
                        rs.getString("token"),
                        new User(rs.getString("user_id"), null, null, null, null, null, null)),
                token);
    }
}