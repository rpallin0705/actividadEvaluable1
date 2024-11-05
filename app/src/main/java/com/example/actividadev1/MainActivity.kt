package com.example.actividadev1


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.actividadev1.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar la vista
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        enableEdgeToEdge()

        start()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        const val TAG = "--- SALIDA ---"
    }

    private fun start() {

        mainBinding.webButton.setOnClickListener { openWeb() }
        mainBinding.alarmButton.setOnClickListener {

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, 2)

            /*
            * val explicitIntent = Intent("com.example.action.APP_ACTION")
            * explicitIntent.setPackage(context.getPackageName())
            * context.startActivity(explicitIntent)
            * */

            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "Capítulo nuevo!1")
                putExtra(AlarmClock.EXTRA_HOUR, calendar[Calendar.HOUR_OF_DAY])
                putExtra(AlarmClock.EXTRA_MINUTES, calendar[Calendar.MINUTE])
                putExtra(AlarmClock.EXTRA_SKIP_UI, false)
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Hubo un problema y la aplicación Reloj no está funcionando",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        mainBinding.phoneButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SetPhone::class.java)
            intent.apply { addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) }
            startActivity(intent)
        }
    }

    private fun openWeb() {


        val url = getString(R.string.web_url)
        Toast.makeText(this, "Abriendo navegador" + url, Toast.LENGTH_LONG)
            .show()

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }

        // Verifica que se puedaa menejaar este intent
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d(TAG, "error")
        }
    }


}