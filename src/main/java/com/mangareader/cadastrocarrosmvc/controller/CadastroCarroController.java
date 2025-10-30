package com.mangareader.cadastrocarrosmvc.controller;

import com.mangareader.cadastrocarrosmvc.model.Carro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory; // Importante

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CadastroCarroController {

    private static final String LOG_FILE_PATH = "log_carros.txt";

    // --- Componentes do Formulário ---
    @FXML private TextField textFieldModelo;
    @FXML private TextField textFieldAno;
    @FXML private TextField textFieldCor;
    @FXML private Button btnSalvar;
    @FXML private Button btnAbrirLog;
    @FXML private Label labelStatus;
    @FXML private Label labelErro;

    // --- Componentes da Área de Log ---
    @FXML private TextArea textAreaLog;

    // ---  Componentes da Tabela ---
    @FXML private TableView<Carro> tabelaCarros;
    @FXML private TableColumn<Carro, String> colunaModelo;
    @FXML private TableColumn<Carro, Integer> colunaAno;
    @FXML private TableColumn<Carro, String> colunaCor;

    // Lista "observável" que a tabela usará para se atualizar
    private ObservableList<Carro> listaCarros = FXCollections.observableArrayList();

    /**
     * Este método é chamado automaticamente pelo JavaFX
     * depois que a interface (FXML) é carregada.
     */
    @FXML
    public void initialize() {
        // 1. Configura as colunas da tabela para saberem de onde puxar os dados
        // O texto "modelo" DEVE ser igual ao nome da propriedade no Model (getModelo -> "modelo")
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colunaCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        // 2. Liga a Tabela com a nossa lista de carros
        tabelaCarros.setItems(listaCarros);
    }

    /**
     * Chamado quando o botão 'Salvar' é clicado.
     */
    @FXML
    private void handleSalvarCarro(ActionEvent event) {
        limparFeedback();
        try {
            // 1. Coleta dados da View
            String modelo = textFieldModelo.getText();
            int ano = Integer.parseInt(textFieldAno.getText());
            String cor = textFieldCor.getText();

            // 2. Comunica-se com o Model
            Carro novoCarro = new Carro(modelo, ano, cor);

            // 3. ATUALIZA A TABELA
            listaCarros.add(novoCarro);

            // 4. Atualiza o Log e o Status
            String logMessage = "CADASTRADO: " + novoCarro.toString();
            atualizarLogVisual(logMessage);
            escreverEmArquivoLog(logMessage);

            // 5. Exibe a mensagem de sucesso que você pediu
            labelStatus.setText("Cadastro realizado com sucesso!");
            limparCampos();

        } catch (NumberFormatException e) {
            String msgErro = "Erro: O ano deve ser um número válido.";
            labelErro.setText(msgErro);
            atualizarLogVisual(msgErro);
        } catch (IllegalArgumentException e) {
            String msgErro = "Erro: " + e.getMessage();
            labelErro.setText(msgErro);
            atualizarLogVisual(msgErro);
        } catch (Exception e) {
            String msgErro = "Ocorreu um erro inesperado.";
            labelErro.setText(msgErro);
            atualizarLogVisual(msgErro + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chamado quando o botão 'Abrir Pasta do Log' é clicado.
     */
    @FXML
    private void handleAbrirLog(ActionEvent event) {
        limparFeedback();
        try {
            File logFile = new File(LOG_FILE_PATH);
            if (!logFile.exists()) {
                // Cria um arquivo vazio se não existir
                logFile.createNewFile();
            }
            // Abre o arquivo de log com o programa padrão
            Desktop.getDesktop().open(logFile);
            atualizarLogVisual("Arquivo de log aberto.");

        } catch (IOException e) {
            labelErro.setText("Não foi possível abrir o arquivo de log.");
            atualizarLogVisual("ERRO: Não foi possível abrir o arquivo de log.");
            e.printStackTrace();
        }
    }

    // --- Métodos Auxiliares ---

    private void atualizarLogVisual(String mensagem) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        textAreaLog.appendText("[" + timestamp + "] " + mensagem + "\n");
    }

    private void escreverEmArquivoLog(String mensagem) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String logFormatado = "[" + timestamp + "] " + mensagem + "\n";
        try {
            Files.writeString(Paths.get(LOG_FILE_PATH),
                    logFormatado,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Falha ao escrever no arquivo de log: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        textFieldModelo.clear();
        textFieldAno.clear();
        textFieldCor.clear();
    }

    private void limparFeedback() {
        labelStatus.setText("");
        labelErro.setText("");
    }
}