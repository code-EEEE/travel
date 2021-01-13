package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.favoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class favoriteDaoImpl implements favoriteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Integer> findPage(int start, int pageSize, int uid) {
        List<Integer> list = null;
        try {
            String sql = "select rid from tab_favorite where uid = ? limit ? , ? ";
//            list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Integer>(Integer.class), uid, start, pageSize);
            list = jdbcTemplate.queryForList(sql, Integer.class, uid, start, pageSize);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int findAll(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ? ";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, uid);
        return count;
    }
}
