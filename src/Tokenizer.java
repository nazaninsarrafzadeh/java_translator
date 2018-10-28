import java.util.ArrayList;
import java.util.Map;

/**
 * Created by nazanin on 10/19/2018.
 */

public class Tokenizer {

    private ArrayList<Character> word=new ArrayList<>();

    private char nextChar;
    private StringBuilder stringBuilder=new StringBuilder();
    private String translated;
    private String toBeTranslated;
    private String keywordValue="";
    private String translatedId;

    public String tokenize(String lineOfCode) {

        for (int i = 0; i < lineOfCode.length(); i++) {
            Main.token.setCol(i - 1);
            nextChar = lineOfCode.charAt(i);
         //   System.out.println(nextChar+"  :");

            if (Main.cmSuspect) {
                for (int j = i; j < lineOfCode.length(); j++) {
                    stringBuilder.append(lineOfCode.charAt(j));
                }
                if (lineOfCode.charAt(lineOfCode.length() - 1) == '/' && lineOfCode.charAt(lineOfCode.length() - 2) == '*') {
                    Main.cmSuspect = false;
                }
                break;
            }
//            else if ((i<lineOfCode.length()-1) && (nextChar==' ' && lineOfCode.charAt(i+1)==' ')){
//               // System.out.println("inja");
//                concate();
//
//            }

            else if ((!isDelimeter(nextChar) && !isOperator(nextChar)) ) {
//                if(nextChar==' '){
//                    word.add('^');
//                  //  stringBuilder.append(nextChar);
//                }else
                    word.add(nextChar);
            }
            else if (isOperator(nextChar)) {

                if (nextChar == '/' && lineOfCode.charAt(i + 1) == '*') {
                    Main.cmSuspect = true;
                    for (int j = i; j < lineOfCode.length(); j++) {
                        stringBuilder.append(lineOfCode.charAt(j));
                    }
                    break;
                } else if (nextChar == '/' && lineOfCode.charAt(i + 1) == '/') {
                    for (int j = i; j < lineOfCode.length(); j++) {
                        stringBuilder.append(lineOfCode.charAt(j));
                    }
                    break;
                } else {
                    concate();
                }

            }

            else if (isDelimeter(nextChar) && !isOperator(nextChar)) {

                if (nextChar=='{' || nextChar=='}'){
                    Main.count++;
                }
                concate();
            }

            else {
               // System.out.println("onja");
                word.add(nextChar);
            }
        }

        return stringBuilder.toString();
    }

    private void concate() {
        if (!word.isEmpty()) {

            String ourWord = word.toString().replaceAll(",", "").replace(" ","").replace("[", "").replace("]", "");
            if (isString(ourWord)) {
                ourWord= ourWord.replace('^',' ');
                stringIsValid(ourWord);
                stringBuilder.append(ourWord);
            } else {
                System.out.println(ourWord);
                //   ourWord= ourWord.replace('^',' ');
            //    System.out.println(ourWord);
                prepareToTranslate(ourWord);
                stringBuilder.append(translated);
            }
        }

        stringBuilder.append(nextChar);
        word.clear();

    }

    private boolean isString(String word){

        boolean isString=false;
        if (word.charAt(0)=='"'){
            for (int i = 1; i <word.length() ; i++) {
                if(word.charAt(i-1)!='\\' && word.charAt(i)=='"')
                    isString=true;
            }
        }
        return isString;
    }

    private void stringIsValid(String word) {
        for (int i = 2; i < word.length()-1; i++) {
            if((word.charAt(i-1)=='\\' && (word.charAt(i)!='t' && word.charAt(i)!='n' && word.charAt(i)!='r' && word.charAt(i)!='"' && word.charAt(i)!='\\'))  ||
                    ( word.charAt(i-1)!='\\' && word.charAt(i)=='"')) {
                if(i==3) {
                    if((word.charAt(i-2)!='\\' && word.charAt(i-1)!='\\')||((word.charAt(i-2)!='\\' && word.charAt(i-1)=='\\'))){
                        Main.okStr = false;
                     //   System.out.println(word);

                    }
                }
                else if(i>3){
                    int j=i-3,l=0;
                    while (word.charAt(j)=='\\') {
                        j++;l++;
                    }
                    if(l%2==1){
                        Main.okStr = false;
                      //  System.out.println(word);
                    }
                }
                else{
                    Main.okStr = false;
                  //  System.out.println(word);

                }
            }
        }
    }


