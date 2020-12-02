package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.lexico.Token

/**
 * @author María Alejnandra Naranjo Martinez
 * @author Fabio Augusto Vanegas
 * Esta clase se encarga de realizar el proceso sintactico y revisar que la estructura de los elementos del lenguaje se encuentren de forma correcta
 */
class AnalizadorSintactico2 (var listaTokens:ArrayList<Token>) {
    var posicionActual = 0
    var tokenActual = listaTokens[posicionActual]
    var listaErrores =  ArrayList<ErrorSintactico>()

    /**
     * Este metodo se encarga de obtener el siguiente token a analizar
     */
    fun obtenerSiguienteToken(){
        posicionActual++
        if(posicionActual < listaTokens.size){
            tokenActual = listaTokens[posicionActual]
        }
    }

    /**
     * Este metodo se encarga de guardar los errores producidos en la parte sintactica
     */
    fun reportarError (mensaje : String){
        listaErrores.add(ErrorSintactico(mensaje))
        print("Lista E"+listaErrores.size)
    }

    /**
     * Este metodo es el que se encarga de lanzar la parte sintactica de la app
     */
    fun esUnidadDeCompilacion() : UnidadDeCompilacion? {
        val listaMetodos : ArrayList<Metodo> = esListaMetodos()
        return if (listaMetodos.size > 0){
            UnidadDeCompilacion(listaMetodos)
        }else null
    }

    /**
     * Este metodo se encarga de guardar los metodos creados
     */
    fun esListaMetodos() : ArrayList<Metodo>{
        val lista : ArrayList<Metodo> = ArrayList<Metodo>()
        var m : Metodo? = esMetodo()

        while (m != null){
            lista.add(m)
            m = esMetodo()
        }
        return lista

    }

