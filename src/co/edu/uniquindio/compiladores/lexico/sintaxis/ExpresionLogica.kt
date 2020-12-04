package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import sun.reflect.generics.tree.Tree
import java.util.ArrayList

class ExpresionLogica : Expresion {
    private var expresion1 : ExpresionRelacional ?= null
    private var operadorLogico : Token ?= null
    private var expresion2 : ExpresionRelacional ?= null

    constructor()
    constructor(expresion1 : ExpresionRelacional, operdorLogico : Token, expresion2 : ExpresionRelacional) : this(){
        this.expresion1 = expresion1
        this.operadorLogico = operdorLogico
        this.expresion2 = expresion2
    }
    constructor(expresion1 : ExpresionRelacional) : this(){
        this.expresion1 = expresion1
    }


    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem( "Expresion logica")
        raiz.children.add( expresion1!!.getArbolVisual() )
        if(operadorLogico != null){
            raiz.children.add( TreeItem( operadorLogico!!.lexema) )
        }
        if(expresion2 != null){
            raiz.children.add( expresion2!!.getArbolVisual() )
        }
        return raiz
    }
    override fun traducir(): String? {
        var operador = ""
        if (expresion1 != null) {
            val expA: String? = expresion1!!.traducir()
            if (operadorLogico != null) {
                when (operadorLogico!!.lexema) {
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
        }
        return null
    }

 override fun analizarSemantica(errores: ArrayList<String?>?, ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }
}