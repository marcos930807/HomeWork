package cu.marcos930807.robotrevo

import android.app.Application
import com.beardedhen.androidbootstrap.TypefaceProvider

import io.multimoon.colorful.Defaults
import io.multimoon.colorful.ThemeColor
import io.multimoon.colorful.initColorful


class AppT: Application() {
    override fun onCreate() {
        super.onCreate()


     //   Colors.setTheme(PrimaryColor.INDIGO_500, AccentColor.PINK_A200);
      TypefaceProvider.registerDefaultIconSets()
        val defaults:Defaults = Defaults(
                primaryColor = ThemeColor.BLUE,
                accentColor = ThemeColor.RED,
                useDarkTheme = false,
                translucent = false)
        initColorful(this, defaults)
    }
}
