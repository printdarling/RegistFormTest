package com.wang.servlet;

import com.wang.dao.User;
import com.wang.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import com.alibaba.fastjson.JSON;

public class RegistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType ("text/html;charset=utf-8");
        req.setCharacterEncoding ("utf-8");

        BufferedReader reader = req.getReader();
        String s = reader.readLine();
        User u = JSON.parseObject(s, User.class);
        System.out.println(u);
        //打印生成的对象

        Boolean flag;
        UserService userService = new UserService();

        //查询到了，返回true,没有返回false
        flag = userService.selectByEmail(u);
        System.out.println("数据库查询的结果是："+flag);
        User user = new User();
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());

        //如果没查询到，就注册添加新用户
        if (!flag){
            userService.addUser(user);
        }

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(!flag));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
