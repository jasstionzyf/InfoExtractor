package sample.htmlUnit;

import java.io.IOException;

import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnit {
	private static Log mLog = LogFactory.getLog(HtmlUnit.class);

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
	 final WebClient webClient = new WebClient();
	    final HtmlPage page = webClient.getPage("http://www.sina.com.cn/");
	   // Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
   webClient.getOptions().setJavaScriptEnabled(false);
   webClient.setJavaScriptEnabled(false);
   webClient.getOptions().setCssEnabled(false);
 //  webClient.waitForBackgroundJavaScript(1000*60000);
   String encoding=page.getPageEncoding();
	    final String pageAsXml = page.asXml();
	 //   Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
        //System.out.print(pageAsXml);
       // mLog.info(pageAsXml);
	    final String pageAsText = page.asText();
        System.out.print(pageAsXml.contains("火锅")+"\n");
        System.out.print(encoding+"\n");

	    webClient.closeAllWindows();
}
}
