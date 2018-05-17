package org.lhyf.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm{

    private Map<String,String> userMap = new HashMap<String,String>();
    {
        userMap.put("xiaoming","dd845e5e5412ba7752defdf383bf394f");
        userMap.put("xiaohong","123456");
        userMap.put("xiaoqing","123456");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        Object principal = principals.getPrimaryPrincipal();

        //2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
        Set<String> roles = new HashSet<>();
        roles.add("user");
        if("admin".equals(principal)){
            roles.add("admin");
        }

        Set<String> permissions = new HashSet<>();
        permissions.add("/user/add");
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
        String password = getPasswordByUserName(username);
        if(password == null){
            throw new UnknownAccountException("没有这个用户");
        }

//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
        ByteSource byteSource = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,byteSource,getName());
        return info;
    }

    //模拟数据库
    private String getPasswordByUserName(String userName){
        return userMap.get(userName);
    }
}
