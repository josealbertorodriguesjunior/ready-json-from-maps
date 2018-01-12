/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author junior
 */
public class JSONParse {
    private static String readAll(Reader rd) throws IOException {
        
        StringBuilder sb = new StringBuilder();
        
        int cp;
        
        while ((cp = rd.read()) != -1) {
        
            sb.append((char) cp);
        
        }
        
        return sb.toString();
    
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    
        InputStream  is = new URL(url).openStream();
        
        try {
        
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            
            String jsonText = readAll(rd);
            
            JSONObject json = new JSONObject(jsonText);
            
            return json;
        
        } finally {
        
            is.close();
        
        }
    }
    
    public void retornaJson() throws JSONException, IOException{
        
        String rua = null;
        
        String bairro = null;
        
        String cidade = null;
        
        String uf = null;
        
        String cep = null;
        
        int numero = 0;
        
        String endereco = JOptionPane.showInputDialog("Endereço");
        
        JSONObject json = readJsonFromUrl("https://maps.googleapis.com/maps/api/geocode/json?address="+ endereco.replaceAll(" ", "%20")+"&key=AIzaSyDf4-t6s3_Zz1I2X45HAGaVFDj9XBFnPyE");
        
        JSONArray jArray = json.getJSONArray("results");
        
        for (int i = 0; i < jArray.length(); i++) {
        
            JSONObject e = jArray.getJSONObject(i);
            
            JSONArray a = e.getJSONArray("address_components");
            
            for (int j = 0; j < a.length(); j++) {
                
                JSONObject types = a.getJSONObject(j);
                
                //System.out.println(types + " " + j);
                
                if (types.get("types").toString().contains("route")) {
                
                    rua = types.get("long_name").toString();
                    
                    System.out.println(rua);
                
                }
                
                if (types.get("types").toString().contains("sublocality_level_1")) {
                
                    bairro = types.get("long_name").toString();
                    
                    System.out.println(bairro);
                
                }
                
                if (types.get("types").toString().contains("administrative_area_level_2")) {
                
                    cidade = types.get("long_name").toString();
                    
                    System.out.println(cidade);
                
                }
                
                if (types.get("types").toString().contains("administrative_area_level_1")) {
                
                    uf = types.get("long_name").toString();
                    
                    System.out.println(uf);
                
                }
                
                if (types.get("types").toString().contains("postal_code")) {
                
                    cep = types.get("long_name").toString();
                    
                    System.out.println(cep);
                
                }
                
                if (types.get("types").toString().contains("street_number")) {
                
                    numero = Integer.parseInt(types.get("long_name").toString());
                    
                    System.out.println(numero);
                
                }
                
            }
        }
    }
    
    public static void main(String[] args) throws IOException, JSONException {
        JSONParse json = new JSONParse();
        json.retornaJson();
    }
}
