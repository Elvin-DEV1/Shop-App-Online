package com.example.shoponlineapp.admod

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenAdSnippets {
    private var appOpenAd: AppOpenAd? = null
    private var isShowingAd = false

    /** Interface definition for a callback to be invoked when an app open ad is complete. */
    fun interface OnShowAdCompleteListener {
        fun onShowAdComplete()
    }

    /**
     * Shows the ad if available.
     *
     * @param activity the activity that shows the ad
     */
    fun showAdIfAvailable(activity: Activity) {
        showAdIfAvailable(activity) {
            // Empty because the user will go back to the activity that shows the ad.
        }
    }

    /**
     * Shows the ad if available.
     *
     * @param activity The activity that shows the ad
     * @param onShowAdCompleteListener The listener to be notified when an app open ad is complete
     */
///////////////////////////////4////////////////////////////////////////////////////////////
    fun showAdIfAvailable(activity: Activity, onShowAdCompleteListener: OnShowAdCompleteListener) {
        // If the app open ad is already showing, do not show the ad again.
        if (isShowingAd) {
            Log.d(TAG, "The app open ad is already showing.")
            return
        }

        // If the app open ad is not available yet, invoke the callback then load the ad.
        if (appOpenAd == null) {
            Log.d(TAG, "The app open ad is not ready yet.")
            onShowAdCompleteListener.onShowAdComplete()
            // Load an ad.
            return
        }
        // [START_EXCLUDE silent]
        setFullScreenContentCallback(activity, onShowAdCompleteListener)
        // [END_EXCLUDE]

        isShowingAd = true
        appOpenAd?.show(activity)
    }
///////////////////////////////////////////////////////////////////////////////////////////


    private fun setFullScreenContentCallback(
        activity: Activity,
        onShowAdCompleteListener: OnShowAdCompleteListener,
    ) {
        // [START ad_events]
        appOpenAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Called when full screen content is dismissed.
                    Log.d(TAG, "Ad dismissed fullscreen content.")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    appOpenAd = null
                    isShowingAd = false

                    onShowAdCompleteListener.onShowAdComplete()
                    // Load an ad.
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Called when full screen content failed to show.
                    Log.d(TAG, adError.message)
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    appOpenAd = null
                    isShowingAd = false

                    onShowAdCompleteListener.onShowAdComplete()
                    // Load an ad.
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(TAG, "The ad recorded an impression.")
                }

                override fun onAdClicked() {
                    // Called when ad is clicked.
                    Log.d(TAG, "The ad was clicked.")
                }
            }
        // [END ad_events]
    }

    private companion object {
        const val TAG = "AppOpenAdSnippets"
    }
}