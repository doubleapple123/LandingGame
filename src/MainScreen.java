import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class MainScreen extends Application {
    public static void main(String[] args) {
        Planet planet = new Planet();

        launch(args);
    }
  
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Something");
        stage.setHeight(1080);
        stage.setWidth(1980);

        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(1980,1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);


        stage.setScene(scene);
        stage.show();
    }
}
