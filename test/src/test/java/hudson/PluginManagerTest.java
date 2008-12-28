package hudson;

import org.jvnet.hudson.test.HudsonTestCase;
import org.apache.commons.io.FileUtils;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlButton;

import java.io.File;

/**
 * @author Kohsuke Kawaguchi
 */
public class PluginManagerTest extends HudsonTestCase {
    /**
     * Manual submission form.
     */
    public void testUpload() throws Exception {
        HtmlPage page = new WebClient().goTo("pluginManager/advanced");
        HtmlForm f = page.getFormByName("uploadPlugin");
        File dir = env.temporaryDirectoryAllocator.allocate();
        File plugin = new File(dir, "tasks.hpi");
        FileUtils.copyURLToFile(getClass().getClassLoader().getResource("plugins/tasks.hpi"),plugin);
        f.getInputByName("name").setValueAttribute(plugin.getAbsolutePath());
        f.submit((HtmlButton)last(f.getHtmlElementsByTagName("button")));

        assertTrue( new File(hudson.getRootDir(),"plugins/tasks.hpi").exists() );
    }
}
