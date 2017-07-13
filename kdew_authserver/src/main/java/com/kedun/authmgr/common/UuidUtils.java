package com.kedun.authmgr.common;

import org.apache.commons.codec.binary.Base64;

import java.util.UUID;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
public class UuidUtils {

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return compressedUUID(uuid);
    }

    private static String compressedUUID(UUID uuid) {
        byte[] byUuid = new byte[16];
        long least = uuid.getLeastSignificantBits();
        long most = uuid.getMostSignificantBits();
        long2bytes(most, byUuid, 0);
        long2bytes(least, byUuid, 8);
        String compressUUID = Base64.encodeBase64URLSafeString(byUuid);
        return compressUUID;
    }

    public static void long2bytes(long value, byte[] bytes, int offset) {
        for(int i = 7; i > -1; --i) {
            bytes[offset++] = (byte)((int)(value >> 8 * i & 255L));
        }

    }

    public static String compress(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return compressedUUID(uuid);
    }

    public static String uncompress(String compressedUuid) {
        if(compressedUuid.length() != 22) {
            throw new IllegalArgumentException("Invalid uuid!");
        } else {
            byte[] byUuid = Base64.decodeBase64(compressedUuid + "==");
            long most = bytes2long(byUuid, 0);
            long least = bytes2long(byUuid, 8);
            UUID uuid = new UUID(most, least);
            return uuid.toString();
        }
    }

    public static long bytes2long(byte[] bytes, int offset) {
        long value = 0L;

        for(int i = 7; i > -1; --i) {
            value |= ((long)bytes[offset++] & 255L) << 8 * i;
        }

        return value;
    }

}
