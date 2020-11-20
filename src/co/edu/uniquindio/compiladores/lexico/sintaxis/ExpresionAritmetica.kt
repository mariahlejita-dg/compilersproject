package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica : Expresion {
    private var termino : Termino ?= null
    private var  operador : Token ?= null
    private var termino2 : Termino ?= null
    private var expresionA : ExpresionAritmetica ?= null
    constructor()
    constructor(termino: Termino):this(){
        this.termino = termino
    }
    constructor(termino : Termino, operador : Token, termino2 : Termino) : this(){
        this.termino = termino
        this.operador = operador
        this.termino2 = termino2
    }
    constructor(termino : Termino, operador : Token, expresionA: ExpresionAritmetica) : this(){
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
            }else {
                raiz.children.add( termino!!.getArbolVisual() )
            }
        }
        return raiz
    }


}