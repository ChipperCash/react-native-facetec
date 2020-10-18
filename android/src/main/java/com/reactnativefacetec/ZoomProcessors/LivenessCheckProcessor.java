// Demonstrates performing a Liveness Check.

// The FaceTec Device SDKs will cancel from the Progress Screen if onProgress() is not called for
// 60 seconds. This provides a failsafe for users getting stuck in the process because of a networking
// issue. If you would like to force users to stay on the Progress Screen for longer than 60 seconds,
// you can write code in the FaceMap or ID Scan Processor to call onProgress() via your own custom logic.
package com.reactnativefacetec.ZoomProcessors;

import android.content.Context;

import com.facetec.zoom.sdk.ZoomCustomization;
import com.facetec.zoom.sdk.ZoomFaceMapProcessor;
import com.facetec.zoom.sdk.ZoomFaceMapResultCallback;
import com.facetec.zoom.sdk.ZoomSessionActivity;
import com.facetec.zoom.sdk.ZoomSessionResult;
import com.facetec.zoom.sdk.ZoomSessionStatus;
import com.facetec.zoom.sdk.ZoomSDK;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class LivenessCheckProcessor extends Processor implements ZoomFaceMapProcessor {
    ZoomFaceMapResultCallback zoomFaceMapResultCallback;
    ZoomSessionResult latestZoomSessionResult;
    SessionTokenSuccessCallback sessionTokenSuccessCallback;
    SessionTokenErrorCallback sessionTokenErrorCallback;
    private boolean _isSuccess = false;

    public LivenessCheckProcessor(final Context context, final SessionTokenErrorCallback sessionTokenErrorCallback, SessionTokenSuccessCallback sessionTokenSuccessCallback) {
        this.sessionTokenSuccessCallback = sessionTokenSuccessCallback;
        this.sessionTokenErrorCallback = sessionTokenErrorCallback;
        NetworkingHelpers.getSessionToken(new NetworkingHelpers.SessionTokenCallback() {
            @Override
            public void onResponse(String sessionToken) {
                // Launch the ZoOm Session.
                ZoomSessionActivity.createAndLaunchZoomSession(context, LivenessCheckProcessor.this, sessionToken);
            }

            @Override
            public void onError() {
                sessionTokenErrorCallback.onError("LivenessCheckProcessor");
            }
        });
    }

    public boolean isSuccess() {
        return _isSuccess;
    }

    // Update zoom loading UI to either success or failed
    public void updateLoadingUI(final boolean success) {
        if (success) {
            ZoomCustomization.overrideResultScreenSuccessMessage = "Liveness\nConfirmed";
            this.zoomFaceMapResultCallback.succeed();
        } else {
            this.zoomFaceMapResultCallback.retry();
        }
    }

    // Required function that handles calling ZoOm Server to get result and decides how to continue.
    public void processZoomSessionResultWhileZoomWaits(final ZoomSessionResult zoomSessionResult, final ZoomFaceMapResultCallback zoomFaceMapResultCallback) {
        this.latestZoomSessionResult = zoomSessionResult;
        this.zoomFaceMapResultCallback = zoomFaceMapResultCallback;

        // Cancel last request in flight.  This handles case where processing is is taking place but cancellation or Context Switch occurs.
        // Our handling here ends the latest in flight request and simply re-does the normal logic, which will cancel out.
        NetworkingHelpers.cancelPendingRequests();

        // cancellation, timeout, etc.
        if (zoomSessionResult.getStatus() != ZoomSessionStatus.SESSION_COMPLETED_SUCCESSFULLY) {
            sessionTokenErrorCallback.onError(zoomSessionResult.getStatus().toString());
            zoomFaceMapResultCallback.cancel();
            this.zoomFaceMapResultCallback = null;
            return;
        }

        JSONObject result = new JSONObject();
        JSONObject images = new JSONObject();
        String base64FaceMapImage = zoomSessionResult.getFaceMetrics().getFaceMapBase64();

        try {
            images.put("base64FaceMapImage", base64FaceMapImage);
            if (zoomSessionResult.getFaceMetrics().getAuditTrailCompressedBase64().length > 0) {
                String compressedBase64AuditTrailImage = zoomSessionResult.getFaceMetrics().getAuditTrailCompressedBase64()[0];
                images.put("base64AuditTrailImage", compressedBase64AuditTrailImage);
            }

            if (zoomSessionResult.getFaceMetrics().getLowQualityAuditTrailCompressedBase64().length > 0) {
                String lowQualityBase64AuditTrailImage = zoomSessionResult.getFaceMetrics().getLowQualityAuditTrailCompressedBase64()[0];
                images.put("base64LowQualityAuditTrailImage", lowQualityBase64AuditTrailImage);
            }

            result.put("base64Images", images);
            result.put("zoomSessionId", zoomSessionResult.getSessionId());
            result.put("zoomAPIUserAgent", ZoomSDK.createZoomAPIUserAgentString(""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sessionTokenSuccessCallback.onSuccess(result.toString());

        // Create and parse request to ZoOm Server.
        // NetworkingHelpers.getLivenessCheckResponseFromZoomServer(zoomSessionResult, this.zoomFaceMapResultCallback, new FaceTecManagedAPICallback() {
        //     @Override
        //     public void onResponse(JSONObject responseJSON) {
        //         UXNextStep nextStep = ServerResultHelpers.getLivenessNextStep(responseJSON);

        //         if (nextStep == UXNextStep.Succeed) {
        //             _isSuccess = true;

        //             JSONObject result = new JSONObject();
        //             JSONObject images = new JSONObject();
        //             String base64FaceMapImage = zoomSessionResult.getFaceMetrics().getFaceMapBase64();

        //             try {
        //                 images.put("base64FaceMapImage", base64FaceMapImage);
        //                 if (zoomSessionResult.getFaceMetrics().getAuditTrailCompressedBase64().length > 0) {
        //                     String compressedBase64AuditTrailImage = zoomSessionResult.getFaceMetrics().getAuditTrailCompressedBase64()[0];
        //                     images.put("base64AuditTrailImage", compressedBase64AuditTrailImage);
        //                 }

        //                 if (zoomSessionResult.getFaceMetrics().getLowQualityAuditTrailCompressedBase64().length > 0) {
        //                     String lowQualityBase64AuditTrailImage = zoomSessionResult.getFaceMetrics().getLowQualityAuditTrailCompressedBase64()[0];
        //                     images.put("base64LowQualityAuditTrailImage", lowQualityBase64AuditTrailImage);
        //                 }
        //                 result.put("base64Images", images);
        //                 result.put("livenessCheckResponse", responseJSON);
        //                 result.put("zoomSessionId", zoomSessionResult.getSessionId());
        //                 result.put("zoomAPIUserAgent", ZoomSDK.createZoomAPIUserAgentString(""));
        //             } catch (JSONException e) {
        //                 e.printStackTrace();
        //             }

        //             sessionTokenSuccessCallback.onSuccess(result.toString());
        //             ZoomCustomization.overrideResultScreenSuccessMessage = "Liveness\nConfirmed";
        //             zoomFaceMapResultCallback.succeed();
        //         }
        //         else if (nextStep == UXNextStep.Retry) {
        //             zoomFaceMapResultCallback.retry();
        //         }
        //         else {
        //             zoomFaceMapResultCallback.cancel();
        //         }
        //     }
        // });

    }
}
