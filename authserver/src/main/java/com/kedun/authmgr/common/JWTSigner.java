package com.kedun.authmgr.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.OperationNotSupportedException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 生成JWT  token
 */
public class JWTSigner {

    /**
     * 生成header
     * @param algorithm
     * @return
     */
    private String encodedHeader(Algorithm algorithm) throws UnsupportedEncodingException {
        if (algorithm == null) {
            algorithm = Algorithm.HS256;
        }
        ObjectNode header = JsonNodeFactory.instance.objectNode();
        header.put("typ", "JWT");
        header.put("alg", algorithm.name());
        return base64UrlEncode(header.toString().getBytes("UTF-8"));
    }

    /**
     *  生成dPayload
     * @throws Exception
     */
    private String encodedPayload(Map<String, Object> map) throws Exception {
        String payload = new ObjectMapper().writeValueAsString(map);
        return base64UrlEncode(payload.getBytes("UTF-8"));
    }

    private String encodedSignature(String signingInput, Algorithm algorithm) throws Exception {
        byte[] signature = sign(algorithm, signingInput);
        return base64UrlEncode(signature);
    }

    /**
     * 根据不同的算法生成签名
     * @param algorithm
     * @param msg
     * @return
     * @throws Exception
     */
    private static byte[] sign(Algorithm algorithm, String msg) throws Exception {
        switch (algorithm) {
            case HS256:
                return signHmac(algorithm, msg);
            case HS512:
                return signHmac(algorithm, msg);
            default:
                throw new OperationNotSupportedException("Unsupported signing method");
        }
    }

    private static byte[] signHmac(Algorithm algorithm, String msg) throws Exception {
        Mac mac = Mac.getInstance(algorithm.getValue());
        //使用随机数生成秘钥
        mac.init(new SecretKeySpec(RandomStringUtils.randomAlphabetic(22).getBytes(), algorithm.getValue()));
        return mac.doFinal(msg.getBytes());
    }

    private String base64UrlEncode(byte[] str) {
        return Base64.encodeBase64String(str);
    }

    /**
     *
     * 生成token签名 目前统一用HmacSHA256
     * @param map
     * @return
     */
    public String sign(Map<String, Object> map) {
        Algorithm algorithm = Algorithm.HS256;
        List<String> segments = new ArrayList<String>();
        try {
            //正常情况是header+payload进行签名，但这里仅使用payload签名，避免暴露具体加密内容
            String  payLoad=encodedPayload(map);
            segments.add(encodedHeader(algorithm));
            segments.add(payLoad);
            segments.add(encodedSignature(payLoad,algorithm));
        } catch (Exception e) {
             throw (e instanceof RuntimeException) ? (RuntimeException) e : new RuntimeException(e);
        }
        return join(segments, ".");
    }

    private String join(List<String> input, String on) {
        int size = input.size();
        int count = 1;
        StringBuilder joined = new StringBuilder();
        for (String string : input) {
            joined.append(string);
            if (count < size) {
                joined.append(on);
            }
            count++;
        }
        return joined.toString();
    }
}
