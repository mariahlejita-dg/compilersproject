package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem

class Clase (var modificador : Token, var palabra_reservada_clase : Token, var identificador_clase : Token,
             var cuerpoClase : CuerpoClase ) {
    fun getArbolVisual() : TreeItem<String>{
        val raiz = TreeItem("Clase")
        if(cuerpoClase != null){
            raiz.children.add( TreeItem(identificador_clase.lexema))
            raiz.children.add( cuerpoClase.getArbolVisual() )
            return raiz
        }
        return raiz
    }
}