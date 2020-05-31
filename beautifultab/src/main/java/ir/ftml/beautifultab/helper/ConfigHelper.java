package ir.ftml.beautifultab.helper;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.DimenRes;

public class ConfigHelper {

  @SuppressWarnings("deprecation")
  private static int[] getScreenSize(Context context) {
    int width, height;
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      Point size = new Point();
      windowManager.getDefaultDisplay().getSize(size);
      width = size.x;
      height = size.y;
    } else {
      Display display = windowManager.getDefaultDisplay();
      width = display.getWidth();
      height = display.getHeight();
    }
    Log.i("IconicTabBar", "Get screen size!, width= " + width + ", height= " + height);
    return new int[]{width, height};
  }

  public static int getScreenWidth(Context context) {
    return getScreenSize(context)[0];
  }

  public static int getPxFromDimenRes(@DimenRes int dimenRes, Context context) {
    return context.getResources().getDimensionPixelSize(dimenRes);
  }


}
