package com.mattmx.packbrowser.gui.components;

import com.mattmx.packbrowser.gui.PackTabs;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.constraints.*;
import lombok.Builder;

import java.awt.*;
import java.util.List;

@Builder
public class SectionBrowser extends UIBlock {
    private List<UIComponent> texts;
    private int selected = 0;
    private Color active;
    private Color inactive;

    public SectionBrowser(List<UIComponent> texts , float spacing, Color active, Color inactive) {
        this.texts = texts;
        this.active = active;
        this.inactive = inactive;
        for (UIComponent text : texts) {
            if (spacing > 0) {
                text.setX(new AdditiveConstraint(new SiblingConstraint(), new PixelConstraint(spacing)));
            }
            text.setY(new CenterConstraint());
            text.setChildOf(this);
        }
        setHeight(new ChildBasedMaxSizeConstraint());
    }

    public int getSelectedNum() {
        return selected;
    }

    public void setSelected(int i) {
        selected = i;
    }
    public UIText getSelected() {
        return (UIText) texts.get(selected);
    }
    public void update() {
        for (int i = 0; i < texts.size(); i++) {
            UIText text = (UIText) texts.get(i);
            if (selected == i) {
                text.setColor(active);
            } else {
                text.setColor(inactive);
            }
        }
    }
}
