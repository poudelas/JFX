package sample;

import javafx.animation.ScaleTransition;
import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RMSPreloader extends Preloader {

    /** The Progress Bar. */
    protected ProgressBar progressBar;

    /** The preloader Stage. */
    protected Stage preloaderStage;

    /** The text that will display message received. */
    protected Text messageText;

    /** Flag that indicates if the application is initialized. */
    protected boolean isAppInitialized = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleProgressNotification(final ProgressNotification pn) {
        if (this.isAppInitialized && pn.getProgress() >= 0.0 && pn.getProgress() <= 1.0) {
            this.progressBar.setProgress(pn.getProgress());
        } else {
            this.messageText.setText(getMessageFromCode((int) pn.getProgress()));
        }
    }

    /**
     * Gets the message from code.
     *
     * @param messageCode the message code
     *
     * @return the message from code
     */
    private String getMessageFromCode(final int messageCode) {
        String res = "";
        switch (messageCode) {
            case 100:
                res = "Initializing";
                break;
            case 200:
                res = "";// Provisioned for custom pre-init task
                break;
            case 300:
                res = "";// Provisioned for custom pre-init task
                break;
            case 400:
                res = "Loading Messages Properties";
                break;
            case 500:
                res = "Loading Parameters Properties";
                break;
            case 600:
                res = "Preparing Core Engine";
                break;
            case 700:
                res = "Preloading Resources";
                break;
            case 800:
                res = "Preloading Modules";
                break;
            case 900:
                res = "";// Provisioned for custom post-init task
                break;
            case 1000:
                res = "Starting";
                break;
            default:
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleStateChangeNotification(final StateChangeNotification event) {
        switch (event.getType()) {
            case BEFORE_LOAD:
                break;
            case BEFORE_INIT:
                this.isAppInitialized = true;
                break;
            case BEFORE_START:
                hideStage();
                break;
            default:
        }
    }

    /**
     * Perform actions before the application start.
     *
     * @throws InterruptedException
     */
    protected void hideStage() {
        final Stage stage = this.preloaderStage;

        final ScaleTransition st = new ScaleTransition();
        st.setFromX(1.0);
        st.setToX(0.0);
        st.setDuration(Duration.millis(400));
        st.setNode(stage.getScene().getRoot());
        st.setOnFinished(actionEvent -> stage.hide());
        st.play();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        // Store the preloader stage to reuse it later
        this.preloaderStage = stage;

        // Configure the stage
        stage.centerOnScreen();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(createPreloaderScene());

        // Let's start the show
        stage.show();
    }

    /**
     * Creates the preloader scene.
     *
     * @return the scene
     */
    protected Scene createPreloaderScene() {

        final StackPane p = new StackPane();

        final ImageView logo = new ImageView(new Image("restaurantlogo.png"));
        p.getChildren().add(logo);
        StackPane.setAlignment(logo, Pos.CENTER);

        this.progressBar = new ProgressBar(0.0);
        this.progressBar.setPrefSize(460, 20);
        p.getChildren().add(this.progressBar);
        StackPane.setAlignment(this.progressBar, Pos.BOTTOM_CENTER);
        StackPane.setMargin(this.progressBar, new Insets(30));

        this.messageText = new Text("Loading");
        p.getChildren().add(this.messageText);
        StackPane.setAlignment(this.messageText, Pos.BOTTOM_CENTER);
        StackPane.setMargin(this.messageText, new Insets(10));

        return new Scene(p, 600, 600, Color.TRANSPARENT);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void handleApplicationNotification(final PreloaderNotification n) {
        messageText.setText(n.toString());
    }
}

