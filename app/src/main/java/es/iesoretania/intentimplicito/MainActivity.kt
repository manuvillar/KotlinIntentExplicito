package es.iesoretania.intentimplicito

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.iesoretania.intentimplicito.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Referencia a las vistas del layout activity_main.xml.
    // "lateinit" porque no podemos crearla aquí: hay que esperar a onCreate,
    // que es cuando la Activity ya tiene un contexto válido para inflar el layout.
    private lateinit var binding: ActivityMainBinding

    // Todo lo que va aquí pertenece a la CLASE, no a cada objeto (equivale a "static" en Java).
    // Definimos la clave del extra en un único sitio para no equivocarnos al escribirla
    // en la Activity que envía y en la que recibe.
    companion object {
        const val EXTRA_NOMBRE = "nombre"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Inflamos el layout: convierte el XML en objetos View reales.
        // 2) binding.root es la vista raíz de ese layout.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Desde Android 15 la app se dibuja bajo la barra de estado, la de navegación
        // y el recorte de la cámara. Sin este bloque, el contenido se solapa con ellos.
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { vista, insets ->
            // "Insets" = el espacio que ocupan los elementos del sistema.
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            // Pasamos 16dp a píxeles, que es lo que necesita setPadding.
            val extra = (16 * resources.displayMetrics.density).toInt()
            // Margen interior = espacio del sistema + nuestro margen estético.
            vista.setPadding(
                barras.left + extra,
                barras.top + extra,
                barras.right + extra,
                barras.bottom + extra
            )
            insets
        }

        // Al pulsar el botón, recogemos el texto y abrimos la segunda pantalla.
        binding.btnEnviar.setOnClickListener {
            // Leemos lo que el usuario escribió en el EditText.
            val nombre = binding.etNombre.text.toString()

            // INTENT EXPLÍCITO: indicamos la clase concreta que debe abrirse.
            //  - this: el Context de origen (esta Activity)
            //  - SegundaActivity::class.java: la clase destino
            val intent = Intent(this, SegundaActivity::class.java)

            // Adjuntamos un dato extra con formato clave -> valor.
            // SegundaActivity lo recuperará con la misma clave.
            intent.putExtra(EXTRA_NOMBRE, nombre)

            // Pedimos al sistema que abra la nueva Activity.
            // MainActivity pasará a segundo plano (onPause/onStop) y quedará en la pila.
            startActivity(intent)
        }
    }
}