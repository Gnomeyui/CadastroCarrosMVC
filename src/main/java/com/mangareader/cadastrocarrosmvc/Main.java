// Dentro de /com/mangareader/cadastrocarrosmvc/Main.java

// 1. PACOTE CORRIGIDO
package com.mangareader.cadastrocarrosmvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 2. CAMINHO DO FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mangareader/cadastrocarrosmvc/view/CadastroCarroView.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Cadastro de Carros - MVC");
            primaryStage.setScene(new Scene(root, 900, 500));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}