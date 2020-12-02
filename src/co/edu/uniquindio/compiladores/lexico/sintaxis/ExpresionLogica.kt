package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import sun.reflect.generics.tree.Tree

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
}