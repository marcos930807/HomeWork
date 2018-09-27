package cu.marcos930807.robotrevo.ui.utils


import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Patterns


import android.widget.Toast
import com.afollestad.materialdialogs.folderselector.FileChooserDialog
import com.squareup.picasso.Picasso
import com.zhihu.matisse.MimeType
import java.io.File
import java.util.*
import java.util.regex.Pattern


/**
 * Created by Marcos on 12/6/2018.
 */
fun AppCompatActivity.mytoast (body: String){

}

fun Context.getCurrentTime() : String{

    return Date(System.currentTimeMillis()).toString()

}

fun AppCompatActivity.replaceFragment( frameId: Int, fragment : Fragment){
    supportFragmentManager.beginTransaction()
            .replace(frameId, fragment)
            .commit()
}

fun AppCompatActivity.selectFile(mimeType: String?="image/*"){

    FileChooserDialog.Builder(this)
            .initialPath(Environment.getExternalStorageDirectory().path)  // changes initial path, defaults to external storage directory
            .mimeType(mimeType) // Optional MIME type filter
            //    .extensionsFilter(".png", ".jpg") // Optional extension filter, will override mimeType()
            .tag("optional-identifier")
            .goUpLabel("Up") // custom go up label, default label is "..."
            .show(this) // an AppCompatActivity which implements FileCallback
}

fun BitmapfromPath(path:String): Bitmap {

    val img = BitmapFactory.decodeFile(path)
    return img
}


fun String.isValidEmail(): Boolean
        = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

