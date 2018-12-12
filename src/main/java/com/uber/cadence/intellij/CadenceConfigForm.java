package com.uber.cadence.intellij;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;

public class CadenceConfigForm {
    private String originalDomain;
    private JTextField textFieldDomain;
    private JPanel panel;

    public CadenceConfigForm(String domain) {
        setOriginalDomain(domain);
        setDomain(domain);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void dispose() {
    }

    public boolean isModified() {
        return !textFieldDomain.getText().equals(originalDomain);
    }

    public String getDomain() {
        return textFieldDomain.getText();
    }

    public void setDomain(String domain) {
        textFieldDomain.setText(domain);
    }

    public void setOriginalDomain(String domain) {
        originalDomain = domain;
    }

}
