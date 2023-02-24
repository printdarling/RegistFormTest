package com.wang.service;

import com.wang.dao.User;
import com.wang.utils.JDBCUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public void addUser(User u){
        String sql = "insert into users(email,password) values(?,?);";
        Object[] params = {u.getEmail(),u.getPassword()};
        int row = JDBCUtil.update(sql, params);
        System.out.println(sql);
    }

    //查询到有，返回true,没有返回false
    public boolean selectByEmail(User u) {
        String sql = "select * from users where email = ?";
        Object[] params = {u.getEmail()};
        JDBCUtil j = new JDBCUtil();
        ResultSet select = j.select(sql, params);

        try {
            if (select.next()) {
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean selectUser(User u) {
        String sql = "select * from users where email = ? and password = ?;";
        Object[] params = {u.getEmail(),u.getPassword()};
        JDBCUtil j = new JDBCUtil();
        ResultSet select = j.select(sql, params);

        try {
            if (select.next()) {
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
