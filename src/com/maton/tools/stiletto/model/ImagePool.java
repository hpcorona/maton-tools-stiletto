package com.maton.tools.stiletto.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class ImagePool extends ModelEventProvider {
	private HashMap<String, Image> images;
	private BundleContext ctx;

	public ImagePool(BundleContext ctx) {
		images = new HashMap<String, Image>();
		this.ctx = ctx;
	}

	public void free() {
		Collection<Image> imgs = images.values();
		for (Image img : imgs) {
			img.free();
			notifyChange(img);
		}
	}
	
	public void removeUnloaded() {
		String key;
		
		ArrayList<String> toDelete = new ArrayList<String>();
		
		Iterator<String> iter = images.keySet().iterator();
		while ((key = iter.next()) != null) {
			Image img = images.get(key);
			
			if (!img.isLoaded()) {
				toDelete.add(key);
			}
		}
		
		for (String k : toDelete) {
			notifyDelete(images.get(k));
			images.remove(k);
		}
	}

	public void reload() {
		free();

		File f = new File(ctx.getImagePath(""));
		File[] pngs = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.toLowerCase().endsWith(".png");
			}
		});

		for (File png : pngs) {
			loadSingle(png);
		}
	}

	public Image loadSingle(File png) {
		String fileName = png.getName();

		int idxExt = fileName.lastIndexOf('.');
		String name = fileName.substring(0, idxExt);

		Image img = images.get(name);
		if (img == null) {
			img = new Image(fileName, ctx);

			images.put(name, img);
			
			notifyNew(img);
		} else {
			img.reload();
			
			notifyChange(img);
		}
		
		return img;
	}

	public boolean importFile(File source) {
		File target = new File(ctx.getImagePath(""));
		if (source.getAbsolutePath().equals(target.getAbsolutePath())) {
			loadSingle(source);
		}

		File destination = new File(target, source.getName());

		try {
			FileInputStream fis = new FileInputStream(source);
			FileOutputStream fos = new FileOutputStream(destination);
			try {
				byte[] buf = new byte[1024];
				int i = 0;
				while ((i = fis.read(buf)) != -1) {
					fos.write(buf, 0, i);
				}
			} catch (Exception e) {
				return false;
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
				} catch (Throwable e) {
				}
			}
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
	
	public Image getImage(String name) {
		return images.get(name);
	}
	
	public Object[] toArray() {
		return images.values().toArray();
	}

}
