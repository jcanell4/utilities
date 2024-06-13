package org.elsquatrecaps.utilities.ocr.conversor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josepcanellas
 */
public class DocumentAIJson2textBlocList {
    JSONObject jObject;
    List<String> strList;
    
    public void read(String fileName){
        try {
            String text = new String(Files.readAllBytes(Paths.get(fileName)));
            jObject = new JSONObject(text);
        } catch (IOException ex) {
            Logger.getLogger(DocumentAIJson2textBlocList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void convert(){
        strList = new ArrayList<>();
        int len = jObject.getJSONArray("pages").length();
        int posStart=0;
        for(int i=0; i<len; i++){
            JSONArray blocks = ((JSONObject) jObject.getJSONArray("pages").get(i)).getJSONArray("blocks");
            for(int j=0; j<blocks.length(); j++){
                int posEnd = ((JSONObject) blocks.get(j)).getJSONObject("layout")
                        .getJSONObject("textAnchor").getJSONArray("textSegments").getJSONObject(0).getInt("endIndex");
                String v = jObject.getString("text").substring(posStart, posEnd);
                strList.add(v);
                posStart=posEnd;
            }
        }       
    }
    
    public void save(String fileName){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for(int i=0; i<strList.size(); i++){
               writer.append(String.valueOf(i));
               writer.append(",\"");
               writer.append(strList.get(i));
               writer.append("\"");
               writer.append("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(DocumentAIJson2textBlocList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                                                                                                                                                             
      
}
