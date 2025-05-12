package com.example.test_web.global.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    // bcrypt 전용 Base64 문자셋
    private static final String BCRYPT_BASE64_CODE = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String getSha256(String pass) {
        String SHA="";
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(pass.getBytes());
            byte [] byteData = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(byte aByteData:byteData) {
                //System.out.println(aByteData+" "+Integer.toString((aByteData & 0xff)+0*100,16));
                sb.append(Integer.toString((aByteData & 0xff)
						/*
						byte&0xff
						비트연산자 &을 수행하는 경우 비트수가 넓은 곳에 맞춰서 낮은 비트를 가진 자료형을 확장한다.
						즉, 위의 식을 실행하는 경우, byte는 32비트의 int형으로 강제 형변환이 된다.
						*/
                        +0x100,  //강제로 3자리 숫자를 만들어 주는 식. 강제로 0x100을 더하여 준다.
                        16).substring(1));
            }
            SHA=sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            SHA=null;
        }
        return SHA;
    }

    public static String getBCrypt(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public static String getBCryptBySalt(String pass, String salt){
        return BCrypt.hashpw(pass, salt);
    }

    public static String getSalt(String pass) {
        String salt = pass.split("\\$")[3].substring(0, 22);
        int maxLength = 16;

        byte[] dest = new byte[maxLength];
        int pos = 0;
        int i = 0;
        while (i < salt.length() && pos < maxLength) {
            int c1 = BCRYPT_BASE64_CODE.indexOf(salt.charAt(i++));
            int c2 = BCRYPT_BASE64_CODE.indexOf(salt.charAt(i++));
            dest[pos++] = (byte)((c1 << 2) | ((c2 & 0x30) >> 4));
            if (pos >= maxLength || i >= salt.length()) break;
            int c3 = BCRYPT_BASE64_CODE.indexOf(salt.charAt(i++));
            dest[pos++] = (byte)(((c2 & 0x0f) << 4) | ((c3 & 0x3c) >> 2));
            if (pos >= maxLength || i >= salt.length()) break;
            int c4 = BCRYPT_BASE64_CODE.indexOf(salt.charAt(i++));
            dest[pos++] = (byte)(((c3 & 0x03) << 6) | c4);
        }
        return bytesToHex(dest);
    }

    public static String baseEncodeSalt(String str){
        byte[] data = hexToBytes(str);
        int length = data.length;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < length) {
            int c1 = data[i++] & 0xff;
            sb.append(BCRYPT_BASE64_CODE.charAt((c1 >> 2) & 0x3f));
            c1 = (c1 & 0x03) << 4;
            if (i >= length) {
                sb.append(BCRYPT_BASE64_CODE.charAt(c1 & 0x3f));
                break;
            }
            int c2 = data[i++] & 0xff;
            c1 |= (c2 >> 4) & 0x0f;
            sb.append(BCRYPT_BASE64_CODE.charAt(c1 & 0x3f));
            c1 = (c2 & 0x0f) << 2;
            if (i >= length) {
                sb.append(BCRYPT_BASE64_CODE.charAt(c1 & 0x3f));
                break;
            }
            int c3 = data[i++] & 0xff;
            c1 |= (c3 >> 6) & 0x03;
            sb.append(BCRYPT_BASE64_CODE.charAt(c1 & 0x3f));
            sb.append(BCRYPT_BASE64_CODE.charAt(c3 & 0x3f));
        }
        return sb.toString();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        if (len % 2 != 0) throw new IllegalArgumentException("헥사 문자열 길이는 짝수여야 합니다.");

        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
