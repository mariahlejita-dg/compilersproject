package co.edu.uniquindio.compiladores.lexico.lexico

class Token (var lexema: String,var fila: Int, var columna: Int,var columnaReal: Int, var categoria: Categoria) {






    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }
}