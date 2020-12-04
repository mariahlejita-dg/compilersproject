package co.edu.uniquindio.compiladores.lexico.prueba

import co.edu.uniquindio.compiladores.lexico.lexico.AnalizadorLexico
//import co.edu.uniquindio.compiladores.lexico.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.lexico.sintaxis.AnalizadorSintactico2


fun main (){
   val lexico = AnalizadorLexico("   #d#  . ; |Y [s]")
    lexico.analizar()
    lexico.listaSimbolos
    val sintaxis= AnalizadorSintactico2(lexico.listaSimbolos)



}