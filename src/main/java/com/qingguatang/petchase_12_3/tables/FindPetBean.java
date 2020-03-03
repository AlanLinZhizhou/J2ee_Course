package com.qingguatang.petchase_12_3.tables;

public class FindPetBean {
    private String icon;
    private String releaser;
    private String re_content;
    private String re_contact;

    public FindPetBean(String icon, String releaser, String re_content, String re_contact) {
        this.icon = icon;
        this.releaser = releaser;
        this.re_content = re_content;
        this.re_contact = re_contact;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getReleaser() {
        return releaser;
    }

    public void setReleaser(String releaser) {
        this.releaser = releaser;
    }

    public String getRe_content() {
        return re_content;
    }

    public void setRe_content(String re_content) {
        this.re_content = re_content;
    }

    public String getRe_contact() {
        return re_contact;
    }

    public void setRe_contact(String re_contact) {
        this.re_contact = re_contact;
    }


}
