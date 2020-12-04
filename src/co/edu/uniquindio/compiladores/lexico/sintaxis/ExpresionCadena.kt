package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

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
    override fun traducir(): String? {
        val exp = expresion!!.traducir()
        return cadena.toString() + "+" + exp
    }

    override fun analizarSemantica(errores: ArrayList<String?>?, ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

}