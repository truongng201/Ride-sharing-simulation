package me.truongng.journeymapapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;

import me.truongng.journeymapapi.models.User;

@Repository
public class UserRepository implements UserRepositoryInterface {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public boolean create(User user) {
        int res = jdbcTemplate.update(
                "INSERT INTO users (username, hashed_password, email, role, image_url, is_verified) VALUES (?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getImage_url(),
                user.getIs_verified());

        return res == 1 ? true : false;
    }

    @Override
    public boolean update(User user) {
        int res = jdbcTemplate.update(
                "UPDATE users SET username = ?, password = ?, email = ?, role = ?, image_url = ?, is_verified = ? WHERE id = ?",
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getImage_url(),
                user.getIs_verified(),
                user.getId());

        return res == 1 ? true : false;
    }

    @Override
    public List<User> findById(String id) {
        return jdbcTemplate.query(
                "SELECT id, username, email, role, image_url, is_verified FROM users WHERE id = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                id);
    }

    @Override
    public boolean deleteById(String id) {
        return jdbcTemplate.update(
                "DELETE FROM users WHERE id = ?",
                id) == 1;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT id, username, email, role, image_url, is_verified FROM users",
                BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public List<User> findByEmail(String email) {
        return jdbcTemplate.query(
                "SELECT id, username, email, role, image_url, is_verified FROM users WHERE email = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                email);
    }
}