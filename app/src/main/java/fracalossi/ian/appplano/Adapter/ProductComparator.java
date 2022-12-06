package fracalossi.ian.appplano.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import fracalossi.ian.appplano.Model.Product;


public class ProductComparator extends DiffUtil.ItemCallback<Product> {
    @Override
    public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
        return oldItem.id.equals(newItem.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
        return oldItem.id.equals(newItem.id) &&
                oldItem.name.equals(newItem.name) &&
                oldItem.price.equals(newItem.price);
    }
}
