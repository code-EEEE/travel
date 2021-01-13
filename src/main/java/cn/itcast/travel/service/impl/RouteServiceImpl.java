package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> route(int currentPage, int pageSize, int cid, String rname) {
        PageBean<Route> pageBean = new PageBean<>();
//        当前页码
//        private int currentPage;
        pageBean.setCurrentPage(currentPage);
//        总记录数
//        private int totalCount;
        int total = routeDao.findTotal(cid,rname);
        pageBean.setTotalCount(total);
//        总页码
//        private int totalPage;
        int totalPage;
        if (total % pageSize == 0){
            totalPage = total/pageSize;
        }else {
            totalPage = total/pageSize + 1;
        }
        pageBean.setTotalPage(totalPage);
//        每页显示的条数
//        private int pageSize;
        pageBean.setPageSize(pageSize);
//        每页需要展示的对象
//        private List<T> categoryList;
        int start = (currentPage * pageSize) - 5;
        List<Route> list = routeDao.findRoute(start, pageSize, cid, rname);
        pageBean.setCategoryList(list);
        return pageBean;
    }

    @Override
    public Route findOne(int rid) {

        Route one = routeDao.findOne(rid);
        int sid = one.getSid();

        List<RouteImg> imgList = routeDao.findImg(rid);
        one.setRouteImgList(imgList);

        Seller seller = routeDao.findSeller(sid);
        one.setSeller(seller);

        return one;
    }

    @Override
    public boolean isFav(int rid, int uid) {
        boolean flag = routeDao.isFav(rid, uid);
        return flag;
    }

    @Override
    public int favCount(int rid) {
        int favCount = routeDao.favCount(rid);
        return favCount;
    }

    @Override
    public void addFav(int rid, int uid) {
        //调用dao中的添加方法
        routeDao.addFav(rid,uid);
    }

    @Override
    public void delFav(int rid, int uid) {
        //调用dao中的删除方法
        routeDao.delFav(rid,uid);
    }
}
