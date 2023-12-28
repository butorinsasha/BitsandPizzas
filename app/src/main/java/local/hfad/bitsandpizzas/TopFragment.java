package local.hfad.bitsandpizzas;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout topFragmentRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_top, container, false);

        //...

        RecyclerView pizzaRecycler = topFragmentRelativeLayout.findViewById(R.id.pizza_recycler);

        String[] pizzaNames = new String[2];
        for (int i = 0; i < 2; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName();
        }

        int[] pizzaImages = new int[2];
        for (int i = 0; i < 2; i++) {
            pizzaImages[i] = Pizza.pizzas[i].getImageResourceId();
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        pizzaRecycler.setLayoutManager(gridLayoutManager);

        //...

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImages);

        pizzaRecycler.setAdapter(adapter);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZANO, position);
                getActivity().startActivity(intent);
            }
        });

        return topFragmentRelativeLayout;
    }
}