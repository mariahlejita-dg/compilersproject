package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import javax.swing.tree.DefaultMutableTreeNode

class Retorno {

    private var palabra_reservada: Token? = null
    private var termino: Termino? = null
    private var terminal: Token? = null

    constructor(palabra_reservada: Token, termino: Termino,terminal: Token ){

        this.palabra_reservada = palabra_reservada
        this.termino = termino
    }

    fun getArbolVisual() : TreeItem<String>
    {
            val raiz = TreeItem("Retorno")
            raiz.children.add(termino!!.getArbolVisual())
            return raiz
        }

}