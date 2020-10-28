package com.reactnativefacetec.ZoomProcessors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.facetec.zoom.sdk.ZoomCancelButtonCustomization;
import com.facetec.zoom.sdk.ZoomCustomization;
import com.facetec.zoom.sdk.ZoomGuidanceCustomization;
import com.facetec.zoom.sdk.ZoomSDK;
import com.reactnativefacetec.R;

public class ThemeHelpers {
  Context context;
  public ThemeHelpers(Context context) {
    this.context = context;
  }

  public void setAppTheme(String theme) {
        ZoomGlobalState.currentCustomization = getCustomizationForTheme(theme);
        ZoomSDK.setCustomization(ZoomGlobalState.currentCustomization);
        ZoomSDK.setLowLightCustomization(ZoomGlobalState.currentCustomization);
    }

    public ZoomCustomization getCustomizationForTheme(String theme) {
        ZoomCustomization currentCustomization = new ZoomCustomization();

            int[] retryScreenSlideshowImages = new int[]{R.drawable.zoom_ideal_1, R.drawable.zoom_ideal_2, R.drawable.zoom_ideal_3, R.drawable.zoom_ideal_4, R.drawable.zoom_ideal_5};

            int outerBackgroundColor = Color.parseColor("#ffffff");

            int primaryColor = Color.parseColor("#333333"); // white
            int backgroundColor = Color.parseColor("#FFFFFF"); // navy
            int numio = Color.parseColor("#8413F5");
            int buttonText = Color.parseColor("#414141");

            // Overlay Customization
            currentCustomization.getOverlayCustomization().backgroundColor = backgroundColor;
            currentCustomization.getOverlayCustomization().showBrandingImage = false;
            currentCustomization.getOverlayCustomization().brandingImage = R.color.zoom_hint_white;

            // Guidance Customization
            currentCustomization.getGuidanceCustomization().backgroundColors = backgroundColor;
            currentCustomization.getGuidanceCustomization().foregroundColor = primaryColor;
            // currentCustomization.getGuidanceCustomization().headerFont = ResourcesCompat.getFont(context, R.font.nunito_bold);
            currentCustomization.getGuidanceCustomization().headerTextSize = 24;
            currentCustomization.getGuidanceCustomization().headerTextSpacing = 0.05f;
            // currentCustomization.getGuidanceCustomization().subtextFont = ResourcesCompat.getFont(context, R.font.nunito_regular);
            currentCustomization.getGuidanceCustomization().subtextTextSize = 14;
            currentCustomization.getGuidanceCustomization().subtextTextSpacing = 0f;
            // currentCustomization.getGuidanceCustomization().buttonFont = ResourcesCompat.getFont(context, R.font.nunito_bold);
            currentCustomization.getGuidanceCustomization().buttonTextSize = 16;
            currentCustomization.getGuidanceCustomization().buttonTextSpacing = 0.05f;
            currentCustomization.getGuidanceCustomization().buttonTextNormalColor = backgroundColor;
            currentCustomization.getGuidanceCustomization().buttonBackgroundNormalColor = primaryColor;
            currentCustomization.getGuidanceCustomization().buttonTextHighlightColor = buttonText;
            currentCustomization.getGuidanceCustomization().buttonBackgroundHighlightColor = Color.parseColor("#565656");
            currentCustomization.getGuidanceCustomization().buttonTextDisabledColor = backgroundColor; //Color.parseColor("#4D1D174F");
            currentCustomization.getGuidanceCustomization().buttonBackgroundDisabledColor = Color.parseColor("#565656");
            currentCustomization.getGuidanceCustomization().buttonBorderColor = numio;

            currentCustomization.getGuidanceCustomization().buttonBorderWidth = 1;
            currentCustomization.getGuidanceCustomization().buttonCornerRadius = 30;
            currentCustomization.getGuidanceCustomization().buttonRelativeWidth = 1.0f;
            currentCustomization.getGuidanceCustomization().readyScreenOvalFillColor = Color.parseColor("#4d8413F5"); //Color.parseColor("#4DFFFFFF");
            currentCustomization.getGuidanceCustomization().readyScreenTextBackgroundColor = backgroundColor;
            currentCustomization.getGuidanceCustomization().readyScreenTextBackgroundCornerRadius = 2;
            currentCustomization.getGuidanceCustomization().readyScreenSubtextTextColor = buttonText;

            currentCustomization.getGuidanceCustomization().retryScreenImageBorderColor = primaryColor;
            currentCustomization.getGuidanceCustomization().retryScreenImageBorderWidth = 2;
            currentCustomization.getGuidanceCustomization().retryScreenImageCornerRadius = 30;
            currentCustomization.getGuidanceCustomization().retryScreenOvalStrokeColor = backgroundColor; //primaryColor;
            currentCustomization.getGuidanceCustomization().retryScreenSlideshowImages = retryScreenSlideshowImages;
            currentCustomization.getGuidanceCustomization().retryScreenSlideshowInterval = 1500;
            currentCustomization.getGuidanceCustomization().enableRetryScreenSlideshowShuffle = false;
            currentCustomization.getGuidanceCustomization().enableRetryScreenBulletedInstructions = false;
            currentCustomization.getGuidanceCustomization().cameraPermissionsScreenImage = R.drawable.camera_white_navy;
            // ID Scan Customization
            currentCustomization.getIdScanCustomization().showSelectionScreenBrandingImage = false;
            currentCustomization.getIdScanCustomization().selectionScreenBrandingImage = 0;
            currentCustomization.getIdScanCustomization().selectionScreenBackgroundColors = backgroundColor;
            currentCustomization.getIdScanCustomization().reviewScreenBackgroundColors = backgroundColor;
            currentCustomization.getIdScanCustomization().captureScreenForegroundColor = primaryColor;
            currentCustomization.getIdScanCustomization().reviewScreenForegroundColor = backgroundColor;
            currentCustomization.getIdScanCustomization().selectionScreenForegroundColor = primaryColor;
            currentCustomization.getIdScanCustomization().captureScreenFocusMessageTextColor = primaryColor;
            currentCustomization.getIdScanCustomization().headerFont = ResourcesCompat.getFont(context, R.font.nunito_bold);
            currentCustomization.getIdScanCustomization().headerTextSize = 24;
            currentCustomization.getIdScanCustomization().headerTextSpacing = 0.05f;
            // currentCustomization.getIdScanCustomization().subtextFont = ResourcesCompat.getFont(context, R.font.nunito_regular);
            currentCustomization.getIdScanCustomization().subtextTextSize = 14;
            currentCustomization.getIdScanCustomization().subtextTextSpacing = 0f;
            // currentCustomization.getIdScanCustomization().buttonFont = ResourcesCompat.getFont(context, R.font.nunito_bold);
            currentCustomization.getIdScanCustomization().buttonTextSize = 20;
            currentCustomization.getIdScanCustomization().buttonTextSpacing = 0.05f;
            currentCustomization.getIdScanCustomization().buttonTextNormalColor = buttonText;
            currentCustomization.getIdScanCustomization().buttonBackgroundNormalColor = numio;
            currentCustomization.getIdScanCustomization().buttonTextHighlightColor = buttonText;
            currentCustomization.getIdScanCustomization().buttonBackgroundHighlightColor = Color.parseColor("#BFFFFFFF");
            currentCustomization.getIdScanCustomization().buttonTextDisabledColor = backgroundColor;
            currentCustomization.getIdScanCustomization().buttonBackgroundDisabledColor = numio;
            currentCustomization.getIdScanCustomization().buttonBorderColor = backgroundColor;
            currentCustomization.getIdScanCustomization().buttonBorderWidth = 1;
            currentCustomization.getIdScanCustomization().buttonCornerRadius = 30;
            currentCustomization.getIdScanCustomization().buttonRelativeWidth = 1.0f;
            currentCustomization.getIdScanCustomization().captureScreenTextBackgroundColor = backgroundColor;
            currentCustomization.getIdScanCustomization().captureScreenFocusMessageTextColor = primaryColor;
            currentCustomization.getIdScanCustomization().captureScreenTextBackgroundBorderColor = backgroundColor;
            currentCustomization.getIdScanCustomization().captureScreenTextBackgroundBorderWidth = 2;
            currentCustomization.getIdScanCustomization().captureScreenTextBackgroundCornerRadius = 2;
            currentCustomization.getIdScanCustomization().reviewScreenTextBackgroundColor = primaryColor;
            currentCustomization.getIdScanCustomization().reviewScreenTextBackgroundBorderColor = backgroundColor;
            currentCustomization.getIdScanCustomization().reviewScreenTextBackgroundBorderWidth = 2;
            currentCustomization.getIdScanCustomization().reviewScreenTextBackgroundCornerRadius = 2;
            currentCustomization.getIdScanCustomization().captureScreenBackgroundColor = backgroundColor;
            currentCustomization.getIdScanCustomization().captureFrameStrokeColor = primaryColor;
            currentCustomization.getIdScanCustomization().captureFrameStrokeWidth = 2;
            currentCustomization.getIdScanCustomization().captureFrameCornerRadius = 12;
            currentCustomization.getIdScanCustomization().activeTorchButtonImage = R.drawable.torch_active_white;
            currentCustomization.getIdScanCustomization().inactiveTorchButtonImage = R.drawable.torch_inactive_white;

            // Result Screen Customization
            currentCustomization.getResultScreenCustomization().backgroundColors = backgroundColor;
            currentCustomization.getResultScreenCustomization().foregroundColor = primaryColor;
            // currentCustomization.getResultScreenCustomization().messageFont  = ResourcesCompat.getFont(context, R.font.nunito_bold);
            currentCustomization.getResultScreenCustomization().messageTextSize = 18;
            currentCustomization.getResultScreenCustomization().messageTextSpacing = 0.05f;
            currentCustomization.getResultScreenCustomization().activityIndicatorColor = primaryColor;
            currentCustomization.getResultScreenCustomization().customActivityIndicatorImage = R.drawable.activity_indicator_white;
            currentCustomization.getResultScreenCustomization().customActivityIndicatorRotationInterval = 1000;
            currentCustomization.getResultScreenCustomization().customActivityIndicatorAnimation = 0;
            currentCustomization.getResultScreenCustomization().resultAnimationBackgroundColor = backgroundColor;
            currentCustomization.getResultScreenCustomization().resultAnimationForegroundColor = primaryColor;
            currentCustomization.getResultScreenCustomization().resultAnimationSuccessBackgroundImage = R.drawable.reticle_white;
            currentCustomization.getResultScreenCustomization().resultAnimationUnsuccessBackgroundImage = R.drawable.reticle_white;
            currentCustomization.getResultScreenCustomization().customResultAnimationSuccess = 0;
            currentCustomization.getResultScreenCustomization().customResultAnimationUnsuccess = 0;
            currentCustomization.getResultScreenCustomization().customStaticResultAnimationSuccess = 0;
            currentCustomization.getResultScreenCustomization().customStaticResultAnimationUnsuccess = 0;
            currentCustomization.getResultScreenCustomization().showUploadProgressBar = true;
            currentCustomization.getResultScreenCustomization().uploadProgressTrackColor = Color.parseColor("#33FFFFFF");
            currentCustomization.getResultScreenCustomization().uploadProgressFillColor = primaryColor;
            currentCustomization.getResultScreenCustomization().animationRelativeScale = 1.0f;
            // Feedback Customization
            currentCustomization.getFeedbackCustomization().backgroundColors = numio; //primaryColor;
            currentCustomization.getFeedbackCustomization().textColor = backgroundColor;
            // currentCustomization.getFeedbackCustomization().textFont = ResourcesCompat.getFont(context, R.font.nunito_bold);
            currentCustomization.getFeedbackCustomization().textSize = 18;
            currentCustomization.getFeedbackCustomization().textSpacing = 0.05f;
            currentCustomization.getFeedbackCustomization().cornerRadius = 30;
            currentCustomization.getFeedbackCustomization().elevation = 1;
            currentCustomization.getFeedbackCustomization().relativeWidth = 1.0f;
            // Frame Customization
            currentCustomization.getFrameCustomization().backgroundColor = backgroundColor;
            currentCustomization.getFrameCustomization().borderColor = numio;
            currentCustomization.getFrameCustomization().borderWidth = 0;
            currentCustomization.getFrameCustomization().cornerRadius = 0;
            currentCustomization.getFrameCustomization().elevation = 0;
            // Oval Customization
            currentCustomization.getOvalCustomization().strokeColor = primaryColor;
            currentCustomization.getOvalCustomization().progressColor1 = numio; //Color.parseColor("#BFFFFFFF");
            currentCustomization.getOvalCustomization().progressColor2 = numio; //Color.parseColor("#BFFFFFFF");
            // Cancel Button Customization
            currentCustomization.getCancelButtonCustomization().customImage = R.drawable.cancel_navy;
            currentCustomization.getCancelButtonCustomization().setLocation(ZoomCancelButtonCustomization.ButtonLocation.TOP_LEFT);
            return currentCustomization;
    }
}
