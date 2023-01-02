import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import java.util.ArrayList; //FOR DISPLAYING PURPOSES (makes it easier)

public class ElectronicStoreView extends Pane{

    private TextField            salesField, revField, perSalesField;
    private ListView<Product>    stock, popular;
    private ListView<String>     cart;
    private Button               reset, add, remove, completeSale;
    private Label                label7;

    public ListView<Product> getStock() { return stock; }
    public ListView<String> getCart() { return cart; }
    public Button getAdd() { return add; }
    public Button getRemove() { return remove; }
    public Button getCompleteSale() { return completeSale; }
    public Button getReset() { return reset; }



    public ElectronicStoreView() {

        // Adding labels
        Label label1 = new Label("Store Summary:");
        label1.relocate(40,20);

        Label label2 = new Label("# Sales:");
        label2.relocate(30, 50);

        Label label3 = new Label("Revenue:");
        label3.relocate(22, 80);

        Label label4 = new Label("$ / Sale:");
        label4.relocate(28, 110);

        Label label5 = new Label("Most Popular Items:");
        label5.relocate(30, 140);

        Label label6 = new Label("Store Stock:");
        label6.relocate(280, 20);

        label7 = new Label("Current Cart ($0.00):");
        label7.relocate(570, 20);

        // Adding text fields
        salesField = new TextField();
        salesField.relocate(75, 45);
        salesField.setPrefSize(80, 20);
        salesField.setEditable(false);
        salesField.setText("0");

        revField = new TextField();
        revField.relocate(75, 75);
        revField.setPrefSize(80, 20);
        revField.setEditable(false);
        revField.setText("0.00");

        perSalesField = new TextField();
        perSalesField.relocate(75, 105);
        perSalesField.setPrefSize(80, 20);
        perSalesField.setEditable(false);
        perSalesField.setText("N/A");

        // Adding ListViews
        stock = new ListView<Product>();
        stock.relocate(165, 45);
        stock.setPrefSize(300, 275);

        cart = new ListView<String>();
        cart.relocate(475, 45);
        cart.setPrefSize(300, 275);

        popular = new ListView<Product>();
        popular.relocate(10, 170);
        popular.setPrefSize(145, 150);

        // Adding Buttons
        reset = new Button("Reset Store");
        reset.relocate(20, 325);
        reset.setPrefSize(125,50);

        add = new Button("Add to Cart");
        add.relocate(250, 325);
        add.setPrefSize(125, 50);
        add.setDisable(true);

        remove = new Button("Remove from Cart");
        remove.relocate(475, 325);
        remove.setPrefSize(150, 50);
        remove.setDisable(true);

        completeSale = new Button("Complete Sale");
        completeSale.relocate(625, 325);
        completeSale.setPrefSize(150, 50);
        completeSale.setDisable(true);


        getChildren().addAll(label1, label2, label3, label4, label5, label6, label7,
                salesField, revField, perSalesField, stock, cart, popular,
                reset, add, remove, completeSale);
        setPrefSize(800, 400);
    }

    public void update(ElectronicStore model) {
        ArrayList<Product> curStocktemp = new ArrayList<Product>();
        ArrayList<String> curCartTemp = new ArrayList<String>();

        for (Product p: model.getStock()) {
            if (p != null) {
                if (p.getStockQuantity() > 0 && p.getInCart() != p.getStockQuantity()) {
                    curStocktemp.add(p);
                }
                if (p.getInCart() == p.getStockQuantity()) {
                    add.setDisable(true);
                }
            }
        }

        Product[] curStock = new Product[curStocktemp.size()];

        int count = 0;
        for (Product p: curStocktemp) {
            curStock[count] = p;
            count++;
        }

        for (Product p : model.getStock()) {
            if (p != null) {
                if (p.getInCart() > 0) {
                    curCartTemp.add(p.getInCart() + " x " + p);
                }
                if (p.getInCart() == 0) {
                    remove.setDisable(true);
                }
            }
        }

        String[] curCart = new String[curCartTemp.size()];

        count = 0;
        for (String s: curCartTemp) {
            curCart[count] = s;
            count++;
        }


        stock.setItems(FXCollections.observableArrayList(curStock));
        popular.setItems(FXCollections.observableArrayList(model.computePopular()));
        cart.setItems(FXCollections.observableArrayList(curCart));


        label7.setText(String.format("Current Cart ($%,.2f):", model.computeTotal()));
        salesField.setText(String.format("%d", model.getSales()));
        revField.setText(String.format("%,.2f", model.getRevenue()));
        perSalesField.setText(String.format("%,.2f", model.getAvg()));


        completeSale.setDisable(curCartTemp.size() <= 0);
        add.setDisable(stock.getSelectionModel().getSelectedIndex() < 0);
        remove.setDisable(cart.getSelectionModel().getSelectedIndex() < 0);



    }
}
