package com.example.actividadev1


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadev1.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        enableEdgeToEdge()
        start()

    }

    companion object {
        const val TAG = "--- SALIDA ---"
    }

    /**
     * Función que crea los Listener de todos los botones del aactivity
     */
    private fun start() {
        mainBinding.webButton.setOnClickListener { openWeb() }
        mainBinding.alarmButton.setOnClickListener { setAlarm() }
        mainBinding.phoneButton.setOnClickListener { openPhoneSetter() }
    }

    /**
     * Abre un segundo activity para configurar el número de teléfono. Si el número de
     * teléfono ha sido configurado previamente se realizará una comprobación en el segundo
     * activity y este te mandará al activity que realiza la llamada
     */
    private fun openPhoneSetter() {
        val intent = Intent(this@MainActivity, SetPhoneActivity::class.java)
        intent.apply { addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) }
        startActivity(intent)
    }


    /**
     * Crea una alarma dos minutos por delante de la hora actual del sistema,
     * te manda a la aplicación reloj para confirmarla. Si establecemos laa banderaa
     * EXTRA_SKIP_UI a false pondrá la alarma sin necesidad de abrir el Reloj
     */
    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, 2)

        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Capítulo nuevo!")
            putExtra(AlarmClock.EXTRA_HOUR, calendar[Calendar.HOUR_OF_DAY])
            putExtra(AlarmClock.EXTRA_MINUTES, calendar[Calendar.MINUTE])
            // Si le pasamos true pondrá la alarma sin necesidad de abrir la aplicación del reloj
            putExtra(AlarmClock.EXTRA_SKIP_UI, false)
        }

        startActivity(intent)
    }


    /**
     * Abre un enlace web a la url que está establecidad en el fichero strings.xml
     */
    private fun openWeb() {
        val url = getString(R.string.web_url)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }

        startActivity(intent)
    }
}