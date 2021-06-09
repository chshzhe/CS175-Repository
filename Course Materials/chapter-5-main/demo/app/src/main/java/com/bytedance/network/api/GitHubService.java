package com.bytedance.network.api;

import com.bytedance.network.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    /**
     * 假设我们现在传入的 userName 参数为 JakeWharton，那么经过在程序运行时，经过 @Path 的匹配
     * 替换掉 {username} 之后就会变成 users/JakeWharton/repos
     */
    @GET("users/{username}/repos")
    Call<List<Repo>> getRepos(@Path("username") String userName,
                              @Query("page") int page,
                              @Query("per_page") int perPage,
                              @Header("accept") String accept);

}
