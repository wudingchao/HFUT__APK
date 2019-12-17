package com.example.hpdemo.service;

public interface DownloadResult {
    void onDownloadProcess(String paramString, long paramLong);

    void onDownloadResult(String paramString, boolean paramBoolean);
}
