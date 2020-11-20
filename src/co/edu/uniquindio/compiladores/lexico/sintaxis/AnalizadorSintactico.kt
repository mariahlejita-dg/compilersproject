package co.edu.uniquindio.compiladores.lexico.sintaxis

import co.edu.uniquindio.compiladores.lexico.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.lexico.Token
import java.util.*


class AnalizadorSintactico(tablaToken: ArrayList<Token>) {
    var tablaErrores :ArrayList<ErrorSintactico> = ArrayList<ErrorSintactico>()
    var tablaSimbolos : ArrayList<Token> = tablaToken
    var posActual : Int = 0
    var tokenActual = tablaToken.get(posActual)
    var unidadDeCompilacion : UnidadDeCompilacion ?= null
    
    fun analizar(){
        
    }

    /**
     * Metodo para volver a una instancia anterior del codigo
     *
     * @param posInicial: posicion donde se inicio el analisis
     */
    fun hacerBactracking(posInicial: Int) {
        posActual = posInicial
        tokenActual = tablaSimbolos[posActual]
    }
    fun obtenerSiguienteToken(){
        if(posActual < tablaSimbolos.size - 1){
            posActual++
            tokenActual = tablaSimbolos.get(posActual)
        }else {
            tokenActual = Token("", 0, 0, 0, Categoria.FINCODIGO)
        }
    }
    
    fun reportarError(mensaje: String, fila: Int, columna: Int){
        tablaErrores.add(ErrorSintactico(mensaje, fila, columna))
    }
    
    fun esUnidadCompilacion() : UnidadDeCompilacion? {
        var paquete : Paquete = esPaquete()
        if(paquete != null){
            var importaciones : ArrayList<Importacion> = esListaImportaciones()
            if(importaciones != null){
                var clase : Clase = esClase()
                if(clase != null){
                    obtenerSiguienteToken()
                    return UnidadDeCompilacion(paquete, importaciones, clase)
                }else {
                    reportarError("Falta la clase en UC", tokenActual.fila, tokenActual.columna)
                }
            }else {
                var clase : Clase = esClase()
                if(clase != null){
                    obtenerSiguienteToken()
                    return UnidadDeCompilacion(paquete, clase)
                }else {
                    reportarError("Falta clase en UC", tokenActual.fila, tokenActual.columna)
                }
            }
        }else {
            reportarError("Falta paquete en UC", tokenActual.fila, tokenActual.columna)
        }

        return null
    }

