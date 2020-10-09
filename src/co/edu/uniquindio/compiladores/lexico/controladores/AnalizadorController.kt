package co.edu.uniquindio.compiladores.lexico.controladores

import co.edu.uniquindio.compiladores.lexico.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.lexico.Token
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
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
        var aux = ""
        var texto = ""
        try {
            val file = JFileChooser()
            file.dialogTitle = "Abrir Archivo"
            val filter = FileNameExtensionFilter(".MG", "mg")
            file.fileFilter = filter
            file.showOpenDialog(null)
            fichero = file.selectedFile
            if (fichero != null) {
                val archivos = FileReader(fichero)
                val buff = BufferedReader(archivos)
                while (!buff.readLine().equals(null)) {
                    texto += buff.readLine()
                }
                buff.close()
            }

        } catch (ex: IOException) {
            JOptionPane.showMessageDialog(
                null, "$ex\nNo se ha encontrado el archivo", "Error",
                JOptionPane.ERROR_MESSAGE
            )
        } catch (e2: NullPointerException) {
            JOptionPane.showMessageDialog(null, "Se ha cancelado el cargado")
        }
    }

    fun guardarArchivo(actionEvent: ActionEvent) {
        val archivos: FileWriter
        if (guardado) {
            try {
                archivos = FileWriter(fichero)
                val buff = BufferedWriter(archivos)
                buff.write(txtCodigo.text)
                buff.close()
                JOptionPane.showMessageDialog(null, "Se ha guardado correctamente")
            } catch (e1: IOException) {
                JOptionPane.showMessageDialog(null, "No se ha guardado")
            } catch (e2: NullPointerException) {
                JOptionPane.showMessageDialog(null, "Se ha cancelado el guardado")
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
                JOptionPane.showMessageDialog(null, "Se ha guardado correctamente")
                fichero = fileToSave!!.absoluteFile
                guardado = true
            } catch (e1: IOException) {
                JOptionPane.showMessageDialog(null, "No se ha guardao")
            } catch (e2: NullPointerException) {
                JOptionPane.showMessageDialog(null, "Se ha cancelado el guardado")
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