package de.ruu.app.jeeeraaah.client.fx;

import de.ruu.app.jeeeraaah.client.fx.taskgroup.TaskGroupManagement;
import de.ruu.app.jeeeraaah.client.fx.taskgroup.TaskGroupManagementDisposeRequestEvent;
import de.ruu.lib.cdi.se.EventDispatcher;
import de.ruu.lib.fx.FXUtil;
import de.ruu.lib.fx.comp.DefaultFXCViewController;
import de.ruu.lib.fx.comp.FXCAppStartedEvent;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import static de.ruu.lib.fx.FXUtil.getStage;

/**
 * Java FX Component View Controller
 * <p>
 * generated by {@code de.ruu.lib.gen.java.fx.comp.GeneratorFXCViewController} at 2024.10.11 17:28:37:119
 */
@Slf4j
class MainController extends DefaultFXCViewController
{
	@FXML private Button btnGantt;
	@FXML private Button btnTaskGroups;
	@FXML private Button btnTasksByGroup;
	@FXML private Label lblJeeeRaaah;
	@FXML private VBox main;
	@FXML private AnchorPane root;

	@Inject private TaskGroupManagement taskGroupManagement;

	@Inject private EventDispatcher<FXCAppStartedEvent>                 eventDispatcherFXCAppStarted;
	@Inject private EventDispatcher<TaskGroupManagementDisposeRequestEvent> eventDispatcherTaskGroupManagerExitRequested;

	@Override @FXML
	protected void initialize()
	{
		log.debug("eventDispatcherFXCAppStarted == null {}", eventDispatcherFXCAppStarted == null);

		eventDispatcherFXCAppStarted                .add(e -> onAppStarted(e));
		eventDispatcherTaskGroupManagerExitRequested.add(e -> onTaskGroupManagerExitRequested(e));

		btnTaskGroups.setOnAction(e -> onTaskGroups(e));
	}

	private void onTaskGroups(ActionEvent e)
	{
		ObservableList<Node> mainRootChildren        = root.getChildren();
		Parent               taskGroupManagementRoot = taskGroupManagement.getLocalRoot();

		mainRootChildren.remove(main);
		mainRootChildren.add(taskGroupManagementRoot);

		FXUtil.setAnchorsInAnchorPaneTo(taskGroupManagementRoot, 0);
	}

	private void onTaskGroupManagerExitRequested(TaskGroupManagementDisposeRequestEvent e)
	{
		ObservableList<Node> mainRootChildren = root.getChildren();

		mainRootChildren.remove(taskGroupManagement.getLocalRoot());
		mainRootChildren.add(main);

		FXUtil.setAnchorsInAnchorPaneTo(main, 0);
	}

	private void onAppStarted(FXCAppStartedEvent e) { getStage(root).ifPresent(s -> s.setTitle("jeee RAAAH")); }
}