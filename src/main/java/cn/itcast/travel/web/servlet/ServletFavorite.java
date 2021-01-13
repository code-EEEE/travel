package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.favoriteService;
import cn.itcast.travel.service.impl.favoriteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class ServletFavorite extends ServletBase {
    favoriteService favoriteService = new favoriteServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPage = null;
        String pageSize = null;
        try {
            currentPage = request.getParameter("currentPage");
            pageSize = request.getParameter("pageSize");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //当前页码默认为1
        if (currentPage == null || currentPage.length() == 0){
            currentPage = "1";
        }
        //页面大小默认为12
        if (pageSize == null || pageSize.length() == 0){
            pageSize = "12";
        }
        User user = (User)request.getSession().getAttribute("user");
        int uid = 0;
        if (user != null){
            uid = user.getUid();
        }
        //将当前页码和页面大小以及uid传入favoriteService调用查询返回pageBean对象
        PageBean<Route> pageBean = favoriteService.findPage(currentPage, pageSize, uid);
        //设置编码，将pageBean对象写回页面
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),pageBean);
    }
}
