package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional : Expresion {
    private var expresionAritmetica : ExpresionAritmetica ?= null
    private var expresionAritmetica2 : ExpresionAritmetica ?= null
    private var operadorRelacional : Token ?= null
    private var expresionC : ExpresionCadena ?= null
    private var expresionC1 : ExpresionCadena ?= null

    constructor()
    constructor(expresionAritmetica: ExpresionAritmetica, operadorRelacional: Token, expresionAritmetica1: ExpresionAritmetica ): this(){
        this.expresionAritmetica = expresionAritmetica
        this.operadorRelacional = operadorRelacional
        this.expresionAritmetica2 = expresionAritmetica1
    }
    constructor(expresionC : ExpresionCadena, operadorRelacional: Token, expresionC1: ExpresionCadena) : this(){
        this.expresionC = expresionC
        this.operadorRelacional = operadorRelacional
        this.expresionC1 = expresionC1
    }
    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem( "Expresion relasional" )
        if(expresionAritmetica != null){
            raiz.children.add( expresionAritmetica!!.getArbolVisual())
            if(operadorRelacional != null){
                raiz.children.add( TreeItem(operadorRelacional!!.lexema))
                if(expresionAritmetica2 != null){
                    raiz.children.add( expresionAritmetica2!!.getArbolVisual() )
                }
            }
        }else if( expresionC != null ){
            raiz.children.add(expresionC!!.getArbolVisual())
            if(operadorRelacional != null){
                raiz.children.add( TreeItem(operadorRelacional!!.lexema) )
                if(expresionC1 != null){
                    raiz.children.add(expresionC1!!.getArbolVisual())
                }
            }
        }
        return raiz
    }
}