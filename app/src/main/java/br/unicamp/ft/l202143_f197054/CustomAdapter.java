package br.unicamp.ft.l202143_f197054;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter {

    private final ArrayList<HistoricoDB> historicoDB;
    Context context;

    public CustomAdapter(Context c, ArrayList<HistoricoDB> historicoDB) {
        context = c;
        this.historicoDB = historicoDB;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((CustomViewHolder) viewHolder).bind(historicoDB.get(i), i);
    }

    @Override
    public int getItemCount() {
        return historicoDB.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvData;
        private final TextView tvTipo;

        private int position;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            tvData = itemView.findViewById(R.id.tvData);
            tvTipo = itemView.findViewById(R.id.tvTipo);
        }

        public void bind(HistoricoDB historicoDB, int position) {
            tvData.setText(historicoDB.getData());
            tvTipo.setText(historicoDB.getTipo());
            this.position = position;
        }

        public int getHolderPosition() {
            return position;
        }
    }
}