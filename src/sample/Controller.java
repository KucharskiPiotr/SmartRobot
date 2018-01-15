
package sample;

        import com.smartrobot.model.SmartRobotModel;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.Group;
        import javafx.scene.canvas.Canvas;
        import javafx.scene.canvas.GraphicsContext;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.CheckBox;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.Region;
        import javafx.scene.paint.Color;

        import java.io.IOException;
        import java.net.URL;
        import java.util.ResourceBundle;

        import static java.awt.image.ImageObserver.HEIGHT;
        import static java.awt.image.ImageObserver.WIDTH;
        import static javafx.application.Application.launch;

/**
 * Class controls all events happened in application window, links SmartRobotModel with GUI
 */
public class Controller implements Initializable {

    /**
     * FXML elements loaded from file sample.fxml made with Scene Builder
     */
    @FXML
    private CheckBox checkbox;

    @FXML
    private Button startButton;

    @FXML
    private Button decideButton;

    @FXML
    private Region region;

    @FXML
    private Button resetButton;

    @FXML
    private Button quitButton;

    @FXML
    private Canvas canvas;

    private SmartRobotModel model;
    private GraphicsContext graphicsContext;
    /**
     * Booleans control behaviour of algorithm (make more strict or for testing purpose)
     */
    private boolean check = false;
    private boolean isAlgorithmStrict = false;

    /**
     * Method draws all dummy robots generated in model to canvas in black and single main robot in yellow
     * @param graphicsContext graphics context passed as argument by reference to draw in
     */
    private void drawRobots(GraphicsContext graphicsContext)
    {
        graphicsContext.setFill(Color.BLACK);

        for (int i = 0; i < model.getRobotManager().getRobots().size() - 1; i++)
        {
            graphicsContext.fillOval(model.getRobotManager().getRobots().get(i).getX(), model.getRobotManager().getRobots().get(i).getY(), 5, 5);
        }


        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillOval(model.getRobotManager().getRobots().get(model.getRobotManager().getRobots().size() - 1).getX(), model.getRobotManager().getRobots().get(model.getRobotManager().getRobots().size() - 1).getY(), 5, 5);
    }

    /**
     * Method draws antennas generated by in model of application
     * @param graphicsContext graphics context passed as argument by reference to draw in
     */
    private void drawAntennas(GraphicsContext graphicsContext)
    {
        graphicsContext.setFill(Color.WHITE);

        for (int i = 0; i < 3; i++)
        {
            graphicsContext.fillOval(model.getAntennas()[i].getX(), model.getAntennas()[i].getY(), 5, 5);
        }
    }

    /**
     * Method changes boolean that determines if antennas should be in shape of equilateral triangle
     * @param event event to be handled
     */
    @FXML
    void checkboxAction(ActionEvent event)
    {
        check = !check;
        System.out.println("CheckBox matched!");
    }

    /**
     * Method toggles strictness of algorithm
     * @param event
     */
    @FXML
    void checkbox1Action(ActionEvent event)
    {
        isAlgorithmStrict = !isAlgorithmStrict;
        System.out.println("CheckBox1 matched!");
    }

    /**
     * Method initializes application model and draws all antennas and robots in canvas
     * @param event mouse event to be handled
     */
    @FXML
    void startButtonAction(MouseEvent event)
    {
        model = new SmartRobotModel(420, 305);
        try
        {
            model.initializeModel("./src/com/smartrobot/model/parameters.ini", !check);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        drawRobots(graphicsContext);
        drawAntennas(graphicsContext);

        System.out.println("Canvas ON!");
    }

    /**
     * Method activates algorithm to determine if main robot should escape and gives output
     * @param event
     */
    @FXML
    void decideButtonAction(ActionEvent event)
    {
        if(model.shouldMainRobotStay(isAlgorithmStrict))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Decide Alert");
            alert.setHeaderText("Robot should stay or not?");
            alert.setContentText("Main Robot should stay!");
            //alert.setContentText("I have a great message for you!" + '\n' + "Buy PREMIUM version!");
            alert.showAndWait();

            System.out.println("Main Robot should stay!");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Decide Alert");
            alert.setHeaderText("Robot should stay or not?");
            alert.setContentText("Main Robot should fly away!");
            //alert.setContentText("I have a great message for you!" + '\n' + "Buy PREMIUM version!");
            alert.showAndWait();

            System.out.println("Main Robot should fly away!");
        }
    }

    /**
     * Method to present best case for the algorithm
     * @param event
     */
    @FXML
    void testButtonAction(ActionEvent event)
    {
        model = new SmartRobotModel(420, 305);
        try
        {
            model.initializeModelTest("./src/com/smartrobot/model/parameters.ini", false);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        drawRobots(graphicsContext);
        drawAntennas(graphicsContext);

        System.out.println("Canvas ON!");
    }

    /**
     * Method closes all window and shuts application down
     * @param event
     */
    @FXML
    void quitButtonAction(ActionEvent event)
    {
        System.exit(0);
    }

    /**
     * Constructor that is blank just for bug preventing
     */
    public Controller()
    {

    }

    /**
     * Method initializes graphicsContext from canvas and initializes model (late initialization when canvas is avaible)
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        graphicsContext = canvas.getGraphicsContext2D();
        model = new SmartRobotModel((int)canvas.getWidth(), (int)canvas.getHeight());
    }
}
