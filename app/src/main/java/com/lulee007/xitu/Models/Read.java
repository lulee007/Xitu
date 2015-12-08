package com.lulee007.xitu.Models;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * User: lulee007@live.com
 * Date: 2015-12-07
 * Time: 21:36
 */


@AVClassName("View")
public class Read extends AVObject {
    public Read() {
    }

    public Read(Entry var1) {
        this.put("entry", var1);
    }

    public Read(String var1) {
        super(var1);
    }
}
