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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
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

    public static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView tvData;
        TextView tvTipo;

        public HistoricoViewHolder(View v) {
            super(v);
            tvData = (TextView) v.findViewById(R.id.tvData);
            tvTipo = (TextView) v.findViewById(R.id.tvTipo);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lview = inflater.inflate(R.layout.fragment_historico, container, false);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://gasapp-c67e5.firebaseio.com/");
        SnapshotParser<HistoricoDB> parser = new SnapshotParser<HistoricoDB>() {
            @Override
            public HistoricoDB parseSnapshot(DataSnapshot dataSnapshot) {
                HistoricoDB historicoDB = dataSnapshot.getValue(HistoricoDB.class);
                return historicoDB;
            }
        };

        DatabaseReference messagesRef = mFirebaseDatabaseReference.child("historico");
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
                                            int position,
                                            HistoricoDB historicoDB) {
                viewHolder.tvData.setText(historicoDB.getData());
                viewHolder.tvTipo.setText(historicoDB.getTipo());
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