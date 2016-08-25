package com.somniatores.kriszplusz.managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by locisvv on 20.08.2016.
 */
public class SongsDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "";
    private static String DB_NAME = "krisz_plusz.db";
    private static SongsDatabaseHelper instance;
    private SQLiteDatabase dataBase;
    private final Context context;

    private SongsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/com.somniatores.kriszplusz" + "/databases/";
        }
        this.context = context;

        createDataBase();
    }

    public static synchronized SongsDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SongsDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    public void createDataBase() {
        boolean dataBaseExist = checkDataBase();
        if (!dataBaseExist) {
            try {
                copyDataBase();
            } catch (IOException mIOException) {
                throw new RuntimeException("ErrorCopyingDataBase" + mIOException);
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = context.getAssets().open(DB_NAME);

        this.getReadableDatabase();
        this.close();

        OutputStream mOutput = new FileOutputStream(context.getDatabasePath(DB_NAME));
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

//    public boolean openDataBase() {
//        String mPath = DB_PATH + DB_NAME;
//        dataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);
//        return dataBase != null;
//    }

    @Override
    public synchronized void close() {
        if (dataBase != null)
            dataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
