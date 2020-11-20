package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Termino (var termino: Token) {
    fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem( "Termino" )
        if( termino != null ){
            raiz.children.add( TreeItem(termino.lexema) )
        }
        return raiz
    }
}