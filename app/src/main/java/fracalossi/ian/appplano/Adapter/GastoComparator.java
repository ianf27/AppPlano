package fracalossi.ian.appplano.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import fracalossi.ian.appplano.Model.Gasto;


public class GastoComparator extends DiffUtil.ItemCallback<Gasto> {
    @Override
    public boolean areItemsTheSame(@NonNull Gasto oldItem, @NonNull Gasto newItem) {
        return oldItem.id.equals(newItem.id);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Gasto oldItem, @NonNull Gasto newItem) {
        return oldItem.id.equals(newItem.id) &&
                oldItem.name.equals(newItem.name) &&
                oldItem.price.equals(newItem.price);
    }
}
