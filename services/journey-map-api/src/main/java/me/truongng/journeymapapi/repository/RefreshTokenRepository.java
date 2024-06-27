package me.truongng.journeymapapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import me.truongng.journeymapapi.models.RefreshToken;

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
                BeanPropertyRowMapper.newInstance(RefreshToken.class),
                token);
    }
}
