package ru.kcode.view;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBLHelper extends GridBagConstraints {
    private static final long serialVersionUID = -8218771576922362754L;

    private GBLHelper() {
        super();
    }

    public static GBLHelper create() {
        return new GBLHelper();
    }

    public GBLHelper setGrid(int x, int y) {
        this.gridx = x;
        this.gridy = y;
        return this;
    }

    public GBLHelper colSpan() {
        this.gridwidth = GridBagConstraints.REMAINDER;
        return this;
    }

    public GBLHelper colSpan(int col) {
        this.gridwidth = col;
        return this;
    }

    public GBLHelper rowSpan(int row) {
        this.gridheight = row;
        return this;
    }
    
    public GBLHelper fillH() {
        this.fill = GridBagConstraints.HORIZONTAL;
        if (this.weightx == 0) {
            this.weightx = 0.01;
        }
        return this;
    }

    public GBLHelper fillV() {
        this.fill = GridBagConstraints.VERTICAL;
        if (this.weighty == 0) {
            this.weighty = 0.01;
        }
        return this;
    }

    public GBLHelper fillB() {
        this.fill = GridBagConstraints.BOTH;
        if (this.weightx == 0) {
            this.weightx = 0.01;
        }
        if (this.weighty == 0) {
            this.weighty = 0.01;
        }
        return this;
    }
    
    public GBLHelper weightH(double weigth) {
        this.weightx = weigth;
        return this;
    }
    
    public GBLHelper weightV(double weigth) {
        this.weighty = weigth;
        return this;
    }
    
    public GBLHelper anchorT() {
        this.anchor = GridBagConstraints.FIRST_LINE_START;
        if (this.weighty == 0) {
            this.weighty = 0.01;
        }
        return this;
    }
    
    public GBLHelper margin(int top, int right, int bottom, int left) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }
    
    public GBLHelper margin(int topBottom, int rightLeft) {
        this.insets = new Insets(topBottom, rightLeft, topBottom, rightLeft);
        return this;
    }
    
    public GBLHelper margin(int all) {
        this.insets = new Insets(all, all, all, all);
        return this;
    }
}
