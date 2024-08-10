package de.ruu.app.demo.client.datamodel.fx.postaladdress;

import de.ruu.app.demo.client.datamodel.fx.postaladdress.PostalAddressManagerController.PostalAddressFXBeanWithEntityInfo;
import de.ruu.app.demo.client.datamodel.fx.postaladdress.edit.PostalAddressFXBeanEditor;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import lombok.NonNull;

import java.util.Optional;

import static javafx.scene.control.ButtonBar.ButtonData.OK_DONE;

public class PostalAddressFXBeanEditorUpdate
{
	@NonNull private PostalAddressFXBeanEditor editor;

	public PostalAddressFXBeanEditorUpdate(@NonNull PostalAddressFXBeanEditor editor) { this.editor = editor; }

	public Optional<PostalAddressFXBeanWithEntityInfo> optionalPostalAddressUpdateData()
	{
		Dialog<PostalAddressFXBeanWithEntityInfo> dialog = new Dialog<>();

		DialogPane pane = dialog.getDialogPane();
		pane.setContent(editor.getLocalRoot());
		pane.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

		dialog.setTitle("new");
		dialog.setResultConverter(btn -> dialogResultConverterFXBean(btn));

		return dialog.showAndWait();
	}

	private PostalAddressFXBeanWithEntityInfo dialogResultConverterFXBean(ButtonType btn)
	{
		if (btn.getButtonData() == OK_DONE)
		{
			Optional<PostalAddressFXBean> result = editor.getService().postalAddress();
			if (result.isPresent())
			{
				try
				{
					return (PostalAddressFXBeanWithEntityInfo) result.get();
				}
				catch (ClassCastException e)
				{
					throw new IllegalStateException(
							"unexpected type " + result.getClass().getName()
									+   "expected type " + PostalAddressFXBeanWithEntityInfo.class.getName(), e);
				}
			}
		}
		return null;
	}
}