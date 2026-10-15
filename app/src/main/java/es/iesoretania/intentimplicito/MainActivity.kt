package es.iesoretania.intentimplicito

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.iesoretania.intentimplicito.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object{
        const val EXTRA_NOMBRE = "nombre"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Evita el solapamiento con la cámara y las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { vista, insets ->
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            val extra = (16 * resources.displayMetrics.density).toInt()
            vista.setPadding(
                barras.left + extra,
                barras.top + extra,
                barras.right + extra,
                barras.bottom + extra
            )
            insets
        }

        binding.btnEnviar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()

            val intent = Intent(this, SegundaActivity::class.java)
            intent.putExtra(EXTRA_NOMBRE, nombre)

            startActivity(intent)
        }
    }
}