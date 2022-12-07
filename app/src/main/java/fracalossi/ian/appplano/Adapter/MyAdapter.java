package fracalossi.ian.appplano.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;


import fracalossi.ian.appplano.Activity.HomeActivity;
import fracalossi.ian.appplano.Model.Gasto;
import fracalossi.ian.appplano.R;

public class MyAdapter extends PagingDataAdapter<Gasto, MyViewHolder> {
    HomeActivity homeActivity;

    public MyAdapter(HomeActivity homeActivity, @NonNull DiffUtil.ItemCallback<Gasto> diffCallback) {
        super(diffCallback);
        this.homeActivity = homeActivity;
    }

    /**
     * Cria os elementos de UI referente a um item da lista
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.gasto_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    /**
     * Preenche um item da lista
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Gasto gasto = this.getItem(position);

        // preenche o campo de nome
        TextView tvNameList = holder.itemView.findViewById(R.id.tvNameGastoList);
        tvNameList.setText(gasto.name);

        // preenche o campo de preço
        TextView tvPiceList = holder.itemView.findViewById(R.id.tvPriceGastoList);
        tvPiceList.setText(gasto.price);



    }
}
