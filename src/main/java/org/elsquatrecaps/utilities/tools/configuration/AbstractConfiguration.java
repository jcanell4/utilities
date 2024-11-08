package org.elsquatrecaps.utilities.tools.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.elsquatrecaps.utilities.tools.Callback;


/**
 *
 * @author josep
 */
public abstract class AbstractConfiguration implements Configuration{
    protected File initConfigFile=null;
    private final Set<String> attrs = new HashSet<>();

    protected abstract boolean setDefaultArg(String dest, Object val);

    public void parseArgumentsAndConfigure(String[] args) throws IOException {
        parseArguments(args);
        configure();
    }

    private static Properties loadAndGetConfigProperties(String propertiesPath) throws IOException{
        Properties properties = new Properties();
        properties.load(new FileReader(propertiesPath));
        return properties;
    }
    
    private static Properties loadAndGetConfigProperties() throws IOException{
        Properties properties;
        if(Files.exists(Paths.get("init.properties"))){
            properties = loadAndGetConfigProperties("init.properties");
        }else if(Files.exists(Paths.get("config/init.properties"))){
            properties = loadAndGetConfigProperties("config/init.properties");
        } else{
            properties = new Properties();
        } 
        return properties;
    }
    
    public void configure(String propertiesPath) throws IOException{
        AbstractConfiguration args = this;
        Properties properties = loadAndGetConfigProperties(propertiesPath);
        properties.forEach((Object k, Object v) -> {
            boolean b = args.setDefaultArg(String.valueOf(k), v);
            if(!b){
                System.setProperty(String.valueOf(k), (String) v);
            }
        });
    }
    
    public void configure() throws IOException{
        Properties properties;
        AbstractConfiguration args = this;
        if(this.getInitConfigFile()!=null){
            properties = loadAndGetConfigProperties(getInitConfigFile().getAbsolutePath());
        }else{
            properties = loadAndGetConfigProperties();
        }
        properties.forEach((Object k, Object v) -> {
            args.setDefaultArg(String.valueOf(k), v);
        });
    }
    
    
    public abstract void parseArguments(String[] args);

    public File getFile(String val) {
        return getFile(val, "");
    }
    
    private File getFile(String val, String defaultFile) {
        File ret;
        if(val!=null){
            ret = new File(val);
        }else{
            ret = new File(defaultFile);
        }
        return ret; 
    }

    public Boolean getBoolean(String val) {
        return val != null && (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("t") || val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("y") || val.equalsIgnoreCase("si") || val.equalsIgnoreCase("s") || val.equalsIgnoreCase("vertader") || val.equalsIgnoreCase("vertadera") || val.equalsIgnoreCase("v") || val.equalsIgnoreCase("cert") || val.equalsIgnoreCase("certa") || val.equalsIgnoreCase("c"));
    }

    public Integer[][][] getInt3dArray(String val) {
        Integer[][][] ret = null;
        if (val != null) {
            String[] sGroups = val.split("[#*]");
            ret = new Integer[sGroups.length][][];
            Arrays.setAll(ret, (int i) -> {
                String[] sCols = sGroups[i].split("[;|/]");
                Integer[][] itemCols = new Integer[sCols.length][];
                Arrays.setAll(itemCols, (int j) -> {
                    String[] sret = sCols[j].split("[,]");
                    Integer[] itemRet = new Integer[sret.length];
                    Arrays.setAll(itemRet, (int k) -> {
                        return Integer.valueOf(sret[k]);
                    });
                    return itemRet;
                });
                return itemCols;
            });
        }
        return ret;
    }

    public <T> T[] getArray(String val, Callback<Integer, T[]> creator, Callback<String, T> transformer) {
        T[] ret = null;
        if (val != null) {
            val = Pattern.compile("(?:\\[?)(.*?)(?:\\]?)", Pattern.MULTILINE).matcher(val).replaceAll("$1");
            String[] sret = val.trim().split("[,]");
            ret = creator.call(sret.length);
            Arrays.setAll(ret, (int index) -> {
                return transformer.call(sret[index].trim());
            });
        }
        return ret;
    }

    public<T> Map<String, T> getMap(String val, Callback<Void, Map<String, T>> creator, Callback<String, T> transformer) {
        Map<String, T> ret=null;
        if (val != null) {
            val = Pattern.compile("(?:\\{?)(.*?)(?:\\}?)", Pattern.MULTILINE).matcher(val).replaceAll("$1");
            String[] sret = val.trim().split("[,]");
            ret = creator.call(null);
            for(String strEntry: sret){
                String[] aStrEntry = strEntry.split("[:]");
                ret.put(aStrEntry[0], transformer.call(aStrEntry[1].trim()));
            }
        }
        return ret;
    }
 
    public Map<String, String> getStringMap(String val) {
        return getMap(val, (Void param) -> new HashMap<>(), (String param) ->  param.trim());
    }
    
    public String[] getStringArray(String val) {
        return getArray(val, (Integer l) -> new String[l], (String param) -> param.trim());
    }

    public Integer[] getIntArray(String val) {
        return getArray(val, (Integer l) -> new Integer[l], (String param) -> Integer.valueOf(param.trim()));
    }

    protected abstract void updateAttrs();

    public Set<String> getAttrs() {
        return attrs;
    }

    /**
     * @return the initConfigFile
     */
    public File getInitConfigFile() {
        return initConfigFile;
    }
    
    protected void setInitConfigFile(File iniConfigFile) {
        this.initConfigFile = iniConfigFile;                
    }
    
}
