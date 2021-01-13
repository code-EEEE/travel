package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    //分页查找线路方法
    public PageBean<Route> route(int currentPage, int pageSize, int cid, String rname);
    //根据rid查询一个线路方法
    public Route findOne(int rid);
    //判断是否添加收藏方法
    public boolean isFav(int rid, int uid);
    //查询线路收藏数量方法
    public int favCount(int rid);
    //添加收藏方法
    public void addFav(int rid, int uid);
    //删除收藏方法
    public void delFav(int rid, int uid);
}
