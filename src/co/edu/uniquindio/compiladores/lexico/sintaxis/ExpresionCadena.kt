package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionCadena : Expresion {
    private var cadena : Token ?= null
    private var palabraReservada : Token ?= null
    private var expresion : Expresion ?= null
    constructor()
    constructor(cadena : Token) : this(){
        this.cadena = cadena
    }

    constructor(cadena : Token, palabraReservada : Token, expresion: Expresion): this(){
        this.cadena = cadena
        this.palabraReservada = palabraReservada
        this.expresion = expresion
    }


    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem( "Expresion cadena")
        raiz.children.add( TreeItem(cadena!!.lexema) )
        if(palabraReservada != null){
            raiz.children.add( expresion!!.getArbolVisual() )
        }
        return raiz
    }
}