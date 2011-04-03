package com.maton.tools.stiletto.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Device;

import com.maton.tools.stiletto.model.base.IBaseModel;
import com.maton.tools.stiletto.model.io.ModelInput;
import com.maton.tools.stiletto.model.io.ModelOutput;

public class Bundle {
	protected File file;
	protected ImagePool images;
	protected SpritePool sprites;
	protected AnimationPool animations;
	protected ActorPool actors;
	protected FontPool fonts;
	protected BundleContext ctx;

	public Bundle(Device device, File file) {
		this.file = file;

		String path = file.getParentFile().getAbsolutePath();
		ctx = new BundleContext(device, path);

		images = new ImagePool(ctx);
		sprites = new SpritePool(ctx);
		animations = new AnimationPool(ctx);
		actors = new ActorPool(ctx);
		fonts = new FontPool(ctx);

		if (file.exists()) {
			load();
		} else {
			images.reload();
			save();
		}
	}

	public ImagePool getImages() {
		return images;
	}

	public SpritePool getSprites() {
		return sprites;
	}

	public AnimationPool getAnimations() {
		return animations;
	}

	public ActorPool getActors() {
		return actors;
	}

	public FontPool getFonts() {
		return fonts;
	}

	public void refresh() {
		images.reload();
	}

	public void imports(File[] pngs) {
		for (File png : pngs) {
			images.importFile(png);
		}
	}

	public void build() {

	}

	public void save() {
		ModelOutput.save(this);
	}

	public void load() {
		ModelInput.load(this);
	}

	public File getFile() {
		return file;
	}

	public void clear() {
		fonts.clear();
		actors.clear();
		animations.clear();
		sprites.clear();
		images.clear();
	}

	public BundleContext getCtx() {
		return ctx;
	}

	public String getName() {
		String name = file.getName();
		int idx = name.lastIndexOf(".");
		return name.substring(0, idx);
	}

	public List<IBaseModel> findUses(IBaseModel item) {
		if (item instanceof Image) {
			return findUses((Image) item);
		} else if (item instanceof Sprite) {
			return findUses((Sprite) item);
		} else if (item instanceof Animation) {
			return findUses((Animation) item);
		} else if (item instanceof Actor) {
			return findUses((Actor) item);
		} else if (item instanceof Font) {
			return findUses((Font) item);
		}

		return new ArrayList<IBaseModel>();
	}

	public List<IBaseModel> findUses(Image image) {
		ArrayList<IBaseModel> uses = new ArrayList<IBaseModel>();

		for (Sprite spr : sprites.getList()) {
			boolean hasIt = false;

			for (Positioned pos : spr.getList()) {
				if (pos.getSource() == image) {
					hasIt = true;
					break;
				}
			}

			if (hasIt) {
				uses.add(spr);

				List<IBaseModel> spriteUses = findUses(spr);
				uses.addAll(spriteUses);
			}
		}

		return uses;
	}

	public List<IBaseModel> findUses(Sprite sprite) {
		ArrayList<IBaseModel> uses = new ArrayList<IBaseModel>();

		for (Animation ani : animations.getList()) {
			boolean hasIt = false;

			for (Frame fra : ani.getList()) {
				if (fra.getSource() == sprite) {
					hasIt = true;
					break;
				}
			}

			if (hasIt) {
				uses.add(ani);

				List<IBaseModel> animationUses = findUses(ani);
				uses.addAll(animationUses);
			}
		}

		return uses;
	}

	public List<IBaseModel> findUses(Animation animation) {
		ArrayList<IBaseModel> uses = new ArrayList<IBaseModel>();

		for (Actor actor : actors.getList()) {
			boolean hasIt = false;

			for (Action act : actor.getList()) {
				if (act.getSource() == animation) {
					hasIt = true;
					break;
				}
			}

			if (hasIt) {
				uses.add(actor);
			}
		}

		return uses;
	}

	public List<IBaseModel> findUses(Actor actor) {
		return new ArrayList<IBaseModel>();
	}

	public List<IBaseModel> findUses(Font font) {
		return new ArrayList<IBaseModel>();
	}

	public void remove(IBaseModel item) {
		if (item instanceof Image) {
			remove((Image) item);
		} else if (item instanceof Sprite) {
			remove((Sprite) item);
		} else if (item instanceof Animation) {
			remove((Animation) item);
		} else if (item instanceof Actor) {
			remove((Actor) item);
		} else if (item instanceof Font) {
			remove((Font) item);
		}
	}

	public void remove(Image image) {
		for (Sprite spr : sprites.getList()) {
			List<Positioned> list = spr.getList();

			for (int i = list.size() - 1; i >= 0; i--) {
				Positioned pos = list.get(i);

				if (pos.getSource() == image) {
					spr.removeChild(i);
				}
			}
		}

		images.removeElement(image);
		File fisical = new File(ctx.getImagePath(image.getName()));
		fisical.delete();
	}

	public void remove(Sprite sprite) {
		for (Animation ani : animations.getList()) {
			List<Frame> list = ani.getList();

			for (int i = list.size() - 1; i >= 0; i--) {
				Frame fra = list.get(i);

				if (fra.getSource() == sprite) {
					ani.removeChild(i);
				}
			}
		}

		sprites.removeElement(sprite);
	}

	public void remove(Animation animation) {
		for (Actor actor : actors.getList()) {
			List<Action> list = actor.getList();

			for (int i = list.size() - 1; i >= 0; i--) {
				Action act = list.get(i);

				if (act.getSource() == animation) {
					actor.removeChild(i);
				}
			}
		}

		animations.removeElement(animation);
	}

	public void remove(Actor actor) {
		actors.removeElement(actor);
	}

	public void remove(Font font) {
		fonts.removeElement(font);
	}
}
