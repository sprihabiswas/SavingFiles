package skillbooting.savingfiles;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    String filename="myFile.txt";
    String myText="I am learning Saving Files";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Your app's internal storage directory is specified by your app's package name in a special location of the Android file system.
     * Technically, another app can read your internal files if you set the file mode to be readable.
     * However, the other app would also need to know your app package name and file names.
     * Other apps cannot browse your internal directories and do not have read or write access unless you explicitly set the files
     * to be readable or writable. So as long as you use MODE_PRIVATE for your files on the internal storage, they are never accessible to other apps.
     */
    public void usingFile(View v)
    {
        File file = new File(this.getFilesDir(), filename);
    }

    public void usingFileOutputStream(View v)
    {
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(myText.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void usingCache(View v){

            try {
                //String fileName = Uri.parse(url).getLastPathSegment();
                File.createTempFile(filename, null, this.getCacheDir());}
                catch (IOException e) {
                    // Error while creating file
                }

    }

    public void usingExternalStorage(View v){
        if(isExternalStorageWritable())
            System.out.println("ExternalStorageWritable");

        if(isExternalStorageReadable())
            System.out.println("ExternalStorageReadable");

        try{
            File file = new File(getFolderStorageDir("Training"), filename);
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(myText);
            myOutWriter.close();
            fOut.close();

            Toast.makeText(getApplicationContext(),filename + " saved",Toast.LENGTH_LONG).show();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    /**Remember that getExternalFilesDir() creates a directory inside a directory that is deleted
     * when the user uninstalls your app. If the files you're saving should remain available
     * after the user uninstalls your app—such as when your app is a camera and the user will want to keep the photos—
     * you should instead use getExternalStoragePublicDirectory().*/

    public File getFolderStorageDir(String folderName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), folderName);

        if (!file.mkdirs()) {
            Log.e("getFolderStorageDir", "Directory not created");
        }
        return file;
    }

    public File getFolderStorageDir(Context context, String folderName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), folderName);
        if (!file.mkdirs()) {
            Log.e("getFolderStorageDir", "Directory not created");
        }
        return file;
    }


    /**
     * You aren't required to check the amount of available space before you save your file.
     * You can instead try writing the file right away, then catch an IOException if one occurs.
     * You may need to do this if you don't know exactly how much space you need.
     * For example, if you change the file's encoding before you save it by converting a PNG image to JPEG, you won't know the file's size beforehand.
     */
    public void querySpace(View v){

        File file = new File(this.getFolderStorageDir("Training"), filename);
        System.out.println(file.getFreeSpace());
        System.out.println(file.getTotalSpace());
    }



    /** you should manually delete all cached files created with getCacheDir()
     * on a regular basis and also regularly delete other files you no longer need.*/
    public void deleteMyFile(View v){
        File file = new File(getFolderStorageDir("Training"), filename);
        if(file.delete())
            Toast.makeText(MainActivity.this, "File deleted from external storage!", Toast.LENGTH_SHORT).show();
        //If the file is saved on internal storage, you can also ask the Context to locate and delete a file by calling deleteFile():
        if(this.deleteFile(filename))
            Toast.makeText(MainActivity.this, "File deleted from internal storage!", Toast.LENGTH_SHORT).show();

    }
}
