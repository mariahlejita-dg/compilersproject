package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class SentenciaDecremento  :Sentencia{
    private var identificadorVariable: Token? = null
    private var decremento: Token? = null



    constructor(identificadorVariable: Token, decremento: Token ){

        this.identificadorVariable = identificadorVariable
        this.decremento = decremento
    }

    override fun getArbolVisual(): TreeItem<String>{
            val raiz = TreeItem("Sentencia incremento")
            raiz.children.add(TreeItem<String>(identificadorVariable!!.lexema))
            raiz.children.add(TreeItem<String>(decremento!!.lexema))
            return raiz
        }

     fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito:Simbolo) {
        // TODO Auto-generated method stub
    }

    fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String? {
        return identificadorVariable?.lexema!!.replace("#", "").toString() + "--"
    }
}