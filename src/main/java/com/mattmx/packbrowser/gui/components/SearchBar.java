package com.mattmx.packbrowser.gui.components;

import com.mattmx.packbrowser.gui.PackGUI;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIImage;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.constraints.animation.AnimatingConstraints;
import gg.essential.elementa.constraints.animation.Animations;
import gg.essential.elementa.constraints.animation.HeightAnimationComponent;
import gg.essential.elementa.effects.ScissorEffect;
import gg.essential.elementa.font.FontProvider;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class SearchBar extends UIBlock {
    private final PackGUI _parent;
    private boolean searching = false;

    public SearchBar(String title, PackGUI parent) {
        this._parent = parent;
        setColor(UIColors.BACKGROUND_BAR);
        enableEffect(new ScissorEffect());
        UIComponent searchIcon = new UIText("\u009E", false)
                .setX(new PixelConstraint(15f))
                .setY(new AdditiveConstraint(new CenterConstraint(), new PixelConstraint(30f)))
                .setChildOf(this);
        UIComponent search = new UITextInput("Search",
                    false,
                    UIColors.HIGHLIGHT,
                    UIColors.TEXT_ACTIVE
                )
                .setX(new PixelConstraint(30f))
                .setY(new AdditiveConstraint(new CenterConstraint(), new PixelConstraint(30f)))
                .setTextScale(new PixelConstraint(1.2f))
                .setWidth(new FillConstraint(true))
                .setColor(UIColors.TEXT_ACTIVE)
                .setChildOf(this);
        searchIcon.hide(true);
        search.hide(true);

        UIComponent titleText = new UIText(title, false)
                .setX(new PixelConstraint(15f))
                .setY(new CenterConstraint())
                .setColor(UIColors.HIGHLIGHT)
                .setChildOf(this);
        UIComponent searchButton = new UIButton()
                .setX(new SubtractiveConstraint(new RelativeConstraint(1f), new PixelConstraint(25f)))
                .setY(new CenterConstraint())
                .setWidth(new PixelConstraint(25f))
                .setHeight(new RelativeConstraint(1f))
                .setChildOf(this);
        searchButton.onMouseClickConsumer(e -> {
            AnimatingConstraints animSearchButton = searchButton.makeAnimation();
            AnimatingConstraints animText = titleText.makeAnimation();
            AnimatingConstraints searchAnim = search.makeAnimation();
            AnimatingConstraints searchIconAnim = searchIcon.makeAnimation();
            animText.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new PixelConstraint(-30f));
            animSearchButton.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new PixelConstraint(-30f));
            searchAnim.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new CenterConstraint());
            searchIconAnim.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new CenterConstraint());
            titleText.animateTo(animText);
            searchButton.animateTo(animSearchButton);
            search.unhide(false);
            search.animateTo(searchAnim);
            searchIcon.unhide(false);
            searchIcon.animateTo(searchIconAnim);
            searching = true;
            animSearchButton.onCompleteRunnable(() -> {
                titleText.hide();
                searchButton.hide();
            });
        });
        onMouseClickConsumer(e -> {
            if (searching) {
                search.grabWindowFocus();
            }
        });
        search.onKeyTypeConsumer((c, i) -> {
            if (i == GLFW_KEY_ENTER) {
                // execute search
                _parent.search(((UITextInput)search).getText());
            } else if (i == GLFW_KEY_ESCAPE) {
                hideSearch(searchButton, titleText, search, searchIcon);
            }
        });

        new UIText("\u009E", false)
                .setX(new CenterConstraint())
                .setY(new CenterConstraint())
                .setTextScale(new PixelConstraint(0.8f))
                .setChildOf(searchButton);
    }

    public void hideSearch(UIComponent searchButton, UIComponent titleText, UIComponent search, UIComponent searchIcon) {
        ((UITextInput)search).setText("");
        _parent.search(null);
        AnimatingConstraints animSearchButton = searchButton.makeAnimation();
        AnimatingConstraints animText = titleText.makeAnimation();
        AnimatingConstraints searchAnim = search.makeAnimation();
        AnimatingConstraints searchIconAnim = searchIcon.makeAnimation();
        animText.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new CenterConstraint());
        animSearchButton.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new CenterConstraint());
        searchAnim.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new PixelConstraint(30f));
        searchIconAnim.setYAnimation(Animations.IN_OUT_EXP, 0.5f, new PixelConstraint(30f));
        titleText.unhide(true);
        searchButton.unhide(true);
        titleText.animateTo(animText);
        searchButton.animateTo(animSearchButton);
        search.animateTo(searchAnim);
        searchIcon.animateTo(searchIconAnim);
        searching = false;
        animSearchButton.onCompleteRunnable(() -> {
            search.hide();
            searchIcon.hide();
        });
    }
}
