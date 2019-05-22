package br.unicamp.ft.l202143_f197054;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Adapter adapter;

    public HistoricoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment if view is null to avoid waste
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_historico, container, false);
        }

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Configurar e setar o adapter...
        adapter = new Adapter(Abastecimentos.abastecimentosList);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
