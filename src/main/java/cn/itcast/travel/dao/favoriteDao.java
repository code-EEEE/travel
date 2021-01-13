package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface favoriteDao {
    public List<Integer> findPage(int start, int pageSize, int uid);
    //查询个人收藏总量方法
    public int findAll(int uid);
}
