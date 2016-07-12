package com.andcup.android.app.info.changer.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.text.ClipboardManager;
import android.util.Log;
import android.widget.CheckBox;

import com.andcup.android.app.info.changer.EditorApplication;
import com.andcup.android.app.info.changer.R;
import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.data.DevHook;
import com.andcup.android.app.info.changer.model.EditorItem;
import com.andcup.android.app.info.changer.rxfileobserver.FileEvent;
import com.andcup.android.app.info.changer.rxfileobserver.RxFileObserver;

import org.apache.http.util.EncodingUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Observable;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/2/23.
 */
public class AndroidUtils {
    public static String mFilePath= Environment.getExternalStorageDirectory()+"/IMEI.txt";
    public static String randomModel(Context context){
        String modelList = context.getResources().getString(R.string.models);
        String[] modelArray = modelList.split("\\|");
        Random rnd = new Random();
        int index = rnd.nextInt(modelArray.length);
        if(index == modelArray.length){
            index--;
        }
        return modelArray[index].trim();
    }

    public static String randomImei(int n) {
        String res = "";
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            res = res + rnd.nextInt(10);
        }
        return res;
    }

    public static String randomPhone() {
        /**  */
        String head[] = {"+8613", "+8615", "+8617", "+8618", "+8616"};
        Random rnd = new Random();
        String res = head[rnd.nextInt(head.length)];
        for (int i = 0; i < 9; i++) {
            res = res + rnd.nextInt(10);
        }
        return res;
    }

    public static String readFiles(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getString("files", "");
    }

    public static void saveFiles(Context context, String files) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putString("files", files);
        pre.commit();
    }

    public static String readDataFiles(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getString("filesdata", "");
    }

    public static void saveDataFiles(Context context, String files) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putString("filesdata", files);
        pre.commit();
    }

    public static String readModel(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getString(DevHook.model, randomModel(context));
    }

    public static void saveModel(Context context, String model) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putString(DevHook.model, model);
        pre.commit();
    }

    public static String readPhone(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return  sh.getString(DevHook.number, randomPhone());
    }

    public static void saveOtherDev(Context context){
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putString(DevHook.imsi, randomImei(15));
        pre.putString(DevHook.simserial, randomImei(20));
        pre.putString(DevHook.wifimac, randomMac());
        pre.putString(DevHook.bluemac, randomMac1());
        pre.putString(DevHook.androidid, randomABC(16));
        pre.putString(DevHook.serial, randomImei(19));
        pre.putString(DevHook.wifissid,randomSSID(8));
        pre.putString(DevHook.wifibssid,randomMac());
        pre.commit();
    }

    private static String randomMac() {
        String chars = "abcde0123456789";
        String res = "";
        Random rnd = new Random();
        int leng = chars.length();
        for (int i = 0; i < 17; i++) {
            if (i % 3 == 2) {
                res = res + ":";
            } else {
                res = res + chars.charAt(rnd.nextInt(leng));
            }

        }
        return res;
    }

    private static String randomMac1() {
        String chars = "ABCDE0123456789";
        String res = "";
        Random rnd = new Random();
        int leng = chars.length();
        for (int i = 0; i < 17; i++) {
            if (i % 3 == 2) {
                res = res + ":";
            } else {
                res = res + chars.charAt(rnd.nextInt(leng));
            }

        }
        return res;
    }

    private static String randomABC(int n) {
        String chars = "abcde0123456789";
        String res = "";
        Random rnd = new Random();
        int leng = chars.length();
        for (int i = 0; i < n; i++) {
            res = res + chars.charAt(rnd.nextInt(leng));

        }
        return res;
    }

    private static String randomSSID(int n){
        String chars = "abcdeifABCDEIF0123456789";
        String res = "";
        Random rnd = new Random();
        int leng = chars.length();
        for (int i = 0; i < n; i++) {
            res = res + chars.charAt(rnd.nextInt(leng));

        }
        return res;
    }

    public static void savePhone(Context context, String phone) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putString(DevHook.number, phone);
        pre.commit();
    }

    public static String readBrand(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return  sh.getString(DevHook.brand, "");
    }

    public static void saveBrand(Context context, String brand) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putString(DevHook.brand, brand);
        pre.commit();
    }

    public static String readImei(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getString(DevHook.imei, randomImei(15));
    }

    public static void saveImei(Context context, String imei) {
        try {
            SharedPreferences sh = context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor pre = sh.edit();
            pre.putString(DevHook.imei, imei);
            pre.commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            return;
        }
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File subFile :files){
                deleteFile(subFile.getAbsolutePath());
            }
        }
        Log.e(AndroidUtils.class.getName(), "----> delete file : " + file.getAbsolutePath());
        file.delete();
    }

    public static void CreateFile(){
        File file =new File(mFilePath);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (Exception e){
            }
        }
    }

    public static String readFile(){
        String res="";
        try{
            FileInputStream fin = new FileInputStream(mFilePath);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
//            res=fileStr.split("\\n")[0];
//            dealWithData(fileStr);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static void  dealWithData(String data){
        String [] fileStr=data.split("\\n");
        String str="";
        for(int i=1;i<fileStr.length;i++){
            str+=fileStr[i];
            if(i==fileStr.length-1){

            }else {
                str+="\n";
            }

        }
        writeFile(str,false);
    }

    public static void  writeFile(String writr_str,boolean add){
        try{
            FileWriter fout= new FileWriter(mFilePath,add);

            fout.write(writr_str);
            if(add){
                fout.write("\r\n");
            }else {

            }

            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Boolean readData(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getBoolean("data", false);
    }

    public static void saveData(Context context, Boolean data) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putBoolean("data", data);
        pre.commit();
    }

    public static Boolean readDatas(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getBoolean("datas", false);
    }

    public static void saveDatas(Context context, Boolean data) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putBoolean("datas", data);
        pre.commit();
    }

    public static Boolean readData2(Context context) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getBoolean("data2", false);
    }

    public static void saveData2(Context context, Boolean data) {
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putBoolean("data2", data);
        pre.commit();
    }

    public static String readDataName(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("dataname", "");
    }
    public static void saveDataName(Context context,String dataName){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("dataname", dataName);
        pre.commit();
    }

    public static String readDataNames(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("datanames","");
    }
    public static void saveDataNames(Context context,String dataName){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("datanames",dataName);
        pre.commit();
    }

    public static String readDataName2(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("dataname2","");
    }
    public static void saveDataName2(Context context,String dataName){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("dataname2",dataName);
        pre.commit();
    }

    public static String readPageName(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("pageName","");
    }
    public static void savePageName(Context context,String dataName){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("pageName",dataName);
        pre.commit();
    }

    public static String readPageNames(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("pageNames","");
    }
    public static void savePageNames(Context context,String dataName){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("pageNames",dataName);
        pre.commit();
    }

    public static String readPageName2(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("pageName2","");
    }
    public static void savePageName2(Context context,String dataName){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("pageName2",dataName);
        pre.commit();
    }

    public static void saveDeleteftles(Context context,Boolean deleteftles){
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putBoolean("deleteftles", deleteftles);
        pre.commit();
    }

    public static Boolean readDeleteftles(Context context){
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getBoolean("deleteftles",false);
    }

    public static void saveFolder(Context context,Boolean folder){
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putBoolean("folder", folder);
        pre.commit();
    }

    public static Boolean readFolder(Context context){
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getBoolean("folder",false);
    }

    public static void saveDataFolder(Context context,Boolean folder){
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre = sh.edit();
        pre.putBoolean("folderdata", folder);
        pre.commit();
    }

    public static Boolean readDataFolder(Context context){
        SharedPreferences sh = context.getSharedPreferences(DevHook.prefs, Context.MODE_WORLD_READABLE);
        return sh.getBoolean("folderdata",false);
    }

    public static void saveHistoricalRecord(Context context,Boolean historicalRecord){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putBoolean("historicalRecord",historicalRecord);
        pre.commit();
    }

    public static Boolean readHistoricalRecord(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getBoolean("historicalRecord",false);
    }

    public static void savePackageName(Context context,String packageName){
        SharedPreferences sh= context.getSharedPreferences(DevHook.prefs,context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("packageName", packageName);
        pre.commit();
    }
    public static String readPackageName(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("packageName", "");
    }

    public static void savePackageNames(Context context,String packageName){
        SharedPreferences sh= context.getSharedPreferences(DevHook.prefs,context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("packageNames",packageName);
        pre.commit();
    }
    public static String readPackageNames(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("packageNames","");
    }

    public static void savePackageName2(Context context,String packageName){
        SharedPreferences sh= context.getSharedPreferences(DevHook.prefs,context.MODE_WORLD_READABLE);
        SharedPreferences.Editor pre=sh.edit();
        pre.putString("packageName2",packageName);
        pre.commit();
    }
    public static String readPackageName2(Context context){
        SharedPreferences sh=context.getSharedPreferences(DevHook.prefs,Context.MODE_WORLD_READABLE);
        return sh.getString("packageName2","");
    }

    public static void OpenMonitor(){
        File sdCard = Environment.getExternalStorageDirectory();
        Observable<FileEvent> sdCardFileEvents = RxFileObserver.create(sdCard);
        sdCardFileEvents.subscribe(fileEvent -> {
            Log.i("用户操作", fileEvent.toString());
        });
    }

    public static boolean copy(String content, Context context){
        try{
            ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content.trim());
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public static String getCopy(Context context){
        try{
            ClipboardManager cmb=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            return cmb.getText().toString();
        }catch (Exception e){

        }
            return " ";
    }

    public static void OpenAPP(Context context,String packages){
        try{
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packages);
            if (intent != null) {
                context.startActivity(intent);
            } else {
            }
        }catch (Exception e){

        }

    }

    public static List<PackageInfo> getInstalledApps(){
        List<PackageInfo> appInfos = new ArrayList<>();
        List<PackageInfo> packages = EditorApplication.INST.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appInfos.add(packages.get(i));//如果非系统应用，则添加至appList
            }
        }
        return appInfos;
    }
}

