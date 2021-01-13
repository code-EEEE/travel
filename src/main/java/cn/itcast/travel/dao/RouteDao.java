package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {
    public int findTotal(int cid, String rname);
    public List<Route> findRoute(int start, int pageSize, int cid, String rname);
    public Route findOne (int rid);
    public List<RouteImg> findImg (int rid);
    public Seller findSeller (int sid);
    public boolean isFav(int rid,int uid);
    public int favCount(int rid);
    //添加收藏方法
    public void addFav(int rid, int uid);
    //删除收藏方法
    public void delFav(int rid, int uid);
}
