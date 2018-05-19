package org.lhyf.test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Test {
    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","tom",1024);
        System.out.println(md5Hash);
    }
}
