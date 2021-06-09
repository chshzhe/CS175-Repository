package com.bytedance.practice5;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.practice5.model.UploadResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
    private static final int REQUEST_CODE_COVER_IMAGE = 101;
    private static final String COVER_IMAGE_TYPE = "image/*";
    private IApi api;
    private Uri coverImageUri;
    private SimpleDraweeView coverSD;
    private EditText toEditText;
    private EditText contentEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetwork();
        setContentView(R.layout.activity_upload);
        coverSD = findViewById(R.id.sd_cover);
        toEditText = findViewById(R.id.et_to);
        contentEditText = findViewById(R.id.et_content);
        findViewById(R.id.btn_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile(REQUEST_CODE_COVER_IMAGE, COVER_IMAGE_TYPE, "选择图片");
            }
        });


        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submitMessageWithURLConnection();
                submit();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_COVER_IMAGE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                coverImageUri = data.getData();
                coverSD.setImageURI(coverImageUri);

                if (coverImageUri != null) {
                    Log.d(TAG, "pick cover image " + coverImageUri.toString());
                } else {
                    Log.d(TAG, "uri2File fail " + data.getData());
                }

            } else {
                Log.d(TAG, "file pick fail");
            }
        }
    }

    private void initNetwork() {
        //TODO 3
        // 创建Retrofit实例
        // 生成api对象
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(IApi.class);
    }

    private void getFile(int requestCode, String type, String title) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, requestCode);
    }

    private void submit() {
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "请输入TA的名字", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入想要对TA说的话", Toast.LENGTH_SHORT).show();
            return;
        }

        if (coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO 5
        // 使用api.submitMessage()方法提交留言
        // 如果提交成功则关闭activity，否则弹出toast

        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<UploadResponse> call = api.submitMessage(
                        Constants.STUDENT_ID,
                        "",
                        MultipartBody.Part.createFormData("from", Constants.USER_NAME),
                        MultipartBody.Part.createFormData("to", to),
                        MultipartBody.Part.createFormData("content", content),
                        MultipartBody.Part.createFormData("image",
                                "cover.png",
                                RequestBody.create(MediaType.parse("multipart/form-data"), coverImageData)),
                        Constants.token);
                try {
                    Response<UploadResponse> response = call.execute();
                    if (response.isSuccessful() && response.body().success) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UploadActivity.this,
                                        "上传成功!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        UploadActivity.this.finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UploadActivity.this,
                                        "上传失败!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static Bitmap getAssetsBitmap(Context context, String path) {
        AssetManager am = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = am.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }


    private byte[] readDataFromUri(Uri uri) {
        byte[] data = null;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            data = Util.inputStream2bytes(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    // TODO 7 选做 用URLConnection的方式实现提交
    // 选做部分参考 `https://github.com/Smileglaze/bytedance-android-camp/blob/309e6ebb147d65cdf4ea0f8f0f34fa7e4b03c2a7/chapter5/app/src/main/java/com/bytedance/practice5/UploadActivity.java`
    
    private void submitMessageWithURLConnection() {
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "请输入TA的名字", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入想要对TA说的话", Toast.LENGTH_SHORT).show();
            return;
        }

        if (coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
            return;
        }
        if (coverImageData.length == 0) {
            Toast.makeText(this, "请添加图片", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                OutputStream os = null;
                String query = "?student_id=" + Constants.STUDENT_ID;

                String urlStr = String.format("%s%s%s", Constants.BASE_URL, "messages", query);
                UploadResponse RseponseResult = null;

                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(10 * 1000);
                    conn.setReadTimeout(10 * 1000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("token", Constants.token);
                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + Constants.BOUNDARY);
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.connect();

                    os = new DataOutputStream(conn.getOutputStream());
                    StringBuilder contentBody = new StringBuilder();
                    contentBody.append(Constants.PRE_FIX);
                    contentBody.append("Content-Disposition: form-data; name=\"from\"" + "\r\n");
                    contentBody.append("\r\n");
                    contentBody.append(Constants.USER_NAME);

                    contentBody.append(Constants.PRE_FIX);
                    contentBody.append("Content-Disposition: form-data; name=\"to\"" + "\r\n");
                    contentBody.append("\r\n");
                    contentBody.append(to);

                    contentBody.append(Constants.PRE_FIX);
                    contentBody.append("Content-Disposition: form-data; name=\"content\"" + "\r\n");
                    contentBody.append("\r\n");
                    contentBody.append(content);

                    os.write(contentBody.toString().getBytes());

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append(Constants.PRE_FIX);
                    strBuf.append("Content-Disposition: form-data; name=\"image\"; filename=\"cover.jpg\"\r\n");
                    strBuf.append("Content-Type: image/jpeg" + "\r\n\r\n");
                    os.write(strBuf.toString().getBytes());
                    os.write(coverImageData);

                    os.write(Constants.END_FIX.getBytes());
                    os.flush();

                    if (conn.getResponseCode() == 200) {
                        is = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                        RseponseResult = new Gson().fromJson(reader, new TypeToken<UploadResponse>() {
                        }.getType());
                        if (RseponseResult.success) {
                            makeUIToast(UploadActivity.this, "上传成功！", Toast.LENGTH_SHORT);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UploadActivity.this.finish();
                                }
                            });
                        } else {
                            makeUIToast(UploadActivity.this, "上传失败", Toast.LENGTH_SHORT);
                        }
                        reader.close();
                        is.close();
                    } else {
                    }
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    makeUIToast(UploadActivity.this, "上传失败" + e.toString(), Toast.LENGTH_SHORT);
                }
            }
        }).start();
    }

    private Map<String, MultipartBody.Part> getUploadData() {
        Map<String, MultipartBody.Part> UploadParts = new HashMap<String, MultipartBody.Part>();
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return UploadParts;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "请输入TA的名字", Toast.LENGTH_SHORT).show();
            return UploadParts;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入想要对TA说的话", Toast.LENGTH_SHORT).show();
            return UploadParts;
        }

        if (coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
            return UploadParts;
        }
        if (coverImageData.length == 0) {
            Toast.makeText(this, "请添加图片", Toast.LENGTH_SHORT).show();
            return UploadParts;
        }
        MultipartBody.Part coverPart = MultipartBody.Part.createFormData("image", "cover.png",
                RequestBody.create(MediaType.parse("multipart/form-data"), coverImageData));
        MultipartBody.Part fromPart = MultipartBody.Part.createFormData("from", Constants.USER_NAME);
        MultipartBody.Part toPart = MultipartBody.Part.createFormData("to", to);
        MultipartBody.Part contentPart = MultipartBody.Part.createFormData("content", content);
        UploadParts.put("image", coverPart);
        UploadParts.put("from", fromPart);
        UploadParts.put("to", toPart);
        UploadParts.put("content", contentPart);
        return UploadParts;
    }

    public void makeUIToast(Context context, String text, int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        });
    }


}


