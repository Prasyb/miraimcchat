package net.prasyb.miraimcchat.util;

import net.prasyb.miraimcchat.JavaPluginMain;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    //读取json文件
    public static String readJsonFile(String path) {
        String jsonStr = "";
        File jsonFile = new File(path);
        if (!jsonFile.exists()) {
            return jsonStr;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            jsonStr = buffer.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            JavaPluginMain.INSTANCE.getLogger().error(e.getMessage(), e);
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    JavaPluginMain.INSTANCE.getLogger().error(e.getMessage(), e);
                }
            }
        }
    }

    //新建json文件并写入内容
    public static boolean createJsonFile(String jsonStr, String path, String name, boolean isOverwrite) {
        File jsonFile = new File(path + name + ".json");
        jsonFile.getParentFile().mkdirs();
        BufferedWriter writer;
        try {
            if (isOverwrite) {
                if (jsonFile.exists()) {
                    jsonFile.delete();
                    jsonFile.createNewFile();
                }
            } else {
                if (jsonFile.exists()) {
                    return false;
                }
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path + name + ".json",false), StandardCharsets.UTF_8));
            writer.write("");
            writer.write(jsonStr);
            writer.close();
            return true;
        } catch (IOException e) {
            JavaPluginMain.INSTANCE.getLogger().error(e.getMessage(), e);
        }
        return false;
    }

    public static boolean copyFile(File source, File target,boolean isOverwrite) {
        FileInputStream inStream;
        FileOutputStream outStream;
        try {
            if (source.exists()) {
                if (isOverwrite) {
                    inStream = new FileInputStream(source);
                    outStream = new FileOutputStream(target);
                    if (target.exists()) {
                        target.delete();
                        target.createNewFile();
                    }
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = inStream.read(buffer)) != -1)
                        outStream.write(buffer, 0, len);
                } else {
                    if (target.exists()) {
                        return false;
                    }
                    inStream = new FileInputStream(source);
                    outStream = new FileOutputStream(target);
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = inStream.read(buffer)) != -1)
                        outStream.write(buffer, 0, len);
                }
                inStream.close();
                outStream.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            JavaPluginMain.INSTANCE.getLogger().error(e.getMessage(), e);
        }
        return false;
    }
}
