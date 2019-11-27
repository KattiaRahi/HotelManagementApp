package hkrFX;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

interface IDeserializable<T> {

    public T Deserialize(FileReader reader);
}

interface ISerializable<T> {

    public T Serialize();
}

public class MainFX extends Application {

    public static short SCENE_HEIGHT = 440;
    public static short SCENE_WIDTH = 600;
    private static Config _config;
    private BookingInfoStage bookingInfoStage;
    private HomeStage homeStage;
    public static Translator Translator;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setResizable(false);
        bookingInfoStage = new BookingInfoStage();
        homeStage = new HomeStage();
        _config = new Config();
        loadConfig();
        Translator = new Translator();

        initializeEvents();
        stage.setScene(homeStage.getScene());
        stage.show();

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    }

    private void initializeEvents(){

        homeStage.getButton("Customer").setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                bookingInfoStage.show();
            }
        });

        homeStage.getButton("Employee").setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //new ReceptionStage();

            }
        });

        homeStage.getButton("Admin").setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                new LoginStage();
            }
        });

        //scene.setFill(Color.TRANSPARENT);
        //stage.initStyle(StageStyle.TRANSPARENT);
    }

    private void loadConfig(){
        File f = new File("config.json");
        if(!f.exists()){
            try (FileWriter file = new FileWriter("config.json", false)) {

                MainFX._config.setLanguageCode("en");
                String json = MainFX._config.Serialize();
                file.write(json);
                file.flush();

            } catch (IOException e) {
                Logger.LogException(e.getMessage());
            }
        }
        else {

            try (FileReader reader = new FileReader("config.json"))
            {
                JSONParser jsonParser = new JSONParser();
                Object json = jsonParser.parse(reader);
                MainFX._config = MainFX._config.Deserialize(reader);

            }
            catch (IOException | ParseException e) {
                Logger.LogException(e.getMessage());
            }
        }
    }

    private void unloadConfig(){
        //on app close
    }

    private void loadDefaults(){
        try (FileWriter file = new FileWriter("config.json", false)) {

            MainFX._config = new Config();
            MainFX._config.setLanguageCode("en");

            String json = MainFX._config.Serialize();
            file.write(json);
            file.flush();

        } catch (IOException e) {
            Logger.LogException(e.getMessage());
        }
    }

    public static boolean equals(char[] str1, char[] str2){
        if(str1.length != str2.length)
            return false;
        for (short i = 0; i < str1.length; i++)
            if(str1[i] != str2[i])
                return false;


        return true;
    }

    public static Config getConfig(){
        return _config;
    }
}
