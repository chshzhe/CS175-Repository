package com.bytedance.network.model;

import com.google.gson.annotations.SerializedName;

public class Repo {
    // 仓库名
    @SerializedName("name")
    private String name;

    // 仓库描述
    @SerializedName("description")
    private String description;

    // 仓库 fork 数量
    @SerializedName("forks_count")
    private long forksCount;

    // 仓库 star 数量
    @SerializedName("stargazers_count")
    private long starsCount;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getForksCount() {
        return forksCount;
    }

    public long getStarsCount() {
        return starsCount;
    }
}

