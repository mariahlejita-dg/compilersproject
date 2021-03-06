package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import javax.swing.tree.DefaultMutableTreeNode

class Paquete {


    private var palabra_reservada:  Token?= null
    var nombrePaquete: String? = null
    private var identificador_terminal: Token? = null

    /**
     * @param palabra_reservada
     * @param nombrePaquete
     */
    constructor(palabra_reservada: Token, nombrePaquete: String){
        this.palabra_reservada = palabra_reservada
        this.nombrePaquete = nombrePaquete
    }


    fun getArbolVisual() : TreeItem<String>
    {
        val raiz = TreeItem("Paquete")
        raiz.children.add(TreeItem(nombrePaquete))
            return raiz
        }
    fun traducir(): String {
        val st = nombrePaquete!!.replace("_".toRegex(), "")
        return "package $st;"
    }

    /**
     * @param palabra_reservada
     * @param nombrePaquete
     */
    init {
        this.palabra_reservada = palabra_reservada
        this.nombrePaquete = nombrePaquete
    }



}