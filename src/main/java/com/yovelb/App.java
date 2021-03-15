package com.yovelb;

import com.yovelb.viewmodel.ApplicationState;
import com.yovelb.viewmodel.ApplicationViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        MainView mainView = new MainView(appViewModel);
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        mainView.draw();
    }
    public static void main(String[] args) { launch(); }
}