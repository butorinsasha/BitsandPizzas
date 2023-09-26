package local.hfad.bitsandpizzas;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int currentPosition = 0;


    // Implementation of Listener for drawer list items
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parentListView,  //  The view which item was clicked (in this case, the list view)
                                View itemView,                  //  The item which is clicked in parentListView
                                int position,                   // The position of the item which is clicked if parentListView
                                long id) {                      //  (?) don't know the difference between position and id
            selectItem(position);
        }
    }


    // android.app.Activity OVERRIDDEN METHODS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // INITIALIZE the drawer's list by string-array of titles
        titles = getResources().getStringArray(R.array.titles);



        // INITIALIZE MainActivity field - drawer -  by findViewById()
        drawerListView = (ListView) findViewById(R.id.drawer);

        // Use ArrayAdapter(
        //      android.content.Context   context
        //      @LayoutRes                int resource,
        //      T[]                       objects
        //    )
        // to papulate the ListView
        drawerListView.setAdapter(new ArrayAdapter<>(          // setAdapter for drawer
                this,                                          // MainActivity context
                android.R.layout.simple_list_item_activated_1, // Using simple_list_item_activiated_1 means that the item the user clicks on is highlighted
                titles                                         // string-array of titles
        ));

        // Use an OnItemClickListener to respond to clicks in the list view
        // (see the implementation of DrawerItemClickListener above in MainActivity)
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());




        // INITIALIZE MainActivity field - drawerLayout -  by findViewById()
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);




        // If the MainActivity is newly created, use the selectItem() method to display TopFragment.
        // (?) Seems like everything works well without this code
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }




        // INITIALIZE MainActivity field -  actionBarDrawerToggle
        // The best way of setting up a DrawerListener is to use an ActionBarDrawerToggle.
        // An ActionBarDrawerToggle is a special type of DrawerListener that works with an action bar.
        // It allows you to listen for DrawerLayout events like a normal DrawerListener,
        //  and it also lets you open and close the drawer by clicking on an icon on the action bar.
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                   // android.app.Activity activity,
                drawerLayout,           // android.support.v4.widget.DrawerLayout drawerLayout
                R.string.open_drawer,   // @StringRes int openDrawerContentDescRes // Add these to strings.xml. These are needed for accessibility
                R.string.close_drawer   // @StringRes int closeDrawerContentDescRe // Add these to strings.xml. These are needed for accessibility
        ) {

            // Called when a drawer has settled in a completely open state.
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
                // When you call the invalidateOptionsMenu() method, the activity’s onPrepareOptionsMenu() method gets called. You can override this method to specify how the menu items need to change.
                invalidateOptionsMenu();
            }

            // Called when a drawer has settles in a completely closed state
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // When you call the invalidateOptionsMenu() method, the activity’s onPrepareOptionsMenu() method gets called. You can override this method to specify how the menu items need to change.
                invalidateOptionsMenu();
            }
        };




        // Set actionBarDrawerToggle to drawerLayout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        // Enable the Up (Back) button so you can use it for the drawer
        // https://developer.android.com/reference/android/app/ActionBar#setHomeButtonEnabled(boolean)
        //  Setting the DISPLAY_HOME_AS_UP display option will automatically enable the home button.
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        Fragment fragment = getFragmentManager().findFragmentByTag("visible_fragment");

                        if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                        }
                        if (fragment instanceof PizzaFragment) {
                            currentPosition = 1;
                        }
                        if (fragment instanceof PastaFragment) {
                            currentPosition = 2;
                        }
                        if (fragment instanceof StoresFragment) {
                            currentPosition = 3;
                        }
                        setActionBarTitle(currentPosition);
                        drawerListView.setItemChecked(currentPosition, true);
                    }
                }
        );

    }




            // ADDITIONAL MainActivity METHODS

            // What happens when item was clicked.
            // This method is used in DrawerItemClickListener.onItemClick()
            private void selectItem(int position) {
                currentPosition = position;
                // Defined which fragment to show drawerListView
                Fragment fragment;
                switch(position) {
                    case 1:
                        fragment = new PizzaFragment();
                        break;
                    case 2:
                        fragment = new PastaFragment();
                        break;
                    case 3:
                        fragment = new StoresFragment();
                        break;
                    default:                                // (?) don't know when this executes
                        fragment = new TopFragment();
                }

                // Show certain fragment
                // (!) This typical code for fragment to be shown
                FragmentTransaction fragmentTransaction = getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment, "visible_fragment")
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();

                // Change the title by calling the setActionTitle() method,
                // passing the position of the item that was clicked on
                // (see additional MainActivity.setActionBarTitle() implementation below)
                setActionBarTitle(position);

                // Close the drawer. This saves the user from closing it themselves.
                drawerLayout.closeDrawer(drawerListView);
            }



            // Setting ActionBar title using MainActivity.getActionBar()
            // and ActionBar.setTitle(CharSequence charSequence)
            private void setActionBarTitle(int position) {
                String title;
                if (position == 0) {
                    title = getResources().getString(R.string.app_name); // If the user clicks on the “Home” option, use the app name for the title.
                } else {
                    title = titles[position]; // Otherwise, get the String from the titles array for the position that was clicked and wse that
                }
                getActionBar().setTitle(title); // Display the title in the action bar.
            }


    // Sync the toggle state after onRestoreInstanceState has occurred.
    // You need to add this method to yout activity so that the state of the ActionBarDrawerToggle
    // is in sync with the state of the drawer
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    // You need to add this method to your activity
    // so that any configuration changes get passed to the ActionBarDrawerToggle
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("position", currentPosition);
    }



    // ACTION BAR OVERRIDDEN android.app.Activity METHODS

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean isDrawerOpened = drawerLayout.isDrawerOpen(drawerListView);
        menu.findItem(R.id.action_share).setVisible(!isDrawerOpened);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }


        // Additional ActionBar methods
        private void setIntent(String text) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            shareActionProvider.setShareIntent(intent);
        }

        private void share(String text) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (actionBarDrawerToggle.onOptionsItemSelected(menuItem)) {
            // actionBarDrawerToggle.onOptionsItemSelected(menuItem)
            // returns true if the ActionBarDrawerToggle has handled being clicked.
            // If it returns false, this means that another action item in the action bar has been clicked,
            // and the rest of the code in the activity’s onOptionsItemSelected() method will run.
            return true;
        }
        switch(menuItem.getItemId()) {
            case R.id.action_create_order:
                Toast.makeText(this, menuItem.getTitle() + " selected", LENGTH_SHORT).show();
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_share:
                share("This is example text");
                return true;
            case R.id.action_settings:
                Toast.makeText(this, menuItem.getTitle() + " selected", LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "Action Bar default option item", LENGTH_SHORT).show();
                return super.onOptionsItemSelected(menuItem);
        }
    }
}