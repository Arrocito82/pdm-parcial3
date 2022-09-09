package parcial3.mm18057.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import parcial3.mm18057.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {
    private int insertedCount=0;
    private  String domain="http://192.168.1.6:80/";
    private FragmentDashboardBinding binding;
    EditText duiEditText,modeloEditText,memoriaEditText;
    Button guardarBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        guardarBtn=view.findViewById(R.id.guardarBtn);
        duiEditText=view.findViewById(R.id.duiEditText);
        modeloEditText=view.findViewById(R.id.modeloEditText);
        memoriaEditText=view.findViewById(R.id.memoriaEditText);
        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dui,modelo;
                int memoria;
                Boolean isInserted=false;
                dui=duiEditText.getText().toString();
                modelo=modeloEditText.getText().toString();
                memoria=Integer.parseInt(memoriaEditText.getText().toString());
                isInserted=insert(getContext(), dui, modelo,  memoria);
                if(isInserted){
                    Toast.makeText(getContext(),"Guardado!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Error! DUI no existe, intentalo de nuevo.",Toast.LENGTH_LONG).show();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public Boolean insert(Context context, String dui, String modelo, int memoria){
        String url = domain+"crear_celular.php?dui="+dui+"&modelo="+modelo+"&memoria="+memoria;
        JsonArrayRequest request = new JsonArrayRequest( url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject object = null;

                try {
                    object = response.getJSONObject(0);
                    insertedCount =object.getInt("resultado");
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
        return insertedCount==1;
    }
}