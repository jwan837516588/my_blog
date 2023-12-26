package com.example.my_blog.enums;

public enum LatestOrRecommendEnum {
    Latest(0, "latest"), RECOMMEND(1, "recommend");

    private final int type;
    private final String name;

    LatestOrRecommendEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
