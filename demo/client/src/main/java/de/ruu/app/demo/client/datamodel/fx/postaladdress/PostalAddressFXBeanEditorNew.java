package de.ruu.app.demo.client.datamodel.fx.postaladdress;

import de.ruu.app.demo.client.datamodel.fx.postaladdress.edit.PostalAddressFXBeanEditor;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import lombok.NonNull;

import java.util.Optional;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;
import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.OK;

public class PostalAddressFXBeanEditorNew
{
	@NonNull private PostalAddressFXBeanEditor editor;

	public PostalAddressFXBeanEditorNew(@NonNull PostalAddressFXBeanEditor editor) { this.editor = editor; }

	public Optional<PostalAddressFXBean> optionalPostalAddressCreateData()
	{
		PostalAddressFXBean newPostalAddressFXBean = new PostalAddressFXBean();

		Dialog<PostalAddressFXBean> dialog = new Dialog<>();

		DialogPane pane = dialog.getDialogPane();
		pane.setContent(editor.getLocalRoot());
		pane.getButtonTypes().addAll(CANCEL, OK);

		dialog.setTitle("new");
		dialog.setResultConverter(btn -> dialogResultConverterFXBean(btn));

		editor.getService().postalAddress(newPostalAddressFXBean);

		return dialog.showAndWait();
	}

	private PostalAddressFXBean dialogResultConverterFXBean(ButtonType btn)
	{
		if (btn.getButtonData() == OK_DONE)
				return editor.getService().postalAddress().orElse(null);
		return null;
	}
}