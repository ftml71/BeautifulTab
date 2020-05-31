package ir.ftml.beautifultab.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.MenuRes;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import ir.ftml.beautifultab.R;
import ir.ftml.beautifultab.helper.ConfigHelper;


public class BeautifulTabBar extends FrameLayout implements BeautifulTab.OnTabClickListener {

  public static final int MAX_TAB = 5;
  private static final int SELECTED_NONE = -1;

  private AttributeSet attributeSet;

  private FrameLayout frameTabs;
  private LinearLayout llTabs;

  private List<BeautifulTab> tabs;

  private int selectedTabPosition;
  private int unSelectedTabPosition;

  private int backgroundColor;
  private int tabDefaultColor;
  private int tabSelectedColor;

  private BeautifulTab selectedTab;
  private BeautifulTab unSelectedTab;

  private OnTabSelectedListener onTabSelectedListener;

  public BeautifulTabBar(Context context) {
    super(context);
    inflateView();
  }

  public BeautifulTabBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    attributeSet = attrs;
    inflateView();
  }

  public BeautifulTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    attributeSet = attrs;
    inflateView();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BeautifulTabBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    attributeSet = attrs;
    inflateView();
  }

  private void inflateView() {
    inflate(getContext(), R.layout.layout_tab_bar, this);
    frameTabs = findViewById(R.id.frame_tabs);
    llTabs = findViewById(R.id.ll_tabs);
    initStyle();
  }

  private void initStyle() {
    TypedArray styledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.BeautifulTabBar);
    int menuResId = styledAttributes.getResourceId(R.styleable.BeautifulTabBar_tabFromMenu, -1);
    backgroundColor = styledAttributes.getColor(R.styleable.BeautifulTabBar_barBackground, ContextCompat.getColor(getContext(), R.color.white));
    tabDefaultColor = styledAttributes.getColor(R.styleable.BeautifulTabBar_tabDefaultColor, ContextCompat.getColor(getContext(), R.color.defaultColor));
    tabSelectedColor = styledAttributes.getColor(R.styleable.BeautifulTabBar_tabSelectedColor, ContextCompat.getColor(getContext(), R.color.defaultSelectedColor));

    frameTabs.setBackgroundColor(backgroundColor);
