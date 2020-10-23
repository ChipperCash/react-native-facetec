package com.reactnativefacetec;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.reactnativefacetec.Processors.*;
import com.facetec.sdk.FaceTecCustomization;
import com.facetec.sdk.FaceTecGuidanceCustomization;
import com.facetec.sdk.FaceTecSDK;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.UUID.randomUUID;

public class FacetecModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "Facetec";
    private static ReactApplicationContext reactContext = null;
    public Processor latestProcessor;

    Callback onSuccess;
    Callback onFail;
    Callback onFaceScanDone;
    private FacetecModule self;

    public FacetecModule(ReactApplicationContext context) {
        // Pass in the context to the constructor and save it so you can emit events
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        super(context);
        reactContext = context;
        ThemeHelpers themeHelpers = new ThemeHelpers();
        themeHelpers.setAppTheme("Sample Bank");
    }

    @Override
    public String getName() {
        // Tell React the name of the module
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        return REACT_CLASS;
    }

    @Override
    public Map<String, Object> getConstants() {
        // Export any constants to be used in your native module
        // https://facebook.github.io/react-native/docs/native-modules-android.html#the-toast-module
        final Map<String, Object> constants = new HashMap<>();
        constants.put("EXAMPLE_CONSTANT", "example");

        return constants;
    }

    private static void emitDeviceEvent(String eventName, @Nullable WritableMap eventData) {
        // A method for emitting from the native side to JS
        // https://facebook.github.io/react-native/docs/native-modules-android.html#sending-events-to-javascript
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, eventData);
    }

    @ReactMethod
    public void Init(String licenseText,  Callback onSuccess, Callback onFail) {
    this.onSuccess = onSuccess;
    this.onFail = onFail;

    FaceTecSDK.initializeInDevelopmentMode(reactContext, Config.DeviceKeyIdentifier, Config.PublicFaceScanEncryptionKey, new FaceTecSDK.InitializeCallback() {
        @Override
        public void onCompletion(boolean successful) {
            WritableMap params = Arguments.createMap();
            Log.d("ZoomSDK", "Attempt initialization");
            try{
                params.putString("initState", FaceTecSDK.getStatus(getCurrentActivity()).toString());
            }catch (Exception e){
                Log.d("ZoomSDK", "Could not putString()");
                e.printStackTrace();
            }
            if(successful){
                Log.d("ZoomSDK", "successfull");
                params.putBoolean("successful", true);
                onSuccess.invoke(params);
            }
            else{
                Log.d("ZoomSDK", "not successful");
                params.putBoolean("successful", false);
                onFail.invoke(params);
            }
        }
    });

//    FaceTecSDK.initializeWithLicense(reactContext, licenseText, ZoomGlobalState.DeviceLicenseKeyIdentifier, ZoomGlobalState.PublicFaceMapEncryptionKey, new ZoomSDK.InitializeCallback() {
//      @Override
//      public void onCompletion(final boolean successful) {
//        WritableMap params = Arguments.createMap();
//        try{
//          params.putString("initState", ZoomSDK.getStatus(getCurrentActivity()).toString());
//        }catch (Exception e){
//          e.printStackTrace();
//        }
//        if(successful){
//          params.putBoolean("successful", true);
//          onSuccess.invoke(params);
//        }
//        else{
//          params.putBoolean("successful", false);
//          onFail.invoke(params);
//        }
//      }
//    });
  }

    @ReactMethod
    public void LivenessCheck(Callback onSuccess, Callback onFail, Callback onFaceScanDone) {
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.onFaceScanDone = onFaceScanDone;

        NetworkingHelpers.getSessionToken((new NetworkingHelpers.SessionTokenCallback() {
            @Override
            public void onSessionTokenReceived(String sessionToken) {
                Log.d("ZoomSDK", "Got token back" + sessionToken);
                latestProcessor = new LivenessCheckProcessor(sessionToken, getCurrentActivity(), new LivenessCheckProcessor.LivenessCheckCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("ZoomSDK", "LivenessCheck was successful");
                        self.onSuccess.invoke();
                    }

                    @Override
                    public void onError(String error) {
                        Log.d("ZoomSDK", "LivenessCheck was failed with error" + error);
                        self.onFail.invoke();
                    }

                    @Override
                    public void onFaceScanDone(String faceScanResult) {
                        Log.d("ZoomSDK", "LivenessCheck face scan completed and returned" + faceScanResult);
                        self.onFaceScanDone.invoke(faceScanResult);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Log.d("ZoomSDK", "Exception raised while attempting HTTPS call.");
            }
        }));
    }

//    @ReactMethod
//    public void UpdateLoadingUI(boolean success) {
//        latestProcessor.updateLoadingUI(success);
//    }

//    Processor.SessionTokenErrorCallback sessionTokenErrorCallback = new Processor.SessionTokenErrorCallback() {
//        @Override
//        public void onError(String msg) {
//          try{
//            onFail.invoke(msg);
//          }catch (Exception e){
//            e.printStackTrace();
//          }
//        }
//    };
//
//    Processor.SessionTokenSuccessCallback sessionTokenSuccessCallback = new Processor.SessionTokenSuccessCallback() {
//        @Override
//        public void onSuccess(String msg) {
//            onSuccess.invoke(msg);
//        }
//    };
}
