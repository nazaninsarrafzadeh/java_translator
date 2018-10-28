import java.util.HashMap;
import java.util.Map;

/**
 * Created by nazanin on 10/19/2018.
 */
public class DictionaryManager {

    public static Map<String, String> dictionary = new HashMap<String, String>();
    public static Map<String,String> translatedIdentifiers=new HashMap<>();


    public DictionaryManager(){
        dictionary.put("if","اگر");
        dictionary.put("else","اگرنه");
        dictionary.put("while","تاوقتی");
        dictionary.put("for","برای");
        dictionary.put("SystemOutPrintln","سطربعدبنویس");
        dictionary.put("int","عددصحیح");
        dictionary.put("float","ممیزشناور");
        dictionary.put("double","اعشاردوتایی");
        dictionary.put("String","رشته");
        dictionary.put("char","کاراکتر");
        dictionary.put("return","بازگردان");
        dictionary.put("true","درست");
        dictionary.put("false","غلط");
        dictionary.put("class","کلاس");
        dictionary.put("public","عمومی");
        dictionary.put("private","خصوصی");
        dictionary.put("static","استاتیک");
        dictionary.put("void","خالی");
        dictionary.put("new","جدید");
        dictionary.put("main","اصلی");
        dictionary.put("args","آرگومانها");

    }
}
