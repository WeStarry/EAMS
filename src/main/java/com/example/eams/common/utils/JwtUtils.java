package com.example.eams.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业级 JWT 生成与校验工具类
 */
public class JwtUtils {

    // 1. 定义 Token 的有效生存期（Expiration Time），单位为毫秒。这里设置为 24 小时。
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    // 2. 定义服务器内部的绝对机密：哈希私钥（Secret Key）。
    // 按照 HMAC-SHA256 算法的底层要求，密钥字符串的字节长度必须大于等于 256 位（32 个字符）。
    private static final String SECRET_KEY_STRING = "EamsEnterpriseAssetManagementSystemSecretKey2026";

    // 3. 将字符串转换为符合底层加密规范的 Key 对象实例
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    /**
     * 生成 JWT 字符串的核心方法
     * @param id 用户底层主键
     * @param usercode 用户名
     * @param role 角色权限
     * @return 经过 Base64 编码并包含数字签名的 JWT 字符串
     */
    public static String generateToken(Long id, String usercode, String role) {

        // 4. 实例化 Payload（载荷）数据结构，将需要传递的业务属性压入内存 Map 中
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("usercode", usercode);
        claims.put("role", role);

        // 5. 调用 Jwts 构建器，执行底层加密与序列化
        return Jwts.builder()
                .setClaims(claims) // 注入 Payload
                .setIssuedAt(new Date()) // 记录 Token 的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 注入到期时间戳
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 注入私钥并指定 SHA-256 哈希算法执行签名
                .compact(); // 将 Header、Payload、Signature 进行 URL 安全的 Base64 编码，并拼接输出
    }
}