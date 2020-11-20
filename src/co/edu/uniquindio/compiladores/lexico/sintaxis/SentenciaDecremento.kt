package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class SentenciaDecremento {
    private var identificadorVariable: Token? = null
    private var decremento: Token? = null



    constructor(identificadorVariable: Token, decremento: Token ){

        this.identificadorVariable = identificadorVariable
        this.decremento = decremento
    }

    val arbolVisual: TreeItem<String>
        get() {
            val raiz = TreeItem("Sentencia incremento")
            raiz.children.add(TreeItem<String>(identificadorVariable!!.lexema))
            raiz.children.add(TreeItem<String>(decremento!!.lexema))
            return raiz
        }


}