package com.t2w.utils.common;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.t2w.utils.common.domain.EncryptionAlgorithm;
import com.t2w.utils.exception.EncryptionKeySizeException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @author T2W
 * @version V1.0.0
 * @email tang.wangwang@qq.com
 * @date 2019-08-08 10:07
 * @name com.t2w.utils.common.SecurityUtils.java
 * @see describing 加密工具类
 */
public class SecurityUtils {

    /** 编码格式；默认使用uft-8 */
    private static final String CHARSET = "UTF-8";

    /**
     * @param plaintext 待加密的文本（明文）
     * @param digest    摘要加密算法类型（支持的算法：MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-08 11:06
     * @see describing 使用 Java 自带的 MessageDigest 进行单向加密（摘要算法，不可逆，无密钥）
     */
    public static String messageDigest(String plaintext, EncryptionAlgorithm.Digest digest) {
        return messageDigest(plaintext, digest.getName());
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param algorithm 加密算法名称（支持的算法：MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-08 11:06
     * @see describing 使用 Java 自带的 MessageDigest 进行单向加密（摘要算法，不可逆，无密钥）
     */
    private static String messageDigest(String plaintext, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = plaintext.getBytes(CHARSET);
            return encodeBase64(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param hmac      散列消息鉴别码类型（支持的算法：HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512）
     * @param key       加密的密钥
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-08 13:46
     * @see describing 使用 Java 自带的 KeyGenerator 中的 Hmac 类型算法进行密钥加密（Hmac 算法，不可逆，有密钥）
     */
    public static String keyGenerator(String plaintext, EncryptionAlgorithm.Hmac hmac, String key) {
        return keyGenerator(plaintext, hmac.getName(), key);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param algorithm 加密算法名称（支持的算法：HmacMD5, HmacSHA1, HmacSHA256, HmacSHA384, HmacSHA512）
     * @param key       加密的密钥
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-08 13:46
     * @see describing 使用 Java 自带的 KeyGenerator 中的 Hmac(散列消息鉴别码) 类型算法进行密钥加密（不可逆，需要密钥）
     */
    private static String keyGenerator(String plaintext, String algorithm, String key) {
        try {
            SecretKey sk = null;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = key.getBytes(CHARSET);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(plaintext.getBytes());
            return encodeBase64(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param plaintext           待加密（解密）的文本（密明/文）
     * @param secretKeyEncryption 对称密钥加密算法类型（支持的算法：AES, ARCFOUR, Blowfish, DES, DESede, RC2, RC4）
     * @param encode              加密状态标志（true: 加密 ; false: 解密）
     * @return java.lang.String   加密（解密）后的文本（密/明文）
     * @date 2019-08-08 20:52
     * @see describing 使用 Java 自带的 KeyGenerator 中的对称密钥加密算法进行密钥加密/解密（默认密钥长度加密）
     */
    public static String keyGenerator(String plaintext, EncryptionAlgorithm.SecretKeyEncryption secretKeyEncryption, boolean encode) {
        return keyGenerator(plaintext, secretKeyEncryption.getName(), null, 0, encode);
    }

    /**
     * @param plaintext           待加密（解密）的文本（密明/文）
     * @param secretKeyEncryption 对称密钥加密算法类型（支持的算法：AES, ARCFOUR, Blowfish, DES, DESede, RC2, RC4）
     * @param key                 加密/解密的密钥
     * @param encode              加密状态标志（true: 加密 ; false: 解密）
     * @return java.lang.String   加密（解密）后的文本（密/明文）
     * @date 2019-08-08 20:56
     * @see describing 使用 Java 自带的 KeyGenerator 中的对称密钥加密算法进行密钥加密/解密（密钥加密）
     */
    public static String keyGenerator(String plaintext, EncryptionAlgorithm.SecretKeyEncryption secretKeyEncryption, String key, boolean encode) {
        return keyGenerator(plaintext, secretKeyEncryption.getName(), key, 0, encode);
    }

    /**
     * @param plaintext           待加密（解密）的文本（密明/文）
     * @param secretKeyEncryption 对称密钥加密算法类型（支持的算法：AES, ARCFOUR, Blowfish, DES, DESede, RC2, RC4）
     * @param size                加密/解密密钥长度
     * @return java.lang.String   加密（解密）后的文本（密/明文）
     * @date 2019-08-08 20:58
     * @see describing 使用 Java 自带的 KeyGenerator 中的对称密钥加密算法进行密钥加密/解密（密钥长度加密，使用系统随机数生成密钥，不可逆）
     */
    public static String keyGenerator(String plaintext, EncryptionAlgorithm.SecretKeyEncryption secretKeyEncryption, int size) {
        switch (secretKeyEncryption) {
            case AES:
                if (size != 128 && size != 192 && size != 256)
                    throw new EncryptionKeySizeException("size 必须是：128, 192, 256");
            case DES:
                if (size != 56)
                    throw new EncryptionKeySizeException("size 必须是：56");
            case RC2:
            case RC4:
            case ARCFOUR:
                if (size < 40 || size > 1024)
                    throw new EncryptionKeySizeException("size 必须在 40 - 1024 之间");
            case DESEDE:
                if (size != 112 && size != 168)
                    throw new EncryptionKeySizeException("size 必须是：112, 168");
            case BLOWFISH:
                if (size < 32 || size > 448 || size % 8 != 0)
                    throw new EncryptionKeySizeException("size 必须是 8 的倍数，并且只能在 32 到 448 之间（包括 32 和 448 ）");
        }
        return keyGenerator(plaintext, secretKeyEncryption.getName(), null, size, true);
    }

    /**
     * @param plaintext           待加密（解密）的文本（密明/文）
     * @param secretKeyEncryption 对称密钥加密算法类型（支持的算法：AES, ARCFOUR, Blowfish, DES, DESede, RC2, RC4）
     * @param key                 加密/解密的密钥（如果key为空则表示使用系统随机数初始化密钥，生成的密文不可解密）
     * @param size                加密/解密密钥长度
     * @param encode              加密状态标志（true: 加密 ; false: 解密）
     * @return java.lang.String    加密（解密）后的文本（密/明文）
     * @date 2019-08-08 20:59
     * @see describing 使用 Java 自带的 KeyGenerator 中的对称密钥加密算法进行密钥加密/解密（密钥和密钥长度加密）
     */
    public static String keyGenerator(String plaintext, EncryptionAlgorithm.SecretKeyEncryption secretKeyEncryption, String key, int size, boolean encode) {
        return keyGenerator(plaintext, secretKeyEncryption.getName(), key, size, encode);
    }

    /**
     * @param text      待加密（解密）的文本（密明/文）
     * @param algorithm 加密算法名称（支持的算法：AES, ARCFOUR, Blowfish, DES, DESede, RC2, RC4）
     * @param key       加密的秘钥（如果key为空则表示使用系统随机数初始化密钥，生成的密文不可解密）
     * @param size      生成秘钥的大小
     * @param encode    加密状态标志（true: 加密 ; false: 解密）
     * @return java.lang.String 加密（解密）后的文本（密/明文）
     * @date 2019-08-08 17:03
     * @see describing 使用 Java 自带的 KeyGenerator 中的对称密钥加密算法进行密钥加密/解密（可逆，可选是否需要密钥） <br/>
     * 注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     */
    private static String keyGenerator(String text, String algorithm, String key, int size, boolean encode) {
        String result = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            // 初始化 keyGenerator
            if (StringUtils.isNotEmpty(key)) {
                byte[] keyBytes = key.getBytes(CHARSET);
                assert PathUtils.getOS() != null;
                switch (PathUtils.getOS()) {
                    // Window中的KeyGenerator初始化用下面的方法
                    case WINDOWS:
                        if (size == 0) {
                            keyGenerator.init(new SecureRandom(keyBytes));
                        } else {
                            keyGenerator.init(size, new SecureRandom(keyBytes));
                        }
                        break;
                    // Linux中的KeyGenerator初始化用下面的方法
                    case LINUX:
                        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                        secureRandom.setSeed(keyBytes);
                        if (size == 0) {
                            keyGenerator.init(secureRandom);
                        } else {
                            keyGenerator.init(size, secureRandom);
                        }
                        break;
                }
            } else {
                keyGenerator.init(size);
            }
            SecretKey secretKey = keyGenerator.generateKey();
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (encode) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                byte[] plaintextBytes = text.getBytes(CHARSET);
                byte[] resultBytes = cipher.doFinal(plaintextBytes);
                result = StringUtils.toHexString(resultBytes);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                byte[] plaintextBytes = StringUtils.toBytes(text);
                byte[] resultBytes = cipher.doFinal(plaintextBytes);
                result = new String(resultBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 09:11
     * @see describing 使用 MD5 加密算法进行加密（不可逆，不需要密钥）
     */
    public static String MD5(String plaintext) {
        return messageDigest(plaintext, EncryptionAlgorithm.Digest.MD5);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param key       加密的密钥
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 09:11
     * @see describing 使用 MD5 加密算法进行加密（不可逆，需要密钥）
     */
    public static String MD5(String plaintext, String key) {
        return keyGenerator(plaintext, EncryptionAlgorithm.Hmac.HMAC_MD5, key);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     * @param res 需要加密的原文
     * @return
     */
    /**
     * @param plaintext 待加密的文本（明文）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 09:15
     * @see describing 使用 SHA1 加密算法进行加密（不可逆，不需要密钥）
     */
    public static String SHA1(String plaintext) {
        return messageDigest(plaintext, EncryptionAlgorithm.Digest.SHA1);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param key       加密的密钥
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 09:15
     * @see describing 使用 SHA1 加密算法进行加密（不可逆，需要密钥）
     */
    public static String SHA1(String plaintext, String key) {
        return keyGenerator(plaintext, EncryptionAlgorithm.Hmac.HMAC_SHA1, key);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 13:26
     * @see describing 使用 AES 加密算法进行加密（使用系统随机数初始化的密钥，不可逆）
     */
    public static String AES(String plaintext) {
        return AES(plaintext, 128);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param size      初始 KeyGenerator 时密钥大小。这是特定于算法的一种规格，是以位数为单位指定的
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 13:26
     * @see describing 使用 AES 加密算法进行加密（使用系统随机数初始化的密钥，不可逆）
     */
    public static String AES(String plaintext, int size) throws EncryptionKeySizeException {
        // size 为 128, 192, 256
        if (size != 128 && size != 192 && size != 256)
            throw new EncryptionKeySizeException("size 必须是：128, 192, 256");
        return keyGenerator(plaintext, EncryptionAlgorithm.SecretKeyEncryption.AES, null, size, true);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 11:45
     * @see describing 使用 DES 加密算法进行加密（使用系统随机数初始化的密钥，不可逆）
     */
    public static String DES(String plaintext) {
        return keyGenerator(plaintext, EncryptionAlgorithm.SecretKeyEncryption.DES, null, 56, true);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param key       加密的密钥
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 13:33
     * @see describing  使用 AES 加密算法经行加密（可逆，需要密钥）
     */
    public static String encodeAES(String plaintext, String key) {
        return keyGenerator(plaintext, EncryptionAlgorithm.SecretKeyEncryption.AES, key, 0, true);
    }

    /**
     * @param ciphertext 待解密的文本（密文）
     * @param key        加密的密钥
     * @return java.lang.String 解密后的文本（明文）
     * @date 2019-08-09 13:34
     * @see describing 对使用 AES 加密算法的密文进行解密（可逆）
     */
    public static String decodeAES(String ciphertext, String key) {
        return keyGenerator(ciphertext, EncryptionAlgorithm.SecretKeyEncryption.AES, key, 0, false);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param key       加密的密钥
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 13:35
     * @see describing  使用 DES 加密算法进行加密（可逆，需要密钥）
     */
    public static String encodeDES(String plaintext, String key) {
        return keyGenerator(plaintext, EncryptionAlgorithm.SecretKeyEncryption.DES, key, 0, true);
    }

    /**
     * @param ciphertext 待解密的文本（密文）
     * @param key        加密的密钥
     * @return java.lang.String 解密后的文本（明文）
     * @date 2019-08-09 13:35
     * @see describing   对使用 DES 加密算法的密文进行解密（可逆）
     */
    public static String decodeDES(String ciphertext, String key) {
        return keyGenerator(ciphertext, EncryptionAlgorithm.SecretKeyEncryption.DES, key, 0, false);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @param key       加密的密钥
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 13:37
     * @see describing 使用异或进行加密（可逆，需要密钥）
     */
    public static String encodeXOR(String plaintext, String key) {
        byte[] bs = plaintext.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return StringUtils.toHexString(bs);
    }

    /**
     * @param ciphertext 待解密的文本（密文）
     * @param key        加密的密钥
     * @return java.lang.String 解密后的文本（明文）
     * @date 2019-08-09 13:38
     * @see describing 使用异或进行解密（可逆，需要密钥）
     */
    public static String decodeXOR(String ciphertext, String key) {
        byte[] bs = StringUtils.toBytes(ciphertext);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     * @param src 待加密（解密）的内容（密明/文）
     * @param key 加密的密钥
     * @return int 加密（解密）后的内容（密/明文）
     * @date 2019-08-09 13:39
     * @see describing 直接使用异或（可逆，第一调用加密，第二次调用解密）
     */
    public static int XOR(int src, String key) {
        return src ^ key.hashCode();
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-08 11:27
     * @see describing 使用 Base64 进行加密，将 8bit 转化为 6bit，将所有字节都转化为可打印字符（可逆，无需密钥）
     */
    public static String encodeBase64(byte[] plaintext) {
        return Base64.encode(plaintext);
    }

    /**
     * @param plaintext 待加密的文本（明文）
     * @return java.lang.String 加密后的文本（密文）
     * @date 2019-08-09 13:42
     * @see describing 使用 Base64 进行加密（可逆，无需密钥）
     */
    public static String encodeBase64(String plaintext) {
        return encodeBase64(plaintext.getBytes());
    }

    /**
     * @param ciphertext 待解密的文本（密文）
     * @return java.lang.String 解密后的文本（明文）
     * @date 2019-08-09 13:43
     * @see describing 使用 Base64 进行解密（可逆，无需密钥）
     */
    public static String decodeBase64(String ciphertext) {
        return new String(Base64.decode(ciphertext));
    }

}
