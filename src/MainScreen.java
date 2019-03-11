import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class MainScreen extends Application {
    private final int screenHeight = 900;
    private final int screenWidth = 900;

    public static void main(String[] args) {
        Planet planet = new Planet();

        launch(args);
    }
  
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Something");
        stage.setHeight(screenHeight);
        stage.setWidth(screenWidth);

        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(screenHeight,screenWidth);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);


        stage.setScene(scene);
        stage.show();
    }
}
