package com.aligarh.real.dao;

import com.aligarh.real.model.Like;

import java.util.List;

public interface LikeDao {
    List<Like> findAllLikers();
    void addLiker(Like username);
    void removeLiker(Like username);
}
