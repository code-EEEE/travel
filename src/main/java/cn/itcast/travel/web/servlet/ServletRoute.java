package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/route/*")
public class ServletRoute extends ServletBase {
    RouteService routeService = new RouteServiceImpl();

    public void route(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid_str = request.getParameter("cid");
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        if (rname != null && rname.length() != 0) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }
        //cid默认为1
        int cid = 0;
        if (cid_str != null && cid_str.length() != 0 && !"null".equals(cid_str)) {
            cid = Integer.parseInt(cid_str);
        }
        //页码默认为1
        int currentPage;
        if (currentPage_str != null && currentPage_str.length() != 0) {
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            currentPage = 1;
        }
        //页面容量默认为5
        int pageSize;
        if (pageSize_str != null && pageSize_str.length() != 0) {
            pageSize = Integer.parseInt(pageSize_str);
        } else {
            pageSize = 5;
        }
        PageBean<Route> pageBean = routeService.route(currentPage, pageSize, cid, rname);
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), pageBean);
    }

    public void findDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid_str = request.getParameter("rid");
        //将查询得到的字符串形式rid转换成int形式
        int rid = Integer.parseInt(rid_str);
        //根据给出的rid查询得到的route对象
        Route route = routeService.findOne(rid);
        //System.out.println(route);
        //将对象转换为json格式写回页面,设置格式为json
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), route);
    }

    public void isFav(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session中的user对象,以及uid的值
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user != null) {
            uid = user.getUid();
        }
        //获取rid的值
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);
        //传入将uid与rid传入service，查询是否添加，返回boolean值,写入resultInfo
        //传入rid查询tab_favorite表返回收藏次数并写入resultInfo
        ResultInfo resultInfo = new ResultInfo();
        int favCount = routeService.favCount(rid);
        boolean flag = routeService.isFav(rid, uid);
        resultInfo.setData(favCount);
        resultInfo.setFlag(flag);
        if (!flag){
            resultInfo.setErrorMsg("请登陆！");
        }
        //将resultInfo写回页面
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),resultInfo);
    }
    public void addFav(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收储存在session中的uid，以及传递过来的rid
        String rid_str = request.getParameter("rid");
        int rid = Integer.parseInt(rid_str);

        User user =(User) request.getSession().getAttribute("user");
        int uid = user.getUid();

        //将uid与rid传入调用查询是否添加，若添加取消收藏，若未添加，添加收藏
        boolean flag = routeService.isFav(rid, uid);
        if (flag){
            //已经添加，调用删除方法
            routeService.delFav(rid,uid);
        }else {
            //未添加，调用添加方法
            routeService.addFav(rid,uid);
        }
    }


}
