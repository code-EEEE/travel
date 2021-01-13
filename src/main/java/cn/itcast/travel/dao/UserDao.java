package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao {
    public User findByName(User user);
    public void insert(User user);
    public User findByCode(String code);
    public void updateStatus(User user);
    public User findByUsernameAndPassword(User user);
}
