package com.tencent.wxcloudrun.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: son
 * @description: 微信
 * @author: fenghuohuo
 * @create: 2022-03-16 09:53
 **/
@RestController("/wechat")
public class WechatController {
    @GetMapping("/checkSignature")
    public String checkSignature(String signature, String timestamp, String nonce, String echostr)
        throws NoSuchAlgorithmException {
        ArrayList<String> strings = new ArrayList<>();

        strings.add(signature);
        strings.add(timestamp);
        strings.add(nonce);

        List<String> collect = strings.stream()
            .sorted()
            .collect(Collectors.toList());

        MessageDigest instance = MessageDigest.getInstance("SHA-1");

        String s = Hex.encodeHexString(instance.digest(String.join("", collect).getBytes()));

        if (s.equals(signature)) {
            return echostr;
        }
        return "";
    }
}
