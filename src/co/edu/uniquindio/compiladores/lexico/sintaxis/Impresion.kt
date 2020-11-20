package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Impresion : Sentencia{
    private var palabraReservada : Token ?= null
    private var exp : Expresion ?= null
    constructor(palabraReservada: Token, exp: Expresion){
        this.palabraReservada = palabraReservada
        this.exp = exp
    }
    override fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Imprimir")
        var expresiones = ArrayList<Expresion>()
        expresiones.add(exp!!)
        for (e in expresiones) {
            raiz.children.add(e.getArbolVisual())
        }
        return raiz
    }
}