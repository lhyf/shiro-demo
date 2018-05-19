package org.lhyf.dao;

import org.lhyf.entity.User;

import java.util.List;

public interface UserDao {
    String getPasswordByUserName(String userName);

    List<String> getRolesByUserName(String userName);
}
