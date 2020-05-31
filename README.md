# BeautifulTab
[![](https://jitpack.io/v/ftml71/BeautifulTab.svg)](https://jitpack.io/#ftml71/BeautifulTab)

<img src="https://raw.githubusercontent.com/ftml71/BeautifulTab/master/screenShot/4%20chat.png" width="20%"> <img src="https://raw.githubusercontent.com/ftml71/BeautifulTab/master/screenShot/3%20calls.png" width="20%"> <img src="https://raw.githubusercontent.com/ftml71/BeautifulTab/master/screenShot/2%20contacts.png" width="20%"> <img src="https://raw.githubusercontent.com/ftml71/BeautifulTab/master/screenShot/1%20setting.png" width="20%"> 










activity_main:

```
<ir.ftml.beautifultab.view.BeautifulTabBar
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:barBackground="@color/white"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:tabDefaultColor="@color/textColor"
        app:tabSelectedColor="@color/colorPrimary" />
        
```
MainActivity:

```
 tabLayout.addTabFromMenu(R.menu.main_tab_menu,
                resources.obtainTypedArray(R.array.tabItemColor),
                resources.obtainTypedArray(R.array.tabTextColor)
        )
```
tab_colors:
```
<?xml version="1.0" encoding="utf-8"?>
<resources>

  <array name="tabTextColor">
    <item>@color/textHome</item>
    <item>@color/textTests</item>
    <item>@color/textProfile</item>
    <item>@color/textEsanj</item>
  </array>

  <array name="tabItemColor">
    <item>@color/bgHome</item>
    <item>@color/bgTests</item>
    <item>@color/bgProfile</item>l
    <item>@color/bgEsanj</item>
  </array>
</resources>
```

build.gradle(.):
```
allprojects {
 repositories {
		...
		maven { url 'https://jitpack.io' }
	}
  }
  ```
  build.gradle(app):
  
  ```
  dependencies {
	        implementation 'com.github.ftml71:BeautifulTab:1.0.0'
	}
  
  ```
  
  
