//
// Welcome to the annotated FaceTec Device SDK core code for performing secure Liveness Checks!
//
package com.reactnativefacetec.Processors;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import com.facetec.sdk.*;

// This is an example self-contained class to perform Liveness Checks with the FaceTec SDK.
// You may choose to further componentize parts of this in your own Apps based on your specific requirements.

// Android Note 1:  Some commented "Parts" below are out of order so that they can match iOS and Browser source for this same file on those platforms.
// Android Note 2:  Android does not have a onFaceTecSDKCompletelyDone function that you must implement like "Part 10" of iOS and Android Samples.  Instead, onActivityResult is used as the place in code you get control back from the FaceTec SDK.
public class LivenessCheckProcessor extends Processor implements FaceTecFaceScanProcessor {
  private boolean isSuccess = false;
  LivenessCheckCallback callback;
  FaceTecSessionResult sessionResult;
  FaceTecFaceScanResultCallback scanResultCallback;

  public interface LivenessCheckCallback {
    void onSuccess(String faceScanResult);
    void onError(String error);
  }
//    private SampleAppActivity sampleAppActivity;

  public LivenessCheckProcessor(String sessionToken, Context context, LivenessCheckCallback callback) {
//        this.sampleAppActivity = (SampleAppActivity) context;
    this.callback = callback;

    //
    // Part 1:  Starting the FaceTec Session
    //
    // Required parameters:
    // - Context:  Unique for Android, a Context is passed in, which is required for the final onActivityResult function after the FaceTec SDK is done.
    // - FaceTecFaceScanProcessor:  A class that implements FaceTecFaceScanProcessor, which handles the FaceScan when the User completes a Session.  In this example, "self" implements the class.
    // - sessionToken:  A valid Session Token you just created by calling your API to get a Session Token from the Server SDK.
    //
    FaceTecSessionActivity.createAndLaunchSession(context, LivenessCheckProcessor.this, sessionToken);
  }

  // Update zoom loading UI to either success or failed
  public void updateLoadingUI(final boolean success) {
    Log.d("ZoomSDK", "LIVENESS - called update loading UI");
    if (success) {
      Log.d("ZoomSDK", "LIVENESS - SUCCESS - finish");
      FaceTecCustomization.overrideResultScreenSuccessMessage = "Liveness\nConfirmed";
      this.scanResultCallback.succeed();
    } else {
      Log.d("ZoomSDK", "LIVENESS - FAILED - retry");
      this.scanResultCallback.retry();
    }
  }

  //
  // Part 2:  Handling the Result of a FaceScan
  //
  public void processSessionWhileFaceTecSDKWaits(final FaceTecSessionResult sessionResult, final FaceTecFaceScanResultCallback faceScanResultCallback) {
    //
    // DEVELOPER NOTE:  These properties are for demonstration purposes only so the Sample App can get information about what is happening in the processor.
    // In the code in your own App, you can pass around signals, flags, intermediates, and results however you would like.
    //
    this.sessionResult = sessionResult;
    this.scanResultCallback = faceScanResultCallback;

    //
    // Part 3:  Handles early exit scenarios where there is no FaceScan to handle -- i.e. User Cancellation, Timeouts, etc.
    //
    if(sessionResult.getStatus() != FaceTecSessionStatus.SESSION_COMPLETED_SUCCESSFULLY) {
      NetworkingHelpers.cancelPendingRequests();
      faceScanResultCallback.cancel();
      return;
    }

    // IMPORTANT:  FaceTecSDK.FaceTecSessionStatus.SessionCompletedSuccessfully DOES NOT mean the Liveness Check was Successful.
    // It simply means the User completed the Session and a 3D FaceScan was created.  You still need to perform the Liveness Check on your Servers.

    //
    // Part 4:  Get essential data off the FaceTecSessionResult
    //
    JSONObject result = new JSONObject();
    JSONObject images = new JSONObject();
    String base64FaceMapImage = sessionResult.getFaceScanBase64();

    try {
      images.put("base64FaceMapImage", base64FaceMapImage);
      if (sessionResult.getAuditTrailCompressedBase64().length > 0) {
        String compressedBase64AuditTrailImage = sessionResult.getAuditTrailCompressedBase64()[0];
        images.put("base64AuditTrailImage", compressedBase64AuditTrailImage);
      }

      if (sessionResult.getLowQualityAuditTrailCompressedBase64().length > 0) {
        String lowQualityBase64AuditTrailImage = sessionResult.getLowQualityAuditTrailCompressedBase64()[0];
        images.put("base64LowQualityAuditTrailImage", lowQualityBase64AuditTrailImage);
      }

      result.put("base64Images", images);
      result.put("zoomSessionId", sessionResult.getSessionId());
      result.put("zoomAPIUserAgent", FaceTecSDK.createFaceTecAPIUserAgentString(""));
      callback.onSuccess(result.toString());
    } catch (JSONException e) {
      e.printStackTrace();
      callback.onError(e.getMessage());
    }

    //
    // Part 9:  For better UX, update the User if the upload is taking a while.  You are free to customize and enhance this behavior to your liking.
    //
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override
      public void run() {
        if(faceScanResultCallback == null) { return; }
        faceScanResultCallback.uploadMessageOverride("Still Uploading...");
      }
    }, 6000);
  }

  public boolean isSuccess() {
    return this.isSuccess;
  }
}
