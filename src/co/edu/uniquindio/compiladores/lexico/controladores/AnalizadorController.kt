package co.edu.uniquindio.compiladores.lexico.controladores

import co.edu.uniquindio.compiladores.lexico.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.lexico.Token
import co.edu.uniquindio.compiladores.lexico.sintaxis.ErrorSintactico
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.io.*
import java.net.URL
import java.util.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter


class AnalizadorController: Initializable {

    @FXML lateinit var txtCodigo : TextArea
    @FXML lateinit var tablaSimbolos : TableView<Token>
    @FXML lateinit var columLexema : TableColumn<Token, String>
    @FXML lateinit var columFila : TableColumn<Token, Int>
    @FXML lateinit var columColumna : TableColumn<Token, Int>
    @FXML lateinit var columCategoria : TableColumn<Token, String>
    @FXML lateinit var tableErrores : TableView<Token>
    @FXML lateinit var columLexemaError : TableColumn<Token, String>
    @FXML lateinit var columFilaError : TableColumn<Token, String>
    @FXML lateinit var columColumnaError : TableColumn<Token, String>
    @FXML lateinit var columCategoriaError : TableColumn<Token, String>
    @FXML lateinit var tabCompilador : Tab
    @FXML lateinit var tabSimbolos : Tab
    @FXML lateinit var tabErrores : Tab
    @FXML lateinit var tapPane : TabPane

    @FXML lateinit var tablaErrorSintactico : TableView<ErrorSintactico>
    @FXML lateinit var ColumMensaje : TableColumn<ErrorSintactico,String>
    @FXML lateinit var columFilaSintaxis : TableColumn<ErrorSintactico,String>
    @FXML lateinit var columColunmnaSintaxis : TableColumn<ErrorSintactico,String>

    @FXML lateinit var TreeVisual : TreeView<String>

    lateinit var fichero : File
    var guardado : Boolean = false


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        //Tabla de simbolos
        columLexema.cellValueFactory = PropertyValueFactory("lexema")
        columFila.cellValueFactory = PropertyValueFactory("fila")
        columColumna.cellValueFactory = PropertyValueFactory("columna")
        columCategoria.cellValueFactory = PropertyValueFactory("categoria")
        //Tabla de errores
        columLexemaError.cellValueFactory = PropertyValueFactory("lexema")
        columFilaError.cellValueFactory = PropertyValueFactory("fila")
        columColumnaError.cellValueFactory = PropertyValueFactory("columna")
        columCategoriaError.cellValueFactory = PropertyValueFactory("categoria")
    }



    fun abrirArchivo(actionEvent: ActionEvent) {
        val fileChooser = FileChooser()

        val extFilter = FileChooser.ExtensionFilter("Archivos (*.mg)", "*.mg")
        fileChooser.extensionFilters.add(extFilter)

        val file = fileChooser.showOpenDialog(Stage())
        if (file != null) {
            txtCodigo.text = readFile(file) //obtener el texto
        }
    }
    private fun readFile(file: File): String? {
        val stringBuffer = StringBuilder()
        var bufferedReader: BufferedReader? = null
        try {
            bufferedReader = BufferedReader(FileReader(file))
            var text: String?
            while (bufferedReader.readLine().also { text = it } != null) {
                stringBuffer.append(text+"\n")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            try {
                bufferedReader!!.close()
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
        return stringBuffer.toString()
    }

    fun guardarArchivo(actionEvent: ActionEvent) {
        val archivos: FileWriter
        if (guardado) {
            try {
                archivos = FileWriter(fichero)
                val buff = BufferedWriter(archivos)
                buff.write(txtCodigo.text)
                buff.close()
                val alert = Alert(AlertType.INFORMATION)
                alert.title = "Información"
                alert.headerText = "Información"
                alert.contentText = "Se ha guardado correctamente"
                alert.show()
            } catch (e1: IOException) {
                val alert = Alert(AlertType.INFORMATION)
                alert.title = "Información"
                alert.headerText = "Información"
                alert.contentText = "No se ha guardado"
                alert.show()
            } catch (e2: NullPointerException) {
                val alert = Alert(AlertType.INFORMATION)
                alert.title = "Información"
                alert.headerText = "Información"
                alert.contentText = "Se ha cancelado el guardado"
                alert.show()
            }
        } else {
            try {
                val fileChooser = JFileChooser()
                fileChooser.dialogTitle = "Elija donde guardar"
                val filter = FileNameExtensionFilter(".MG", "mg")
                fileChooser.fileFilter = filter
                val userSelection = fileChooser.showSaveDialog(null)
                var fileToSave: File? = null
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    fileToSave = fileChooser.selectedFile
                }
                archivos = FileWriter(fileToSave!!.absolutePath + ".MG")
                val buff = BufferedWriter(archivos)
                buff.write(txtCodigo.text)
                buff.close()
                val alert = Alert(AlertType.INFORMATION)
                alert.title = "Información"
                alert.headerText = "Información"
                alert.contentText = "Se ha guardado correctamente"
                alert.show()
                fichero = fileToSave!!.absoluteFile
                guardado = true
            } catch (e1: IOException) {
                val alert = Alert(AlertType.INFORMATION)
                alert.title = "Información"
                alert.headerText = "Información"
                alert.contentText = "No se ha gusradado"
                alert.show()
            } catch (e2: NullPointerException) {
                val alert = Alert(AlertType.INFORMATION)
                alert.title = "Información"
                alert.headerText = "Información"
                alert.contentText = "Se ha cancelado el guardado"
                alert.show()
            }

        }

    }

    fun nuevoArchivo(actionEvent: ActionEvent) {
        txtCodigo.text = ""
        txtCodigo.requestFocus()
        tableErrores.setItems(FXCollections.observableArrayList());
        tablaSimbolos.setItems(FXCollections.observableArrayList());
    }
    @FXML
    fun compilar(actionEvent: ActionEvent) {
        if(txtCodigo.text.length > 0){
            val lexicoA = AnalizadorLexico(txtCodigo.text)
            lexicoA.analizar()
            print(lexicoA.listaErrores)
            print(lexicoA.listaSimbolos)
            tablaSimbolos.items =FXCollections.observableArrayList(lexicoA.listaSimbolos)
            tableErrores.items = FXCollections.observableArrayList(lexicoA.listaErrores)

        }
    }


}