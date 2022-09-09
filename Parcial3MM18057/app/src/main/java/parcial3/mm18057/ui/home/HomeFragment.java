package parcial3.mm18057.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import parcial3.mm18057.R;
import parcial3.mm18057.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private  String domain="http://192.168.1.6:80/";
    private int sumatorioMemoria=0;
    private FragmentHomeBinding binding;
    EditText duiEditText;
    Button consultarBtn;
    TextView resultadoTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        consultarBtn=view.findViewById(R.id.consultarBtn);
        duiEditText=view.findViewById(R.id.duiEditTextConsulta);
        resultadoTextView=view.findViewById(R.id.resultadoMemoria);
        consultarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dui=duiEditText.getText().toString();
                int currentSumatoriaMemoria;
                currentSumatoriaMemoria=consultar(getContext(),dui);
                sumatorioMemoria=0;
                resultadoTextView.setText(String.valueOf(currentSumatoriaMemoria));
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public int consultar(Context context, String dui){
        String url = domain+"calculo_memoria_por_dui.php?dui="+dui;
        JsonArrayRequest request = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject object = null;

                try {
                    object = response.getJSONObject(0);
                    sumatorioMemoria =object.getInt("resultado");

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);
        return sumatorioMemoria;
    }
}