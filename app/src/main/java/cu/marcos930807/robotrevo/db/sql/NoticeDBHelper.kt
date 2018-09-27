package cu.marcos930807.robotrevo.db.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import cu.marcos930807.robotrevo.db.mappers.convertClientFromDB
import cu.marcos930807.robotrevo.db.mappers.convertFromDB
import cu.marcos930807.robotrevo.db.mappers.createValuesfromClient
import cu.marcos930807.robotrevo.db.mappers.createValuesfromNotice
import cu.marcos930807.robotrevo.db.model.Client
import cu.marcos930807.robotrevo.db.model.Notice


class NoticeDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_CLIENT)
        db?.execSQL(SQL_CREATE_ENTRIES)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db?.execSQL(SQL_DELETE_CLIENT)
        db?.execSQL(SQL_DELETE_ENTRIES)

        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertNotice(notice: Notice): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = createValuesfromNotice(notice)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.NoticeEntry.TABLE_NAME, null, values)

        return true
    }
    @Throws(SQLiteConstraintException::class)
    fun insertClient(client: Client): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = createValuesfromClient(client)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.ClientEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateNotice(notice: Notice): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        val selection = DBContract.NoticeEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(notice.id!!.toString())

        // Create a new map of values, where column names are the keys
       val values = createValuesfromNotice(notice)

        // Update the  row, returning the primary key value of the row
        val newRowId = db.update(DBContract.NoticeEntry.TABLE_NAME,values,selection,selectionArgs)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateClient(client: Client): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        val selection = DBContract.ClientEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(client.id!!.toString())

        // Create a new map of values, where column names are the keys
        val values = createValuesfromClient(client)

        // Update the  row, returning the primary key value of the row
        val newRowId = db.update(DBContract.ClientEntry.TABLE_NAME,values,selection,selectionArgs)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteNotice(noticeid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.NoticeEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(noticeid)
        // Issue SQL statement.
        db.delete(DBContract.NoticeEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }
    @Throws(SQLiteConstraintException::class)
    fun deleteClient(clientid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.ClientEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(clientid)
        // Issue SQL statement.
        db.delete(DBContract.ClientEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }
    fun readNotice(noticeid: String): ArrayList<Notice> {

        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.NoticeEntry.TABLE_NAME + " WHERE " + DBContract.NoticeEntry.COLUMN_ID + "='" + noticeid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        return convertFromDB(cursor)
      }

    fun readClient(clientid: String): ArrayList<Client> {

        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.ClientEntry.TABLE_NAME + " WHERE " + DBContract.ClientEntry.COLUMN_ID + "='" + clientid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        return convertClientFromDB(cursor)
    }


    fun readAllNotice(): ArrayList<Notice> {

        val db = writableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from " + DBContract.NoticeEntry.TABLE_NAME,null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        return convertFromDB(cursor)
    }

    fun readAllNoticefromClientid(clientid:String): ArrayList<Notice> {

        val db = writableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from " + DBContract.NoticeEntry.TABLE_NAME + " WHERE " + DBContract.NoticeEntry.COLUMN_CLIENTID + "='" + clientid + "'", null)

        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        return convertFromDB(cursor)
    }

    fun readAllClients(): ArrayList<Client> {

        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.ClientEntry.TABLE_NAME,null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }
        return convertClientFromDB(cursor)
    }


    companion object {

        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 5
        val DATABASE_NAME = "Notice.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.NoticeEntry.TABLE_NAME + " (" +
                        DBContract.NoticeEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DBContract.NoticeEntry.COLUMN_CATEGORY + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_TITLE + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_BODY + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_PRICE + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_MAIL + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_OWNERNAME + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_PHOTOA + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_PHOTOB + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_PHOTOC + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_NUMBER + " TEXT," +
                        DBContract.NoticeEntry.COLUMN_CLIENTID + " INTEGER," +
                        " FOREIGN KEY ("+DBContract.NoticeEntry.COLUMN_CLIENTID +") REFERENCES " + DBContract.ClientEntry.TABLE_NAME+"("+DBContract.ClientEntry.COLUMN_ID+"))"


        private val SQL_CREATE_CLIENT =
                "CREATE TABLE " + DBContract.ClientEntry.TABLE_NAME + " (" +
                        DBContract.ClientEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DBContract.ClientEntry.COLUMN_MAIL + " TEXT," +
                        DBContract.ClientEntry.COLUMN_NAME + " TEXT," +
                        DBContract.ClientEntry.COLUMN_PHONE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.NoticeEntry.TABLE_NAME
        private val SQL_DELETE_CLIENT = "DROP TABLE IF EXISTS " + DBContract.ClientEntry.TABLE_NAME
    }

}