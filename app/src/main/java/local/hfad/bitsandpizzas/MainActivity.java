package local.hfad.bitsandpizzas;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.action_create_order:
                Toast.makeText(this, menuItem.getTitle() + " selected", LENGTH_SHORT).show();
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_share:
            case R.id.action_settings:
                Toast.makeText(this, menuItem.getTitle() + " selected", LENGTH_SHORT).show();
                return true;
            default:
                super.onOptionsItemSelected(menuItem);

        }

        Toast.makeText(this, "Action Bar item clicked", LENGTH_SHORT).show();
        return super.onOptionsItemSelected(menuItem);
    }
}