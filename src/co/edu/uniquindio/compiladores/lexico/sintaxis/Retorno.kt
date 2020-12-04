package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class Retorno : Sentencia {

    private var palabra_reservada: Token? = null
    private var termino: Termino? = null
    private var terminal: Token? = null

    constructor(palabra_reservada: Token, termino: Termino ){

        this.palabra_reservada = palabra_reservada
        this.termino = termino
    }

    override fun getArbolVisual(): TreeItem<String>{
            val raiz = TreeItem("Retorno")
            raiz.children.add(termino!!.getArbolVisual())
            return raiz
        }
    fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {}
    fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String {
        return "return " + termino!!.traducir().toString() + ";"
    }

}