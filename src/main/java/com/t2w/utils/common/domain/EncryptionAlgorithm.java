package com.t2w.utils.common.domain;

import lombok.Getter;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-08 10:13
 * @name com.t2w.utils.common.domain.Encryption.java
 * @see describing
 */
public class EncryptionAlgorithm {

    /**
     * @date 2019-08-08 14:14
     * @see describing 摘要算法(不可逆算法，无需密钥)
     */
    @Getter
    public enum Digest {

        /** MD2 加密算法 */
        MD2("MD2"),
        /** MD5 加密算法 */
        MD5("MD5"),
        /** SHA-1 加密算法 */
        SHA1("SHA-1"),
        /** SHA-256 加密算法 */
        SHA256("SHA-256"),
        /** SHA-384 加密算法 */
        SHA384("SHA-384"),
        /** SHA-512 加密算法 */
        SHA512("SHA-512");

        Digest(String name) {
            this.name = name;
        }

        private String name;

    }

    /**
     * @date 2019-08-08 14:18
     * @see describing 散列消息鉴别码(不可逆算法，需要密钥)
     */
    @Getter
    public enum Hmac {

        /** HmacMD5 加密算法 */
        HMAC_MD5("HmacMD5"),
        /** HmacSHA1 加密算法 */
        HMAC_SHA1("HmacSHA1"),
        /** HmacSHA256 加密算法 */
        HMAC_SHA256("HmacSHA256"),
        /** HmacSHA384 加密算法 */
        HMAC_SHA384("HmacSHA384"),
        /** HmacSHA512 加密算法 */
        HMAC_SHA512("HmacSHA512");

        Hmac(String name) {
            this.name = name;
        }

        private String name;
    }

    /**
     * @date 2019-08-08 16:58
     * @see describing 对称密钥加密算法(可逆，需要密钥)
     */
    @Getter
    public enum SecretKeyEncryption {

        /** AES 算法 */
        AES("AES"),
        /** ARCFOUR 算法 */
        ARCFOUR("ARCFOUR"),
        /** Blowfish 算法 */
        BLOWFISH("Blowfish"),
        /** DES 算法 */
        DES("DES"),
        /** DESede 算法 */
        DESEDE("DESede"),
        /** RC2 算法 */
        RC2("RC2"),
        /** RC4 算法 */
        RC4("RC4");

        SecretKeyEncryption(String name) {
            this.name = name;
        }

        private String name;

    }

}
