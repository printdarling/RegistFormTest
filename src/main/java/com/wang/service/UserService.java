package com.wang.service;

import com.wang.dao.User;
import com.wang.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public static Connection conn;
    public static PreparedStatement ps;

    //查询到有，返回true,没有返回false
    public boolean selectByEmail(User u) {
        conn = JDBCUtils.getConnection();
        String sql = "select * from users where email = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,u.getEmail());

            ResultSet res = ps.executeQuery();

            while (res.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public void addUser(User u){
        conn = JDBCUtils.getConnection();
        String sql = "insert into users(email,password) values(?,?);";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,u.getEmail());
            ps.setString(2,u.getPassword());
            System.out.println(sql);

            int row = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean selectUser(User u) {
        conn = JDBCUtils.getConnection();
        String sql = "select * from users where email = ? and password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,u.getEmail());
            ps.setString(2,u.getPassword());

            System.out.println(sql);
            ResultSet res = ps.executeQuery();
            while (res.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
