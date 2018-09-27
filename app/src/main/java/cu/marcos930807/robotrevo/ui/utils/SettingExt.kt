package cu.marcos930807.robotrevo.ui.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity


fun mypref(c:Context) : SharedPreferences{
    return  PreferenceManager.getDefaultSharedPreferences(c);
}
fun getName(preferences: SharedPreferences) : String{
    return  preferences.getString("example_text","")
}

fun getDark(preferences: SharedPreferences) : Boolean{
    return  preferences.getBoolean("dark_switch",false)
}

