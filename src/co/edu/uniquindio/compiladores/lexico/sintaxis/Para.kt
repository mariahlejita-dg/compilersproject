package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class Para {
    private var para: Token? = null
    var expresion: Expresion? = null
    private var decremento: SentenciaDecremento? = null
    private var incremento: SentenciaIncremento? = null
    private var listaSentencias: ArrayList<Sentencia>? = null




    val arbolVisual: TreeItem<String>
        get() {
            val raiz = TreeItem("Para")
            raiz.children.add(expresion!!.getArbolVisual())
            if (decremento != null) {
                if (listaSentencias != null) {
                    for (sentencia in listaSentencias!!) {
                        raiz.children.add(sentencia.getArbolVisual())
                    }
                }
            }
            if (incremento != null) {
                if (listaSentencias != null) {
                    for (sentencia in listaSentencias!!) {
                        raiz.children.add(sentencia.getArbolVisual())
                    }
                }
            }
            return raiz
        }

    fun analizarSemantica(error: ArrayList<String?>?, tS: TablaSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    fun llenarTablaSimbolos(ts: TablaSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }
}