package com.reactnativefacetec.ZoomProcessors;

public abstract class Processor {
    public abstract boolean isSuccess();

    public void updateLoadingUI(boolean success) {
    }

    public interface SessionTokenErrorCallback {
        void onError(String msg);
    }

    public interface SessionTokenSuccessCallback {
        void onSuccess(String msg);
    }
}

