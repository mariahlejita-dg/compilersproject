package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class InvocacionMetodo :  Sentencia {
    private var identificadorClase  : Token ?= null
    private var argumentos : ArrayList<Expresion> ?= null

    constructor(identificadorClase : Token, argumentos : ArrayList<Expresion>){
        this.identificadorClase = identificadorClase
        this.argumentos = argumentos
    }
    override fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Invocacion Metodo")
        raiz.children.add( TreeItem(identificadorClase!!.lexema) )
        if(argumentos!!.isNotEmpty()){
            for (i in argumentos!!){
                raiz.children.add(i.getArbolVisual())
            }
        }
        return raiz
    }
}