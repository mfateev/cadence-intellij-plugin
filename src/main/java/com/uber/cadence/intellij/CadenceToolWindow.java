package com.uber.cadence.intellij;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;

public class CadenceToolWindow {
    private final Project project;
    private final ToolWindow toolWindow;
    private JPanel toolWindowPanel;
    private JTabbedPane tabbedPane;
    private JLabel domainLabel;
    private JPanel closedWorkflowsTab;
    private JTable closedWorkflowsTable;
    private String domainName;

    public CadenceToolWindow(ToolWindow toolWindow, Project project) {
        this.toolWindow = toolWindow;
        this.project = project;
        loadConfig();
    }

    public JComponent getContent() {
        return toolWindowPanel;
    }

    public void onDomainUpdate() {
        loadConfig();
    }

    private void loadConfig() {
        if (project != null) {
            PropertiesComponent properties = PropertiesComponent.getInstance(project);
            domainName = properties.getValue(CadenceConfigurable.CADENCE_DOMAIN_PROPERTY, CadenceConfigurable.DEFAULT_VALUE);
            domainLabel.setText(domainName);
        }
    }
}
