package com.domker.study.androidstudy;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    ViewPager pager = null;
    LayoutInflater layoutInflater = null;
    List<View> pages = new ArrayList<View>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        layoutInflater = getLayoutInflater();
        pager = (ViewPager) findViewById(R.id.view_pager);

        pager.post(new Runnable() {
            @Override
            public void run() {
                addImage(decodeBitmapFromResource(getResources(),
                        R.drawable.large,
                        200,
                        400));
                addImage(decodeBitmapFromVectorResource(R.drawable.ic_markunread));
                new ReadFileTask(pager.getWidth(), pager.getHeight()).execute("/sdcard/fileimage.jpg");
                new ReadAssetsTask(pager.getWidth(), pager.getHeight()).execute("assetsimage.jpg");
                new ReadRawTask(pager.getWidth(), pager.getHeight()).execute(R.raw.rawimage);
                loadNetImage(pager.getWidth(), pager.getHeight());

                ViewAdapter adapter = new ViewAdapter();
                adapter.setDatas(pages);
                pager.setAdapter(adapter);
            }
        });

    }

    private class ReadRawTask extends AsyncTask<Integer, Void, Bitmap> {
        int mWidth = 0;
        int mHeight = 0;
        ImageView imageView;

        ReadRawTask(int width, int height) {
            imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
            pages.add(imageView);
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            return decodeBitmapFromRaw(ImageActivity.this.getResources(), integers[0], mWidth, mHeight);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            addImageAsyn(imageView, bitmap);
        }
    }

    private class ReadFileTask extends AsyncTask<String, Void, Bitmap> {
        int mWidth = 0;
        int mHeight = 0;
        ImageView imageView;

        ReadFileTask(int width, int height) {
            imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
            pages.add(imageView);
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return decodeBitmapFromFile(strings[0],
                    mWidth,
                    mHeight);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            addImageAsyn(imageView, bitmap);
        }
    }

    private class ReadAssetsTask extends AsyncTask<String, Void, Bitmap> {
        int mWidth = 0;
        int mHeight = 0;
        ImageView imageView;

        ReadAssetsTask(int width, int height) {
            imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
            pages.add(imageView);
            mWidth = width;
            mHeight = height;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return decodeBitmapFromAssets(ImageActivity.this,
                    strings[0],
                    mWidth,
                    mHeight);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            addImageAsyn(imageView, bitmap);
        }
    }

    private void loadNetImage(final int width, final int height) {
        final ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        pages.add(imageView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = decodeBitmapFromNet("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562328963756&di=9c0c6c839381c8314a3ce8e7db61deb2&imgtype=0&src=http%3A%2F%2Fpic13.nipic.com%2F20110316%2F5961966_124313527122_2.jpg",
                        width,
                        height);
                ImageActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addImageAsyn(imageView, bitmap);
                    }
                });
            }
        }).start();
    }

    private void addImage(Bitmap bitmap) {
        ImageView imageView = (ImageView) layoutInflater.inflate(R.layout.activity_image_item, null);
        imageView.setImageBitmap(bitmap);
        pages.add(imageView);
    }

    private void addImageAsyn(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    private Bitmap decodeBitmapFromNet(String url, int reqWidth, int reqHeight) {
        InputStream is = null;
        byte[] data = null;
        try {
            URL imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            data = inputStreamToByteArray(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (data != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            return bitmap;
        } else {
            return null;
        }
    }

    public static byte[] inputStreamToByteArray(InputStream in) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

    private Bitmap decodeBitmapFromVectorResource(int resId) {
        Drawable drawable = getDrawable(resId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private Bitmap decodeBitmapFromFile(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    private Bitmap decodeBitmapFromRaw(Resources res, int resId, int reqWidth, int reqHeight) {
        InputStream is = res.openRawResource(resId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        try {
            is.reset();
        } catch (IOException e) {
            return null;
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private Bitmap decodeBitmapFromAssets(Context context, String fileName, int reqWidth, int reqHeight) {
        AssetManager asset = context.getAssets();
        InputStream is;
        try {
            is = asset.open(fileName);
        } catch (IOException e) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        try {
            is.reset();
        } catch (IOException e) {
            return null;
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
