package cu.marcos930807.robotrevo.db.sql

import android.provider.BaseColumns

object DBContract {
 /* Inner class that defines the table contents */
    class NoticeEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "NoticeTable"
            val COLUMN_ID = "_id"
            val COLUMN_CATEGORY = "category"
            val COLUMN_TITLE = "title"
            val COLUMN_BODY = "body"
            val COLUMN_PRICE = "price"
            val COLUMN_MAIL = "mail"
            val COLUMN_OWNERNAME = "ownername"
            val COLUMN_NUMBER = "number"
            val COLUMN_PHOTOA = "photoa"
            val COLUMN_PHOTOB = "photob"
            val COLUMN_PHOTOC = "photoc"
            val COLUMN_CLIENTID = "client_id"
        }
    }

    class ClientEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "ClientTable"
            val COLUMN_ID = "_id"
            val COLUMN_MAIL = "mail"
            val COLUMN_NAME = "ownername"
            val COLUMN_PHONE = "phone"

        }
    }


}