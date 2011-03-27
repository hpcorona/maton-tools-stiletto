package com.maton.tools.stiletto.view.dnd;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

public class DefaultTransferType extends Transfer {

	public int type;
	public String typeName;

	public DefaultTransferType(String typeName) {
		this.typeName = typeName;

		type = LocalSelectionTransfer.registerType(typeName);
	}
	
	public int getType() {
		return type;
	}

	@Override
	public TransferData[] getSupportedTypes() {
		TransferData data = new TransferData();
		data.type = type;

		return new TransferData[] { data };
	}

	@Override
	public boolean isSupportedType(TransferData transferData) {
		return transferData.type == type;
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { type };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { typeName };
	}

	@Override
	protected void javaToNative(Object object, TransferData transferData) {
		LocalSelectionTransfer.getTransfer().javaToNative(object, transferData);
	}

	@Override
	protected Object nativeToJava(TransferData transferData) {
		return LocalSelectionTransfer.getTransfer().nativeToJava(transferData);
	}

}
