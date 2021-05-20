package tqlin.plugins;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.plugins.PluginConfigurationException;
import org.apache.commons.digester3.plugins.PluginCreateRule;
import org.apache.commons.digester3.plugins.PluginInvalidInputException;
import org.apache.commons.digester3.plugins.PluginRules;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.NoOpLog;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Digester插件模块示例
 */
public class TestDefaultPlugin {

    // --------------------------------------------------------------- Test cases
    @Test
    public void testDefaultPlugins1()
            throws Exception {
        // * tests that when a PluginCreateRule is defined with a default
        // class, that the default class is instantiated when no class
        // or id is specified in the xml file.
        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);

        PluginCreateRule pcr = new PluginCreateRule(Widget.class, TextLabel.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);

        digester.parse(Utils.getInputStream(this, "defaultPlugin.xml"));

        Object child;
        List<Widget> children = root.getChildren();
        assertNotNull(children);
        assertEquals(3, children.size());

        child = children.get(0);
        assertNotNull(child);
        assertEquals(TextLabel.class, child.getClass());
        TextLabel label1 = (TextLabel) child;
        assertEquals("label1", label1.getLabel());

        child = children.get(1);
        assertNotNull(child);
        assertEquals(TextLabel.class, child.getClass());
        TextLabel label2 = (TextLabel) child;
        assertEquals("label2", label2.getLabel());

        child = children.get(2);
        assertNotNull(child);
        assertEquals(Slider.class, child.getClass());
        Slider slider1 = (Slider) child;
        assertEquals("slider1", slider1.getLabel());
    }

    public void testDefaultPlugins2()
            throws Exception {
        // * tests that when there is no default plugin, it is an error
        // not to have one of plugin-class or plugin-id specified
        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);

        PluginCreateRule pcr = new PluginCreateRule(Widget.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);

        Log oldLog = digester.getLogger();
        try {
            digester.setLogger(new NoOpLog());
            digester.parse(Utils.getInputStream(this, "defaultPlugin.xml"));
            fail("Expected SAXParseException");
        } catch (SAXParseException e) {
            assertEquals(PluginInvalidInputException.class, e.getException().getClass());
        } finally {
            digester.setLogger(oldLog);
        }

    }

    public void testDefaultPlugins3()
            throws Exception {
        // * tests that the default plugin must implement or extend the
        // plugin base class.
        Digester digester = new Digester();
        PluginRules rc = new PluginRules();
        digester.setRules(rc);

        PluginCreateRule pcr = new PluginCreateRule(Widget.class, Object.class);
        digester.addRule("root/widget", pcr);
        digester.addSetNext("root/widget", "addChild");

        Container root = new Container();
        digester.push(root);

        Log oldLog = digester.getLogger();
        try {
            digester.setLogger(new NoOpLog());
            digester.parse(Utils.getInputStream(this, "test2.xml"));
            fail("Expected SAXParseException");
        } catch (SAXParseException e) {
            assertEquals(PluginConfigurationException.class, e.getException().getClass());
        } finally {
            digester.setLogger(oldLog);
        }
    }
}