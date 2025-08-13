package cl.estay.android.proyectorestaurante

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.estay.android.proyectorestaurante.Restaurante.CuentaMesa
import cl.estay.android.proyectorestaurante.Restaurante.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var cuentaMesa: CuentaMesa
    private lateinit var pastel: ItemMenu
    private lateinit var cazuela: ItemMenu
    private val formatoPesos = NumberFormat.getCurrencyInstance(Locale("es", "CL"))


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        pastel = ItemMenu("Pastel de Choclo", 12000)
        cazuela = ItemMenu("Cazuela", 10000)
        cuentaMesa = CuentaMesa()

        val editPastel = findViewById<EditText>(R.id.CantidadPastel)
        val editCazuela = findViewById<EditText>(R.id.editCantidadCazuela)
        val switchPropina = findViewById<Switch>(R.id.propina)
        val textResumen = findViewById<TextView>(R.id.textResumen)


        val actualizarResumen = {
            cuentaMesa = CuentaMesa()
            val cantPastel = editPastel.text.toString().toIntOrNull() ?: 0
            val cantCazuela = editCazuela.text.toString().toIntOrNull() ?: 0
            cuentaMesa.aceptaPropina = switchPropina.isChecked

            if (cantPastel > 0) cuentaMesa.agregarItem(pastel, cantPastel)
            if (cantCazuela > 0) cuentaMesa.agregarItem(cazuela, cantCazuela)

            val subtotalPastel = cantPastel * pastel.precio
            val subtotalCazuela = cantCazuela * cazuela.precio
            val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
            val propina = cuentaMesa.calcularPropina()
            val totalConPropina = cuentaMesa.calcularTotalConPropina()

            textResumen.text = """
                Pastel de Choclo x$cantPastel: ${formatoPesos.format(subtotalPastel)}
                Cazuela x$cantCazuela: ${formatoPesos.format(subtotalCazuela)}
                
                Comida: ${formatoPesos.format(totalSinPropina)}
                Propina: ${formatoPesos.format(propina)}
                TOTAL: ${formatoPesos.format(totalConPropina)}
            """.trimIndent()
        }

        editPastel.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) = actualizarResumen()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
             })

editCazuela.addTextChangedListener(object:TextWatcher {
    override fun afterTextChanged(s: Editable?) = actualizarResumen()
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

})

        switchPropina.setOnCheckedChangeListener { _, _ -> actualizarResumen() }
    }
}




