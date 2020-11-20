package co.edu.uniquindio.compiladores.lexico.sintaxis

import javafx.scene.control.TreeItem
import java.util.ArrayList

abstract class Sentencia {
    abstract fun getArbolVisual() : TreeItem<String>


}