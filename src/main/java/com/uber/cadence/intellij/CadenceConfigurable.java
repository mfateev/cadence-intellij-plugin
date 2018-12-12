package com.uber.cadence.intellij;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.function.Consumer;

public class CadenceConfigurable implements Configurable {

    public interface Notifier {
        void onConfigChange();
    }

    private static final String CONFIG_TOPIC_DISPLAY_NAME = "Cadence Config Notifier";
    static Topic<Notifier> CONFIG_TOPIC = Topic.create(CONFIG_TOPIC_DISPLAY_NAME, Notifier.class);
    static final String CADENCE_DOMAIN_PROPERTY = "uber.cadence.domain";
    public static final String DEFAULT_VALUE = "samples";
    private final Project project;
    private CadenceConfigForm form;
    PropertiesComponent properties;

    public CadenceConfigurable(Project project) {
        this.project = project;
        properties = PropertiesComponent.getInstance(project);
        String domain = properties.getValue(CADENCE_DOMAIN_PROPERTY, DEFAULT_VALUE);
        form = new CadenceConfigForm(domain);
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Cadence";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return form.getPanel();
    }

    @Override
    public boolean isModified() {
        return form.isModified();
    }

  @Override
  public void apply() throws ConfigurationException {
    String domain = form.getDomain();
    properties.setValue(CADENCE_DOMAIN_PROPERTY, domain);
    form.setOriginalDomain(domain);
    if (project != null) {
      MessageBus messageBus = project.getMessageBus();
      messageBus.connect();
      Notifier notifier = messageBus.syncPublisher(CONFIG_TOPIC);
      notifier.onConfigChange();
    }
  }

  @Override
  public void reset() {
        form.setDomain(DEFAULT_VALUE);
    }

    @Override
    public void disposeUIResources() {
        form.dispose();
    }
}
