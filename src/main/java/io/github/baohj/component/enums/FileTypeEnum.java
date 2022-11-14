package io.github.baohj.component.enums;

public enum FileTypeEnum {
    png(".png"),
    jpg(".jpg"),
    svg(".svg");

    private String type;

    FileTypeEnum(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
