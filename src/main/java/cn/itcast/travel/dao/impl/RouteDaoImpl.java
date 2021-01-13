package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotal(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() != 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        Integer total = jdbcTemplate.queryForObject(sb.toString(), Integer.class, params.toArray());
        return (int) total;

    }

    @Override
    public List<Route> findRoute(int start, int pageSize, int cid, String rname) {
        List<Route> list = null;
        try {
            String sql = "select * from tab_route where 1=1 ";
            StringBuilder sb = new StringBuilder(sql);
            List params = new ArrayList();
            if (cid != 0) {
                sb.append(" and cid = ? ");
                params.add(cid);
            }
            if (rname != null && rname.length() != 0 && !"null".equals(rname)) {
                sb.append(" and rname like ? ");
                params.add("%" + rname + "%");
            }
            sb.append(" limit ?,? ");
            params.add(start);
            params.add(pageSize);
            list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Route findOne(int rid) {
        Route route = null;
        try {
            String sql = "select * from tab_route where rid = ? ";
            route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return route;
    }

    @Override
    public List<RouteImg> findImg(int rid) {
        List<RouteImg> routeImgs = null;
        try {
            String sql = "select * from tab_route_img where rid = ? ";
            routeImgs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return routeImgs;
    }

    @Override
    public Seller findSeller(int sid) {
        Seller seller = null;
        try {
            String sql = "select * from tab_seller where sid = ? ";
            seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seller;
    }

    @Override
    public boolean isFav(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if (favorite != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int favCount(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ? ";
        Integer favCount = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        return favCount;
    }

    @Override
    public void addFav(int rid, int uid) {
        //insert into 表名(列名1,列名2,...列名n) values(值1,值2,...值n);
        //创建date对象
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String sql = "insert into tab_favorite ( rid , date , uid ) values ( ? , ? , ? );";
        jdbcTemplate.update(sql,rid,currentDate,uid);
    }

    @Override
    public void delFav(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ?";
        jdbcTemplate.update(sql,rid,uid);
    }
}
