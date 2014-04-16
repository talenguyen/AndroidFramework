package com.talenguyen.androidframework.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import android.content.Context;
import android.os.Environment;


public class FileUtils {

    private FileUtils() {
    }

    public static void mkdirs(String dir) {
        final File fileDir = new File(dir);
        if (fileDir.exists()) {
            return;
        }
        fileDir.mkdirs();
    }

    /**
     * Returns a boolean indicating whether this file can be found on the underlying file system.
     * @param filePath The file to check
     * @return <code>true</code> if this file exists, <code>false</code> otherwise.
     */
    public static boolean exists(String filePath) {
        return new File(filePath).exists();
    }

    public static String getAvailableStorageDir(Context context) {
        if (FileUtils.isExternalStorageWriteable()) {
            return Environment.getExternalStorageDirectory().getPath();
        } else {
            return context.getFilesDir().getPath();
        }
    }

    public static boolean isExternalStorageWriteable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment
                .getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /**
     * Unzip the zip file.
     *
     * @param zipFile
     *            The file to unzip.
     * @param outputPath
     *            the output path or <code>null</code> for current path
     * @throws java.util.zip.ZipException
     * @throws java.io.IOException
     */
    public static String unZip(String zipFile, String outputPath,
                             boolean deletedFileSource) throws ZipException, IOException {
        int BUFFER = 2048; // 2 Kbs
        File file = new File(zipFile);

        final String temp;
        final int index = file.getAbsolutePath().lastIndexOf(".");
        if (index > 0) {
            temp = file.getPath().substring(0, index) + "_temp";
        } else {
            temp = file.getPath() + "_temp";
        }

        ZipFile zip = null;

        try {
            zip = new ZipFile(file);

            Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements()) {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                File destFile = new File(temp, entry.getName());

                if (entry.isDirectory()) {
                    mkdirs(destFile.getAbsolutePath());
                } else {
                    mkdirs(destFile.getParent());

                    BufferedInputStream is = new BufferedInputStream(
                            zip.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos,
                            BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zip != null) {
                zip.close();
            }
        }

        final File tempFile = new File(temp);
        if (outputPath == null || outputPath.equals(zipFile)) {
            outputPath = temp.replace("temp", "");
            tempFile.renameTo(new File(outputPath));
        } else {
            tempFile.renameTo(new File(outputPath));
        }

        if (deletedFileSource) {
            rm(zipFile);
        }
        return outputPath;
    }

    /**
     * Remove file or directory by given file path.
     * @param filePath
     */
    public static void rm(String filePath) {
        final File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                String[] list = file.list();
                if (list != null && list.length > 0) {
                    for (String child : list) {
                        rm(child);
                    }
                }
            }
            file.delete();
        }
    }

    public static void write(InputStream inStream, String output)
            throws IOException {
        final File outputFile = new File(output);
        final File parent = outputFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        FileOutputStream outStream = new FileOutputStream(outputFile);
        byte[] buf = new byte[1024];
        int l;
        while ((l = inStream.read(buf)) >= 0) {
            outStream.write(buf, 0, l);
        }
        inStream.close();
        outStream.flush();
        outStream.close();
    }

    public static void write(byte[] data, String output) throws IOException {
        final File outputFile = new File(output);
        final File parent = outputFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        FileOutputStream os = new FileOutputStream(outputFile);
        os.write(data);
        os.flush();
        os.close();
    }

    public static String readTextFile(String filePath) {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {

            String currentLine;

            br = new BufferedReader(new FileReader(filePath));

            while ((currentLine = br.readLine()) != null) {
                sb.append(currentLine + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static byte[] read(String filePath) {
        try {
            FileInputStream in = new FileInputStream(new File(filePath));
            return getByteArray(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getByteArray(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int l;
        try {
            while ((l = in.read(buf)) >= 0) {
                baos.write(buf, 0, l);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (in != null) {
                in.close();
            }
            baos.close();
        }
    }

    public static void append(String filePath, String text) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter(filePath, true)));
            out.println(text);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String standardDiskName(String inputName) {
        if (inputName == null || inputName.isEmpty()) {
            return "";
        } else if (inputName.length() == 1) {
            return inputName;
        }
        String pattern = "[0-9a-zA-Z]";
        final char[] characters = inputName.toCharArray();
        final StringBuilder standardBuilder = new StringBuilder();
        for (int i = 0; i < characters.length; i++) {
            if (String.valueOf(characters[i]).matches(pattern)) {
                standardBuilder.append(characters[i]);
            }
        }
        return standardBuilder.toString();
    }

}