package minerva;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import litera.Data.LocalDataManager;
import litera.Defaults.Defaults;

import javax.swing.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    // FXML StyleToolbar Declaration
    @FXML
    private ToolBar styleToolbar;
    @FXML
    private ToggleButton boldToggleButton;
    @FXML
    private ToggleButton italicToggleButton;
    @FXML
    private ToggleButton underlineToggleButton;
    @FXML
    private ToggleButton strikethroughToggleButton;
    @FXML
    private ToggleButton insertOrderedListToggleButton;
    @FXML
    private ToggleButton insertUnorderedListToggleButton;
    @FXML
    private Button audioAddButton;
    @FXML
    private Button videoAddButton;
    @FXML
    private Button imgAddButton;

    // FXML Rest of the Program Declaration
    @FXML
    private ListView noteListScrollPane;
    @FXML
    private WebView editor;
    @FXML
    private TextField noteNameTextField;
    @FXML
    private Button trashButton;
    @FXML
    private Button addNoteButton, deleteNoteButton;
    @FXML
    private Button optionsButton;
    @FXML
    private ColorPicker foregroundColorPicker;

    // Other variables' Declaration
    private static Note currentNote;
    private ObservableList<String> noteListScrollPaneItems;
    private static WebPage webPage;
    private boolean isChanged;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        // Style Buttons
        assert styleToolbar != null : "fx:id\"styleToolBar\" was not injected: check you FXML file 'minerva.fxml'.";
        assert boldToggleButton != null : "fx:id=\"bold\" was not injected: check your FXML file 'minerva.fxml'.";
        assert italicToggleButton != null : "fx:id=\"italic\" was not injected: check your FXML file 'minerva.fxml'.";
        assert underlineToggleButton != null : "fx:id=\"underline\" was not injected: check your FXML file 'minerva.fxml'.";
        assert strikethroughToggleButton != null : "fx:id=\"strikethrough\" was not injected: check your FXML file 'minerva.fxml'.";
        assert insertOrderedListToggleButton != null : "fx:id=\"insertOrderedList\" was not injected: check your FXML file 'minerva.fxml'.";
        assert insertUnorderedListToggleButton != null : "fx:id=\"insertUnorderedList\" was not injected: check your FXML file 'minerva.fxml'.";

        // Editor and Pane
        assert noteListScrollPane != null : "fx:id=\"noteListScrollPane\" was not injected: check your FXML file 'minerva.fxml'.";
        //assert editor != null : "fx:id=\"noteListScrollPane\" was not injected: check your FXML file 'minerva.fxml'.";

        // Add delete pane
        assert noteNameTextField != null : "fx:id=\"noteListScrollPane\" was not injected: check your FXML file 'minerva.fxml'.";
        assert trashButton != null : "fx:id=\"noteListScrollPane\" was not injected: check your FXML file 'minerva.fxml'.";
        assert deleteNoteButton != null : "fx:id=\"noteListScrollPane\" was not injected: check your FXML file 'minerva.fxml'.";
        assert optionsButton != null : "fx:id=\"noteListScrollPane\" was not injected: check your FXML file 'minerva.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

        // initializing webPage
        webPage = Accessor.getPageFor(editor.getEngine());
        isChanged = false;

        /*** *** *** *** *** START OF Button Listeners *** *** *** *** ***/
        // Button Listeners for Style
        boldToggleButton.setOnAction(event -> addStyle(Defaults.BOLD_COMMAND));
        italicToggleButton.setOnAction(event -> addStyle(Defaults.ITALIC_COMMAND));
        underlineToggleButton.setOnAction(event -> addStyle(Defaults.UNDERLINE_COMMAND));
        strikethroughToggleButton.setOnAction(event -> addStyle(Defaults.STRIKETHROUGH_COMMAND));
        insertOrderedListToggleButton.setOnAction(event -> addStyle(Defaults.NUMBERS_COMMAND));
        insertUnorderedListToggleButton.setOnAction(event -> addStyle(Defaults.BULLETS_COMMAND));

        // Listeners for Style buttons
        editor.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> buttonFeedback());
        editor.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> buttonFeedback());
        editor.addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, event -> buttonFeedback());
        editor.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> buttonFeedback());

        // Button Listeners for Bottom Toolbar
        trashButton.setOnAction(event -> {
            System.out.println("trashButton clicked");
        });

        optionsButton.setOnAction(event -> {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("options.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Options");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch ( Exception ex )
            {

            }
        });

        audioAddButton.setOnAction( event ->{
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("audio.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Audio");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch ( Exception ex )
            {

            }
        });

        videoAddButton.setOnAction( event ->{
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("video.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Video");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch ( Exception ex )
            {

            }
        });

        imgAddButton.setOnAction( event ->{
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("video.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Picture");
                stage.setScene(new Scene(root));
                stage.show();
            }
            catch ( Exception ex )
            {

            }
        });

        foregroundColorPicker.setOnAction(ev1 -> {
            Color newValue = foregroundColorPicker.getValue();
            if (newValue != null) {
                addStyle(Defaults.FOREGROUND_COLOR_COMMAND, colorValueToHex(newValue));
                foregroundColorPicker.hide();
            }
        });

        addNoteButton.setOnAction(event -> {
            noteListScrollPaneItems.add(LocalDataManager.createNewNote());
            noteListScrollPane.getSelectionModel().select(noteListScrollPane.getItems().size() - 1);
        });

        deleteNoteButton.setOnAction(event -> {
            noteListScrollPaneItems.remove(Defaults.newNoteName);
            noteListScrollPane.getSelectionModel().select(noteListScrollPane.getItems().size() - 1);
        });

        // Listener for Note list selection
        noteListScrollPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if ( isChanged ) //there is no need for a save operation if you didn't change anything
                {
                    currentNote.setHtmlNote(getWebViewContent());
                    LocalDataManager.saveNote(currentNote);
                    isChanged = false; //checked via webPage listeners
                }
                /* Gets the selected note from the notes directory
                 * If LocalDataManager can't find the file, returns null. Either this is the first run or the user messed up with Litera directory.
                 * While evaluating this statement, the note gets loaded into the currentNote object.*/
                currentNote = LocalDataManager.getNote(newValue);
                editor.getEngine().loadContent(currentNote.getHtmlNote());
                noteNameTextField.setText(newValue);
            }
        });
        /*** *** *** *** *** END OF Button Listeners *** *** *** *** ***/

        // Start up of program
        populateNoteListbox();
        loadLastNote();
    }

    // Saving on exit
    public static void onExit()
    {
        currentNote.setHtmlNote(webPage.getHtml(webPage.getMainFrame()));
        LocalDataManager.saveNote(currentNote);
    }

    // Returns the string version of the page
    private String getWebViewContent()
    {
        return webPage.getHtml(webPage.getMainFrame()); //old method: (String)editor.getEngine().executeScript("document.documentElement.outerHTML");
    }

    // Style method for all things need styling
    private void addStyle(String command)
    {
        webPage.executeCommand(command, null);
        editor.requestFocus();
        buttonFeedback();
    }

    private void addStyle(String command, String color)
    {
        webPage.executeCommand(command, color);
        editor.requestFocus();
        buttonFeedback();
    }

    private boolean populateNoteListbox()
    {
        String[] noteList = LocalDataManager.getNoteNames();
        if (noteList == null)
        {
            currentNote = new Note(Defaults.welcomeList[0], Defaults.welcomePage);
            LocalDataManager.saveNote(currentNote);
            noteList = LocalDataManager.getNoteNames();
        }
        noteListScrollPaneItems = FXCollections.observableArrayList(noteList);
        noteListScrollPane.setItems(noteListScrollPaneItems);
        return true;
    }

    /**
     * @description the aim is to open the last note the user edited. I know it doesn't work as expected right now. (Celik)
     * @return
     */
    private boolean loadLastNote()
    {
        noteListScrollPane.getSelectionModel().select(0);
        return true;
    }

    // Button Feedback is aimed to show buttons natural :d
    private void buttonFeedback()
    {
        isChanged = true;
        boldToggleButton.setSelected(webPage.queryCommandState(Defaults.BOLD_COMMAND));
        italicToggleButton.setSelected(webPage.queryCommandState(Defaults.ITALIC_COMMAND));
        underlineToggleButton.setSelected(webPage.queryCommandState(Defaults.UNDERLINE_COMMAND));
        strikethroughToggleButton.setSelected(webPage.queryCommandState(Defaults.STRIKETHROUGH_COMMAND));
        insertOrderedListToggleButton.setSelected(webPage.queryCommandState(Defaults.NUMBERS_COMMAND));
        insertUnorderedListToggleButton.setSelected(webPage.queryCommandState(Defaults.BULLETS_COMMAND));
    }

    private static String colorValueToHex(Color c) {
        return String.format((Locale)null, "#%02x%02x%02x",
                Math.round(c.getRed() * 255),
                Math.round(c.getGreen() * 255),
                Math.round(c.getBlue() * 255));
    }
}
