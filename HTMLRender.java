import java.util.Scanner;

/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author Aditi Chamarthy
 *	@since November 20. 2023
 */
public class HTMLRender {
	
	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private final int TOKENS_SIZE = 100000;	// size of array

	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
	
		
	public HTMLRender() {
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
	}
	
	
	public static void main(String[] args) {
		HTMLRender hf = new HTMLRender();
		hf.run(args[0]);
	}
	
	public void run(String arg) {
		
		tokens = tokenArray(arg);
		printRender();

		/*
		// Sample renderings from HtmlPrinter class
		
		// Print plain text without line feed at end
		browser.print("First line");
		
		// Print line feed
		browser.println();
		
		// Print bold words and plain space without line feed at end
		browser.printBold("bold words");
		browser.print(" ");
		
		// Print italic words without line feed at end
		browser.printItalic("italic words");
		
		// Print horizontal rule across window (includes line feed before and after)
		browser.printHorizontalRule();
		
		// Print words, then line feed (printBreak)
		browser.print("A couple of words");
		browser.printBreak();
		browser.printBreak();
		
		// Print a double quote
		browser.print("\"");
		
		// Print Headings 1 through 6 (Largest to smallest)
		browser.printHeading1("Heading1");
		browser.printHeading2("Heading2");
		browser.printHeading3("Heading3");
		browser.printHeading4("Heading4");
		browser.printHeading5("Heading5");
		browser.printHeading6("Heading6");
		
		// Print pre-formatted text (optional)
		browser.printPreformattedText("Preformat Monospace\tfont");
		browser.printBreak();
		browser.print("The end");
		
		*/
	}
	
	private String[] tokenArray(String fileName){
		HTMLUtilities util = new HTMLUtilities();
		int count = 0;
		Scanner input = FileUtils.openToRead(fileName);
		
		while (input.hasNext()) {
			String line = input.nextLine();
			String [] lineTokens = util.tokenizeHTMLString(line);
			for(int i = 0; i < lineTokens.length; i++){
				tokens[count] = lineTokens[i];
				count++;
			}
		}
		input.close();
		
		String [] tokenArr = new String[count];
		for(int i = 0; i < count; i++){
			tokenArr[i] = tokens[i];
		}
		
		return tokenArr;
	}
	
	private void printRender(){
		boolean ifBold = false;
		boolean ifItalic = false;
		
		
		for(int i = 0; i < tokens.length; i++){
			if(tokens[i].equalsIgnoreCase("<b>")){
				ifBold = true;
			}
			else if(tokens[i].equalsIgnoreCase("</b>")){
				ifBold = false;
			}
			else if(tokens[i].equalsIgnoreCase("<i>")){
				ifItalic = true;
			}
			else if(tokens[i].equalsIgnoreCase("</i>")){
				ifItalic = false;
			}
			else if(tokens[i].equalsIgnoreCase("<q>") || tokens[i].equalsIgnoreCase("</q>")){
				browser.print("\"");
			}
			else if(tokens[i].equalsIgnoreCase("<p>") || tokens[i].equalsIgnoreCase("</p>")){
				browser.println();
			}
			else if(tokens[i].equalsIgnoreCase("<hr>")){
				browser.printHorizontalRule();
			}
			else{
				if(ifBold){
					browser.printBold(tokens[i]);
				}
				else if(ifItalic){
					browser.printItalic(tokens[i]);
				}
			}
					
			browser.print(" ");
		}
	}
}
