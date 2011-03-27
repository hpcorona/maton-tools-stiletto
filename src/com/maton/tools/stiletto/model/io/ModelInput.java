package com.maton.tools.stiletto.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import nanoxml.XMLElement;
import nanoxml.XMLParseException;

import com.maton.tools.stiletto.model.Action;
import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.model.Frame;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.Positioned;

public class ModelInput {
	public static boolean load(Bundle bundle) {
		bundle.clear();

		File file = bundle.getFile();

		XMLElement xml = new XMLElement();
		FileReader reader;
		try {
			reader = new FileReader(file);
			xml.parseFromReader(reader);

			@SuppressWarnings("unchecked")
			Vector<XMLElement> childs = xml.getChildren();
			for (XMLElement child : childs) {
				if (child.getName().equals("images")) {
					loadImages(child, bundle);
				} else if (child.getName().equals("sprites")) {
					loadSprites(child, bundle);
				} else if (child.getName().equals("animations")) {
					loadAnimations(child, bundle);
				} else if (child.getName().equals("actors")) {
					loadActors(child, bundle);
				}
			}

			bundle.getImages().reload();

			return true;
		} catch (FileNotFoundException e) {
		} catch (XMLParseException e) {
		} catch (IOException e) {
		}

		return false;
	}

	public static void loadActors(XMLElement actors, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = actors.getChildren();
		for (XMLElement actor : childs) {
			String actorName = actor.getStringAttribute("name");
			Actor act = bundle.getActors().newElement(actorName);

			@SuppressWarnings("unchecked")
			Vector<XMLElement> actions = actor.getChildren();
			for (XMLElement action : actions) {
				String name = action.getStringAttribute("name");
				String animName = action.getStringAttribute("animation");

				Animation anim = bundle.getAnimations().getElement(animName);

				Action actionNew = act.addChild(anim);
				actionNew.setName(name);
			}
		}
	}

	public static void loadImages(XMLElement images, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = images.getChildren();
		for (XMLElement image : childs) {
			String name = (String) image.getAttribute("name");
			String filename = (String) image.getAttribute("filename");
			String export = (String) image.getAttribute("export");

			Image img = bundle.getImages().loadSingle(filename);
			img.setExportName(name);
			img.setExport(export.equals("true"));
		}
	}

	public static void loadSprites(XMLElement sprites, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = sprites.getChildren();
		for (XMLElement sprite : childs) {
			String spriteName = sprite.getStringAttribute("name");
			String rendered = sprite.getStringAttribute("rendered");
			String imageName = sprite.getStringAttribute("image");

			Sprite spr = bundle.getSprites().newElement(spriteName);
			spr.setImageName(imageName);
			spr.setRendered(rendered.equals("true"));

			@SuppressWarnings("unchecked")
			Vector<XMLElement> modules = sprite.getChildren();
			for (XMLElement module : modules) {
				String name = module.getStringAttribute("name");
				int x = module.getIntAttribute("x");
				int y = module.getIntAttribute("y");
				int alpha = module.getIntAttribute("alpha");
				float rotation = (float) module.getDoubleAttribute("rotation");

				Image img = bundle.getImages().getElement(name);

				Positioned<Image> pos = spr.addChild(img);
				pos.setX(x);
				pos.setY(y);
				pos.setAlpha(alpha);
				pos.setRotation(rotation);
			}
		}
	}

	public static void loadAnimations(XMLElement animations, Bundle bundle) {
		@SuppressWarnings("unchecked")
		Vector<XMLElement> childs = animations.getChildren();
		for (XMLElement animation : childs) {
			String animName = animation.getStringAttribute("name");
			Animation anim = bundle.getAnimations().newElement(animName);

			@SuppressWarnings("unchecked")
			Vector<XMLElement> frames = animation.getChildren();
			for (XMLElement frame : frames) {
				String spriteName = frame.getStringAttribute("sprite");
				int time = frame.getIntAttribute("time");

				Sprite spr = bundle.getSprites().getElement(spriteName);

				Frame fra = anim.addChild(spr);
				fra.setTime(time);
			}
		}
	}
}
