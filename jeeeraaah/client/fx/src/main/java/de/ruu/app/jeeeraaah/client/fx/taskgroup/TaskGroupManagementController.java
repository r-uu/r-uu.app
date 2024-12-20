package de.ruu.app.jeeeraaah.client.fx.taskgroup;

import de.ruu.app.jeeeraaah.client.fx.taskgroup.editor.TaskGroupEditor;
import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.*;
import de.ruu.app.jeeeraaah.client.fx.taskgroup.mapstruct.Map_TaskGroup_Bean_DTO;
import de.ruu.app.jeeeraaah.client.rs.ClientTaskGroup;
import de.ruu.app.jeeeraaah.common.dto.TaskGroupEntityDTO;
import de.ruu.lib.fx.comp.DefaultFXCViewController;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.OK;

/**
 * Java FX Component View Controller
 * <p>
 * generated by {@code de.ruu.lib.gen.java.fx.comp.GeneratorFXCViewController} at 2024.10.12 07:54:38:618
 */
@Slf4j
class TaskGroupManagementController extends DefaultFXCViewController implements TaskGroupManagementService
{
	@FXML private Button btnAdd;
	@FXML private Button btnEdit;
	@FXML private Button btnExit;
	@FXML private Button btnRemove;

	@FXML private ImageView ivwAdd;
	@FXML private ImageView ivwEdit;
	@FXML private ImageView ivwRemove;
	@FXML private ImageView ivwExit;

	@FXML private AnchorPane root;
	@FXML private ToolBar tbr1;
	@FXML private ToolBar tbr2;
	@FXML private TableView<TaskGroupFXBean> tv;
	@FXML private VBox vbx;

	@Inject
	private TaskGroupEditor editor;
	private Parent          editorLocalRoot;

	@Inject
	private ClientTaskGroup client;

	@Override @FXML protected void initialize()
	{
		// force editor to fxml-inject internal fx-controls
		editorLocalRoot = editor.getLocalRoot();

		TableViewConfigurator.configure(tv);
		client.findAll().forEach
		(
				tg ->
				{
					TaskGroupDTO    taskGroupDTO    = Map_TaskGroup_EntityDTO_DTO.INSTANCE.map(tg);
					TaskGroupBean   taskGroupBean   = Map_TaskGroup_DTO_Bean     .INSTANCE.map(taskGroupDTO);
					TaskGroupFXBean taskGroupFXBean = Map_TaskGroup_Bean_FXBean  .INSTANCE.map(taskGroupBean);
					tv.getItems().add(taskGroupFXBean);
				}
		);
		btnAdd .setOnAction(e -> onAdd (e));
		btnExit.setOnAction(e -> onExit(e));
	}

	private void onAdd(ActionEvent e)
	{
		// populate editor with new item, call to getService() has to be done after call to getLocalRoot() to make sure
		// internal java fx bindings can be establihed (see initialize)
		TaskGroupBean       taskGroupBean   = new TaskGroupBean("new task group");
		TaskGroupFXBean     taskGroupFXBean = taskGroupBean.toFXSource();
		editor.getService().taskGroup(taskGroupFXBean);

		Dialog<TaskGroupFXBean> dialog = new Dialog<>();

		DialogPane pane = dialog.getDialogPane();
		pane.setContent(editorLocalRoot);
		pane.getButtonTypes().addAll(CANCEL, OK);

		dialog.setTitle("new");
		dialog.setResultConverter(this::dialogResultConverterFXBean);

		Optional<TaskGroupFXBean> optional = dialog.showAndWait();

		if (optional.isPresent())
		{
			taskGroupFXBean = optional.get();
			taskGroupBean   = taskGroupFXBean.toFXSource();

			// create a task group dto from task group bean (which is a task group dto)
			TaskGroupEntityDTO taskGroupEntityDTO = taskGroupBean.toDTO();

			// let client create a new item in db
			taskGroupEntityDTO = client.create(taskGroupEntityDTO);
			// create fx bean from entity dto
			TaskGroupDTO taskGroupDTO = Map_TaskGroup_EntityDTO_DTO.INSTANCE.map(taskGroupEntityDTO);
			taskGroupBean             = Map_TaskGroup_DTO_Bean     .INSTANCE.map(taskGroupDTO);
			// create fx bean from bean
			taskGroupFXBean = taskGroupBean.toFXSource();

			// add and select item with retrieved item
			tv.getItems().add(taskGroupFXBean);
			tv.getSelectionModel().select(taskGroupFXBean);
		}
	}

	private void onExit(ActionEvent e)
	{
		CDI.current().getBeanManager().getEvent().fire(new TaskGroupManagementDisposeRequestEvent(this));
	}

	private TaskGroupFXBean dialogResultConverterFXBean(ButtonType btn)
	{
		if (btn.getButtonData() == OK_DONE)
				return editor.getService().taskGroup().orElse(null);
		return null;
	}
}