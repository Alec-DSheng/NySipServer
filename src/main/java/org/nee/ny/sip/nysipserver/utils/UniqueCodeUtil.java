package org.nee.ny.sip.nysipserver.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.UUID;

/**
 * @Author: alec
 * Description:
 * @date: 15:17 2020-11-27
 */
public class UniqueCodeUtil {

    private static String base64Code(UUID uuid) {
        byte[] byUuid = new byte[16];
        long least = uuid.getLeastSignificantBits();
        long most =  uuid.getMostSignificantBits();
        long2bytes(most, byUuid, 0);
        long2bytes(least, byUuid, 8);
        return Base64.encodeBase64URLSafeString(byUuid);
    }

    private static void long2bytes(long value, byte[] bytes, int offset) {
        for (int i = 7; i > -1; i--) {
            bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
        }
    }

    public static String base64Code () {
        return base64Code(UUID.randomUUID());
    }


    public static String padLeft(String oriStr,int len, char alexIn){
        StringBuilder targetStr = new StringBuilder();
        int strLen = oriStr.length();
        if(strLen < len){
            for(int i = 0;i<len-strLen;i++){
                targetStr.append(alexIn);
            }
        }
        targetStr.insert(len-strLen, oriStr);
        return targetStr.toString();
    }
}
