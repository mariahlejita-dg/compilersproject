package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class SentenciaIncremento: Sentencia {
    private var identificadorVariable: Token? = null
    private var incremento: Token? = null

    constructor(identificadorVariable: Token, incremento: Token ){
        this.identificadorVariable = identificadorVariable
        this.incremento = incremento
    }

    override fun getArbolVisual() : TreeItem<String>
    {
            val raiz = TreeItem("Sentencia incremento")
            raiz.children.add(TreeItem<String>(identificadorVariable!!.lexema))
            raiz.children.add(TreeItem<String>(incremento!!.lexema))
            return raiz
        }

    fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

     fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String? {
        return identificadorVariable!!.lexema.replace("#", "").toString() + "++"
    }

}