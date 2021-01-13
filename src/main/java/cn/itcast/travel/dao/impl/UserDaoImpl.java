package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByName(User user) {
        User user1 = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user1;
    }

    @Override
    public void insert(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        jdbcTemplate.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(User user) {
        User user1 = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
            System.out.println(user1);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user1;
    }
}
