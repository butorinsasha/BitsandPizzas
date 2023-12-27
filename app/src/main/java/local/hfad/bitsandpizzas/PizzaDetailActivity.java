package local.hfad.bitsandpizzas;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class PizzaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PIZZANO = "pizzaNo";
    private androidx.appcompat.widget.ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int pizzaNo = (Integer) getIntent().getExtras().get(EXTRA_PIZZANO);

        String pizzaName = Pizza.pizzas[pizzaNo].getName();
        TextView textView = findViewById(R.id.pizza_text);
        textView.setText(pizzaName);

        int pizzaImage = Pizza.pizzas[pizzaNo].getImageResourceId();
        ImageView imageView = findViewById(R.id.pizza_image);

        // Sets a drawable as the content of this ImageView.
        // This does Bitmap reading and decoding on the UI thread, which can cause a latency hiccup.
        // If that's a concern, consider using setImageDrawable(Drawable) or setImageBitmap(Bitmap) and android.graphics.BitmapFactory instead.
//        imageView.setImageResource(pizzaImage);
        imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), pizzaImage, null));

        imageView.setContentDescription(pizzaName);
    }
}