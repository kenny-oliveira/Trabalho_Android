package com.example.trabalho

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.w3c.dom.Text


class ObtainGPS(context: Context) : Service(), LocationListener {
    private val mContext: Context

    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false
    private var location // location
            : Location? = null
    private var latitude // latitude
            = 0.0
    private var longitude // longitude
            = 0.0

    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null
    fun getLocation(): Location? {
        try {
            locationManager = mContext.getSystemService(LOCATION_SERVICE) as LocationManager?

            // getting GPS status
            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            // getting network status
            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                mContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return null
                        }
                    }
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            latitude = location!!.getLatitude()
                            longitude = location!!.getLongitude()
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        if (locationManager != null) {
                            location = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                latitude = location!!.getLatitude()
                                longitude = location!!.getLongitude()
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return location
    }

    /**
     * Function to get latitude
     */
    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.getLatitude()
        }

        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */

    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.getLongitude()
        }

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    fun showSettingsAlert() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(mContext)

        // Setting Dialog Title
        alertDialog.setTitle("GPS")

        // Setting Dialog Message
        alertDialog.setMessage("GPS não está habilitado. Você deseja configura-lo?")

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings",
            DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                mContext.startActivity(intent)
            })

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        // Showing Alert Message
        alertDialog.show()
    }

    override fun onLocationChanged(location: Location?) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    companion object {
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1 // 1 minute
                ).toLong()
    }

    init {
        mContext = context
        getLocation()
    }
}


class DisciplinaActivity : AppCompatActivity() {

    var gps: ObtainGPS? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_disciplina)

        val Disciplina : TextView = findViewById(R.id.discalvo)
        val extras = intent.extras

        if (extras != null) {
            val DiscliplinAlvo = extras.getString("disciplinaalvo")
            Disciplina.text = DiscliplinAlvo
        }

        val btn_voltar : Button = findViewById(R.id.btn_voltar)
        btn_voltar.setOnClickListener {
            /**voltar para os dias da semana**/
            val intent = Intent(this, ListagemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        getLocalization()
    }

    fun getLocalization() {
        gps = ObtainGPS(this)
        if (GetLocalization(this)) {
            val latitude : TextView = findViewById(R.id.latitude)
            val longitude : TextView = findViewById(R.id.longitude)
            val latitudeunicid  = -23.5362861
            val longitudeunicid = -46.5603371
            if (gps!!.canGetLocation()) {
                val LatitudeAtual = gps!!.getLatitude()
                val longitudeAtual = gps!!.getLongitude()
                latitude.text = (LatitudeAtual).toString()
                longitude.text = (longitudeAtual).toString()

                val loc1 = Location("")
                loc1.latitude = latitudeunicid
                loc1.longitude = longitudeunicid

                val loc2 = Location("")
                loc2.latitude = LatitudeAtual
                loc2.longitude = longitudeAtual

                val distanceInMeters = loc1.distanceTo(loc2) / 1000

                Toast.makeText(this, "$distanceInMeters Metros da unicid", Toast.LENGTH_SHORT).show()
            } else {
                latitude.text = "Erro"
                longitude.text = "Erro"
                gps!!.showSettingsAlert()
            }
        }
    }

    fun GetLocalization(context: Context?): Boolean {
        val REQUEST_PERMISSION_LOCALIZATION = 221
        var res = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                res = false
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    REQUEST_PERMISSION_LOCALIZATION
                )
            }
        }
        return res
    }

}

