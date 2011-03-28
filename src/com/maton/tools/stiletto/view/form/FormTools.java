package com.maton.tools.stiletto.view.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;

public class FormTools {

	public static void forLabel(Control principal, Control input) {
		FormData data = new FormData(50, SWT.DEFAULT);
		
		data.left = new FormAttachment(0);
		data.top = new FormAttachment(input, 0, SWT.CENTER);
		
		principal.setLayoutData(data);
	}
	
	public static void forInput(Control principal, Control label, Control superior, int head) {
		FormData data = new FormData(150, SWT.DEFAULT);
		
		if (superior != null) {
			data.top = new FormAttachment(superior, head);
		} else {
			data.top = new FormAttachment(0);
		}
		
		data.left = new FormAttachment(label);
		data.right = new FormAttachment(100);
		
		principal.setLayoutData(data);
	}
	
	public static void forBoth(Control label, Control control, Control prev, int head) {
		forLabel(label, control);
		forInput(control, label, prev, head);
	}
	
}
