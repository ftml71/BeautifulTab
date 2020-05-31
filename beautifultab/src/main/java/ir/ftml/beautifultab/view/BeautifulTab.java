package ir.ftml.beautifultab.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import ir.ftml.beautifultab.R;

public class BeautifulTab extends LinearLayout {

  private static final int TRANSITION_DURATION = 100;

  private ImageView tabIcon;
  private CardView tabItem;
  private TextView tabText, tabBadge;

  private int tabPosition;
  private int badgeCount;

  private int tabDefaultColor;
  private int tabSelectedColor;

  private int tabBackgroundDefaultColor;
  private int tabBackgoundSelectedColor;

  private OnTabClickListener onTabClickListener;

  public BeautifulTab(Context context) {
    super(context);
    inflateView();
  }

  public BeautifulTab(Context context, AttributeSet attrs) {
    super(context, attrs);
    inflateView();
  }

  public BeautifulTab(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflateView();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public BeautifulTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    inflateView();
  }

  private void inflateView() {
    inflate(getContext(), R.layout.layout_tab_item, this);

    tabIcon = findViewById(R.id.tab_item_icon);
    tabItem = findViewById(R.id.tab_item);
    tabText = findViewById(R.id.tab_item_text);
    tabBadge = findViewById(R.id.tab_item_badge);

    tabDefaultColor = ContextCompat.getColor(getContext(), R.color.defaultColor);
    tabSelectedColor = ContextCompat.getColor(getContext(), R.color.defaultSelectedColor);

    tabBackgroundDefaultColor = ContextCompat.getColor(getContext(), android.R.color.transparent);
    tabBackgoundSelectedColor = ContextCompat.getColor(getContext(), android.R.color.transparent);

    tabIcon.setColorFilter(tabDefaultColor);
    tabText.setTextColor(tabDefaultColor);

    tabText.setVisibility(GONE);
    tabItem.setCardBackgroundColor(tabBackgroundDefaultColor);

    tabBadge.setVisibility(GONE);
  }

  public void bindData(int position, @DrawableRes int iconResId, @StringRes int textResId, @ColorRes int bgResId) {
    tabPosition = position;
    tabIcon.setImageResource(iconResId);
    tabText.setText(textResId);
    tabBackgoundSelectedColor =bgResId;
    setBadgeCount(0);
    onTabItemClick();
  }

  public void bindData(int position, @DrawableRes int iconResId, CharSequence text,  @ColorRes int bgResId) {
    tabPosition = position;
    tabIcon.setImageResource(iconResId);
    tabText.setText(text);
    tabBackgoundSelectedColor =bgResId;


    setBadgeCount(0);
    onTabItemClick();
  }

  public void bindData(int position, Drawable iconDrawable, CharSequence text,@ColorRes int bgResId) {
    tabPosition = position;
    tabIcon.setImageDrawable(iconDrawable);
    tabText.setText(text);
    tabBackgoundSelectedColor =bgResId;


    setBadgeCount(0);
    onTabItemClick();
  }
  public void bindData(int position, Drawable iconDrawable, CharSequence text) {
    tabPosition = position;
    tabIcon.setImageDrawable(iconDrawable);
    tabText.setText(text);

    setBadgeCount(0);
    onTabItemClick();

  }

  public void setTabDefaultColor(@ColorInt int tabDefaultColor) {
    this.tabDefaultColor = tabDefaultColor;
    tabIcon.setColorFilter(tabDefaultColor);
    tabText.setTextColor(tabDefaultColor);
  }

  public void setTabSelectedColor(@ColorInt int tabSelectedColor) {
    this.tabSelectedColor = tabSelectedColor;
  }

  public void setSelected() {
    tabIcon.setColorFilter(tabSelectedColor);
    tabText.setTextColor(tabSelectedColor);
    tabText.setVisibility(VISIBLE);
//    tabItem.setVisibility(VISIBLE);
    tabItem.setCardBackgroundColor(getResources().getColor(tabBackgoundSelectedColor));

  }

  public void setUnselected() {
    tabIcon.setColorFilter(tabDefaultColor);
    tabText.setTextColor(tabDefaultColor);
    tabText.setVisibility(GONE);
    tabItem.setCardBackgroundColor(tabBackgroundDefaultColor);

  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void setSelectedWithAnimation() {
    TransitionManager.beginDelayedTransition(this, getTransition());
    tabIcon.setColorFilter(tabSelectedColor);
    tabText.setTextColor(tabSelectedColor);
    tabText.setVisibility(VISIBLE);
    tabItem.setCardBackgroundColor(getResources().getColor(tabBackgoundSelectedColor));


  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public void setUnselectedWithAnimation() {
    TransitionManager.beginDelayedTransition(this, getTransition());
    tabIcon.setColorFilter(tabDefaultColor);
    tabText.setTextColor(tabDefaultColor);
    tabText.setVisibility(GONE);
    tabItem.setCardBackgroundColor(tabBackgroundDefaultColor);

  }

  public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
    this.onTabClickListener = onTabClickListener;
  }

  private void onTabItemClick() {
    tabItem.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onTabClickListener != null)
          onTabClickListener.onTabClick(BeautifulTab.this, tabPosition);
      }
    });
  }

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  private Transition getTransition() {
    return new AutoTransition().setDuration(TRANSITION_DURATION);
  }

  public CharSequence getText() {
    return tabText.getText();
  }

  public void setText(@StringRes int stringResId) {
    tabText.setText(stringResId);
  }

  public void setText(CharSequence text) {
    tabText.setText(text);
  }

  public Drawable getIcon() {
    return tabIcon.getDrawable();
  }

  public void setIcon(@DrawableRes int iconResId) {
    tabIcon.setImageResource(iconResId);
  }

  public void setIcon(Drawable iconDrawable) {
    tabIcon.setImageDrawable(iconDrawable);
  }

  public int getBadgeCount() {
    return badgeCount;
  }

  public void setBadgeCount(int badgeCount) {
    this.badgeCount = badgeCount;
    if (badgeCount > 0) {
      tabBadge.setVisibility(VISIBLE);
      tabBadge.setText(String.format("%s", badgeCount));
    } else {
      tabBadge.setVisibility(GONE);

    }
  }

  public interface OnTabClickListener {
    void onTabClick(BeautifulTab tabBottomBar, int position);
  }

}
