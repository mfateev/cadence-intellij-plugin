package com.uber.cadence.intellij;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public class CadenceToolWindowFactory implements ToolWindowFactory {
    private MessageBusConnection messageBusConnection;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        CadenceToolWindow cadenceToolWindow = new CadenceToolWindow(toolWindow, project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(cadenceToolWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);

        MessageBus messageBus = project.getMessageBus();
        messageBusConnection = messageBus.connect();
        messageBusConnection.subscribe(CadenceConfigurable.CONFIG_TOPIC, () -> cadenceToolWindow.onDomainUpdate());
    }
}
