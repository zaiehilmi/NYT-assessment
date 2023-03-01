package com.auzaie.jetnotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ApplicationViewModel(application: Application) : AndroidViewModel(application) {

    private val locationLiveData = GeolocationLiveData(application)
    fun getLocationLiveData() = locationLiveData
    fun startLocationUpdates() {
        locationLiveData.startLocationUpdates()
    }

}