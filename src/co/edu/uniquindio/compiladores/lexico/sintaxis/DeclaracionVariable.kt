package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class DeclaracionVariable (var identificadorVariable : Token, var tipoDato : Token ) : Sentencia() {
    override fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem( "Declaracion Variable" )
        raiz.children.add( TreeItem(identificadorVariable.lexema) )
        raiz.children.add( TreeItem(tipoDato.lexema) )
        return raiz
    }

}