package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class ServletUser extends ServletBase {
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        if (check == null || !check.equalsIgnoreCase(checkcode_server)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            response.setContentType("application/json");
            response.getWriter().write(json);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl();
        boolean flag = userService.register(user);
        ResultInfo resultInfo = new ResultInfo();
        if (flag){
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        if (check == null || !check.equalsIgnoreCase(checkcode_server)){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            response.setContentType("application/json;charset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            response.getWriter().write(json);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService userService = new UserServiceImpl();
        User u = userService.findByUsernameAndPassword(user);
        ResultInfo resultInfo = new ResultInfo();
        if (u != null && "Y".equalsIgnoreCase(u.getStatus())){
            request.getSession().setAttribute("user",u);
            resultInfo.setFlag(true);
        }if (u != null && !"Y".equalsIgnoreCase(u.getStatus())){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请登陆邮箱验证");
        }if (u == null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultInfo);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    public void findByUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getOutputStream(),user);
    }
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        UserService userService = new UserServiceImpl();
        boolean flag = userService.active(code);
        String msg = null;
        if (flag){
            msg = "激活成功，请<a href = 'login.html'>登录</a>";
        }else {
            msg = "激活失败，请联系管理员";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(msg);
    }
}
