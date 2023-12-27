package local.hfad.bitsandpizzas;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private final String[] captions;
    private final int[] imageIds;

    public CaptionedImagesAdapter(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }

    @NonNull
    @Override // Create a new view
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, // the RecyclerView itself
            int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cv) {
            super(cv);
            cardView = cv;
        }
    }

    @Override // Set the values inside the given view
    public void onBindViewHolder(@NonNull CaptionedImagesAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.info_image);
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);

        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);
    }

    @Override // Return the number of items in the data set
    public int getItemCount() {
        return captions.length;
    }
}
