package me.truongng.journeymapapi.repository;

import java.util.List;

import me.truongng.journeymapapi.models.RefreshToken;

public interface RefreshTokenRepositoryInterface {
    boolean create(RefreshToken user);

    List<RefreshToken> findByToken(String id);

}