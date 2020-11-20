package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class InvocacionMetodo {
    private var identificadorClase  : Token ?= null
    private var identificadorMetodo : Token ?= null

    constructor(identificadorClase : Token, identificadorMetodo : Token){
        this.identificadorClase = identificadorClase
        this.identificadorMetodo = identificadorMetodo
    }
    fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Invocacion Metodo")
        raiz.children.add( TreeItem(identificadorClase!!.lexema) )
        raiz.children.add( TreeItem(identificadorMetodo!!.lexema) )
        return raiz
    }
}