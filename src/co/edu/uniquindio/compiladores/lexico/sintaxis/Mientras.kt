package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.semantica.Simbolo
import co.edu.uniquindio.compiladores.lexico.semantica.TabladeSimbolos
import javafx.scene.control.TreeItem
import java.util.ArrayList
import javax.swing.tree.DefaultMutableTreeNode

class Mientras : Sentencia{

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



    override fun getArbolVisual(): TreeItem<String>{
            val raiz = TreeItem("Mientras")
            raiz.children.add(expresionLogica.getArbolVisual())
            if (listaSentencias != null) {
                for (sentencia in listaSentencias!!) {
                    raiz.children.add(sentencia.getArbolVisual())
                }
            }
            return raiz
        }
    fun analizarSemantica(error: ArrayList<String?>?, tS: TabladeSimbolos?, ambito: Simbolo?) {
        // TODO Auto-generated method stub
    }

   fun llenarTablaSimbolos(ts: TabladeSimbolos?, ambito: Simbolo?) {
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