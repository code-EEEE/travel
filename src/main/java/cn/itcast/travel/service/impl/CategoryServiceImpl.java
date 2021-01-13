package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        Set<String> category = jedis.zrange("category", 0, -1);
        List<Category> list = null;
        if (category == null || category.size() == 0){
            list = categoryDao.findAll();
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category",list.get(i).getCid(),list.get(i).getCname());
            }
        }else {
            list = new ArrayList<Category>();
            int index = 1;
            for (String str : category) {
                Category category1 = new Category();
                category1.setCid(index);
                category1.setCname(str);
                index++;
                list.add(category1);
            }
        }
        return list;
    }
}
