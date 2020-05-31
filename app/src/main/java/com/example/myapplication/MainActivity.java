package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ir.ftml.beautifultab.view.BeautifulTab;
import ir.ftml.beautifultab.view.BeautifulTabBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private BeautifulTabBar iconicTabBar;
  private TextView textDemo;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    initViews();
  }
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void initViews() {
    iconicTabBar = findViewById(R.id.bottom_bar);
    textDemo = findViewById(R.id.text_demo);
    textDemo.setText(R.string.chats);
    iconicTabBar.addTabFromMenu(R.menu.menu_bottom_bar,
      getResources().obtainTypedArray(R.array.tabItemColor),
      getResources().obtainTypedArray(R.array.tabTextColor)
    );
    iconicTabBar.setOnTabSelectedListener(new BeautifulTabBar.OnTabSelectedListener() {
      @Override
      public void onSelected(BeautifulTab tab, int position) {
        Log.d(TAG, "selected tab on= " + position);
        String demoText = "";
        int tabId = tab.getId();
        switch (tabId) {
          case R.id.bottom_chats:
            demoText = getString(R.string.chats);
            break;
          case R.id.bottom_calls:
            demoText = getString(R.string.calls);
            break;
          case R.id.bottom_contacts:
            demoText = getString(R.string.contacts);
            break;
          case R.id.bottom_settings:
            demoText = getString(R.string.settings);
            break;
          default:
            break;
        }
        textDemo.setText(demoText);
      }

      @Override
      public void onUnselected(BeautifulTab tab, int position) {
        Log.d(TAG, "unselected tab on= " + position);
      }
    });
  }
}
