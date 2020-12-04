package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList

class Leer : Sentencia {
    private var id : Token ?= null

    constructor(id: Token){
        this.id = id
    }

    override fun getArbolVisual() : TreeItem<String> {
        val raiz = TreeItem("Leer")
        raiz.children.add( TreeItem(id!!.lexema))
        return raiz
    }
    fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String {
        val idVariable: String = id!!.lexema.replace("#", "")
        return "$idVariable=JOptionPane.showInputDialog(null);"
    }
}