package co.edu.uniquindio.compiladores.lexico.prueba

import co.edu.uniquindio.compiladores.lexico.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.sintaxis.AnalizadorSintactico2


fun main (){
   val lexico = AnalizadorLexico("   #d#  . ; |Y [s]")
    lexico.analizar()
    //print(lexico.listaTokens)
    val sintaxis= AnalizadorSintactico2(lexico.listaSimbolos)
    //print (sintaxis.esUnidadCompilacion())


}