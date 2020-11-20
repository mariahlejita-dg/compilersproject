package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class Mientras {

    private var palabraReservada: Token?= null
    var expresionLogica: ExpresionLogica
    private var listaSentencias: ArrayList<Sentencia>? = null

    /**
     * @param palabraReservada
     *
     * @param expresionLogica
     *
     * @param listaSentencias
     */
    constructor(
        palabraReservada: Token,
        expresionLogica: ExpresionLogica,
        listaSentencias: ArrayList<Sentencia>?
    ) : super() {
        this.palabraReservada = palabraReservada
        this.expresionLogica = expresionLogica
        this.listaSentencias = listaSentencias
    }

    /**
     * @param palabraReservada
     * @param simboloA1
     * @param expresionLogica
     */
    constructor(palabraReservada: Token, expresionLogica: ExpresionLogica) : super() {
        this.palabraReservada = palabraReservada
        this.expresionLogica = expresionLogica
    }



    val arbolVisual: TreeItem<String>
        get() {
            val raiz = TreeItem("Mientras")
            raiz.children.add(expresionLogica.getArbolVisual())
            if (listaSentencias != null) {
                for (sentencia in listaSentencias!!) {
                    raiz.children.add(sentencia.getArbolVisual())
                }
            }
            return raiz
        }

    override fun analizarSemantica(error: ArrayList<String?>?, tS: TablaSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun llenarTablaSimbolos(ts: TablaSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }


}