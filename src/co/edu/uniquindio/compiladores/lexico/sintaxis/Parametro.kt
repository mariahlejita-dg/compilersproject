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

    fun getArbolVisual() : TreeItem<String>
    {
            val raiz =TreeItem("Parametro")
            raiz.children.add(TreeItem<String>("${identificadorVariable!!.lexema} : ${tipoDedato!!.lexema}"))
            return raiz
        }


    fun traducir(): String {
        var code = ""
        var st = ""
        if (tipoDedato!!.lexema.equals("CADENA")) {
            code += "String "
        }
        if (tipoDedato!!.lexema.equals("ENTERO")) {
            code += "int "
        }
        if (tipoDedato!!.lexema.equals("BOOLEANO")) {
            code += "boolean "
        }
        if (tipoDedato!!.lexema.equals("REAL")) {
            code += "float "
        }
        st = identificadorVariable!!.lexema.replace("#", "")
        return "$code $st"
    }



    }