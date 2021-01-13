package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.favoriteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.favoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.favoriteService;

import java.util.ArrayList;
import java.util.List;

public class favoriteServiceImpl implements favoriteService {
    favoriteDao favoriteDao = new favoriteDaoImpl();
    RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> findPage(String currentPage, String pageSize, int uid) {
        //需要参数：start，pageSize， uid
        int cp = Integer.parseInt(currentPage);
        int ps = Integer.parseInt(pageSize);
        int start = (cp - 1) * ps;
        List<Integer> ridList = favoriteDao.findPage(start, ps, uid);
        //创建route对象数组
        List<Route> routeList = new ArrayList<>();
        //遍历ridList获取route对象
        for (Integer rid : ridList) {
            Route one = routeDao.findOne(rid);
            routeList.add(one);
        }
        PageBean<Route> pageBean = new PageBean<>();
        //计算总页码：传入uid，调用findAll
        int total = favoriteDao.findAll(uid);
        int totalPage = 0;
        if ((total % ps) == 0) {
            //整除
            totalPage = total / ps;
        } else {
            //不整除
            totalPage = (total / ps) + 1;
        }
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(cp);
        pageBean.setPageSize(ps);
        pageBean.setTotalCount(total);
        pageBean.setCategoryList(routeList);
        //返回pageBean对象
        return pageBean;
    }
}
