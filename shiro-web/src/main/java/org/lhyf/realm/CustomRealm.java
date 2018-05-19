package org.lhyf.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.lhyf.dao.UserDao;
import org.lhyf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CustomRealm extends AuthorizingRealm{

    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        Object principal = principals.getPrimaryPrincipal();

        //2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
        Set<String> roles = getRolesByUserName(principal.toString());

        Set<String> permissions = new HashSet<>();
//        permissions.add("/user/add");
        //3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);

        //4. 返回 SimpleAuthorizationInfo 对象.
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //1.获取从主体传过来的用户名
        String username = upToken.getUsername();

        //2.通过用户名从数据库中获取密码
        String password = userDao.getPasswordByUserName(username);
        if(password == null){
            throw new UnknownAccountException("没有这个用户");
        }

//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
        ByteSource byteSource = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,byteSource,getName());
        return info;
    }

    private Set<String> getRolesByUserName(String userName){
        System.out.println("从数据库中获取权限数据");

        List<String> roles = userDao.getRolesByUserName(userName);
        Set<String> set = new HashSet<>(roles);
        return set;
    }
}