    /**
     * Este metodo se encarga de verificar que la estructura de un metodo se encuentre bien escrita
     */
    fun esMetodo(): Metodo? {
        val modificador : Token? = esModificador()
        if(modificador != null){
            if(tokenActual.categoria == Categoria.IDENTIFICADOR_METODO){
                val nombre = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".("){
                    obtenerSiguienteToken()
                    val parametros : ArrayList<Parametro> = esListaParametros()
                    if(tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)"){
                        obtenerSiguienteToken()
                        var tipoRetorno : Token? = null
                        if (tokenActual.categoria == Categoria.DOS_PUNTOS){
                            obtenerSiguienteToken()
                            tipoRetorno = esTipoRetorno()
                            print(tipoRetorno!!.lexema+"\n")
                            obtenerSiguienteToken()
                            if(tipoRetorno != null){
                                if(tipoRetorno.lexema == "NO-RETORNO"){
                                    if(tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{"){
                                        obtenerSiguienteToken()
                                        val bloqueSentencias : ArrayList<Sentencia> = esBloqueSentencias()
                                        if(bloqueSentencias != null){
                                            print(""+bloqueSentencias.size + "\n")
                                            if(tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}"){
                                                obtenerSiguienteToken()
                                                return Metodo(nombre,parametros, tipoRetorno!!, bloqueSentencias,Termino(tipoRetorno))
                                            }else{
                                                reportarError("Falta llave de cierre")
                                            }
                                        }else {
                                            reportarError("Faltó el bloque de sentencias en el metodo")
                                        }
                                    }else{
                                        reportarError("Falta llave de apertura")
                                    }
                                }else{
                                    if(tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{"){
                                        obtenerSiguienteToken()
                                        val bloqueSentencias : ArrayList<Sentencia> = esBloqueSentencias()
                                        if(bloqueSentencias != null){
                                            if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "RETORNO"){
                                                obtenerSiguienteToken()
                                                var termino : Termino? = esTermino()
                                                if(termino != null){
                                                    if(tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}"){
                                                        obtenerSiguienteToken()
                                                        return Metodo(nombre,parametros, tipoRetorno!!, bloqueSentencias,termino)
                                                    }else{
                                                        reportarError("Falto llave de cierre")
                                                    }
                                                }else{
                                                    reportarError("Falta termino a retornar")
                                                }
                                            }else {
                                                reportarError("falta  RETORNO")
                                            }
                                        }else {
                                            reportarError("Faltan sentencias")
                                        }
                                    }else {
                                        reportarError("Falta llave de apertura")
                                    }
                                }
                            }else {
                                reportarError("Falta tipo de retorno")
                            }

                        }
                    }else {
                        reportarError("Falta parentesis derecho")
                    }
                }else {
                    reportarError("Falta parentesis izquierdo")
                }
            }else {
                reportarError("Falta el nombre de la finción")
            }
        }
        return null
    }

    /**
     * Este metodo verifica que el token es un modificador : Publico o Privado
     */
    fun esModificador(): Token? {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA){
            if(tokenActual.lexema == "PUBLICO" || tokenActual.lexema == "PRIVADO"){
                obtenerSiguienteToken()
                return tokenActual
            }
        }
        return null
    }

    /**
     * Este metodo verifica el tipo de dato que la declaración retorno en un metodo
     */
    fun esTipoRetorno() : Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {

            if (tokenActual.lexema == "ENTERO" || tokenActual.lexema == "CADENA"
                || tokenActual.lexema == "REAL" || tokenActual.lexema == "NO-RETORNO"
                || tokenActual.lexema == "BOOLEANO"
            ) {
                return tokenActual;
            }

        }
        return null
    }

    /**
     * Este metodo se encarga de guardas la lista de parametros que se declaran oara un metodo
     */
    fun esListaParametros() : ArrayList<Parametro>{
        val parametros : ArrayList<Parametro> = ArrayList<Parametro>()
        var p : Parametro? = esParametro()
        while(p != null){
            parametros.add(p)
            p = esParametro()
        }
        return parametros
    }

    /**
     * Este metodo se encarga de verificar la estructura del parametro
     */
    fun esParametro() : Parametro? {

        if(tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE){
            var identificadorVariable = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.DOS_PUNTOS ){
                obtenerSiguienteToken()
               var tipoDato = esTipoDato()
                if(tipoDato != null){
                    return Parametro(identificadorVariable, tipoDato)
                }else{
                    reportarError("Faltó el tipo de variable")
                }

            }else {
                reportarError("Faltan los dos puntos (.:)")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar el tipo de dato de una variable, retorno etc
     */
    fun esTipoDato() : Token? {
        if (tokenActual.categoria === Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "ENTERO" || tokenActual.lexema == "CADENA_CARACTERES" || tokenActual.lexema == "REAL" || tokenActual.lexema == "BOOLEANO" || tokenActual.lexema == "CARACTER") {
                var tokenA = tokenActual
                obtenerSiguienteToken()
                return tokenA
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de guardar la sentencias que se encuentran en metodos o sentencias
     */
    fun esBloqueSentencias() : ArrayList<Sentencia>{
        val sentencias : ArrayList<Sentencia> = ArrayList<Sentencia>()
        var s : Sentencia? = esSentencia()
        while(s != null){
            sentencias.add(s)
            s = esSentencia()
        }
        return sentencias
    }

    /**
     * Este metodo se encarga de la verificacion del tipo de sentencia
     */
    fun esSentencia() : Sentencia? {
        var sentencia : Sentencia? = null

        sentencia = esAsignacion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esSentenciaMientras()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esInvocacionMetodo()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esImpresion()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esDeclaracionVariable()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esSentenciaSi()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esLeer()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esIncremento()
        if(sentencia != null){
            return sentencia
        }
        sentencia = esDecremento()
        if(sentencia != null){
            return sentencia
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de la sentencia decremento
     */
    fun esDecremento() : SentenciaDecremento? {
        if (tokenActual.categoria == Categoria.DECREMENTO) {
            val decremento = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
                val identificador = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.FINCODIGO){
                    obtenerSiguienteToken()
                    return SentenciaDecremento(identificador, decremento)
                }else{
                    reportarError("Falta fin de codigo")
                }
            } else {
                reportarError("Falta el operador de decremento  ")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de analizar la estructura de la sentencia incremento
     */
    fun esIncremento() : SentenciaIncremento? {
        if (tokenActual.categoria == Categoria.INCREMENTO) {
            val incremento = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                val identificador = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.FINCODIGO){
                    obtenerSiguienteToken()
                    return SentenciaIncremento(identificador, incremento)
                }else{
                    reportarError("Falta fin de codigo")
                }
            } else {
                reportarError("Falta el operador de incremento  ")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de leer
     */
    fun esLeer() : Leer? {
        val palabraReservada = tokenActual
        if (palabraReservada.categoria == Categoria.PALABRA_RESERVADA
            && palabraReservada.lexema == "LEER"
        ) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                val id = tokenActual
                obtenerSiguienteToken()
                val terminal = tokenActual
                if (terminal.categoria === Categoria.FINCODIGO) {
                    obtenerSiguienteToken()
                    return Leer(id)
                } else {
                    reportarError("Falta el terminal en el leer")
                }
            } else {
                reportarError("Falta el idVariable en el leer")
            }
        }
        return null
    }
    /**
     * Este metodo se encarga de verificar la estructura de la sentencia SI
     */
    fun esSentenciaSi() : Si? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema.equals("SI")) {
            var si : Token = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                obtenerSiguienteToken()
                var expresion : ExpresionRelacional? = esExpresionRelacional()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                        obtenerSiguienteToken();
                        if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{") {
                            obtenerSiguienteToken()
                            var listaSentencias :ArrayList<Sentencia> = esBloqueSentencias();
                            if (listaSentencias != null) {
                                if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                    obtenerSiguienteToken()
                                    return Si(si, expresion, listaSentencias);
                                } else {
                                    reportarError(
                                        "Falta simbolo de cierre } en SI")
                                }
                            } else {
                                if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                    obtenerSiguienteToken();
                                    return  Si(si, expresion);
                                } else {
                                    reportarError(
                                        "Falta simbolo de cierre } en SI")
                                }

                            }
                        } else {
                            reportarError(
                                "Falta simbolo de apertura en SI")
                        }
                    } else {
                        reportarError(  "Falta simbolo de cierre  en SI")
                    }
                } else {
                    reportarError("Falta una expresion relacional en SI");
                }
            } else {
                reportarError("Falta el simbolo de apertura  en SI")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de la sentencia Declaración de variable
     */
    fun esDeclaracionVariable() : DeclaracionVariable?{
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            val identificadorVariable =  tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.DOS_PUNTOS){
                obtenerSiguienteToken()
                val tipoDato = esTipoDato()
                if(tipoDato != null){
                    if(tokenActual.categoria == Categoria.FINCODIGO){
                        obtenerSiguienteToken()
                        return DeclaracionVariable(identificadorVariable, tipoDato)
                    }else {
                        reportarError("Falto fin de codigo")
                    }
                }else{
                    reportarError("Faltó el tipo de dato")
                }
            }else {
                reportarError("Faltan los dos punto")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de IMPRIMIR
     */
    fun esImpresion() : Impresion? {
        val palabraReservada = tokenActual
        if (palabraReservada.categoria == Categoria.PALABRA_RESERVADA && palabraReservada.lexema == "IMPRIMIR") {
            obtenerSiguienteToken()
            val parIzq = tokenActual
            if (parIzq.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                obtenerSiguienteToken()
                val exp = esExpresion()
                if (exp != null) {
                    val parDer = tokenActual
                    if (parDer.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                        obtenerSiguienteToken()
                        val finSentencia = tokenActual
                        if (finSentencia.categoria === Categoria.FINCODIGO) {
                            obtenerSiguienteToken()
                            return Impresion(palabraReservada, exp)
                        } else {
                            reportarError("Falta el terminal")
                        }
                    } else {
                        reportarError(
                            "Falta parentesis derecho en la impresion")
                    }
                } else {
                    reportarError("Falta expresion en la impresion")
                }
            } else {
                reportarError(
                    "Falta parentesis izquierdo en la impresion")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de invocar un metodo
     */
    fun esInvocacionMetodo() : InvocacionMetodo? {
        if(tokenActual.categoria == Categoria.IDENTIFICADOR_METODO){
            val nombreMetodo = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".("){
                obtenerSiguienteToken()
                val argumentos : ArrayList<Expresion> = esListaArgumentos()
                if(tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)"){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.FINCODIGO){
                        obtenerSiguienteToken()
                        return InvocacionMetodo(nombreMetodo,argumentos)
                    }else {
                        reportarError("Falta fin de codigo en IM")
                    }
                }else{
                    reportarError("Falta adrupador de cierre en IM")
                }
            }else{
                reportarError("Fata agrupador de apertura en IM")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de guardar Expresiones
     */
    fun esListaArgumentos() : ArrayList<Expresion> {
        val argumentos : ArrayList<Expresion> = ArrayList<Expresion>()
        var ex : Expresion? = esExpresion()
        while (ex != null){
            argumentos.add(ex)
            ex = esExpresion()
        }
        return argumentos
    }
    /**
     * Este metodo se encarga de verificar la estructura de la sentencia mientras
     */
    fun esSentenciaMientras() : Mientras? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "MIENTRAS") {
            val mientras = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                obtenerSiguienteToken()
                val expresionLogica = esExpresionLogica()
                if (expresionLogica != null) {
                    if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{") {
                            obtenerSiguienteToken()
                            val listaSentencias = esBloqueSentencias()
                            if (listaSentencias != null) {
                                if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                    obtenerSiguienteToken()
                                    return Mientras(mientras, expresionLogica, listaSentencias)
                                } else {
                                    reportarError(
                                        "Falta terminal en SM" )
                                }
                            } else if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                obtenerSiguienteToken()
                                return Mientras(mientras, expresionLogica)
                            }
                        } else {
                            reportarError(
                                "Falta llave izquierda  en SM")
                        }
                    } else {
                        reportarError(
                            "Falta simbolo de cierre  en SM")
                    }
                } else {
                    reportarError("Falta la expresion logica en SM")
                }
            } else {
                reportarError("Falta el simbolo de apertura  en SM")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de Asignación
     */
    fun esAsignacion() : Asignacion? {
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
                val identificadorVariable = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria === Categoria.OPERADOR_ASIGNACION) {
                    val operador = tokenActual
                    obtenerSiguienteToken()
                    val expresion = esExpresion()
                    if (expresion != null) {
                        if (tokenActual.categoria === Categoria.FINCODIGO) {
                            obtenerSiguienteToken()
                            return Asignacion(tipoDato, identificadorVariable, operador, expresion)
                        } else {
                            reportarError("Falta el terminal en asignacion ")
                        }
                    } else {
                        reportarError("Falta la expresion en asignacion")
                    }
                }
                reportarError("Falta el operador de asignacion ")
            }
            reportarError("Falta el identificafor de variable en asignacion ")
        }
        return null
    }
    /**
     * Este metodo se encarga de verificar Que tipo de Expresión es
     */
    fun esExpresion() : Expresion? {
        var expresion : Expresion? = null

        expresion = esExpresionAritmetica()
        if (expresion != null) {
            return expresion
        }
        expresion = esExpresionRelacional()
        if (expresion != null) {
            return expresion
        }
        expresion = esExpresionLogica()
        if (expresion != null) {
            return expresion
        }
        expresion = esExpresionCadena()
        return expresion

        return expresion
    }

    /**
     * Este metodo se encarga de verificar la estructura de La expresión cadena
     */
    fun esExpresionCadena(): ExpresionCadena? {
        if (tokenActual.categoria === Categoria.CADENA_CARACTERES) {
            val cadena = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.CONCATENACION) {
                val concatenacion = tokenActual
                obtenerSiguienteToken()
                val exp = esExpresion()
                if (exp != null) {
                    obtenerSiguienteToken()
                    return ExpresionCadena(cadena, concatenacion, exp)
                } else {
                    reportarError("Falta la palabra con")
                }
            } else {
                obtenerSiguienteToken()
                return ExpresionCadena(cadena)
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de la expresión lógica
     */
    fun esExpresionLogica(): ExpresionLogica? {
        val expresion1 = esExpresionRelacional()
        if (expresion1 != null) {
            if (tokenActual.categoria === Categoria.OPERADOR_LOGICO) {
                val operador = tokenActual
                obtenerSiguienteToken()
                val expresion2 = esExpresionRelacional()
                if (expresion2 != null) {
                    // obtenerSgteToken();
                    return ExpresionLogica(expresion1, operador, expresion2)
                } else {
                    reportarError("Falta una expresion en EL ")
                }
            } else {

                return ExpresionLogica(expresion1)
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de expresión Relacional
     */
    private fun esExpresionRelacional(): ExpresionRelacional? {
        val expAritmetica: ExpresionAritmetica? = esExpresionAritmetica()
        if (expAritmetica != null) {
            //obtenerSgteToken();
            if (tokenActual.categoria === Categoria.OPERADOR_RELACIONAL) {
                val opRelacional = tokenActual
                obtenerSiguienteToken()
                val expArit: ExpresionAritmetica? = esExpresionAritmetica()
                if (expArit != null) {
                    return ExpresionRelacional(expAritmetica, opRelacional, expArit)
                } else {
                    reportarError("falta Expresión Aritmetica en ER")
                }
            } else {
                reportarError("Falta operador Relacional ER")
            }
        }
        val expCad1 = esExpresionCadena()
        if (expCad1 != null) {
            //obtenerSgteToken();
            if (tokenActual.categoria === Categoria.OPERADOR_RELACIONAL) {
                val opRelacional = tokenActual
                obtenerSiguienteToken()
                val expCad2 = esExpresionCadena()
                if (expCad2 != null) {
                    return ExpresionRelacional(expCad1, opRelacional, expCad2)
                } else {
                    reportarError("falta Expresión Cadena en ER")
                }
            } else {
                reportarError("Falta operador Cadena en ER")
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de la expresión Aritmetica
     */
    fun esExpresionAritmetica() : ExpresionAritmetica? {
        val term1: Termino? = esTermino()
        if (term1 != null) {
            if (tokenActual.categoria === Categoria.OPERADOR_ARITMETICO) {
                val operAritm = tokenActual
                obtenerSiguienteToken()
                val term2: Termino? = esTermino()
                if (term2 != null) {
                    return ExpresionAritmetica(term1, operAritm, term2)
                } else if (tokenActual.categoria === Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                    val agruIzq = tokenActual
                    obtenerSiguienteToken()
                    val exprArit: ExpresionAritmetica? = esExpresionAritmetica()
                    if (exprArit != null) {

                        //obtenerSgteToken();
                        if (tokenActual.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                            val agruDer = tokenActual
                            obtenerSiguienteToken()
                            return ExpresionAritmetica(term1, operAritm, exprArit)
                        } else {
                            reportarError("Falta agrupador derecho en EA")
                        }
                    } else {
                        reportarError("falta expresion aritmetica en EA")
                    }
                } else {
                    reportarError("Falta agrupador izquierdo en EA")
                }
            } else {

                return ExpresionAritmetica(term1)
            }
        }
        return null
    }

    /**
     * Este metodo se encarga de verificar la estructura de Termino
     */
    fun esTermino() : Termino? {
        var termino: Token? = null
        if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
            termino = tokenActual
            obtenerSiguienteToken()
            return Termino(termino)
        } else if (tokenActual.categoria === Categoria.ENTERO) {
            termino = tokenActual
            obtenerSiguienteToken()
            return Termino(termino)
        } else if (tokenActual.categoria === Categoria.REAL) {
            termino = tokenActual
            obtenerSiguienteToken()
            return Termino(termino)
        }
        return null
    }
}