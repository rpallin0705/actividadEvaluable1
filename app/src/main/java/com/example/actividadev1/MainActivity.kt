package com.example.actividadev1


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadev1.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var easterEggRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        enableEdgeToEdge()
        start()

    }

    /**
     * Función que crea los Listener de todos los botones del aactivity
     */
    private fun start() {
        mainBinding.imageView3.setOnClickListener { easterEggSetter() }
        mainBinding.webButton.setOnClickListener { openWeb() }
        mainBinding.alarmButton.setOnClickListener { setAlarm() }
        mainBinding.phoneButton.setOnClickListener { openPhoneSetter() }
        mainBinding.mapButton.setOnClickListener { openMap() }
    }

    private fun openMap() {

        val address = getString(R.string.map_location)
        val geoUri = Uri.parse("geo:0,0?q=${address}")

        Toast.makeText(this, getString(R.string.open_map_msg), Toast.LENGTH_SHORT).show()
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri).apply {
            setPackage("com.google.android.apps.maps")
        }
        startActivity(mapIntent)

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

    /**
     * Funcion que hace un easterEgg en el mainActivity
     */
    private fun easterEggSetter() {
        if (easterEggRunning) {
            mainBinding.imageView3.clearAnimation()
            easterEggRunning = false
        } else {
            val heartbeatAnimation = AnimationUtils.loadAnimation(this, R.anim.easter_egg)
            mainBinding.imageView3.startAnimation(heartbeatAnimation)
            easterEggRunning = true
        }
    }
}