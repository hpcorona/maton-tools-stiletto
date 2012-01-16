package com.maton.tools.stiletto.cmd.helper;

import org.eclipse.core.runtime.IProgressMonitor;

public class CommandProcessMonitor implements IProgressMonitor {
	
	private static CommandProcessMonitor INSTANCE;
	
	public static CommandProcessMonitor getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CommandProcessMonitor();
		}
		
		return INSTANCE;
	}
	
	boolean cancelled = false;
	String taskName;
	String subTask;

	@Override
	public void beginTask(String arg0, int arg1) {
		System.out.println(arg0);
		taskName = arg0;
	}

	@Override
	public void done() {
		System.out.println("Done.");
	}

	@Override
	public void internalWorked(double arg0) {

	}

	@Override
	public boolean isCanceled() {
		return cancelled;
	}

	@Override
	public void setCanceled(boolean arg0) {
		cancelled = arg0;
	}

	@Override
	public void setTaskName(String arg0) {
		taskName = arg0;
	}

	@Override
	public void subTask(String arg0) {
		System.out.println("Subtask: " + arg0);
		subTask = arg0;
	}

	@Override
	public void worked(int arg0) {

	}

}
