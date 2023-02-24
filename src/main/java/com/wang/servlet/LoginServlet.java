package com.wang.servlet;

import com.alibaba.fastjson.JSON;
import com.wang.dao.User;
import com.wang.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(1111);
        resp.setContentType ("text/html;charset=utf-8");
        req.setCharacterEncoding ("utf-8");

        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User u = JSON.parseObject(s, User.class);
        System.out.println(u);

        Boolean flag;
        UserService userService = new UserService();

        //正确返回true,错误返回false
        flag = userService.selectUser(u);
        System.out.println("数据库查询的结果是："+flag);
        User user = new User();
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(flag));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
