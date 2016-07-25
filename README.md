# SavingFiles
How to perform basic file-related tasks in your app (Part of the series Mastering Android)

# Internal storage:

* It's always available.
* Files saved here are accessible by only your app by default.
* When the user uninstalls your app, the system removes all your app's files from internal storage.
* Internal storage is best when you want to be sure that neither the user nor other apps can access your files.

# External storage:

* It's not always available, because the user can mount the external storage as USB storage and in some cases remove it from the device.
* It's world-readable, so files saved here may be read outside of your control.
* When the user uninstalls your app, the system removes your app's files from here only if you save them in the directory from getExternalFilesDir().
* External storage is the best place for files that don't require access restrictions and for files that you want to share with other apps or allow the user to access with a computer.

# Note:

* Although apps are installed onto the internal storage by default, you can specify the android:installLocation attribute in your manifest so your app may be installed on external storage. Users appreciate this option when the APK size is very large and they have an external storage space that's larger than the internal storage.
