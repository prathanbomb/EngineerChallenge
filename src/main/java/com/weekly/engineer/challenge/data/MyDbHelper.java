package com.weekly.engineer.challenge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public MyDbHelper(Context context) {
        super(context, DataContract.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_NEWS_TABLE_1 = "CREATE TABLE " + DataContract.PersonalEntry.TABLE_NAME + " (" +
                DataContract.PersonalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataContract.PersonalEntry.COIN + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.HELP_1 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.HELP_2 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.HI_SUB1 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.HI_SUB2 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.HI_SUB3 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.HI_SUB4 + " INTEGER NOT NULL," +

                DataContract.PersonalEntry.GAME_PERFECT + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.GAME_EX + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.GAME_GOOD + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.USE_COIN + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.GAME_CORRECT_PERFECT + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.GAME_STAR + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.GAME_TRY + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.PLAY_GAME + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_GAME_SUB1 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_GAME_SUB2 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_GAME_SUB3 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_GAME_SUB4 + " INTEGER NOT NULL," +

                DataContract.PersonalEntry.Q_TEST_ALL + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_SUB1 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_SUB2 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_SUB3 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_SUB4 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_MORE_SUB1 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_MORE_SUB2 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_MORE_SUB3 + " INTEGER NOT NULL," +
                DataContract.PersonalEntry.Q_TEST_MORE_SUB4 + " INTEGER NOT NULL" +
                " );";

        final String SQL_CREATE_NEWS_TABLE_2 = "CREATE TABLE " + DataContract.HistoryEntry.TABLE_NAME + " (" +
                DataContract.HistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataContract.HistoryEntry.SUBJECT + " TEXT NOT NULL," +
                DataContract.HistoryEntry.CORRECT + " INTEGER NOT NULL" +
                " );";

        final String SQL_CREATE_NEWS_TABLE_3 = "CREATE TABLE " + DataContract.AchievementEntry.TABLE_NAME + " (" +
                DataContract.AchievementEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DataContract.AchievementEntry.NAME + " TEXT NOT NULL," +
                DataContract.AchievementEntry.CONDITION + " INTEGER NOT NULL," +
                DataContract.AchievementEntry.GOAL + " INTEGER NOT NULL," +
                DataContract.AchievementEntry.DETAIL + " TEXT NOT NULL," +
                DataContract.AchievementEntry.TYPE + " INTEGER NOT NULL," +
                DataContract.AchievementEntry.HAVE + " INTEGER NOT NULL," +
                DataContract.AchievementEntry.IMG + " INTEGER NOT NULL" +
                " );";

        db.execSQL(SQL_CREATE_NEWS_TABLE_1);
        db.execSQL(SQL_CREATE_NEWS_TABLE_2);
        db.execSQL(SQL_CREATE_NEWS_TABLE_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.PersonalEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.HistoryEntry.TABLE_NAME);
        onCreate(db);
    }

    public void deleteall(String table) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(table, null, null);
        } catch (Exception e) {

        } finally {
            db.close();
        }
    }

    public long update(String table, String col, int value) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues val = new ContentValues();
            val.put(col, value);
            return db.update(table, val, "", null);
        } catch (Exception e) {
            return -1;
        } finally {
            db.close();
        }
    }

    public long update(String table, String col, int id, int value) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues val = new ContentValues();
            val.put(col, value);
            return db.update(table, val, "_id=" + id, null);
        } catch (Exception e) {
            return -1;
        } finally {
            db.close();
        }
    }

    public long init() {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues val = new ContentValues();
            val.put(DataContract.PersonalEntry.COIN, 0);
            val.put(DataContract.PersonalEntry.HELP_1, 0);
            val.put(DataContract.PersonalEntry.HELP_2, 0);
            val.put(DataContract.PersonalEntry.HI_SUB1, 0);
            val.put(DataContract.PersonalEntry.HI_SUB2, 0);
            val.put(DataContract.PersonalEntry.HI_SUB3, 0);
            val.put(DataContract.PersonalEntry.HI_SUB4, 0);

            val.put(DataContract.PersonalEntry.GAME_PERFECT, 0);
            val.put(DataContract.PersonalEntry.GAME_EX, 0);
            val.put(DataContract.PersonalEntry.GAME_GOOD, 0);
            val.put(DataContract.PersonalEntry.USE_COIN, 0);
            val.put(DataContract.PersonalEntry.GAME_CORRECT_PERFECT, 0);
            val.put(DataContract.PersonalEntry.GAME_STAR, 0);
            val.put(DataContract.PersonalEntry.GAME_TRY, 0);
            val.put(DataContract.PersonalEntry.PLAY_GAME, 0);
            val.put(DataContract.PersonalEntry.Q_GAME_SUB1, 0);
            val.put(DataContract.PersonalEntry.Q_GAME_SUB2, 0);
            val.put(DataContract.PersonalEntry.Q_GAME_SUB3, 0);
            val.put(DataContract.PersonalEntry.Q_GAME_SUB4, 0);

            val.put(DataContract.PersonalEntry.Q_TEST_ALL, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_SUB1, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_SUB2, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_SUB3, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_SUB4, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_MORE_SUB1, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_MORE_SUB2, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_MORE_SUB3, 0);
            val.put(DataContract.PersonalEntry.Q_TEST_MORE_SUB4, 0);
            return db.insert(DataContract.PersonalEntry.TABLE_NAME, null, val);
        } catch (Exception e) {
            return -1;
        } finally {
            db.close();
        }
    }
    /*public long addDB(String tableName, String column, int value) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues val = new ContentValues();
            val.put(column, value);

            return db.insert(tableName, null, val);
        } catch (Exception e) {
            return -1;
        } finally {
            db.close();
        }
    }*/
    //addDB("personal", "o", 99);

    public void init(AchievementData[] data) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            for (int i = 0; i < data.length; i++) {
                ContentValues val = new ContentValues();
                val.put(DataContract.AchievementEntry.NAME, data[i].getName());
                val.put(DataContract.AchievementEntry.CONDITION, data[i].getCondition());
                val.put(DataContract.AchievementEntry.GOAL, data[i].getGoal());
                val.put(DataContract.AchievementEntry.DETAIL, data[i].getDetail());
                val.put(DataContract.AchievementEntry.TYPE, (data[i].isType() ? 1 : 0));
                val.put(DataContract.AchievementEntry.HAVE, (data[i].isHave() ? 1 : 0));
                val.put(DataContract.AchievementEntry.IMG, data[i].getImgID());
                db.insert(DataContract.AchievementEntry.TABLE_NAME, null, val);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public int[] selectGame() {

        try {
            int[] data = new int[3];
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT " + DataContract.PersonalEntry.COIN + ", " + DataContract.PersonalEntry.HELP_1 + ", " + DataContract.PersonalEntry.HELP_2 + " FROM " + DataContract.PersonalEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    data[0] = cursor.getInt(0);
                    data[1] = cursor.getInt(1);
                    data[2] = cursor.getInt(2);
                }
            }
            cursor.close();
            db.close();

            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public int[] selectTest() {

        try {
            int[] data = new int[4];
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT " + DataContract.PersonalEntry.HI_SUB1 + ", " + DataContract.PersonalEntry.HI_SUB2 + ", " + DataContract.PersonalEntry.HI_SUB3 + ", " + DataContract.PersonalEntry.HI_SUB4 + " FROM " + DataContract.PersonalEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    data[0] = cursor.getInt(0);
                    data[1] = cursor.getInt(1);
                    data[2] = cursor.getInt(2);
                    data[3] = cursor.getInt(3);
                }
            }
            cursor.close();
            db.close();

            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public int select(String table, String col) {

        try {
            int data = 0;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT " + col + " FROM " + table;
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    data = cursor.getInt(0);
                }
            }
            cursor.close();
            db.close();

            return data;
        } catch (Exception e) {
            return -1;
        }
    }

    public long insert(HistoryData data) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues val = new ContentValues();
            val.put(DataContract.HistoryEntry.SUBJECT, data.getSubject());
            val.put(DataContract.HistoryEntry.CORRECT, data.getScore());
            return db.insert(DataContract.HistoryEntry.TABLE_NAME, null, val);
        } catch (Exception e) {
            return -1;
        } finally {
            db.close();
        }
    }

    public ArrayList<AchievementData> selectAllAchData() {

        try {
            ArrayList<AchievementData> data = new ArrayList<AchievementData>();
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT * FROM " + DataContract.AchievementEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        data.add(new AchievementData(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(6), cursor.getInt(7)));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();

            return data;
        } catch (Exception e) {
            return new ArrayList<AchievementData>();
        }
    }

    public ArrayList<AchievementData> selectAllAchData(boolean type) {

        try {
            ArrayList<AchievementData> data = new ArrayList<AchievementData>();
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT * FROM " + DataContract.AchievementEntry.TABLE_NAME + " WHERE " + DataContract.AchievementEntry.TYPE + " = " + ((type) ? 1 : 0);
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        data.add(new AchievementData(cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(6), cursor.getInt(7)));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();

            return data;
        } catch (Exception e) {
            return new ArrayList<AchievementData>();
        }
    }

    public ArrayList<HistoryData> selectAllData() {

        try {
            ArrayList<HistoryData> data = new ArrayList<HistoryData>();
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT * FROM " + DataContract.HistoryEntry.TABLE_NAME + " ORDER BY " + DataContract.HistoryEntry._ID + " DESC";
            Cursor cursor = db.rawQuery(strSQL, null);


            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        data.add(new HistoryData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();

            return data;
        } catch (Exception e) {
            return new ArrayList<HistoryData>();
        }
    }

    public ArrayList<HistoryData> selectSubData(String sub) {

        try {
            ArrayList<HistoryData> data = new ArrayList<HistoryData>();
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data

            String strSQL = "SELECT * FROM " + DataContract.HistoryEntry.TABLE_NAME + " WHERE " + DataContract.HistoryEntry.SUBJECT + " = " + '"' + sub + '"' + " ORDER BY " + DataContract.HistoryEntry._ID + " DESC";
            Cursor cursor = db.rawQuery(strSQL, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        data.add(new HistoryData(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();

            return data;
        } catch (Exception e) {
            return null;
        }
    }


}
