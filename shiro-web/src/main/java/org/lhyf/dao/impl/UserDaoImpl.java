package org.lhyf.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.lhyf.dao.UserDao;
import org.lhyf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getPasswordByUserName(String userName) {
        String sql = "select password from t_user where username=?";


        String password = jdbcTemplate.queryForObject(sql, String.class, userName);
        return password;
    }

    @Override
    public  List<String> getRolesByUserName(String userName) {
        String sql = "select rolename from t_user_role where username=?";
        List<String> roles = jdbcTemplate.queryForList(sql, String.class, userName);
        return roles;
    }
}