    private boolean isDelimeter(char nextChar) {

        if (nextChar == ';' || nextChar == '>' || nextChar==',' || nextChar==' ' || nextChar =='\t' ||
                nextChar == '<' || nextChar == '=' || nextChar == '(' || nextChar == ')' || nextChar=='.' |
                nextChar == '[' || nextChar == ']' || nextChar == '{' || nextChar == '}')
            return true;
        return false;
    }

    private boolean isInString(String word) {

        if (word.charAt(1) == '"') {
            return true;
        }
        return false;
    }

    private boolean isOperator(char nextChar){

        if (nextChar == '+' || nextChar == '-' || nextChar == '*' ||
                nextChar == '/' || nextChar == '>' || nextChar == '<' ||
                nextChar == '=' || nextChar == '!')
            return true;
        return false;
    }

    private boolean isDigit(String word){

        if (word.matches("^[+-]?([0-9]*[.])?[0-9]+$"))
            return true;
        return false;
    }

    private boolean isKeyword(String word)
    {
        for (Map.Entry<String,String> entry : DictionaryManager.dictionary.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (Main.fromLang){
                case "en":
                    if (word.equals(key)) {
                        keywordValue = value;
                        return true;
                    }
                    break;
                case "fa":
                    if (word.equals(value)) {
                        keywordValue = key;
                        return true;
                    }
                    break;
            }

        }
        return false;
    }
    int gj;

    private boolean identifierIsValid(String id){
        if (!Character.isDigit(id.charAt(0)) && id.charAt(0)!='-'  && !id.contains("-") && !id.contains("."))

      //  if (id.matches("^([a-zA-Z_$][a-zA-Z\\d_$]*)$"))
            return true;
        return false;
    }


    private void prepareToTranslate(String ourWord){
      //  System.out.println("prepare");
        toBeTranslated=ourWord;
        toBeTranslated=ourWord.replace("_"," ");
     //   System.out.println(toBeTranslated);
      //  toBeTranslated=word.toString().replaceAll(",","").replace(" ","").replace("[","").replace("]","");

        if (isKeyword(toBeTranslated)){

          //  System.out.println("keyword          "+toBeTranslated);
            translated=keywordValue;
        }
        else if (isDigit(toBeTranslated)){

            translated=toBeTranslated;
            //   System.out.println("number found");

        }

        else {
            if (identifierIsValid(toBeTranslated)){
            //    System.out.println("valid "+toBeTranslated);
                //     System.out.println("id found");
                if(identifierExists(toBeTranslated)){
                    translated=translatedId;
                    //    System.out.println("exist");
                }
                else {
                    try {

                        translated = Translator.translate(Main.fromLang, Main.toLang, toBeTranslated);
                        translated=translated.replace(" ","_");
                        if (Main.fromLang.equals("en")) {

                            DictionaryManager.translatedIdentifiers.put(toBeTranslated, translated);
                            //  System.out.println("key   "+DictionaryManager.translatedIdentifiers.get(toBeTranslated));
                        }
                        else if (Main.fromLang.equals("fa")){

                            DictionaryManager.translatedIdentifiers.put(translated,toBeTranslated);
                            //   System.out.println("key   "+DictionaryManager.translatedIdentifiers.get(translated));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                Main.okId=false;
                System.out.println("invalid id found");
            }
        }

    }

    private boolean identifierExists(String toBeTranslated){
        boolean exists=false;
        for (Map.Entry<String,String> entry : DictionaryManager.translatedIdentifiers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (Main.fromLang){
                case "en":
                    if (toBeTranslated.equals(key)) {
                        translatedId = value;
                        exists=true;
                    }
                    break;
                case "fa":
                    if (toBeTranslated.equals(value)) {
                        translatedId = key;
                        exists=true;
                    }
                    break;
            }
        }
        return exists;
    }

}

