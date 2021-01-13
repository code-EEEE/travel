package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        Collections.sort(list, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getCid() - o2.getCid();
            }
        });
        return list;
    }
}
