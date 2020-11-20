package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Leer : Sentencia {
    private var id : Token ?= null

    constructor(id: Token){
        this.id = id
    }

    override fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Leer")
        raiz.children.add( TreeItem(id!!.lexema))
        return raiz
    }

}