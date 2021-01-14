/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package games.rednblack.editor.view.ui.properties.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.Validators;
import com.kotcrab.vis.ui.widget.*;
import games.rednblack.editor.event.ButtonToNotificationListener;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.h2d.common.view.ui.widget.TintButton;
import games.rednblack.editor.event.CheckBoxChangeListener;
import games.rednblack.editor.event.KeyboardListener;
import games.rednblack.editor.view.ui.properties.UIItemProperties;
import games.rednblack.h2d.common.view.ui.StandardWidgetsFactory;

/**
 * Created by azakhary on 4/15/2015.
 */
public class UIBasicItemProperties extends UIItemProperties {

    public static final String prefix = "games.rednblack.editor.view.ui.properties.panels.UIBasicItemProperties";
    public static final String TINT_COLOR_BUTTON_CLICKED = prefix + ".TINT_COLOR_BUTTON_CLICKED";
    public static final String CUSTOM_VARS_BUTTON_CLICKED = prefix + ".CUSTOM_VARS_BUTTON_CLICKED";
    public static final String TAGS_BUTTON_CLICKED = prefix + ".TAGS_BUTTON_CLICKED";
    public static final String ADD_COMPONENT_BUTTON_CLICKED = prefix + "ADD_COMPONENT_BUTTON_CLICKED";
    public static final String LINKING_CHANGED = prefix + ".LINKING_CHANGED";

    private Image itemTypeIcon;
    private VisLabel itemType;

    private VisImageButton linkImage;
    private VisLabel libraryLinkLabel;

    private VisTable linkageContainer;

    private VisTextField idBox;

    private VisTextField xValue;
    private VisTextField yValue;
    private VisTextField widthValue;
    private VisTextField heightValue;
    private VisTextField scaleXValue;
    private VisTextField scaleYValue;
    private VisCheckBox flipVertical;
    private VisCheckBox flipHorizontal;
    private TintButton tintColorComponent;
    private VisTextField rotationValue;
    private VisTextButton customVarsButton;
    private VisTextButton tagsButton;

    private VisSelectBox<String> nonExistantComponents;
    private VisTextButton addComponentButton;

    public UIBasicItemProperties() {
        Validators.FloatValidator floatValidator = new Validators.FloatValidator();

        itemType = new VisLabel("");
        itemType.setAlignment(Align.left);
        itemTypeIcon = new Image();

        libraryLinkLabel = StandardWidgetsFactory.createLabel("");
        libraryLinkLabel.setAlignment(Align.left);
        linkImage = new VisImageButton("library-link-button");
        linkImage.setWidth(22);

        VisTable iconContainer = new VisTable();
        iconContainer.add(itemTypeIcon).width(22).right();

        linkageContainer = new VisTable();
        linkageContainer.setVisible(false);
        linkageContainer.add(linkImage).width(22);
        linkageContainer.add(libraryLinkLabel);
        linkageContainer.row();

        idBox = StandardWidgetsFactory.createTextField();
        xValue = StandardWidgetsFactory.createValidableTextField(floatValidator);
        yValue = StandardWidgetsFactory.createValidableTextField(floatValidator);
        widthValue = StandardWidgetsFactory.createValidableTextField(floatValidator);
        widthValue.setDisabled(true);
        heightValue = StandardWidgetsFactory.createValidableTextField(floatValidator);
        heightValue.setDisabled(true);
        scaleXValue = StandardWidgetsFactory.createValidableTextField(floatValidator);
        scaleYValue = StandardWidgetsFactory.createValidableTextField(floatValidator);
        flipVertical = StandardWidgetsFactory.createCheckBox();
        flipHorizontal = StandardWidgetsFactory.createCheckBox();
        tintColorComponent = StandardWidgetsFactory.createTintButton();
        rotationValue = StandardWidgetsFactory.createValidableTextField(floatValidator);
        customVarsButton = new VisTextButton("Custom Vars");
        tagsButton = new VisTextButton("Tags");

        nonExistantComponents = StandardWidgetsFactory.createSelectBox(String.class);
        addComponentButton = new VisTextButton("Add");

        VisTable componentsTable = new VisTable();
        componentsTable.add(nonExistantComponents).left().width(150).padRight(10);
        componentsTable.add(addComponentButton).right().height(21);
        componentsTable.row();

        add(iconContainer).padTop(9).padRight(3).right().fillX();
        add(itemType).width(143).height(21).colspan(2).padTop(9).left();
        row();
        addSeparator().padTop(5).padBottom(6).colspan(3);
        add(StandardWidgetsFactory.createLabel("Identifier:", Align.left)).fillX();
        add(idBox).width(151).right().height(21).colspan(2);
        row();
        add(linkageContainer).colspan(3).right();
        row().padTop(2);
        add(StandardWidgetsFactory.createLabel("Position:")).padRight(3).left().top();
        add(getAsTable("X:", xValue, "Y:", yValue)).left();
        add(getAsTable("Width:", widthValue, "Height:", heightValue)).right();
        row().padTop(6);
        add(StandardWidgetsFactory.createLabel("Rotation:")).padRight(3).left();
        add(rotationValue).width(45).height(21).left().padLeft(13);
        add(getTintTable()).width(45).height(21).right().padLeft(10);
        row().padTop(6);
        add(StandardWidgetsFactory.createLabel("Scale:")).padRight(3).left().top();
        add(getAsTable("X:", scaleXValue, "Y:", scaleYValue)).left();
        VisTable buttonsTable = new VisTable();
        buttonsTable.add(customVarsButton);
        buttonsTable.row();
        buttonsTable.add(tagsButton).right().padTop(2);
        add(buttonsTable).height(45).left().top().padLeft(13);
        row();
        addSeparator().padTop(9).padBottom(6).colspan(3);
        add(StandardWidgetsFactory.createLabel("Add additional components:", Align.left)).fillX().colspan(3);
        row().padTop(6);
        add(componentsTable).left().colspan(3);
        row();

        setListeners();
    }

