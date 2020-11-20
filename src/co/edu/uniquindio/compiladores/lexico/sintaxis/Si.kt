package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import java.util.ArrayList


class Si {
    private var si: Token? = null
    var expresion: ExpresionRelacional? = null
    var listaSentencias: ArrayList<Sentencia>? = null

    constructor(si: Token, expresion: ExpresionRelacional) : super() {
        this.si = si
        this.expresion = expresion
    }

    /**
     * @param si
     *
     * @param expresion
     *
     * @param listaSentencias
     */
    constructor(si: Token, expresion: ExpresionRelacional,listaSentencias: ArrayList<Sentencia>?
    ) : super() {
        this.si = si
        this.expresion = expresion
        this.listaSentencias = listaSentencias
    }



    val arbolVisual: TreeItem<String>
        get() {
            val raiz = TreeItem("Si")
            raiz.children.add(expresion!!.getArbolVisual())
            if (listaSentencias != null) {
                for (sentencia in listaSentencias!!) {
                    raiz.children.add(sentencia.getArbolVisual())
                }
            }
            return raiz
        }
}