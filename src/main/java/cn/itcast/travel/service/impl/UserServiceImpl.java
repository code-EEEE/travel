package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.Map;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        User findByName = userDao.findByName(user);
        if (findByName != null){
            return false;
        }else {
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            userDao.insert(user);
            MailUtils.sendMail(user.getEmail(),"<a href = 'http://192.168.0.199:80/travel/servletActiveUser?code="+ user.getCode() +"'>点击激活</a>","激活邮件");
            return true;
        }
    }

    @Override
    public boolean active(String code) {
        User user = userDao.findByCode(code);
        if (user != null){
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User findByUsernameAndPassword(User user) {
        User user1 = userDao.findByUsernameAndPassword(user);
        return user1;
    }
}
