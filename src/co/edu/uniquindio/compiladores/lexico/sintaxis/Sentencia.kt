package co.edu.uniquindio.compiladores.lexico.sintaxis

import javafx.scene.control.TreeItem

abstract class Sentencia {
    abstract fun getArbolVisual() : TreeItem<String>

}