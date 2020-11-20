package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class SentenciaIncremento {
    private var identificadorVariable: Token? = null
    private var incremento: Token? = null

    constructor(identificadorVariable: Token, incremento: Token ){
        this.identificadorVariable = identificadorVariable
        this.incremento = incremento
    }

    val arbolVisual: TreeItem<String>
        get() {
            val raiz = TreeItem("Sentencia incremento")
            raiz.children.add(TreeItem<String>(identificadorVariable!!.lexema))
            raiz.children.add(TreeItem<String>(incremento!!.lexema))
            return raiz
        }



}