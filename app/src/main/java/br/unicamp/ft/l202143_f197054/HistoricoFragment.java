package br.unicamp.ft.l202143_f197054;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HistoricoDB> arrayList;
    private FirebaseRecyclerOptions<HistoricoDB> options;
    private FirebaseRecyclerAdapter<HistoricoDB, HistoricoViewHolder> adapter;
    private DatabaseReference databaseReference;
    private View lview;

    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView tvData;
        TextView tvTipo;
        TextView tvLitros;
        TextView tvTotal;
        ImageView imageView;
        CardView cardView;
        private int position;

        public HistoricoViewHolder(View v) {
            super(v);
            tvData = (TextView) v.findViewById(R.id.tvData);
            tvTipo = (TextView) v.findViewById(R.id.tvTipo);
            tvLitros = (TextView) v.findViewById(R.id.tvLitros);
            tvTotal = (TextView) v.findViewById(R.id.tvTotal);
            imageView = (ImageView) v.findViewById(R.id.imgView);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<HistoricoDB, HistoricoViewHolder>
            mFirebaseAdapter;


    public HistoricoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_historico, container, false);
        }

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        SnapshotParser<HistoricoDB> parser = new SnapshotParser<HistoricoDB>() {
            @Override
            public HistoricoDB parseSnapshot(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);
                HistoricoDB historicoDB = dataSnapshot.getValue(HistoricoDB.class);
                System.out.println(historicoDB.getTipo());
                return historicoDB;
            }
        };

        final DatabaseReference messagesRef = mFirebaseDatabaseReference.child("historico");

        FirebaseRecyclerOptions<HistoricoDB> options =
                new FirebaseRecyclerOptions.Builder<HistoricoDB>()
                        .setQuery(messagesRef, parser)
                        .build();


        mFirebaseAdapter = new FirebaseRecyclerAdapter<HistoricoDB, HistoricoViewHolder>(options) {
            @Override
            public HistoricoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new HistoricoViewHolder(inflater.inflate(R.layout.list_item, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final HistoricoViewHolder viewHolder,
                                            final int position,
                                            HistoricoDB historicoDB) {
                try {
                    viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mFirebaseAdapter.getSnapshots().getSnapshot(position).getRef().removeValue();
                            notifyDataSetChanged();
                            return true;
                        }
                    });
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(getContext(), "Removido", Toast.LENGTH_SHORT).show();

                }

                final String cData = getItem(position).getData();
                final String cTipo = getItem(position).getTipo();
                final Double cLitros = getItem(position).getLitros();
                final Double cTotal = getItem(position).getTotal();


                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), EditActivity.class);
                        intent.putExtra("cData", cData);
                        intent.putExtra("cTipo", cTipo);
                        intent.putExtra("cLitros", cLitros);
                        intent.putExtra("cTotal", cTotal);
                        startActivity(intent);
                    }
                });


                viewHolder.tvData.setText("Data: " + historicoDB.getData());
                viewHolder.tvTipo.setText(historicoDB.getTipo());
                viewHolder.tvLitros.setText("Litros: " + String.valueOf(historicoDB.getLitros()));
                viewHolder.tvTotal.setText("Total: R$ " + String.valueOf(historicoDB.getTotal()));

                if (historicoDB.getPosto().equalsIgnoreCase("Shell")) {
                    viewHolder.imageView.setImageResource(R.drawable.shell);
                } else if (historicoDB.getPosto().equalsIgnoreCase("Petrobras BR")) {
                    viewHolder.imageView.setImageResource(R.drawable.postobr);
                } else if (historicoDB.getPosto().equalsIgnoreCase("Ipiranga")) {
                    viewHolder.imageView.setImageResource(R.drawable.ipiranga);
                } else if (historicoDB.getPosto().equalsIgnoreCase("Ale")) {
                    viewHolder.imageView.setImageResource(R.drawable.ale);
                } else if (historicoDB.getPosto().equalsIgnoreCase("Esso")) {
                    viewHolder.imageView.setImageResource(R.drawable.esso);
                } else if (historicoDB.getPosto().equalsIgnoreCase("Outros")) {
                    viewHolder.imageView.setImageResource(R.drawable.posto);
                }
            }
        };

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView mRecycler = ((RecyclerView) lview.findViewById(R.id.recycler_view));
        mRecycler.setLayoutManager(llm);
        mRecycler.setAdapter(mFirebaseAdapter);
        return lview;
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onPause() {
        mFirebaseAdapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }
}