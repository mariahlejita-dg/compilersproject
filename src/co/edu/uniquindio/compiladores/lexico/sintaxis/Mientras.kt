package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
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



    val arbolVisual: DefaultMutableTreeNode
        get() {
            val nodo = DefaultMutableTreeNode("Mientras")
            nodo.add(expresionLogica.arbolVisual)
            if (listaSentencias != null) {
                for (sentencia in listaSentencias!!) {
                    nodo.add(sentencia.getArbolVisual())
                }
            }
            return nodo
        }

    override fun analizarSemantica(error: ArrayList<String?>?, tS: TablaSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun llenarTablaSimbolos(ts: TablaSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

    override fun traducir(): String {
        val code = "while"
        var sent = ""
        for (i in listaSentencias!!.indices) {
            if (i == listaSentencias!!.size - 1) {
                sent += listaSentencias!![i].traducir()
            } else {
                sent += listaSentencias!![i].traducir().toString() + ", "
            }
        }
        return """$code (${expresionLogica.traducir()} ) {
$sent
 }"""
    }

}