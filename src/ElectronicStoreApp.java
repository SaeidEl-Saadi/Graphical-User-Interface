import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.event.*;

public class ElectronicStoreApp extends Application {

    private ElectronicStore model;

    public ElectronicStoreApp() {
        model = ElectronicStore.createStore();
    }

    public void start(Stage primaryStage) {
        Pane aPane = new Pane();

        ElectronicStoreView view = new ElectronicStoreView();
        view.update(model);
        aPane.getChildren().add(view);

        view.getStock().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model);
            }
        });

        view.getCart().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update(model);
            }
        });

        view.getAdd().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                view.getStock().getSelectionModel().getSelectedItem().buying();
                model.addToCart();
                view.update(model);
            }
        });

        view.getRemove().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.getCartIndex(view.getCart().getSelectionModel().getSelectedIndex()).remove();
                model.removeFromCart(model.getCartIndex(view.getCart().getSelectionModel().getSelectedIndex()));
                view.update(model);
            }
        });

        view.getCompleteSale().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model.computeSales();
                view.update(model);
            }
        });

        view.getReset().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                model = ElectronicStore.createStore();
                view.update(model);
            }
        });



        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
