package com.ravigarbuja.covidinfo.util

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.io.IOException
import java.io.InputStream

object CustomBindingAdapter {

    /**
     * Set the flag on an imageview for a country based on its country code
     */
    @BindingAdapter("setCountryFlag")
    @JvmStatic
    fun ImageView.setCountryFlagFromAsset(countryCode: String) {

        val imagePath = "countriesFlag/" + countryCode.toLowerCase() + ".png"

        var ims: InputStream? = null
        try {
            // get input stream
            ims = context.assets.open(imagePath)
            // load image as Drawable
            val d = Drawable.createFromStream(ims, null)
            // set image to ImageView
            setImageDrawable(d)
        } catch (ex: IOException) {
            Log.e("Error", "getView: " + ex.localizedMessage)
            ex.printStackTrace()
        } finally {
            if (ims != null) {
                try {
                    ims.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    /**
     * Set locale formatted number to the Textview
     */
    @BindingAdapter("numText")
    @JvmStatic
    fun TextView.setNumber(number: Int){
        val formattedString = number.formatNumberBasedOnLocale()
        text = formattedString
    }
}