package com.aligarh.real.service;

import com.aligarh.real.dao.LikeDao;
import com.aligarh.real.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private LikeDao likeDao;

    @Override
    public List<Like> findAllLikers() {
        return likeDao.findAllLikers();
    }

    @Override
    public void addLiker(Like user) {
        if (null != user) {
            likeDao.addLiker(user);
        } else {
            System.err.println("Unable to add null user.");
        }
    }

    @Override
    public void removeLiker(Like user) {
        if (null != user) {
            likeDao.removeLiker(user);
        }
        else{
            System.err.println("Unable to remove null user.");
        }
    }

    @Override
    public int getCount(){
        return findAllLikers().size();
    }

}
