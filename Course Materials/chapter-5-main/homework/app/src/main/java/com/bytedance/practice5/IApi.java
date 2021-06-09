package com.bytedance.practice5;


import com.bytedance.practice5.model.UploadResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IApi {

    //TODO 4
    // 补全所有注解
    Call<UploadResponse> submitMessage(String studentId,
                                     String extraValue,
                                     MultipartBody.Part from,
                                     MultipartBody.Part to,
                                     MultipartBody.Part content,
                                     MultipartBody.Part image,
                                     String token);
}
