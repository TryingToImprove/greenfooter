package greenfooter;

import greenfoot.GreenfootImage;
import greenfoot.UserInfo;
import greenfoot.platforms.GreenfootUtilDelegate;
import java.awt.Component;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreenfootUtilDelegateStandalone implements GreenfootUtilDelegate {

    @Override
    public URL getResource(String relativeFileUri) {

        String prefix = relativeFileUri.substring(relativeFileUri.length() - 4).equals(".wav") ? "sounds/" : "images/";

        try {
            // Greenfoot have hardcoded it image path
            String absoluteFileUri = relativeFileUri.substring(prefix.length());
            File absoluteFile = new File(absoluteFileUri);

            // If the relativeFileUri is a absoluteFileUri, then return that file
            if (absoluteFile.exists()) {
                return absoluteFile.toURI().toURL();
            }

            // We want to have the same splitter
            relativeFileUri = relativeFileUri.replace("/", "\\");

            // Get the current working path
            String path = new File(".").getAbsolutePath();

            // When using `new File(".")` it adds a "." to the end of the path
            path = path.substring(0, path.length() - 1);

            // Get the file uri
            String fileUri = path + relativeFileUri;

            // Get the file
            File file = new File(fileUri);

            // If the file exists then return it url
            if (file.exists()) {
                return file.toURI().toURL();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(GreenfootUtilDelegateStandalone.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Iterable<String> getSoundFiles() {
        return new ArrayList<>();
    }

    @Override
    public String getGreenfootLogoPath() {
        return "logo.jpg";
    }

    @Override
    public void displayMessage(Component cmpnt, String string) {
        System.out.println(string);
    }

    @Override
    public boolean isStorageSupported() {
        return false;
    }

    @Override
    public UserInfo getCurrentUserInfo() {
        return null;
    }

    @Override
    public boolean storeCurrentUserInfo(UserInfo ui) {
        return false;
    }

    @Override
    public List<UserInfo> getTopUserInfo(int i) {
        return null;
    }

    @Override
    public GreenfootImage getUserImage(String string) {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public List<UserInfo> getNearbyUserInfo(int i) {
        return null;
    }

}
