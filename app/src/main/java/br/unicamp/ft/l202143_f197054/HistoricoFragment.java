package br.unicamp.ft.l202143_f197054;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference mFirebaseDatabaseReference;
    FirebaseRecyclerOptions<HistoricoDB> options;
    FirebaseRecyclerAdapter<HistoricoDB, HistoricoViewHolder> mFirebaseAdapter;
    private FirebaseAuth mAuth;
    private View view;

    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {

        TextView tvData;
        TextView tvTotal;
        TextView tvLitros;
        TextView tvTipo;

        public HistoricoViewHolder(View itemView) {
            super(itemView);

            tvData = itemView.findViewById(R.id.tvData);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvLitros = itemView.findViewById(R.id.tvLitros);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
/*
    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {

        TextView tvData;
        TextView tvTotal;
        TextView tvLitros;
        TextView tvTipo;

        public HistoricoViewHolder(View view) {
            super(view);

            tvData = itemView.findViewById(R.id.tvData);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvLitros = itemView.findViewById(R.id.tvLitros);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
*/

    public HistoricoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment if view is null to avoid waste
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_historico, container, false);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("historico");
/*
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        SnapshotParser<HistoricoDB> parser = new SnapshotParser<HistoricoDB>() {
            @NonNull
            @Override
            public HistoricoDB parseSnapshot(@NonNull DataSnapshot snapshot) {
                HistoricoDB historicoDB = null;
                System.out.println(snapshot);
                try {
                    historicoDB = snapshot.getValue(HistoricoDB.class);
                } catch (Exception e) {

                    for (DataSnapshot remoteResposta : snapshot.getChildren()) {
                            System.out.println("DATASET " + remoteResposta);
                            HistoricoDB resposta = remoteResposta.getValue(HistoricoDB.class);
                            Log.v("DATASET", resposta.toString());

                        }

                  //  historicoDB = snapshot.getChildren()[0].getValue(HistoricoDB.class);
                }
                return historicoDB;
            }
        };

        DatabaseReference messagesRef = mFirebaseDatabaseReference.child("historico");
        FirebaseRecyclerOptions<HistoricoDB> options = new FirebaseRecyclerOptions.Builder<HistoricoDB>().setQuery(messagesRef, parser).build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<HistoricoDB, HistoricoViewHolder>(options) {
            @Override
            public HistoricoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new HistoricoViewHolder(inflater.inflate(R.layout.list_item, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final HistoricoViewHolder viewHolder,
                                            int position,
                                            HistoricoDB historicoDB) {
                viewHolder.tvData.setText(historicoDB.getData());
                viewHolder.tvTipo.setText(historicoDB.getTipo());
            }
        };

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView mRecycler = ((RecyclerView) view.findViewById(R.id.recycler_view));
        mRecycler.setLayoutManager(llm);
        mRecycler.setAdapter(mFirebaseAdapter);
*/
        return view;
    }

    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<HistoricoDB>().setQuery(mFirebaseDatabaseReference, HistoricoDB.class).build();

        FirebaseRecyclerAdapter<HistoricoDB, HistoricoViewHolder> adapter = new FirebaseRecyclerAdapter<HistoricoDB, HistoricoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final HistoricoViewHolder holder, int position, @NonNull HistoricoDB model) {
                String historicoID = getRef(position).getKey();

                mFirebaseDatabaseReference.child(historicoID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("historico")) {
                            String data = dataSnapshot.child("data").getValue().toString();
                            String litros = dataSnapshot.child("litros").getValue().toString();
                            String tipo = dataSnapshot.child("tipo").getValue().toString();
                            String total = dataSnapshot.child("total").getValue().toString();

                            holder.tvData.setText(data);
                            holder.tvLitros.setText(litros);
                            holder.tvTipo.setText(tipo);
                            holder.tvTotal.setText(total);
                        }
                        else{
                            System.out.println("Não ha dados!");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
                HistoricoViewHolder historicoViewHolder = new HistoricoViewHolder(view);
                return historicoViewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



}