//    if (menuResId != -1) {
//      addTabFromMenu(menuResId,);
//    }

    styledAttributes.recycle();
  }

  public void addTab(@DrawableRes int iconResId, CharSequence text, @ColorRes int bgResId, @ColorRes int textResId) {
    addTab(0, iconResId, null, text, bgResId, textResId);
  }

  public void addTab(int viewId, @DrawableRes int iconResId, CharSequence text, @ColorRes int bgResId, @ColorRes int textResId) {
    addTab(viewId, iconResId, null, text, bgResId, textResId);
  }

  public void addTab(Drawable iconDrawable, CharSequence text, @ColorRes int bgResId, @ColorRes int textResId) {
    addTab(0, 0, iconDrawable, text, bgResId, textResId);
  }

  public void addTab(int viewId, Drawable iconDrawable, CharSequence text, @ColorRes int bgResId, @ColorRes int textResId) {
    addTab(viewId, 0, iconDrawable, text, bgResId, textResId);
  }

  public void addTab(int viewId, Drawable iconDrawable, CharSequence text, @ColorRes int bgResId) {
    addTab(viewId, 0, iconDrawable, text, bgResId, 0);
  }

  public void addTab(int viewId, Drawable iconDrawable, CharSequence text) {
    addTab(viewId, 0, iconDrawable, text, 0, 0);
  }

  private void addTab(int viewId, @DrawableRes int iconResId, Drawable iconDrawable, CharSequence text, @ColorRes int bgResId, @ColorRes int textResId) {
    if (tabs == null) tabs = new ArrayList<>();
    int latestTabsSize = tabs.size();
    if (latestTabsSize > MAX_TAB)
      throw new UnsupportedOperationException("Cannot add more than 5 tabs.");
    BeautifulTab tab = new BeautifulTab(getContext());
    tab.setId(viewId);
    tab.setTabDefaultColor(tabDefaultColor);
    if (textResId != 0)
      tab.setTabSelectedColor(getResources().getColor(textResId));
    else
      tab.setTabSelectedColor(tabSelectedColor);
    if (iconResId == 0 && iconDrawable != null && bgResId != 0) {
      tab.bindData(latestTabsSize, iconDrawable, text, bgResId);
    } else if (iconResId == 0 && iconDrawable != null && bgResId == 0) {
      tab.bindData(latestTabsSize, iconDrawable, text);
    } else if (iconResId != 0 && iconDrawable == null) {
      tab.bindData(latestTabsSize, iconResId, text, bgResId);
    } else {
      throw new NullPointerException("Tab icon was not specified.");
    }
    tab.setOnTabClickListener(this);
    tabs.add(tab);
    llTabs.addView(tab);
    initTabWidth();
    if (latestTabsSize == 0) {
      selectedTabPosition = 0;
      selectedTab = tab;
      tab.setSelected();
    }
  }

  public void addTabFromMenu(@MenuRes int menuRes) {
    addTabFromMenu(menuRes, null, null);
  }

  public void addTabFromMenu(@MenuRes int menuRes, TypedArray textResId) {
    addTabFromMenu(menuRes, null, textResId);
  }


  public void addTabFromMenu(TypedArray bgDrawable,@MenuRes int menuRes) {
    addTabFromMenu(menuRes, bgDrawable, null);
  }

  public void addTabFromMenu(@MenuRes int menuRes, TypedArray bgDrawable, TypedArray textResId) {
    PopupMenu popupMenu = new PopupMenu(getContext(), null);
    Menu menu = popupMenu.getMenu();
    MenuInflater menuInflater = new MenuInflater(getContext());
    menuInflater.inflate(menuRes, menu);
    for (int i = 0; i < menu.size(); i++) {
      MenuItem item = menu.getItem(i);
      if (textResId != null && bgDrawable != null)
        addTab(item.getItemId(), item.getIcon(), item.getTitle().toString(), bgDrawable.getResourceId(i, 0), textResId.getResourceId(i, 0));
      else if (textResId == null&& bgDrawable != null)
        addTab(item.getItemId(), item.getIcon(), item.getTitle().toString(), bgDrawable.getResourceId(i, 0));
      else if (textResId != null&& bgDrawable == null){
        addTab(item.getItemId(), item.getIcon(), item.getTitle().toString(), 0,textResId.getResourceId(i, 0));
      }
      else if ( textResId == null&&bgDrawable == null) {
        addTab(item.getItemId(), item.getIcon(), item.getTitle().toString());

      }
    }
  }

  public BeautifulTab getTab(int position) {
    return tabs.get(position);
  }

  public void setTabBadge(int tabPosition, int badgeCount) {
    getTab(tabPosition).setBadgeCount(badgeCount);
  }

  public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
    this.onTabSelectedListener = onTabSelectedListener;
  }

  @Override
  public void setBackgroundColor(@ColorInt int backgroundColor) {
    this.backgroundColor = backgroundColor;
    frameTabs.setBackgroundColor(backgroundColor);
  }

  public void setTabDefaultColor(@ColorInt int tabDefaultColor) {
    this.tabDefaultColor = tabDefaultColor;
    for (BeautifulTab tab : tabs) {
      tab.setTabDefaultColor(tabDefaultColor);
    }
  }

  public void setTabSelectedColor(@ColorInt int tabSelectedColor) {
    this.tabSelectedColor = tabSelectedColor;
    for (BeautifulTab tab : tabs) {
      tab.setTabSelectedColor(tabSelectedColor);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  public void onTabClick(BeautifulTab tabBottomBar, int position) {
    if (tabBottomBar.equals(selectedTab)) return;
    setUnselectedTab(selectedTab, selectedTabPosition);
    setSelectedTab(tabBottomBar, position);
  }

  public void setSelectedTab(int tabPosition) {
    if (tabs == null || tabs.size() < tabPosition) return;
    onTabClick(tabs.get(tabPosition), tabPosition);
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private void setSelectedTab(BeautifulTab tab, int tabPosition) {
    selectedTab = tab;
    selectedTabPosition = tabPosition;
    if (onTabSelectedListener != null)
      onTabSelectedListener.onSelected(selectedTab, selectedTabPosition);
    selectedTab.setSelectedWithAnimation();
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private void setUnselectedTab(BeautifulTab tab, int tabPosition) {
    unSelectedTab = tab;
    unSelectedTabPosition = tabPosition;
    if (onTabSelectedListener != null)
      onTabSelectedListener.onUnselected(unSelectedTab, unSelectedTabPosition);
    unSelectedTab.setUnselectedWithAnimation();
  }

  private void initTabWidth() {
    int tabCount = tabs.size();
    int screenWidth = ConfigHelper.getScreenWidth(getContext());
    int tabMaxWidth = ConfigHelper.getPxFromDimenRes(R.dimen.iconic_tab_max_width, getContext());
    if ((tabMaxWidth * tabCount) > screenWidth) {
      llTabs.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
      for (BeautifulTab tab : tabs) {
        tab.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
        tab.setGravity(Gravity.CENTER);
      }
    } else {
      llTabs.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT,
        Gravity.CENTER_HORIZONTAL));
      for (BeautifulTab tab : tabs) {
        tab.setLayoutParams(new LinearLayout.LayoutParams(tabMaxWidth, LayoutParams.MATCH_PARENT));
        tab.setGravity(Gravity.CENTER);
      }
    }
  }

  public interface OnTabSelectedListener {
    void onSelected(BeautifulTab tab, int position);

    void onUnselected(BeautifulTab tab, int position);
  }

}
