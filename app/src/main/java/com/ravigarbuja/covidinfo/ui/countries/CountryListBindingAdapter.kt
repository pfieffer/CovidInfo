package com.ravigarbuja.covidinfo.ui.countries

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.io.IOException
import java.io.InputStream

object CountryListBindingAdapter {

    @BindingAdapter("setCountryFlag")
    @JvmStatic
    fun ImageView.setCountryFlagFromAsset(countryCode: String) {
        //TODO: See if there is a flag corresponding to the country code in assets directory

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

}