package minerva;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alchemistake on 29/03/15.
 */
public class AudioController implements Initializable {
    @FXML
    Button rec;
    @FXML
    Button play;
    @FXML
    Button stop;
    @FXML
    Button choose;
    @FXML
    Button ok;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        rec.setDisable(false);
        play.setDisable(true);
        stop.setDisable(true);
        choose.setDisable(false);
        ok.setDisable(false);

        rec.setOnAction(event -> {
            rec.setDisable(true);
            play.setDisable(true);
            stop.setDisable(false);
            choose.setDisable(true);
            ok.setDisable(true);
        });

        play.setOnAction(event ->{
            rec.setDisable(true);
            play.setDisable(true);
            stop.setDisable(false);
            choose.setDisable(true);
            ok.setDisable(true);
        });

        stop.setOnAction(event ->{
            rec.setDisable(false);
            play.setDisable(false);
            stop.setDisable(true);
            choose.setDisable(false);
            ok.setDisable(false);
        });

        choose.setOnAction(event ->{
            rec.setDisable(true);
            play.setDisable(true);
            stop.setDisable(true);
            choose.setDisable(true);
            ok.setDisable(true);
            FileChooser fileChooser = new FileChooser();
            fileChooser.showOpenDialog(((Node)event.getTarget()).getScene().getWindow());
            // file extension selector should be added!
            rec.setDisable(false);
            play.setDisable(false);
            stop.setDisable(true);
            choose.setDisable(false);
            ok.setDisable(false);
        });

        ok.setOnAction(event ->{});
    }
}
