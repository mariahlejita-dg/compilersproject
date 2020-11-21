package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class Para: Sentencia {
    private var para: Token? = null
    var expresion: Expresion? = null
    private var decremento: SentenciaDecremento? = null
    private var incremento: SentenciaIncremento? = null
    private var listaSentencias: ArrayList<Sentencia>? = null

    constructor(para: Token, expresion: Expresion, decremento: SentenciaDecremento,
                incremento: SentenciaIncremento,listaSentencias: ArrayList<Sentencia>) {
        this.para = para
        this.expresion = expresion
        this.decremento=decremento
        this.incremento=incremento
        this.listaSentencias=listaSentencias
    }

    override fun getArbolVisual() : TreeItem<String>
    {
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


}