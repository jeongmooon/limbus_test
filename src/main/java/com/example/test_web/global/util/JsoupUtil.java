package com.example.test_web.global.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class JsoupUtil {
    public static String replaceTag(String content, String targetTag){
        return Jsoup.clean(content, Safelist.relaxed().removeTags(targetTag));
    }

    public static String safeXss(String content){
        return Jsoup.clean(content, Safelist.basic());
    }
}
