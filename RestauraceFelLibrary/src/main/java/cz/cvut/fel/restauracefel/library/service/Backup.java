package cz.cvut.fel.restauracefel.library.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Lukas Camra
 */
public class Backup {

    public static File createRootFolder(File srcPath, File dstPath){
        if (srcPath.isDirectory()) {
            if (!dstPath.exists()) {
                dstPath.mkdir();
            }
            dstPath = new File(dstPath.getAbsolutePath()+"/"+srcPath.getName());
            if(!dstPath.exists()){
                dstPath.mkdir();
            }
        }
        return dstPath;

    }

    public static void copyDirectory(File srcPath, File dstPath) throws IOException {
        if (srcPath.isDirectory()) {
            if (!dstPath.exists()) {
                dstPath.mkdir();
            }

            String files[] = srcPath.list();
            for (int i = 0; i < files.length; i++) {
                copyDirectory(new File(srcPath, files[i]),
                        new File(dstPath, files[i]));
            }

        } else {

            if (!srcPath.exists()) {
                throw new FileNotFoundException("Databáze nebyla nalezena, kontaktujte prosím administrátor!");
            } else {

                InputStream in = new FileInputStream(srcPath);
                OutputStream out = new FileOutputStream(dstPath);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }
        }
    }
}
