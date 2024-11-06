package com.example.actividadev1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadev1.databinding.ActivitySetPhoneBinding

class SetPhoneActivity : AppCompatActivity() {

    private lateinit var setPhoneBinding: ActivitySetPhoneBinding
    private lateinit var sharedFile: SharedPreferences
    private lateinit var nameSharedPhone: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setPhoneBinding = ActivitySetPhoneBinding.inflate(layoutInflater)
        setContentView(setPhoneBinding.root)
        enableEdgeToEdge()

        initSharedPreferences()
        start()
    }

    /**
     * Función que comprueba que haya un numero de teléfono guardado, si hay
     * carga el PhoneCallActivity, si no haay alerta´ra de que se debe introducir
     * un número de teléfono
     */
    private fun start() {
        val storedPhoneNumber = sharedFile.getString(nameSharedPhone, "")
        if (!storedPhoneNumber.isNullOrEmpty()) {
            loadPhoneCallActivity()
            return
        }

        setPhoneBinding.setPhoneBtn.setOnClickListener { setPhone() }
    }

    /**
     * Comprueba que se haya introducido un numero de teléfono en en EditText, si
     * se ha introducido actualiza ala variable nameSharedPhone en el archivo Shared
     * preferences y carga el siguiente activity
     */
    private fun setPhone() {
        val phoneNumber = setPhoneBinding.phoneNumberTxt.text.toString()

        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, getString(R.string.missing_phone_number), Toast.LENGTH_LONG).show()
        } else if(phoneNumber.length < 9){
            Toast.makeText(this, getString(R.string.wrong_phone_format), Toast.LENGTH_LONG).show()
        } else {
            val editor = sharedFile.edit()
            editor.putString(nameSharedPhone, phoneNumber)
            editor.apply()
            loadPhoneCallActivity()
        }
    }

    /**
     * Carga el siguiente activity y elimina el actual de la pila
     */
    private fun loadPhoneCallActivity() {
        val intent = Intent(this, PhoneCallActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        }
        startActivity(intent)
        finish()
    }

    /**
     * Inicia el archivo sharedPreferences
     */
    private fun initSharedPreferences() {
        val nameSharedFile = getString(R.string.name_prefered_shared_fich)
        this.nameSharedPhone = getString(R.string.name_shared_phone)
        this.sharedFile = getSharedPreferences(nameSharedFile, Context.MODE_PRIVATE)
    }

    /**
     * Metodo para comprobar si se procede del siguiente activity o de
     * este activity por si hubiera alguna interrupción
     */
    override fun onResume() {
        super.onResume()
        val ret = intent.getBooleanExtra("back", false)
        if (ret) {
            setPhoneBinding.phoneNumberTxt.setText("")
            Toast.makeText(this, R.string.msg_new_phone, Toast.LENGTH_LONG).show()
            intent.removeExtra("back")
        }
    }

    /**
     * Método para actualizar el intent
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}
