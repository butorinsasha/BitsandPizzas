package local.hfad.bitsandpizzas;

import static androidx.core.view.MenuItemCompat.getActionProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.content.res.ResourcesCompat;

public class PizzaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PIZZANO = "pizzaNo";
    TextView pizzaTextView;
    ImageView pizzaImageView;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        // Enable the Up button in ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the name of the pizza
        int pizzaNo = (Integer) getIntent().getExtras().get(EXTRA_PIZZANO);
        String pizzaName = Pizza.pizzas[pizzaNo].getName();
        pizzaTextView = findViewById(R.id.pizza_text);
        pizzaTextView.setText(pizzaName);

        // Sets a drawable as the content of this ImageView.
        // This does Bitmap reading and decoding on the UI thread, which can cause a latency hiccup.
        // If that's a concern, consider using setImageDrawable(Drawable) or setImageBitmap(Bitmap) and android.graphics.BitmapFactory instead.
//        imageView.setImageResource(pizzaImage);
        int pizzaImage = Pizza.pizzas[pizzaNo].getImageResourceId();
        pizzaImageView = findViewById(R.id.pizza_image);
        pizzaImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), pizzaImage, null));
        pizzaImageView.setContentDescription(pizzaName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Share the name of the pizza
        CharSequence pizzaName = pizzaTextView.getText();

        MenuItem shareMenuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) getActionProvider(shareMenuItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, pizzaName);
        // Set the default text to share in the Share action
        shareActionProvider.setShareIntent(intent);

        //return true
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_create_order):
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}