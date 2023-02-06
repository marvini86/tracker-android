package com.wzapps.mobile.utils;

import java.text.Normalizer;

public class Utils {

    public static  String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
