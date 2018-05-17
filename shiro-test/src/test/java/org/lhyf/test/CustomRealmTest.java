package org.lhyf.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.lhyf.realm.CustomRealm;

public class CustomRealmTest {

    CustomRealm realm = new CustomRealm();

    @Test
    public void testAuthentication() {
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashIterations(1024);
        matcher.setHashAlgorithmName("MD5");
        realm.setCredentialsMatcher(matcher);

        defaultSecurityManager.setRealm(realm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //2.主体提交认证请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("xiaoming", "123456");

        subject.login(token);
        System.out.println(subject.isAuthenticated());

        subject.checkRole("user");
        subject.checkPermission("/user/add");
        subject.logout();

        System.out.println(subject.isAuthenticated());
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","xiaoming",1024);
        System.out.println(md5Hash.toString());
    }
}
