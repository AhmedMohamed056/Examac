package com.cis.itiapi


import android.os.Bundle
import android.widget.Toolbar
import androidx.preference.PreferenceFragmentCompat



class settingsFragmentteacher : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.teacherfragmentsettings, rootKey)
    }

}