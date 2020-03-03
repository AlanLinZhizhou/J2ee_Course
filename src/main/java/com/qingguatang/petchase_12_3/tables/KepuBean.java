package com.qingguatang.petchase_12_3.tables;

public class KepuBean {
    private String k_icon;
    private String k_releaser;
    private String k_content;
    private String  k_trust;
    public KepuBean(String k_icon,String k_releaser,String k_content,String  k_trust){
        this.k_icon=k_icon;
        this.k_releaser=k_releaser;
        this.k_content=k_content;
        this.k_trust=k_trust;
    }
    public String getK_icon() {
        return k_icon;
    }

    public void setK_icon(String k_icon) {
        this.k_icon = k_icon;
    }

    public String getK_releaser() {
        return k_releaser;
    }

    public void setK_releaser(String k_releaser) {
        this.k_releaser = k_releaser;
    }

    public String getK_content() {
        return k_content;
    }

    public void setK_content(String k_content) {
        this.k_content = k_content;
    }

    public String getK_trust() {
        return k_trust;
    }

    public void setK_trust(String k_trust) {
        this.k_trust = k_trust;
    }
}
