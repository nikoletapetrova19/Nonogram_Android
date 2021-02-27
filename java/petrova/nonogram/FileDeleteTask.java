package petrova.nonogram;

import android.os.AsyncTask;
import android.util.Log;
import java.io.File;


public class FileDeleteTask extends AsyncTask<String, Void, Void> {
    LevelSelectionCustom activity;
    private File mFile = null;

    public FileDeleteTask(LevelSelectionCustom activity, String filename) {
        this.activity = activity;
        Log.d("delete3", filename);
        mFile = new File(activity.getExternalFilesDir("myLevel"), filename);
    }

    @Override
    protected Void doInBackground(String... strings) {
        if (mFile.delete()) {
            Log.d("delete4", mFile.getPath());
        } else {
            Log.d("delete5", mFile.getPath());
        }
        return null;
    }
}

