package com.yovelb.gol;

import com.yovelb.app.command.CommandExecutor;
import com.yovelb.app.event.EventBus;
import com.yovelb.app.state.StateRegistry;
import com.yovelb.gol.logic.editor.EditorApplicationComponent;
import com.yovelb.gol.logic.simulator.SimulatorApplicationComponent;
import com.yovelb.gol.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class App2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        EventBus eventBus = new EventBus();
        StateRegistry stateRegistry = new StateRegistry();
        CommandExecutor commandExecutor = new CommandExecutor(stateRegistry);

        MainView mainView = new MainView(eventBus);
//        mainView.setTop(toolbar);
//        mainView.setCenter(simulationCanvas);
//        mainView.setBottom(infoBar);

        ApplicationContext context = new ApplicationContext(eventBus, stateRegistry, commandExecutor, mainView, 20, 12);

        List<ApplicationComponent> components = new LinkedList<>();
        components.add(new EditorApplicationComponent());
        components.add(new SimulatorApplicationComponent());

        for (ApplicationComponent component : components) {
            component.initComponent(context);
        }

        for (ApplicationComponent component : components) {
            component.initState(context);
        }

        Scene scene = new Scene(mainView, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}