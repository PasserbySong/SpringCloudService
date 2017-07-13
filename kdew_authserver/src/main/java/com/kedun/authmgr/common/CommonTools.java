package com.kedun.authmgr.common;


import com.kedun.authmgr.exception.ServiceException;
import org.jboss.logging.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonTools {

    public static  final String  systemCode="03003";

    private static Logger logger = Logger.getLogger(CommonTools.class);

    private static final char[] hexDigit = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static MessageDigest digest = null;
    private static Random randGen = new Random();
    private static char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static ServiceException createException(ErrorCodeEnum errorCodeEnum) {
        return new ServiceException(errorCodeEnum.getErrorMsg(),
                errorCodeEnum.getErrorCode());
    }

    public static ServiceException createException(ErrorCodeEnum errorCodeEnum, String prefix, String suffix) {
        return new ServiceException(prefix + errorCodeEnum.getErrorMsg() + suffix,
                errorCodeEnum.getErrorCode());
    }

    /**
     * 解析fileds过滤参数
     *
     * @param fields
     * @return
     */
    public static String analysisFields(String fields) {

        if(fields==null||fields.equals("null")){
            return "";
        }

        StringBuffer str = new StringBuffer();

        String[] array = fields.trim().split(",");

        for (String a : array) {
            str.append(fieldMapping(a));
            str.append(" " + a + " ,");
        }
        return str.length() == 0 ? "" : str.substring(0, str.lastIndexOf(",") - 1);
    }

    /**
     * 解析query过滤参数
     *
     * @param query
     * @return
     */
    public static String analysisQuery(String query) {
        try {
            if(query==null||query.equals("null")){
                return "";
            }
            StringBuffer str = new StringBuffer();

            String[] expressions = query.trim().split(",");

            for (int i = 0; i < expressions.length; i++) {

                String item = expressions[i].split(":")[0];
                String value = expressions[i].split(":")[1];

                str.append("and ");

                if (item.contains("__")) {
                    String key = item.split("__")[0];
                    String operator = item.split("__")[1];

                    str.append(fieldMapping(key)+" ");
                    switch (operator) {
                        case "gt":
                            str.append(" > " +value+" ");
                            break;
                        case "gte":
                            str.append(" >= "+value+" ");
                            break;
                        case "lt":
                            str.append(" < "+value+" ");
                            break;
                        case "lte":
                            str.append(" <= "+value+" ");
                            break;
                        case "isnull":
                            if("true".equals(value)){
                                str.append(" is null ");
                            }else if ("false".equals(value)){
                                str.append(" is not null ");
                            }else{
                                throw createException(ErrorCodeEnum.ParamsError, "query", "");
                            }
                            break;
                        case "startwith":
                            str.append("like binary '"+value+"%' ");
                            break;
                        case "istartwith":
                            str.append("like '"+value+"%' ");
                            break;
                        case "contains":
                            str.append("like binary '%"+value+"%' ");
                            break;
                        case "icontains":
                            str.append("like '%"+value+"%' ");
                            break;
                        case "endwith":
                            str.append("like binary '%"+value+"' ");
                        case "iendwith":
                            str.append("like '%"+value+"' ");
                            break;
                        case "iexact":
                            str.append("like binary '"+value+"' ");
                            break;
                        default:
                            throw CommonTools.createException(ErrorCodeEnum.ParamsError, "query", "");
                    }

                } else {
                    str.append(fieldMapping(item) + " = '"+value+"' ");
                }
            }
            return str.toString();
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
            throw CommonTools.createException(ErrorCodeEnum.ParamsError, "query", "");
        }
    }

    /**
     * 拼接排序sql段
     *
     * @param sortbyStr
     * @param orderStr
     * @return
     */
    public static String getOrderByStr(String sortbyStr, String orderStr) {

        if(sortbyStr==null||sortbyStr.equals("null")){
            return "";
        }
        StringBuffer orderby = new StringBuffer();

        String[] sortbys = sortbyStr.trim().split(",");

        String[] orders = orderStr.trim().split(",");

        for (int i = 0; i < sortbys.length; i++) {
            orderby.append(sortbys[i] + " " + orders[i]);
        }
        return orderby.toString();
    }

    public static boolean verifyAu(String ts,String au){
        String right=hash(ts + "www.szkedun@32168").toUpperCase();
        logger.info(right);
        if(!right.equals(au)){
           return false;
        }
        return true;
    }

    /**
     * 字段反映射
     *
     * @param field
     * @return
     */
    private static String fieldMapping(String field) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < field.length(); i++) {
            char c = field.charAt(i);
            if (i > 0 && Character.isUpperCase(c)) {
                str.append("_");
                c = Character.toLowerCase(c);
            }
            str.append(c);
        }

        return str.toString();

    }


    public static final synchronized String MD5(String data) {
        return hash(sha(hash(data)));
    }

    public static final synchronized String hash(String data) {
        if(digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var3) {
                ;
            }
        }

        try {
            digest.update(data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException var2) {
            ;
        }

        return encodeHex(digest.digest());
    }

    public static final synchronized String sha(String data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("SHA");
            md5.update(data.getBytes("utf-8"));
            return encodeHex(md5.digest());
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for(int i = 0; i < bytes.length; ++i) {
            if((bytes[i] & 255) < 16) {
                buf.append("0");
            }

            buf.append(Long.toString((long)(bytes[i] & 255), 16));
        }

        return buf.toString();
    }

    public static final byte[] decodeHex(String hex) {
        char[] chars = hex.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        int byteCount = 0;

        for(int i = 0; i < chars.length; i += 2) {
            byte newByte = 0;
            int var6 = newByte | hexCharToByte(chars[i]);
            var6 <<= 4;
            var6 |= hexCharToByte(chars[i + 1]);
            bytes[byteCount] = (byte)var6;
            ++byteCount;
        }

        return bytes;
    }

    private static final byte hexCharToByte(char ch) {
        switch(ch) {
            case '0':
                return (byte)0;
            case '1':
                return (byte)1;
            case '2':
                return (byte)2;
            case '3':
                return (byte)3;
            case '4':
                return (byte)4;
            case '5':
                return (byte)5;
            case '6':
                return (byte)6;
            case '7':
                return (byte)7;
            case '8':
                return (byte)8;
            case '9':
                return (byte)9;
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            default:
                return (byte)0;
            case 'a':
                return (byte)10;
            case 'b':
                return (byte)11;
            case 'c':
                return (byte)12;
            case 'd':
                return (byte)13;
            case 'e':
                return (byte)14;
            case 'f':
                return (byte)15;
        }
    }

    public static final String randomString(int length) {
        if(length < 1) {
            return null;
        } else {
            char[] randBuffer = new char[length];

            for(int i = 0; i < randBuffer.length; ++i) {
                randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
            }

            return new String(randBuffer);
        }
    }

    public static Timestamp parseDate(String dateString, String pattern) {
        if(dateString != null && !dateString.trim().equals("")) {
            Date d = null;
            SimpleDateFormat df = new SimpleDateFormat(pattern);

            try {
                d = df.parse(dateString);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            return d != null?new Timestamp(d.getTime()):null;
        } else {
            return null;
        }
    }

    public static long parseDateToLong(String dateString, String pattern) {
        long temp = 0L;
        if(isNotEmpty(dateString)) {
            Date d = null;
            SimpleDateFormat df = new SimpleDateFormat(pattern);

            try {
                d = df.parse(dateString);
            } catch (Exception var7) {
                var7.printStackTrace();
            }

            if(d != null) {
                return d.getTime();
            }
        }

        return temp;
    }

    public static String parseLongToDate(long date, String pattern) {
        String sDateTime = "";

        try {
            SimpleDateFormat e = new SimpleDateFormat(pattern);
            Date dt = new Date(date);
            sDateTime = e.format(dt);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return sDateTime;
    }

    public static boolean isEmpty(String[] str) {
        boolean temp = true;
        String[] arr$ = str;
        int len$ = str.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String s = arr$[i$];
            temp = isEmpty(s);
            if(temp) {
                break;
            }
        }

        return temp;
    }

    public static boolean isEmpty(String str) {
        boolean temp = true;
        if(null != str && !"".equals(str) && !"".equals(str.trim()) && !"null".equals(str.trim())) {
            temp = false;
        }

        return temp;
    }

    public static boolean isNotEmpty(String str) {
        boolean temp = false;
        if(null != str && !"".equals(str) && !"".equals(str.trim()) && !"null".equals(str.trim())) {
            temp = true;
        }

        return temp;
    }

    public static String trim(String str) {
        String trimStr = "";
        if(null != str && !"".equals(str)) {
            trimStr = str.trim();
        }

        return trimStr;
    }

    public static boolean matchEmail(String email) {
        boolean temp = false;
        if(isNotEmpty(email)) {
            temp = email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        }

        return temp;
    }

    public static boolean matchPhone(String phone) {
        boolean temp = false;
        if(isNotEmpty(phone)) {
            if(phone.startsWith("86+")) {
                phone = phone.substring("86+".length(), phone.length());
            } else if(phone.startsWith("+886")) {
                phone = phone.substring("+886".length(), phone.length());
            } else if(phone.startsWith("+852")) {
                phone = phone.substring("+852".length(), phone.length());
            } else if(phone.startsWith("+853")) {
                phone = phone.substring("+853".length(), phone.length());
            }

            temp = phone.matches("^[1][3-8]\\d{9}$");
            if(!temp) {
                temp = phone.matches("^[0][9]\\d{8}$");
            } else if(!temp) {
                temp = phone.matches("^[5]\\d{7}$");
            } else if(!temp) {
                temp = phone.matches("^[6]\\d{7}$");
            } else if(!temp) {
                temp = phone.matches("^[9]\\d{7}$");
            }
        }

        return temp;
    }

    public static boolean equals(String str, String str2) {
        return !isEmpty(str2) && !isEmpty(str)?str.equals(str2):false;
    }

    public static char getPYIndexChar(char strChinese, boolean bUpCase) {
        char result;
        if(strChinese >= '낡' && strChinese <= '냄') {
            result = 65;
        } else if(strChinese >= '냅' && strChinese <= '닀') {
            result = 66;
        } else if(strChinese >= '닁' && strChinese <= '듭') {
            result = 67;
        } else if(strChinese >= '듮' && strChinese <= '뛩') {
            result = 68;
        } else if(strChinese >= '뛪' && strChinese <= '랡') {
            result = 69;
        } else if(strChinese >= '랢' && strChinese <= '룀') {
            result = 70;
        } else if(strChinese >= '룁' && strChinese <= '맽') {
            result = 71;
        } else if(strChinese >= '맾' && strChinese <= '믶') {
            result = 72;
        } else if(strChinese >= '믷' && strChinese <= '뾥') {
            result = 74;
        } else if(strChinese >= '뾦' && strChinese <= '삫') {
            result = 75;
        } else if(strChinese >= '사' && strChinese <= '싧') {
            result = 76;
        } else if(strChinese >= '싨' && strChinese <= '쓂') {
            result = 77;
        } else if(strChinese >= '쓃' && strChinese <= '억') {
            result = 78;
        } else if(strChinese >= '얶' && strChinese <= '얽') {
            result = 79;
        } else if(strChinese >= '얾' && strChinese <= '웙') {
            result = 80;
        } else if(strChinese >= '웚' && strChinese <= '좺') {
            result = 81;
        } else if(strChinese >= '좻' && strChinese <= '죵') {
            result = 82;
        } else if(strChinese >= '죶' && strChinese <= '쯹') {
            result = 83;
        } else if(strChinese >= '쯺' && strChinese <= '췙') {
            result = 84;
        } else if(strChinese >= '췚' && strChinese <= '컳') {
            result = 87;
        } else if(strChinese >= '컴' && strChinese <= '톸') {
            result = 88;
        } else if(strChinese >= '톹' && strChinese <= '퓐') {
            result = 89;
        } else if(strChinese >= '퓑' && strChinese <= 'ퟹ') {
            result = 90;
        } else {
            result = (char)(65 + (new Random()).nextInt(25));
        }

        if(!bUpCase) {
            result = Character.toUpperCase(result);
        }

        return result;
    }

    private static char toHex(int nibble) {
        return hexDigit[nibble & 15];
    }

    public static String toEncodedUnicode(String theString, boolean escapeSpace) {
        int len = theString.length();
        int bufLen = len * 2;
        if(bufLen < 0) {
            bufLen = 2147483647;
        }

        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x = 0; x < len; ++x) {
            char aChar = theString.charAt(x);
            if(aChar > 61 && aChar < 127) {
                if(aChar == 92) {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                } else {
                    outBuffer.append(aChar);
                }
            } else {
                switch(aChar) {
                    case '\t':
                        outBuffer.append('\\');
                        outBuffer.append('t');
                        break;
                    case '\n':
                        outBuffer.append('\\');
                        outBuffer.append('n');
                        break;
                    case '\f':
                        outBuffer.append('\\');
                        outBuffer.append('f');
                        break;
                    case '\r':
                        outBuffer.append('\\');
                        outBuffer.append('r');
                        break;
                    case ' ':
                        if(x == 0 || escapeSpace) {
                            outBuffer.append('\\');
                        }

                        outBuffer.append(' ');
                        break;
                    case '!':
                    case '#':
                    case ':':
                    case '=':
                        outBuffer.append('\\');
                        outBuffer.append(aChar);
                        break;
                    default:
                        if(aChar >= 32 && aChar <= 126) {
                            outBuffer.append(aChar);
                        } else {
                            outBuffer.append('\\');
                            outBuffer.append('u');
                            outBuffer.append(toHex(aChar >> 12 & 15));
                            outBuffer.append(toHex(aChar >> 8 & 15));
                            outBuffer.append(toHex(aChar >> 4 & 15));
                            outBuffer.append(toHex(aChar & 15));
                        }
                }
            }
        }

        return outBuffer.toString();
    }

    public static String fromEncodeUnicode(String s) {
        String result = "";
        if(isNotEmpty(s)) {
            result = fromEncodedUnicode(s.toCharArray(), 0, s.length());
        }

        return result;
    }

    public static String fromDecodeUTF8(String s) {
        String result = "";
        if(isNotEmpty(s)) {
            try {
                result = URLDecoder.decode(s, "UTF-8");
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
            }
        }

        return result;
    }

    public static String encodeUTF8(String s) {
        String result = "";
        if(isNotEmpty(s)) {
            try {
                result = URLEncoder.encode(s, "UTF-8");
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
            }
        }

        return result;
    }

    public static String fromEncodedUnicode(char[] in, int off, int len) {
        char[] out = new char[len];
        int outLen = 0;
        int end = off + len;

        while(true) {
            while(true) {
                while(off < end) {
                    char aChar = in[off++];
                    if(aChar == 92) {
                        aChar = in[off++];
                        if(aChar == 117) {
                            int value = 0;

                            for(int i = 0; i < 4; ++i) {
                                aChar = in[off++];
                                switch(aChar) {
                                    case '0':
                                    case '1':
                                    case '2':
                                    case '3':
                                    case '4':
                                    case '5':
                                    case '6':
                                    case '7':
                                    case '8':
                                    case '9':
                                        value = (value << 4) + aChar - 48;
                                        break;
                                    case ':':
                                    case ';':
                                    case '<':
                                    case '=':
                                    case '>':
                                    case '?':
                                    case '@':
                                    case 'G':
                                    case 'H':
                                    case 'I':
                                    case 'J':
                                    case 'K':
                                    case 'L':
                                    case 'M':
                                    case 'N':
                                    case 'O':
                                    case 'P':
                                    case 'Q':
                                    case 'R':
                                    case 'S':
                                    case 'T':
                                    case 'U':
                                    case 'V':
                                    case 'W':
                                    case 'X':
                                    case 'Y':
                                    case 'Z':
                                    case '[':
                                    case '\\':
                                    case ']':
                                    case '^':
                                    case '_':
                                    case '`':
                                    default:
                                        throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                                    case 'A':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                        value = (value << 4) + 10 + aChar - 65;
                                        break;
                                    case 'a':
                                    case 'b':
                                    case 'c':
                                    case 'd':
                                    case 'e':
                                    case 'f':
                                        value = (value << 4) + 10 + aChar - 97;
                                }
                            }

                            out[outLen++] = (char)value;
                        } else {
                            if(aChar == 116) {
                                aChar = 9;
                            } else if(aChar == 114) {
                                aChar = 13;
                            } else if(aChar == 110) {
                                aChar = 10;
                            } else if(aChar == 102) {
                                aChar = 12;
                            }

                            out[outLen++] = aChar;
                        }
                    } else {
                        out[outLen++] = aChar;
                    }
                }

                return new String(out, 0, outLen);
            }
        }
    }

    public static Date formatDateYMDToDate(Date date) {
        String format = "yyyy-MM-dd";
        String dateStr = formatDateTime(date, format);
        return parse(dateStr, format);
    }

    public static Date getCurrentDateYMD() {
        String format = "yyyy-MM-dd";
        String date = formatDateTime(new Date(), format);
        return parse(date, format);
    }

    public static String formatDateTime(Date date, String format) {
        SimpleDateFormat timeFormator = new SimpleDateFormat(format);
        return timeFormator.format(date);
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date2 = null;
        if(date != null && !"".equals(date.trim())) {
            try {
                date2 = sdf.parse(date);
            } catch (ParseException var5) {
                var5.printStackTrace();
            }
        }

        return date2;
    }

    public static String parse(Date date, String format) {
        String temp = "";
        if(date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            try {
                temp = sdf.format(date);
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

        return temp;
    }

    public static String getGUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static Long getCurTimestamp() {
        return Long.valueOf(System.currentTimeMillis());
    }

}
