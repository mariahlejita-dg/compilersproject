package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import javax.swing.tree.DefaultMutableTreeNode

class Parametro {
    private var identificadorVariable: Token? = null
    private var tipoDedato: Token? = null

    constructor(identificadorVariable: Token, tipoDedato: Token) {

        this.identificadorVariable = identificadorVariable
        this.tipoDedato = tipoDedato
    }

    fun getArbolVisual() : TreeItem<String>{
            val raiz =TreeItem("Parametro")
            raiz.children.add(TreeItem<String>("${identificadorVariable!!.lexema} : ${tipoDedato!!.lexema}"))
            return raiz
        }






    }