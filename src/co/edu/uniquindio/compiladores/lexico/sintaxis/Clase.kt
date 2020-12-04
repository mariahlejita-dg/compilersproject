package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Clase  {
    private var modificador : Token ?= null
    var palabra_reservada_clase : Token ?= null
    var identificador_clase : Token ?= null
    var cuerpoClase : CuerpoClase ?= null

    constructor()
    constructor(modificador : Token, palabra_reservada_clase:Token, identificador_clase: Token, cuerpoClase: CuerpoClase):this() {
        this.modificador = modificador
        this.palabra_reservada_clase = palabra_reservada_clase
        this.identificador_clase = identificador_clase
        this.cuerpoClase = cuerpoClase
    }
    constructor(modificador : Token, palabra_reservada_clase: Token, identificador_clase: Token):this() {
        this.modificador = modificador
        this.palabra_reservada_clase = palabra_reservada_clase
        this.identificador_clase = identificador_clase
    }
    fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem("Clase")
        if(cuerpoClase != null){
            raiz.children.add( TreeItem(identificador_clase!!.lexema))
            raiz.children.add( cuerpoClase!!.getArbolVisual() )
            return raiz
        }
        raiz.children.add( TreeItem(identificador_clase!!.lexema))
        return raiz
    }

}