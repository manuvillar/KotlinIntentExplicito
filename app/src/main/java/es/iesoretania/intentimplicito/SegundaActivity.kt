package es.iesoretania.intentimplicito

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.iesoretania.intentimplicito.databinding.ActivitySegundaBinding

class SegundaActivity : AppCompatActivity() {

    // Referencia a las vistas de activity_segunda.xml.
    // Cada layout tiene su propia clase de binding: aquí no sirve ActivityMainBinding.
    // "lateinit" porque se inicializa en onCreate, no en el momento de crear el objeto.
    private lateinit var binding: ActivitySegundaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflamos el layout (XML -> objetos View) y lo mostramos en pantalla.
        // binding.root es la vista raíz de activity_segunda.xml.
        binding = ActivitySegundaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Evita el solapamiento con la cámara y las barras del sistema.
        // Se ejecuta cuando Android nos dice cuánto espacio ocupan esos elementos.
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { vista, insets ->
            // Espacio ocupado por las barras (estado y navegación) y el recorte de la cámara.
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            // Convertimos 16dp a píxeles, que es la unidad que espera setPadding.
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

        // "intent" no se declara: es una propiedad heredada de Activity.
        // Contiene el Intent que MainActivity pasó a startActivity(), con sus extras.
        // Leemos el extra con la MISMA clave que se usó al enviarlo, definida en el
        // companion object de MainActivity (así no hay riesgo de escribirla mal).
        // Si la clave no existe, getStringExtra devuelve null y el operador elvis (?:)
        // nos da un valor por defecto, evitando un posible fallo.
        val nombre = intent.getStringExtra(MainActivity.EXTRA_NOMBRE) ?: "desconocido"

        // Mostramos el saludo usando una plantilla de cadena: $nombre se sustituye por su valor.
        binding.tvSaludo.text = "Hola, $nombre"

        // finish() cierra esta Activity y la saca de la pila.
        // Android vuelve a MainActivity, que estaba detenida debajo.
        // Equivale a pulsar el botón Atrás del sistema.
        binding.btnVolver.setOnClickListener { finish() }
    }
}