package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

abstract class Expresion {
    abstract fun getArbolVisual() : TreeItem<String>
    abstract fun traducir(): String?
    abstract fun analizarSemantica(errores: ArrayList<String?>?, ts: TabladeSimbolos?, ambito: Simbolo?)
}