package com.andcup.android.app.info.changer.tools;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.andcup.android.app.info.changer.data.BundleKey;
import com.andcup.android.app.info.changer.model.EditorItem;
import com.chrisplus.rootmanager.RootManager;

import java.io.File;
import java.math.BigDecimal;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

/**
 * Created by Amos on 2015/9/9.
 */
public class CacheManager {

    private static String DIR_CACHE = "/cache/";
    private static String DIR_PREF = "/shared_prefs/";
    private static String DIR_DATA_BASE = "/databases/";
    private static String DIR_FILES = "/files/";
    private static String DIR_MicroMsg = "/MicroMsg/";

    public static void cleanAppData2(String packageName){
        String path = Environment.getDataDirectory() + "/data/" + packageName;
        RootManager.getInstance().runCommand("rm -rf " + path + DIR_CACHE);
        RootManager.getInstance().runCommand("rm -rf " + path + DIR_PREF);
        RootManager.getInstance().runCommand("rm -rf " + path + DIR_DATA_BASE);
        RootManager.getInstance().runCommand("rm -rf " + path + DIR_FILES);
        RootManager.getInstance().runCommand("rm -rf " + path + DIR_MicroMsg);
        SuUtils.kill(packageName);
//        AndroidUtils.OpenAPP(context, EditorItem.DATA.mValue.getString(BundleKey.KEY_DATA));
    }
    public static void cleanData(String file){
        String path = Environment.getDataDirectory() + "/data/" + file;
        RootManager.getInstance().runCommand("rm -rf " + path);
    }

    public static void kill(String packageName){
        SuUtils.kill(packageName);
    }


    public static void cleanAppData(String packageName){
        String path = Environment.getDataDirectory() + "/data/" + packageName + "/cache/";
        File appDataFile = new File(path);
        if(appDataFile.exists() && appDataFile.isDirectory()){
            File[] files = appDataFile.listFiles();
            for(File file : files){
                if(file.isDirectory() ){
                    if(!file.getName().equals("lib")){
                        AndroidUtils.deleteFile(file.getAbsolutePath());
                    }
                }else{
                    file.delete();
                }
            }
        }
    }


    /**
     * * (/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * *(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * * SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * * /data/data/com.xxx.xxx/files * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * (/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }
    /**
     * *
     *
     * @param filePath
     * */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * *
     *
     * @param context
     * @param filepath
     */
    public static void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists()) {
            if(directory.isDirectory()){
                for (File item : directory.listFiles()) {
                    if(item.isDirectory()){
                        deleteFilesByDirectory(item);
                    }
                }
            }
            directory.delete();
        }
    }

    //
    //Context.getExternalFilesDir() --> SDCard/Android/data/package/files/dir
    //Context.getExternalCacheDir() --> SDCard/Android/data/package/cache/dir
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                //
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     *
     *
     * @param deleteThisPath
     * @param
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {// Ŀ¼
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + " KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " TB";
    }


    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }
}
