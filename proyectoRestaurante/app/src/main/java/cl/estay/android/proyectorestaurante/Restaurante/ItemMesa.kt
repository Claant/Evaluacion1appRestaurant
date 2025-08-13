package cl.estay.android.proyectorestaurante.Restaurante

data class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        return itemMenu.precio * cantidad
    }
}

