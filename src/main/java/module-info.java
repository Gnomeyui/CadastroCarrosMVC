module CadastroCarrosMVC {

    // 1. Requer as bibliotecas do JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // 2. Requer o AWT/Desktop para abrir arquivos
    requires java.desktop;

    // 3. Abre seu pacote principal
    opens com.mangareader.cadastrocarrosmvc to javafx.graphics, javafx.fxml;

    // 4. Abre o pacote controller
    opens com.mangareader.cadastrocarrosmvc.controller to javafx.fxml;

    // 5.
    //    Abre o pacote 'model' para o JavaFX 'base' (para a Tabela funcionar)
    opens com.mangareader.cadastrocarrosmvc.model to javafx.base;
}