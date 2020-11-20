package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion (var tipoDato : Token, var identificadorVariable : Token, var operadorAsignacion : Token, var expresion : Expresion){
fun getArbolVisual() : TreeItem<String>{
    val raiz = TreeItem("Asignación")
        raiz.children.add( TreeItem(tipoDato.lexema) )
        raiz.children.add( TreeItem(identificadorVariable.lexema) )
        raiz.children.add( TreeItem(operadorAsignacion.lexema) )
        raiz.children.add(expresion.getArbolVisual())
    return raiz
}
}