package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

class ExpresionAritmetica : Expresion {

    private var termino : Termino ?= null
    private var  operador : Token ?= null
    private var termino2 : Termino ?= null
    private var expresionA : Expresion ?= null

    constructor()
    constructor(termino: Termino):this(){
        this.termino = termino
    }
    constructor(termino : Termino, operador : Token, termino2 : Termino) : this(){
        this.termino = termino
        this.operador = operador
        this.termino2 = termino2
    }
    constructor(termino : Termino, operador : Token, expresionA: Expresion) : this(){
        this.termino = termino
        this.operador = operador
        this.expresionA = expresionA
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem( "Expresion Aritmetica" )
        if ( termino != null){
            raiz.children.add( termino!!.getArbolVisual() )
            if(operador != null ){
                raiz.children.add( TreeItem(operador!!.lexema) )
                if(termino2 != null){
                    raiz.children.add( termino2!!.getArbolVisual() )

                }else if (expresionA != null ){
                    raiz.children.add( expresionA!!.getArbolVisual() )
                }
            }
        }
        return raiz
    }
    override fun traducir(): String? {
        var operador = ""
        /*if (termino != null) {
            val expA: String? = termino!!.traducir()
            if (operador  != null) {
                when (operador!!.lexema) {
                    "&:" -> operador = "&&"
                    "|:" -> operador = "||"
                    "~:" -> operador = "!"
                    else -> {
                    }
                }
                if (expresion2 != null) {
                    val exp: String? = expresion2!!.traducir()
                    return "$expA $operador $exp"
                }
            }
        }*/
        return null
    }

    override fun analizarSemantica(errores: ArrayList<String?>?, ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }
    }
