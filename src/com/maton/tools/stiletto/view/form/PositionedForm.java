package com.maton.tools.stiletto.view.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;

import com.maton.tools.stiletto.model.Positioned;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.IModelListener;

public class PositionedForm implements IModelListener {

	protected Composite parent;
	protected Sprite sprite;
	protected Positioned current;
	protected Composite container;

	protected Scale rotation;
	protected Scale alpha;
	protected Button flipX;
	protected Button flipY;

	public PositionedForm(Composite parent, Sprite sprite) {
		this.parent = parent;
		this.sprite = sprite;
		sprite.addModelListener(this);
		current = null;

		build();

		modelToScreen();
	}

	public void dispose() {
		sprite.removeModelListener(this);
	}

	public Composite getContainer() {
		return container;
	}

	protected void build() {
		container = new Composite(parent, SWT.BORDER);
		FormLayout layout = new FormLayout();
		layout.spacing = 5;
		layout.marginTop = 5;
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.marginBottom = 5;
		container.setLayout(layout);

		createFormSection();

		setupEvents();
	}

	protected void createFormSection() {
		Label lblRot = new Label(container, SWT.NONE);
		lblRot.setText("Rotation:");
		rotation = new Scale(container, SWT.BORDER);
		rotation.setMaximum(359);
		rotation.setMinimum(0);

		Label lblAlpha = new Label(container, SWT.NONE);
		lblAlpha.setText("Alpha:");
		alpha = new Scale(container, SWT.BORDER);
		alpha.setMaximum(255);
		alpha.setMinimum(0);

		Label lblFlipX = new Label(container, SWT.NONE);
		flipX = new Button(container, SWT.CHECK);
		flipX.setText("Flip X");

		Label lblFlipY = new Label(container, SWT.NONE);
		flipY = new Button(container, SWT.CHECK);
		flipY.setText("Flip Y");

		FormTools.forBoth(lblRot, rotation, null, 10);
		FormTools.forBoth(lblAlpha, alpha, rotation, 0);
		FormTools.forBoth(lblFlipX, flipX, alpha, 0);
		FormTools.forBoth(lblFlipY, flipY, flipX, 0);
	}

	private void setupEvents() {
		Listener generic = new Listener() {
			@Override
			public void handleEvent(Event event) {
				screenToModel();
			}
		};

		rotation.addListener(SWT.Selection, generic);
		alpha.addListener(SWT.Selection, generic);
		flipX.addListener(SWT.Selection, generic);
		flipY.addListener(SWT.Selection, generic);
	}

	protected void screenToModel() {
		if (current == null) {
			container.setVisible(false);
			return;
		}
		container.setVisible(false);

		current.setRotation(rotation.getSelection());
		current.setAlpha(alpha.getSelection());
		current.setFlipX(flipX.getSelection());
		current.setFlipY(flipY.getSelection());

		sprite.notifyChange(current);
	}

	protected void modelToScreen() {
		if (current == null) {
			container.setVisible(false);
			return;
		}
		container.setVisible(true);

		rotation.setSelection((int) current.getRotation());
		alpha.setSelection(current.getAlpha());
		flipX.setSelection(current.isFlipX());
		flipY.setSelection(current.isFlipY());
	}

	public void setCurrent(Positioned pos) {
		current = pos;
		modelToScreen();
	}

	@Override
	public void notifyInsert(Object obj, int idx) {

	}

	@Override
	public void notifyNew(Object obj) {

	}

	@Override
	public void notifyChange(Object obj) {
		if (current == obj) {
			modelToScreen();
		}
	}

	@Override
	public void notifyDelete(Object obj) {
		if (current == obj) {
			current = null;
		}
		modelToScreen();
	}

}
