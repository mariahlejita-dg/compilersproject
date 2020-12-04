package co.edu.uniquindio.compiladores.lexico.semantica

import co.edu.uniquindio.compiladores.lexico.sintaxis.ErrorSemantico
import co.edu.uniquindio.compiladores.lexico.sintaxis.UnidadDeCompilacion

class AnalizadorSemantico (var uc: UnidadDeCompilacion) {

    var unidadCompilacion: UnidadDeCompilacion? = null
    var  erroresSemanticos:ArrayList<ErrorSemantico> = ArrayList()
    var tablaSimbolos:TabladeSimbolos= TabladeSimbolos()


        fun  llenarTablaSimbolos() {

            uc.llenarTablaSimbolos(tablaSimbolos)
        }


        fun  analizarSemantica() {

            uc.analizarSemantica( tablaSimbolos,erroresSemanticos)
        }

    }
