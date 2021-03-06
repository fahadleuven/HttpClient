/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

/**
 *
 * @author Richard Osmar Leon Ingaruca - RNASystems
 */

import context.Const;
import context.TranslateEnvironment;
import text.Text;
import text.TextTranslate;
import utils.WebUtils;

public class ParseTextTranslate implements Parse {
        private TextTranslate textTranslate;
        private StringBuilder url;	        

        public ParseTextTranslate(TextTranslate textTranslate) {
                this.textTranslate = textTranslate;
        }

        //corrected method by jrichardsz
        //problem of google api: if you translate a text hello, the output is:
        //hola,hola,,,,en,,hola,5,1,0,1000,0,1,0,hola,5,hola,1000,1,0,de Hola,0,1,0,0,4,hola,,,es,en,3
        //then as the result is the text before the first "," they make a split[,] and return split[0]
        //All is ok but if you translat a text whit "," the split[0] the remaining text will be lost
        
        public void parse() throws Exception {
                appendURL();
                String result = WebUtils.source(url.toString());
                //correction:return the original output and develop a algotimo to extract only the word translated
                //result = result.replace("[", "").replace("]", "").replace("\"","");
                Text output = textTranslate.getOutput();
                output.setText(result);
        }
        
//        previous method
//        public void parse() {
//                appendURL();
//                String result = WebUtils.source(url.toString());
//                String split[] = result.replace("[", "").replace("]", "").replace("\"","").split(",");
//                Text output = textTranslate.getOutput();
//                output.setText(split[0]);
//        }
        

        public TextTranslate getTextTranslate() {
                return textTranslate;
        }

        //corrected method by jrichardsz
        //problem of google api: the URL is not dynamic : http://translate.google.com.br/translate_a/t? 
        // is only for "br"
        
        public void appendURL() throws Exception {
                Text input = textTranslate.getInput();
                Text output = textTranslate.getOutput();
                //correcction of google api: dynamic URL and not just br : http://translate.google.com.br/translate_a/t?
                url = new StringBuilder(TranslateEnvironment.getSystemProperty(Const.GOOGLE_TRANSLATE_TEXT).replace(".{locale}", "."+TranslateEnvironment.getSystemProperty(Const.LOCALE)));
                url.append("client=t&text=" + input.getText().replace(" ", "%20"));
                url.append("&hl=" + input.getLanguage());
                url.append("&sl=" + input.getLanguage());
                url.append("&tl=" + output.getLanguage());
                url.append("&multires=1&prev=btn&ssel=0&tsel=0&sc=1");
        }
        
//        previous method
//        public void appendURL() {
//                Text input = textTranslate.getInput();
//                Text output = textTranslate.getOutput();
//                url = new StringBuilder(URLCONSTANTS.GOOGLE_TRANSLATE_TEXT);
//                url.append("client=t&text=" + input.getText().replace(" ", "%20"));
//                url.append("&hl=" + input.getLanguage());
//                url.append("&sl=" + input.getLanguage());
//                url.append("&tl=" + output.getLanguage());
//                url.append("&multires=1&prev=btn&ssel=0&tsel=0&sc=1");
//        }
        

}

