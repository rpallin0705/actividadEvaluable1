package com.example.actividadev1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.actividadev1.databinding.ActivityPhoneCallBinding

class PhoneCallActivity : AppCompatActivity() {

    private lateinit var phoneCallBinding: ActivityPhoneCallBinding
    private lateinit var sharedFile: SharedPreferences

    companion object {
        // Variable que utiulizará para solicitar permisos de llamada
        private const val CALL_PHONE_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        phoneCallBinding = ActivityPhoneCallBinding.inflate(layoutInflater)
        setContentView(phoneCallBinding.root)

        initSharedPreferences()
        start()
    }

    private fun start() {

        val phoneNumber = sharedFile.getString(getString(R.string.name_shared_phone), "") ?: ""
        phoneCallBinding.phoneNumberTxt.text = phoneNumber

        phoneCallBinding.callBtn.setOnClickListener { callChecker() }
        phoneCallBinding.backBtn.setOnClickListener { deletePhoneNumber() }
    }

    /**
     * Estaaa función borra el valor del teléfono en el archivo sharedPreferences
     * y te devuelve al setPhoneActivity
     */
    private fun deletePhoneNumber() {
        val editor = sharedFile.edit()
        editor.remove(getString(R.string.name_shared_phone))
        editor.apply()

        Toast.makeText(this, getString(R.string.deleted_phone_number), Toast.LENGTH_SHORT).show()
        phoneCallBinding.phoneNumberTxt.text = ""

        loadSetPhoneActivity()
    }

    /**
     * Cargaa el activity setPhone
     */
    private fun loadSetPhoneActivity() {
        val intent = Intent(this, SetPhoneActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Comprueba que haya un numero de teléfeno guardado (Teoricamente si llegas a este activity
     * es porque hay uno) y si hay uno guardado procede a comprobar permisos y hacer la llamada
     */
    private fun callChecker() {
        val phoneNumber = sharedFile.getString(getString(R.string.name_shared_phone), "") ?: ""
        if (phoneNumber.isNotEmpty()) {
            makeCall(phoneNumber)
        } else {
            Toast.makeText(this, getString(R.string.missing_phone_number2), Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * Comprueba que la SDK sea inferior a la 23, si es así realiza la llamada
     * ya que no se requieren permisos en versiones inferiores
     *
     * Si es una versión superior se piden permisos al usuario mediante un dialogo emergente, si
     * este los rechaza una vez o permanentemente la proxima vez se le avisará y se abrirán la configuración
     * de la aplicación para que conceda los permisos manualmente
     *
     * @param phoneNumber de teléfono al que se realizará la llamada
     */
    private fun makeCall(phoneNumber: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            call(phoneNumber)
            return
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_REQUEST_CODE)

            } else {
                Toast.makeText(this,
                    getString(R.string.missing_phone_permission), Toast.LENGTH_SHORT).show()
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:$packageName")
                }
                startActivity(intent)
            }
        } else {
            call(phoneNumber)
        }
    }

    /**
     * Abre la aplicación del telefono y realiza llamada automaticamente
     */
    private fun call(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(callIntent)
    }

    /**
     * Inicia el fichero de preferencias compartidas
     */
    private fun initSharedPreferences() {
        val nameSharedFile = getString(R.string.name_prefered_shared_fich)
        sharedFile = getSharedPreferences(nameSharedFile, Context.MODE_PRIVATE)
    }
}
