import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import maze.Maze;
import maze.routing.NoRouteFoundException;
import maze.routing.RouteFinder;
import maze.visualisation.Graphics;

import java.io.IOException;

/**
 * The type Maze application.
 */
public class MazeApplication extends Application {

    private RouteFinder routeFinder;
    private Maze maze;
    private Canvas canvas;
    private Scene scene;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start.
     *
     * @param primaryStage the primary stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.maze = Maze.fromTxt("/home/horia/Documents/JavaStuff/comp16412-coursework-2_k55592hr/mazes/maze1.txt");
        this.routeFinder = new RouteFinder(this.maze);
        this.canvas = Graphics.makeCanvas(maze);

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(10,10);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        Button loadMapB = new Button("Load Map");
        Button loadRouteB = new Button("Load Route");
        Button saveRouteB = new Button("Save Route");
        Button stepB = new Button("Step");
        TextField loadMapTxt = new TextField();
        TextField loadRouteTxt = new TextField();
        TextField saveRouteTxt = new TextField();
        gridPane.add(loadMapB, 0, 0);
        gridPane.add(loadMapTxt, 1, 0);
        gridPane.add(loadRouteB, 0, 1);
        gridPane.add(loadRouteTxt, 1, 1);
        gridPane.add(saveRouteB, 0, 2);
        gridPane.add(saveRouteTxt, 1, 2);
        gridPane.add(stepB, 0, 5, 1, 1);
        gridPane.add(canvas, 0, 3, 2, 2);
        Scene scene = new Scene(gridPane, canvas.getWidth(), canvas.getHeight()+130);
        primaryStage.setTitle("Maze");
        primaryStage.setScene(scene);


        stepB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    routeFinder.isThereAnyRoute();
                } catch (NoRouteFoundException e) {
                    e.printStackTrace();
                }
                routeFinder.step();
                Graphics.drawRoute(canvas, routeFinder);
            }
        });

        loadMapB.setOnAction(actionEvent -> {
            String file = loadMapTxt.getText();
            routeFinder = RouteFinder.loadMap("/home/horia/Documents/JavaStuff/comp16412-coursework-2_k55592hr/mazes/" + file + ".txt");
            maze = routeFinder.getMaze();
            Graphics.makeCanvasRe(canvas, maze);
            primaryStage.setWidth(canvas.getWidth());
            primaryStage.setHeight(canvas.getHeight()+167);
        });

        loadRouteB.setOnAction(actionEvent -> {
            String file = loadRouteTxt.getText();
            try {
                routeFinder = RouteFinder.load("/home/horia/Documents/JavaStuff/comp16412-coursework-2_k55592hr/mazes/" + file + ".txt");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            maze = routeFinder.getMaze();
            Graphics.makeCanvasRe(canvas, maze);
            Graphics.drawRoute(canvas, routeFinder);
            primaryStage.setWidth(canvas.getWidth());
            primaryStage.setHeight(canvas.getHeight() + 167);
        });

        saveRouteB.setOnAction(actionEvent -> {
            String file = saveRouteTxt.getText();
            try {
                routeFinder.save("/home/horia/Documents/JavaStuff/comp16412-coursework-2_k55592hr/mazes/" + file + ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        primaryStage.show();

    }
}