    public void setNonExistentComponents(Array<String> componentNames) {
        nonExistantComponents.setItems(componentNames);
    }

    public String getSelectedComponent() {
        return nonExistantComponents.getSelected();
    }

    public void setLinkage(boolean isLinked, String text) {
        linkageContainer.setVisible(true);
        linkImage.setChecked(isLinked);
        libraryLinkLabel.setText(text);
    }

    private Table getTintTable() {
        VisTable tintTable = new VisTable();
        tintTable.add(StandardWidgetsFactory.createLabel("Tint:")).padRight(3);
        tintTable.add(tintColorComponent).padRight(3);
        return tintTable;
    }

    private Table getAsTable(String text1, Actor actor1, String text2, Actor actor2) {
        VisTable positionTable = new VisTable();
        positionTable.add(StandardWidgetsFactory.createLabel(text1)).right().padRight(3);
        positionTable.add(actor1).width(45).height(21);
        positionTable.row().padTop(4);
        positionTable.add(StandardWidgetsFactory.createLabel(text2)).right().padRight(3);
        positionTable.add(actor2).width(45).height(21).left();
        return positionTable;
    }

    public void setItemType(int type, int itemUniqueId) {
        itemType.setText(EntityFactory.itemTypeNameMap.get(type) + " ("+itemUniqueId+")");
        itemTypeIcon.setDrawable(VisUI.getSkin().getDrawable(EntityFactory.itemTypeIconMap.get(type)));
        itemTypeIcon.setScaling(Scaling.fit);
        itemTypeIcon.setWidth(22);
    }

    public String getIdBoxValue() {
        return idBox.getText();
    }

    public void setIdBoxValue(String idBox) {
        this.idBox.setText(idBox);
    }

    public String getRotationValue() {
        return rotationValue.getText();
    }

    public void setRotationValue(String rotationValue) {
        this.rotationValue.setText(rotationValue);
    }

    public String getXValue() {
        return xValue.getText();
    }

    public void setXValue(String xValue) {
        this.xValue.setText(xValue);
    }

    public String getYValue() {
        return yValue.getText();
    }

    public void setYValue(String yValue) {
        this.yValue.setText(yValue);
    }

    //private TextButton customVarsBtn;

    public String getWidthValue() {
        return widthValue.getText();
    }

    public void setWidthValue(String widthValue) {
        this.widthValue.setText(widthValue);
    }

    public void setWidthHeightDisabled(boolean disabled) {
        this.widthValue.setDisabled(disabled);
        this.heightValue.setDisabled(disabled);
    }

    public String getHeightValue() {
        return heightValue.getText();
    }

    public void setHeightValue(String heightValue) {
        this.heightValue.setText(heightValue);
    }

    public String getScaleXValue() {
        return scaleXValue.getText();
    }

    public void setScaleXValue(String scaleXValue) {
        this.scaleXValue.setText(scaleXValue);
    }

    public String getScaleYValue() {
        return scaleYValue.getText();
    }

    public void setScaleYValue(String scaleYValue) {
        this.scaleYValue.setText(scaleYValue);
    }

    public boolean getFlipV() {
        return flipVertical.isChecked();
    }

    public void setFlipV(boolean flipVertical) {
        this.flipVertical.setChecked(flipVertical);
    }

    public boolean getFlipH() {
        return flipHorizontal.isChecked();
    }

    public void setFlipH(boolean flipHorizontal) {
        this.flipHorizontal.setChecked(flipHorizontal);
    }

    public Color getTintColor() {
        return tintColorComponent.getColorValue();
    }

    public void setTintColor(Color tintColor) {
        tintColorComponent.setColorValue(tintColor);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    private void setListeners() {
        idBox.addListener(new KeyboardListener(getUpdateEventName()));
        xValue.addListener(new KeyboardListener(getUpdateEventName()));
        yValue.addListener(new KeyboardListener(getUpdateEventName()));
        widthValue.addListener(new KeyboardListener(getUpdateEventName()));
        heightValue.addListener(new KeyboardListener(getUpdateEventName()));
        scaleXValue.addListener(new KeyboardListener(getUpdateEventName()));
        scaleYValue.addListener(new KeyboardListener(getUpdateEventName()));
        flipVertical.addListener(new CheckBoxChangeListener(getUpdateEventName()));
        flipHorizontal.addListener(new CheckBoxChangeListener(getUpdateEventName()));
        rotationValue.addListener(new KeyboardListener(getUpdateEventName()));

        tintColorComponent.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                facade.sendNotification(TINT_COLOR_BUTTON_CLICKED, tintColorComponent.getColorValue(), null);
            }
        });
        customVarsButton.addListener(new ButtonToNotificationListener(CUSTOM_VARS_BUTTON_CLICKED));
        tagsButton.addListener(new ButtonToNotificationListener(TAGS_BUTTON_CLICKED));
        addComponentButton.addListener(new ButtonToNotificationListener(ADD_COMPONENT_BUTTON_CLICKED));

        linkImage.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                boolean isLinked = linkImage.isChecked();
                facade.sendNotification(LINKING_CHANGED, isLinked);
            }
        });
    }
}
