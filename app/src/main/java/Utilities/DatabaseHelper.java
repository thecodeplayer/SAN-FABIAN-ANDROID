package Utilities;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import Models.DictionaryModel;
import Models.DictionarySecondModel;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "dictionary.db";
    public static final String DBLOCATION = "/data/data/com.example.sanfabian/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {

        super(context, DBNAME,null,2);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {

        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public List<DictionaryModel> nounWords(String wordSearch) {
        DictionaryModel dictionaryModel = null;
        List<DictionaryModel> dictionaryModelList = new ArrayList<>();
        openDatabase();

        String[] args = {"%" + wordSearch + "%"};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWord WHERE wordClassification = 'noun' AND engWord LIKE ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionaryModel = new DictionaryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionaryModelList;
    }

    public List<DictionaryModel> adjectiveWords(String wordSearch) {
        DictionaryModel dictionaryModel = null;
        List<DictionaryModel> dictionaryModelList = new ArrayList<>();
        openDatabase();

        String[] args = {"%" + wordSearch + "%"};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWord WHERE wordClassification = 'adjective' AND engWord LIKE ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionaryModel = new DictionaryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionaryModelList;
    }

    public List<DictionaryModel> pronounWords(String wordSearch) {
        DictionaryModel dictionaryModel = null;
        List<DictionaryModel> dictionaryModelList = new ArrayList<>();
        openDatabase();

        String[] args = {"%" + wordSearch + "%"};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWord WHERE wordClassification = 'pronoun' AND engWord LIKE ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionaryModel = new DictionaryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionaryModelList;
    }

    public List<DictionaryModel> conjunctionWords(String wordSearch) {
        DictionaryModel dictionaryModel = null;
        List<DictionaryModel> dictionaryModelList = new ArrayList<>();
        openDatabase();

        String[] args = {"%" + wordSearch + "%"};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWord WHERE wordClassification = 'conjunction' AND engWord LIKE ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionaryModel = new DictionaryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionaryModelList;
    }

    public List<DictionaryModel> verbWords(String wordSearch) {
        DictionaryModel dictionaryModel = null;
        List<DictionaryModel> dictionaryModelList = new ArrayList<>();
        openDatabase();

        String[] args = {"%" + wordSearch + "%"};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWord WHERE wordClassification = 'verb' AND engWord LIKE ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionaryModel = new DictionaryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionaryModelList;
    }


    public List<DictionaryModel> adverbWords(String wordSearch) {
        DictionaryModel dictionaryModel = null;
        List<DictionaryModel> dictionaryModelList = new ArrayList<>();
        openDatabase();

        String[] args = {"%" + wordSearch + "%"};
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWord WHERE wordClassification = 'adverb' AND engWord LIKE ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionaryModel = new DictionaryModel(cursor.getString(0), cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionaryModelList;
    }

    public List<DictionarySecondModel> greetingWords() {
        DictionarySecondModel dictionarySecondModel = null;
        List<DictionarySecondModel> dictionarySecondModelList = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWords WHERE wordClassification = 'greeting'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionarySecondModel = new DictionarySecondModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            dictionarySecondModelList.add(dictionarySecondModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionarySecondModelList;
    }

    public List<DictionarySecondModel> noticesWords() {
        DictionarySecondModel dictionarySecondModel = null;
        List<DictionarySecondModel> dictionarySecondModelList = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWords WHERE wordClassification = 'notice'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionarySecondModel = new DictionarySecondModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            dictionarySecondModelList.add(dictionarySecondModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionarySecondModelList;
    }

    public List<DictionarySecondModel> commonWords() {
        DictionarySecondModel dictionarySecondModel = null;
        List<DictionarySecondModel> dictionarySecondModelList = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWords WHERE wordClassification = 'common'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionarySecondModel = new DictionarySecondModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            dictionarySecondModelList.add(dictionarySecondModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionarySecondModelList;
    }

    public List<DictionarySecondModel> prepositionWords() {
        DictionarySecondModel dictionarySecondModel = null;
        List<DictionarySecondModel> dictionarySecondModelList = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM tbWords WHERE wordClassification = 'preposition'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionarySecondModel = new DictionarySecondModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            dictionarySecondModelList.add(dictionarySecondModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        return dictionarySecondModelList;
    }

}
