package org.lhyf.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    public void set(byte[] key, byte[] value) {
        Jedis jedis = getResource();

        try {
            jedis.set(key, value);
        } finally {
            if (jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();

        try {
            jedis.expire(key, i);
        } finally {
            if (jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getResource();

        try {
            return jedis.get(key);
        } finally {
            if (jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getResource();

        try {
            jedis.del(key);
        } finally {
            if (jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    public Set<byte[]> keys(String prefix) {
        Jedis jedis = getResource();
        try {
            return jedis.keys((prefix+"*").getBytes());
        }finally {
            if (jedis.isConnected()){
                jedis.close();
            }
        }
    }
}
