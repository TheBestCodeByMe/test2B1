package com.example.test2b1.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyCell {
    private String content;
    private String textColor;
    private String bgColor;
    private String textSize;
    private String textWeight;

    public MyCell() {
    }

    public MyCell(String content) {
        this.content = content;
    }
}