    private fun esClase(): Clase {
        var modificador : Token? = null
        if(modificador != null){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema =="CLASE"){
                var clase : Token = tokenActual
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR_CLASE){
                    var identificadorClase : Token = tokenActual
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{"){
                        obtenerSiguienteToken()
                        var cuerpoClase : CuerpoClase = esCuerpoClase()
                        if(cuerpoClase != null){
                            if(tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}"){
                                obtenerSiguienteToken()
                                return Clase(modificador, clase, identificadorClase, cuerpoClase)
                            }else {
                                reportarError(
                                    "Falta simbolo de cierre .} en clase",
                                    tokenActual.fila,
                                    tokenActual.columna
                                )
                            }
                        }
                        if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {

                            obtenerSiguienteToken();
                            return Clase(modificador, clase, identificadorClase);
                        } else {
                            reportarError(
                                "Falta el simbolo de cierre } en clase", tokenActual.fila,
                                tokenActual.columna
                            );
                        }
                    } else {
                        reportarError(
                            "Falta el simbolo de apertura { en clase", tokenActual.fila,
                            tokenActual.columna
                        );
                    }
                } else {
                    reportarError(
                        "Falta el identificador de la clase", tokenActual.fila,
                        tokenActual.columna
                    );
                }

            } else {
                reportarError("Falta la palabra reservada CLASE", tokenActual.fila, tokenActual.columna);
            }

        }
        return null!!
    }

    private fun esCuerpoClase(): CuerpoClase {

        var posInicial: Int = posActual;
        var metodo:Metodo = esMetodo();

        if (metodo != null) {

            obtenerSiguienteToken()
            var cuerpoClase:CuerpoClase = esCuerpoClase();
            if (cuerpoClase != null) {
                return CuerpoClase(metodo, cuerpoClase);
            } else {
                return  CuerpoClase(metodo);
            }
        } else {
            hacerBactracking(posInicial);
            var declaracionVariable : DeclaracionVariable = esDeclaracionVariable() as DeclaracionVariable
            if (declaracionVariable != null) {

                var cuerpoClase1 : CuerpoClase = esCuerpoClase();
                if (cuerpoClase1 != null) {
                    return CuerpoClase(declaracionVariable, cuerpoClase1)
                } else {
                    return CuerpoClase(declaracionVariable)
                }
            }
        }
        return null!!
    }

    fun esDeclaracionVariable(): Sentencia {
        val posInicial = posActual
        if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
            val identificadorVariable = tokenActual
            obtenerSiguienteToken()
            val tipoDato: Token = esTipoDato()
            if (tipoDato != null) {
                obtenerSiguienteToken()
                if (tokenActual.categoria === Categoria.IDENTIFICADOR_TERMINAL) {
                    obtenerSiguienteToken()
                    return DeclaracionVariable(identificadorVariable, tipoDato)
                } else {
                    reportarError("Falta el terminal en DV", tokenActual.fila, tokenActual.columna)
                }
            } else {
                if (tokenActual.categoria === Categoria.OPERADOR_ASIGNACION || tokenActual.categoria === Categoria.INCREMENTO || tokenActual.categoria === Categoria.DECREMENTO || tokenActual.categoria === Categoria.IDENTIFICADOR_TERMINAL) {
                    hacerBactracking(posInicial)
                } else {
                    reportarError("Falta tipo de dato en DV", tokenActual.fila, tokenActual.columna)
                }
            }
        }
        return null!!
    }



    private fun esMetodo(): Metodo {
        var modificador : Token ?= null;
        var tipoRetorno : Token ?= null;
        modificador = esModificador();
        if (modificador != null) {

            obtenerSiguienteToken();

            tipoRetorno = esTipoRetorno();
            if (tipoRetorno != null) {

                obtenerSiguienteToken();
                if (tokenActual.categoria == Categoria.IDENTIFICADOR_METODO) {
                    var identificadorMetodo : Token = tokenActual;
                    obtenerSiguienteToken();
                    if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {

                        obtenerSiguienteToken();
                        var parametros: ArrayList<Parametro> = esListaParametros();

                        if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                            obtenerSiguienteToken();
                            if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{") {
                                obtenerSiguienteToken();
                                var listaSentencias :ArrayList<Sentencia> = esListaSentencias();

                                if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                    obtenerSiguienteToken();

                                    return Metodo(
                                        modificador, tipoRetorno, identificadorMetodo, parametros,
                                        listaSentencias
                                    );
                                } else {
                                    reportarError(
                                        "Falta llave derecha en metodo", tokenActual.fila,
                                        tokenActual.columna
                                    );
                                }

                            } else {
                                reportarError(
                                    "Falta llave izquierda en metodo", tokenActual.fila,
                                    tokenActual.columna
                                );
                            }
                        } else {
                            reportarError(
                                "Falta agrupador derecho -> en metodo", tokenActual.fila,
                                tokenActual.columna
                            );
                        }

                    } else {
                        reportarError(
                            "Falta simbolo Apertura <- en metodo", tokenActual.fila,
                            tokenActual.columna
                        );
                    }

                } else {
                    reportarError("Falta identificador Metodo", tokenActual.fila, tokenActual.columna);
                }
            } else {
                reportarError("Falta tipo de retorno en metodo", tokenActual.fila, tokenActual.columna);
            }
        }
        return null!!
    }

    private fun esListaSentencias(): ArrayList<Sentencia> {
        var sentencias : ArrayList<Sentencia> = ArrayList<Sentencia>();
        var s : Sentencia = esSentencia();

        while (s != null) {

            sentencias.add(s);
            s = esSentencia();

        }

        return sentencias;
    }

    private fun esSentencia(): Sentencia {
        var sentencia : Sentencia

        sentencia = esSentenciaMientras();
        if (sentencia != null) {
            return sentencia;

        }
        sentencia = esAsignacion();
        if (sentencia != null) {
            return sentencia;

        }
        sentencia = esSentenciaRetorno();
        if (sentencia != null) {
            return sentencia;

        }

        sentencia = esInvocacionMetodo();
        if (sentencia != null) {
            return sentencia;

        }
        sentencia = esImpresion();
        if (sentencia != null) {
            return sentencia;

        }

        sentencia = esDeclaracionVariable()
        if (sentencia != null) {
            return sentencia;

        }
        sentencia = esSentenciaSI();
        if (sentencia != null) {
            return sentencia;

        }
        sentencia = esLeer();
        if (sentencia != null) {
            return sentencia;

        }
        sentencia = esIncremento();
        if (sentencia != null) {
            return sentencia;

        }
        sentencia = esDecremento();
        if (sentencia != null) {
            return sentencia;

        }
        return null;
    }



    private fun esSentenciaSI(): Sentencia {

        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema.equals("SI")) {
            var si : Token = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                obtenerSiguienteToken()
                var expresion : ExpresionRelacional = esExpresionRelacional()
                if (expresion != null) {
                    if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                        obtenerSiguienteToken();
                        if (tokenActual.categoria == Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{") {

                            obtenerSiguienteToken()
                             var listaSentencias :ArrayList<Sentencia> = esListaSentencias();
                            if (listaSentencias != null) {
                                // obtenerSgteToken();
                                if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {

                                    obtenerSiguienteToken()
                                    return Si(si, expresion, listaSentencias);
                                } else {
                                    reportarError(
                                        "Falta simbolo de cierre } en SI", tokenActual.fila,
                                        tokenActual.columna
                                    );
                                }
                            } else {
                                if (tokenActual.categoria == Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                    obtenerSiguienteToken();
                                    return  Si(si, expresion);
                                } else {
                                    reportarError(
                                        "Falta simbolo de cierre } en SI", tokenActual.fila,
                                        tokenActual.columna
                                    );
                                }

                            }
                        } else {
                            reportarError(
                                "Falta simbolo de apertura { en SI", tokenActual.fila,
                                tokenActual.columna
                            );
                        }
                    } else {
                        reportarError(
                            "Falta simbolo de cierre -> en SI", tokenActual.fila,
                            tokenActual.columna
                        );
                    }
                } else {
                    reportarError("Falta una expresion relacional en SI", tokenActual.fila, tokenActual.columna);
                }
            } else {
                reportarError("Falta el simbolo de apertura <- en SI", tokenActual.fila, tokenActual.getColumna());
            }
        }
        return null!!
    }

    fun esExpresionRelacional(): ExpresionRelacional? {
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
                    reportarError("falta Expresión Aritmetica en ER", tokenActual.fila, tokenActual.columna)
                }
            } else {
                reportarError("Falta operador Relacional ER", tokenActual.fila, tokenActual.columna)
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
                    reportarError("falta Expresión Cadena en ER", tokenActual.fila, tokenActual.columna)
                }
            } else {
                reportarError("Falta operador Cadena en ER", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }

    fun esExpresion(): Expresion? {
        var expresion: Expresion? = null
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
    }

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
                    reportarError("Falta la palabra con", tokenActual.fila, tokenActual.columna)
                }
            } else {
                obtenerSiguienteToken()
                return ExpresionCadena(cadena)
            }
        }
        return null
    }

    fun esExpresionAritmetica(): ExpresionAritmetica? {
        val posInicial = posActual
        val term1: Termino = esTermino()
        if (term1 != null) {
            // obtenerSgteToken();
            if (tokenActual.categoria === Categoria.OPERADOR_ARITMETICO) {
                val operAritm = tokenActual
                obtenerSiguienteToken()
                val term2: Termino = esTermino()
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
                            reportarError("Falta agrupador derecho en EA", tokenActual.fila, tokenActual.columna)
                        }
                    } else {
                        reportarError("falta expresion aritmetica en EA", tokenActual.fila, tokenActual.columna)
                    }
                } else {
                    reportarError("Falta agrupador izquierdo en EA", tokenActual.fila, tokenActual.columna)
                }
            } else {
                if (tokenActual.categoria === Categoria.OPERADOR_RELACIONAL || tokenActual.categoria === Categoria.CONCATENACION) {
                    hacerBactracking(posInicial)
                }
                //obtenerSgteToken();
                return ExpresionAritmetica(term1)
            }
        }
        return null
    }

    fun esTermino(): Termino? {
        val termino: Token
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



    private fun esTipoRetorno(): Token {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {

            if (tokenActual.lexema == "ENTERO" || tokenActual.lexema == "CADENA"
                || tokenActual.lexema == "REAL" || tokenActual.lexema == "NORETORNO"
                || tokenActual.lexema == "BOOLEANO"
            ) {
                return tokenActual;
            }

        }
        return null!!
    }


    private fun esListaImportaciones(): ArrayList<Importacion> {
        var importaciones : ArrayList<Importacion> = ArrayList()
        var f : Importacion = esImportacion()
        while(f != null){
            importaciones.add(f)
            f = esImportacion()
        }
        return importaciones
    }

    private fun esImportacion(): Importacion {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "IMPORTAR"){
            var palabra_reservada_importar : Token = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.CADENA_CARACTERES){
                var nombrePaquete : String = tokenActual.lexema
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.PUNTO){
                    obtenerSiguienteToken()
                    if(tokenActual.categoria == Categoria.IDENTIFICADOR_CLASE){
                        var identificador_clase : Token = tokenActual
                        obtenerSiguienteToken()
                        if(tokenActual.categoria == Categoria.IDENTIFICADOR_TERMINAL){
                            obtenerSiguienteToken()
                            return Importacion(palabra_reservada_importar, nombrePaquete, identificador_clase)
                        }else {
                            reportarError("Falta el terminal % en importacion", tokenActual.fila, tokenActual.columna)
                        }
                    }else {
                        reportarError(
                            "Falta el identificador de clase en importacion",
                            tokenActual.fila,
                            tokenActual.columna
                        )
                    }
                }else {
                    reportarError("Falta el punto en importacion", tokenActual.fila, tokenActual.columna)
                }
            }else{
                reportarError("Falta el nombre del paquete en importacion", tokenActual.fila, tokenActual.columna)
            }
        }
        return null!!
    }

    private fun esPaquete(): Paquete {

        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "PAQUETE"){
            var paquete : Token = tokenActual
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.CADENA_CARACTERES){
                var nombrePaquete : String = tokenActual.lexema
                obtenerSiguienteToken()
                if(tokenActual.categoria == Categoria.IDENTIFICADOR_TERMINAL){
                    obtenerSiguienteToken()
                    return Paquete(paquete, nombrePaquete)
                }else {
                    reportarError("Falta el terminal % en paquete", tokenActual.fila, tokenActual.columna)
                }
            }else {
                reportarError("Falta nombre del paquete", tokenActual.fila, tokenActual.columna)
            }
        }
        return null!!
    }

    fun esAsignacion(): Sentencia {
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
                        if (tokenActual.categoria === Categoria.IDENTIFICADOR_TERMINAL) {
                            obtenerSiguienteToken()
                            return Asignacion(tipoDato, identificadorVariable, operador, expresion)
                        } else {
                            reportarError("Falta el terminal en asignacion ", tokenActual.fila, tokenActual.columna)
                        }
                    } else {
                        reportarError("Falta la expresion en asignacion", tokenActual.fila, tokenActual.columna)
                    }
                }
                reportarError("Falta el operador de asignacion ", tokenActual.fila, tokenActual.columna)
            }
            reportarError("Falta el identificafor de variable en asignacion ", tokenActual.fila, tokenActual.columna)
        }
        return null
    }
    fun esInvocacionMetodo(): Sentencia {
        if (tokenActual.categoria === Categoria.IDENTIFICADOR_CLASE) {
            val identificadorClase = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.PUNTO) {
                obtenerSiguienteToken()
                if (tokenActual.categoria === Categoria.IDENTIFICADOR_METODO) {
                    val identificadorMetodo = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria === Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria === Categoria.IDENTIFICADOR_TERMINAL) {
                                obtenerSiguienteToken()
                                return InvocacionMetodo(identificadorClase, identificadorMetodo)
                            } else {
                                reportarError(
                                    "Falta terminal en la invocacion", tokenActual.fila,
                                    tokenActual.columna
                                )
                            }
                        } else {
                            reportarError(
                                "Falta el simbolo de cierre -> en la invocacion", tokenActual.fila,
                                tokenActual.columna
                            )
                        }
                    } else {
                        reportarError(
                            "Falta simbolo de apertura <- en la invocacion", tokenActual.fila,
                            tokenActual.columna
                        )
                    }
                } else {
                    reportarError(
                        "Falta el identificador del metodo", tokenActual.fila,
                        tokenActual.columna
                    )
                }
            } else {
                reportarError(
                    "Falta el punto", tokenActual.fila,
                    tokenActual.columna
                )
            }
        }
        return null
    }
    fun esImpresion(): Sentencia {
        val palabraReservada = tokenActual
        if (palabraReservada.categoria === Categoria.PALABRA_RESERVADA
            && palabraReservada.lexema == "IMPRIMIR"
        ) {
            obtenerSiguienteToken()
            val parIzq = tokenActual
            if (parIzq.categoria === Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                obtenerSiguienteToken()
                val exp = esExpresion()
                if (exp != null) {
                    val parDer = tokenActual
                    if (parDer.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                        obtenerSiguienteToken()
                        val finSentencia = tokenActual
                        if (finSentencia.categoria === Categoria.IDENTIFICADOR_TERMINAL) {
                            obtenerSiguienteToken()
                            return Impresion(palabraReservada, exp)
                        } else {
                            reportarError("Falta el terminal", tokenActual.fila, tokenActual.columna)
                        }
                    } else {
                        reportarError(
                            "Falta parentesis derecho en la impresion", tokenActual.fila,
                            tokenActual.columna
                        )
                    }
                } else {
                    reportarError("Falta expresion en la impresion", tokenActual.fila, tokenActual.columna)
                }
            } else {
                reportarError(
                    "Falta parentesis izquierdo en la impresion", tokenActual.fila,
                    tokenActual.columna
                )
            }
        }
        return null
    }
    fun esSentenciaRetorno(): Sentencia {
        if (tokenActual.categoria === Categoria.PALABRA_RESERVADA && tokenActual.lexema == "RETORNO") {
            val retorno = tokenActual
            obtenerSiguienteToken()
            val termino = esTermino()
            if (termino != null) {
                if (tokenActual.categoria === Categoria.IDENTIFICADOR_TERMINAL) {
                    obtenerSiguienteToken()
                    return Retorno(retorno, termino)
                } else {
                    reportarError("Falta terminal en retorno", tokenActual.fila, tokenActual.columna)
                }
            } else {
                reportarError("Falta termino en retorno", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }
    fun esSentenciaMientras(): Sentencia {
        if (tokenActual.categoria === Categoria.PALABRA_RESERVADA && tokenActual.lexema == "MIENTRAS") {
            val mientras = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".(") {
                obtenerSiguienteToken()
                val expresionLogica = esExpresionLogica()
                if (expresionLogica != null) {
                    //obtenerSgteToken();
                    if (tokenActual.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".)") {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria === Categoria.AGRUPADORES_APERTURA && tokenActual.lexema == ".{") {
                            obtenerSiguienteToken()
                            val listaSentencias = esListaSentencias()
                            if (listaSentencias != null) {
                                //obtenerSgteToken();
                                if (tokenActual.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                    obtenerSiguienteToken()
                                    return Mientras(mientras, expresionLogica, listaSentencias)
                                } else {
                                    reportarError(
                                        "Falta terminal en SM", tokenActual.fila,
                                        tokenActual.columna
                                    )
                                }
                            } else if (tokenActual.categoria === Categoria.AGRUPADORES_CIERRE && tokenActual.lexema == ".}") {
                                obtenerSiguienteToken()
                                return Mientras(mientras, expresionLogica)
                            }
                        } else {
                            reportarError(
                                "Falta llave izquierda  en SM", tokenActual.fila,
                                tokenActual.columna
                            )
                        }
                    } else {
                        reportarError(
                            "Falta simbolo de cierre -> en SM", tokenActual.fila,
                            tokenActual.columna
                        )
                    }
                } else {
                    reportarError("Falta la expresion logica en SM", tokenActual.fila, tokenActual.columna)
                }
            } else {
                reportarError("Falta el simbolo de apertura <- en SM", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }

    /**
     * <ExpresionLogica>::=<ExpresionRelacional><OperadorLogico><ExpresionRelacional>
     *
     * @return
    </ExpresionRelacional></OperadorLogico></ExpresionRelacional></ExpresionLogica> */
    fun esExpresionLogica(): ExpresionLogica? {
        val expresion1 = esExpresionRelacional()
        if (expresion1 != null) {
            // obtenerSgteToken();
            if (tokenActual.categoria === Categoria.OPERADOR_LOGICO) {
                val operador = tokenActual
                obtenerSiguienteToken()
                val expresion2 = esExpresionRelacional()
                if (expresion2 != null) {
                    // obtenerSgteToken();
                    return ExpresionLogica(expresion1, operador, expresion2)
                } else {
                    reportarError("Falta una expresion en EL ", tokenActual.fila, tokenActual.columna)
                }
            } else {
                reportarError("Falta el operador logico en EL", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }

    /*
	 * * <SentenciaIncremento>::=<IdentificadorVariable><Incremento>
	 *
	 */
    fun esIncremento(): SentenciaIncremento? {
        if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.INCREMENTO) {
                val incremento = tokenActual
                obtenerSiguienteToken()
                return SentenciaIncremento(identificador, incremento)
            } else {
                reportarError("Falta el operador de incremento  ", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }

    /*
	 * <SentenciaDecremento>::=<IdentificadorVariable><Decremento>
	 *
	 */
    fun esDecremento(): SentenciaDecremento? {
        if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.DECREMENTO) {
                val decremento = tokenActual
                obtenerSiguienteToken()
                return SentenciaDecremento(identificador, decremento)
            } else {
                reportarError("Falta el operador de decremento  ", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }

    /**
     * <Modificador> ::= PRIVADO | PUBLICO
    </Modificador> */
    fun esModificador(): Token? {
        if (tokenActual.categoria === Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "PRIVADO" || tokenActual.lexema == "PUBLICO") {
                return tokenActual
            }
        }
        return null
    }

    /**
     * <TipoDato> ::= ENTERO | CADENA | REAL|BOOLEANO
    </TipoDato> */
    fun esTipoDato(): Token {
        if (tokenActual.categoria === Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "ENTERO" || tokenActual.lexema == "CADENA" || tokenActual.lexema == "REAL" || tokenActual.lexema == "BOOLEANO") {
                return tokenActual
            }
        }
        return null
    }

    /*
	 * <ListaParametros>::=<Parametro>|[<ListaParametros>]
	 */
    fun esListaParametros(): ArrayList<Parametro> {
        val parametros = ArrayList<Parametro>()
        var p = esParametro()
        while (p != null) {
            parametros.add(p)
            p = esParametro()
        }
        return parametros
    }

    /**
     * <leer> ::= LEER idVariable "%"
     *
     * @return leer
    </leer> */
    fun esLeer(): Leer? {
        val palabraReservada = tokenActual
        if (palabraReservada.categoria === Categoria.PALABRA_RESERVADA
            && palabraReservada.lexema == "LEER"
        ) {
            obtenerSiguienteToken()
            if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
                val id = tokenActual
                obtenerSiguienteToken()
                val terminal = tokenActual
                if (terminal.categoria === Categoria.IDENTIFICADOR_TERMINAL) {
                    obtenerSiguienteToken()
                    return Leer(id)
                } else {
                    reportarError("Falta el terminal en el leer", tokenActual.fila, tokenActual.columna)
                }
            } else {
                reportarError("Falta el idVariable en el leer", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }

    /**
     * <Parametro>::= <IdentificadorVariable><TipoDato>
     *
     * @return Parametro
    </TipoDato></IdentificadorVariable></Parametro> */
    fun esParametro(): Parametro? {
        var tipoDato: Token? = null
        if (tokenActual.categoria === Categoria.IDENTIFICADOR_VARIABLE) {
            val identificadorVariable = tokenActual
            obtenerSiguienteToken()
            tipoDato = esTipoDato()
            if (tipoDato != null) {
                obtenerSiguienteToken()
                return Parametro(identificadorVariable, tipoDato)
            } else {
                reportarError("Falta tipo de dato en parámetro", tokenActual.fila, tokenActual.columna)
            }
        }
        return null
    }

}


