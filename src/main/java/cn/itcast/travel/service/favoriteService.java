package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface favoriteService {
    public PageBean<Route> findPage(String currentPage,String pageSize,int uid);
}
