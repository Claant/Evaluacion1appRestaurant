package cl.estay.android.proyectorestaurante.Restaurante

class CuentaMesa {
    private val _items = mutableListOf<ItemMesa>()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.10).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }


}


