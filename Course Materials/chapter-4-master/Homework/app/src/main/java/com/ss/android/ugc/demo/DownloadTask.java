package com.ss.android.ugc.demo;

import android.os.AsyncTask;
import android.widget.TextView;

class DownloadTask extends AsyncTask<String, Integer, String> {

    public static final String DOWNLOAD_FAIL = "download_fail";
    public static final String DOWNLOAD_SUCCESS = "download_success";

    private TextView mTextView;

    public DownloadTask(TextView textView) {
        mTextView = textView;
    }

    @Override protected void onPreExecute() {
        super.onPreExecute();
        mTextView.setText(R.string.start_download);
    }

    @Override protected String doInBackground(String... strings) {
        String url = strings[0];
        return download(url);
    }

    private String download(String videoId) {
        try {
            int progress = 0;
            while (progress < 100) {
                Thread.sleep(50);
                publishProgress(progress);
                progress++;
            }
            return DOWNLOAD_SUCCESS;
        } catch (Exception e) {
            return DOWNLOAD_FAIL;
        }
    }

    @Override protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mTextView.setText(mTextView.getContext().getResources().getString(R.string.download_progress, values[0]));
    }

    @Override protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (DOWNLOAD_FAIL.equals(s)) {
            mTextView.setText(R.string.download_fail);
        } else {
            mTextView.setText(R.string.download_success);
        }
    }
}
