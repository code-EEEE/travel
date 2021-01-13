package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    public boolean register(User user);
    public boolean active(String code);
    public User findByUsernameAndPassword(User user);
}
