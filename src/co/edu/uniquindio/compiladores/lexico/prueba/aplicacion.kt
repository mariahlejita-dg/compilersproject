package co.edu.uniquindio.compiladores.lexico.prueba

import co.edu.uniquindio.compiladores.lexico.lexico.AnalizadorLexico

fun main (){
   val lexico = AnalizadorLexico("   #d#  . ; |Y [s]")
    lexico.analizar()
    //print(lexico.listaTokens)

}