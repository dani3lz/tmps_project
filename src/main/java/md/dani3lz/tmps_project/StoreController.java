package md.dani3lz.tmps_project;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import md.dani3lz.tmps_project.Assets.*;
import md.dani3lz.tmps_project.Assets.Options.Option;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable {
    @FXML
    private Button allBtn;
    @FXML
    private Button cpuBtn;
    @FXML
    private Button gpuBtn;
    @FXML
    private Button headphoneBtn;
    @FXML
    private Button keyboardBtn;
    @FXML
    private Button mouseBtn;
    @FXML
    private Button ramBtn;
    @FXML
    private Button speakerBtn;
    // ------------------------------------------------------------
    @FXML
    private ImageView componentImg;
    @FXML
    private Label componentName;
    @FXML
    private Label componentPrice;
    @FXML
    private GridPane grid;
    @FXML
    private VBox pickComponentCard;
    @FXML
    private TextField inputSearch;
    @FXML
    private Label searchLabel;
    @FXML
    private ProgressBar progressBar;


    private List<Component> components = new ArrayList<>();
    private MyListener myListener;
    private Option option_selected = null;


    public void btnALL(){
        initMethod(Option.ALL);
    }

    public void btnGPU(){
        initMethod(Option.GPU);
    }

    public void btnCPU(){
        initMethod(Option.CPU);
    }

    public void btnRAM(){
        initMethod(Option.RAM);
    }

    public void btnMouse(){ initMethod(Option.MOUSE);}

    public void btnKeyboard(){
        initMethod(Option.KEYBOARD);
    }

    public void btnHeadphone(){
        initMethod(Option.HEADPHONE);
    }

    public void btnSpeaker(){
        initMethod(Option.SPEAKER);
    }

    public void btnSearch() {
        String textInput = inputSearch.getText().toLowerCase();
        inputSearch.clear();
        if(!textInput.equals("")) {
            searchLabel.setVisible(true);
            searchLabel.setText("Search: \"" + textInput + "\"");
            Search.searchComponents(textInput);
            initMethod(Option.SEARCH);
        }
    }

    private void initMethod(Option option){
        if(option != Option.SEARCH){
            searchLabel.setVisible(false);
        }
        if(option_selected != option || option == Option.SEARCH) {
            option_selected = option;
            setButton(option);
            grid.getChildren().clear();
            List<Component> components;
            components = InitComponents.getInstance().getData(option);

            if (components.size() > 0) {
                setCard(components.get(0));
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(Component component) {
                        setCard(component);
                    }
                };
            }

            int column = 0;
            int row = 1;

            try {
                for (Component component : components) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("component.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ComponentController componentController = fxmlLoader.getController();
                    componentController.setData(component, myListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row);

                    GridPane.setMargin(anchorPane, new Insets(10));
                }

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_PREF_SIZE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCard(Component component){
        componentName.setText(component.getName());
        componentPrice.setText(component.getPrice() + " MDL");
        Image image = new Image(getClass().getResourceAsStream(component.getImgSrc()));
        componentImg.setImage(image);
        pickComponentCard.setStyle( "-fx-background-color: #"+component.getColor()+";\n" +
                "    -fx-background-radius: 30;");
    }

   private void setButton(Option option) {
       allBtn.getStyleClass().remove("btn-selected");
       cpuBtn.getStyleClass().remove("btn-selected");
       gpuBtn.getStyleClass().remove("btn-selected");
       ramBtn.getStyleClass().remove("btn-selected");
       keyboardBtn.getStyleClass().remove("btn-selected");
       mouseBtn.getStyleClass().remove("btn-selected");
       headphoneBtn.getStyleClass().remove("btn-selected");
       speakerBtn.getStyleClass().remove("btn-selected");

       switch (option) {
           case ALL -> allBtn.getStyleClass().add("btn-selected");
           case MOUSE -> mouseBtn.getStyleClass().add("btn-selected");
           case KEYBOARD -> keyboardBtn.getStyleClass().add("btn-selected");
           case HEADPHONE -> headphoneBtn.getStyleClass().add("btn-selected");
           case CPU -> cpuBtn.getStyleClass().add("btn-selected");
           case GPU -> gpuBtn.getStyleClass().add("btn-selected");
           case SPEAKER -> speakerBtn.getStyleClass().add("btn-selected");
           case RAM -> ramBtn.getStyleClass().add("btn-selected");
       }
   }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressBar.setVisible(false);
        initMethod(Option.ALL);
    }
}
