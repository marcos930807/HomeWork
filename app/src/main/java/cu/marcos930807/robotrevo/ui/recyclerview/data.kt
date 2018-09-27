package cu.marcos930807.robotrevo.ui.recyclerview

import android.content.Context
import android.graphics.drawable.Drawable
import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.model.Client
import cu.marcos930807.robotrevo.db.model.Notice
import io.multimoon.colorful.ThemeColor
import java.io.FileDescriptor


/**
 * Created by Marcos on 12/6/2018.
 */
data class optionNotice(
        var title: String,
        var descrip : String,
        var ico  : Int?,
        var notice:Notice
)
data class optionClient(
        var title: String,
        var descrip : String,
        var ico  : Int?,
        var client:Client
)

fun getoptionsfornotice(c : Context,notice:Notice): ArrayList<optionNotice>{
    val arrayoption = ArrayList<optionNotice>()

    arrayoption.add(optionNotice(c.getString(R.string.option_upload),c.getString(R.string.option_upload_desc), R.drawable.ic_file_upload_black_24dp,notice))
    arrayoption.add(optionNotice(c.getString(R.string.option_upload_nopic),c.getString(R.string.option_upload_desc_nopic), R.drawable.ic_broken_image_black_24dp,notice))
  //  arrayoption.add(optionNotice(c.getString(R.string.option_no_captcha),"NO CAPTCHA", R.drawable.ic_file_upload_black_24dp,notice))
    arrayoption.add(optionNotice(c.getString(R.string.option_edit_money),c.getString(R.string.option_edit_money_desc), R.drawable.ic_money,notice))
    arrayoption.add(optionNotice(c.getString(R.string.option_edit),c.getString(R.string.option_edit_desc), R.drawable.ic_edit_black_24dp,notice))
    arrayoption.add(optionNotice(c.getString(R.string.option_delete),c.getString(R.string.option_delete_desc), R.drawable.ic_delete_black_24dp,notice))

    return arrayoption

}

fun getoptionsforClients(c : Context,client: Client): ArrayList<optionClient>{
    val arrayoption = ArrayList<optionClient>()

    arrayoption.add(optionClient(c.getString(R.string.option_new_notice),c.getString(R.string.option_new_notice_desc), R.drawable.ic_notice,client))
    arrayoption.add(optionClient(c.getString(R.string.option_edit_c),c.getString(R.string.option_edit_desc_c), R.drawable.ic_edit_black_24dp,client))
    arrayoption.add(optionClient(c.getString(R.string.option_delete),c.getString(R.string.option_delete_desc), R.drawable.ic_delete_black_24dp,client))

    return arrayoption

}

