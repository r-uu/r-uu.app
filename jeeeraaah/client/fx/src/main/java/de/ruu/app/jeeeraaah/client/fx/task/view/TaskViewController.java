package de.ruu.app.jeeeraaah.client.fx.task.view;

import de.ruu.app.jeeeraaah.client.fx.task.TaskFXBean;
import de.ruu.app.jeeeraaah.common.Task;
import de.ruu.lib.cdi.se.EventDispatcher;
import de.ruu.lib.fx.FXUtil;
import de.ruu.lib.fx.comp.DefaultFXCViewController;
import de.ruu.lib.fx.comp.FXCAppStartedEvent;
import de.ruu.lib.jpa.core.Entity;
import de.ruu.lib.jpa.core.Entity.EntityInfo;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Java FX Component View Controller
 * <p>
 * generated by {@code de.ruu.lib.gen.java.fx.comp.GeneratorFXCViewController} at 2024.09.27 16:54:05:946
 */
@Slf4j
class TaskViewController extends DefaultFXCViewController implements TaskViewService
{
	static class TaskFXBeanWithEntityInfo extends TaskFXBean
	{
		private EntityInfo<Long> info;

		private TaskFXBeanWithEntityInfo(@NonNull Task task)
		{
			super(task);
			info = new EntityInfo<>((Entity<Long>) task);
		}
	}

	@FXML private Label lblActual;
	@FXML private Label lblDescription;
	@FXML private Label lblEstimated;
	@FXML private Label lblFinish;
	@FXML private Label lblId;
	@FXML private Label lblName;
	@FXML private Label lblStart;

	@FXML private GridPane grdPn;
	@FXML private DatePicker dtPckrFinishActual;
	@FXML private DatePicker dtPckrFinishEstimated;
	@FXML private DatePicker dtPckrStartActual;
	@FXML private DatePicker dtPckrStartEstimated;
	@FXML private TextArea taDescription;
	@FXML private TextField tfName;

	@Inject private EventDispatcher<FXCAppStartedEvent> eventDispatcherFXCAppStarted;

	@Override @FXML protected void initialize()
	{
		log.debug("eventDispatcherFXCAppStarted == null {}", eventDispatcherFXCAppStarted == null);
		eventDispatcherFXCAppStarted.add(e -> onAppStarted(e));
	}

	@Override public @NonNull Optional<TaskFXBean> task()
	{
		return Optional.empty();
	}

	@Override public void task(@NonNull TaskFXBean task)
	{

	}

	private void onAppStarted(FXCAppStartedEvent e)
	{
		Optional<Stage> optional = FXUtil.getStage(tfName);

		if (optional.isPresent())
		{
			optional.get().setTitle("task view");
		}
	}
}