package io.github.baohj.component.enums;

public enum UploadEnum {

    nginx("nginx");

    private String type;

    UploadEnum(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
