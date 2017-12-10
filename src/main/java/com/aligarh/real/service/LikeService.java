package com.aligarh.real.service;

import com.aligarh.real.model.Like;

import java.util.List;

public interface LikeService {
    List<Like> findAllLikers();
    void addLiker(Like user);
    void removeLiker(Like user);
    int getCount();
}
