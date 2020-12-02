package co.edu.uniquindio.compiladores.lexico.sintaxis

import javafx.scene.control.TreeItem
import java.util.*


class UnidadDeCompilacion (var list : ArrayList<Metodo>) {

    fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Unidad de Compilacion")
        if(list != null){
            for (i in list!!){
                raiz.children.add(i.getArbolVisual())
            }
        }
        return raiz
    }
}