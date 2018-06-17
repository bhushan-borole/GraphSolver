package com.example.bhushanborole.graphsolver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    MyCustomAdapter adapter;
    public Button btn_add;
    public Button btn_submit;
    public Button btn_edit;
    public Button btn_delete;
    public Button btn_done;
    private EditText edit_txt1;
    private EditText edit_txt2;
    private EditText edit_txt3;
    private EditText editStartNode;
    private EditText editEndNode;
    private EditText editWeight;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private String start_node;
    private String end_node;
    private String weight;
    private ArrayList<GraphEdge> listGraphEdge;
    private Set<int []> Q = new HashSet<>();
    private Set<Integer> P = new HashSet<>();
    private ListView listView;
    private boolean directed = false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_txt1 = findViewById(R.id.edit_txt1);
        edit_txt2 = findViewById(R.id.edit_txt2);
        edit_txt3 = findViewById(R.id.edit_txt3);
        listGraphEdge = new ArrayList<>();
        btn_add = findViewById(R.id.btn_add);
        btn_submit = findViewById(R.id.btn_submit);
        listView = findViewById(R.id.listview);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();

                Q.add(new int[]{Integer.valueOf(start_node), Integer.valueOf(end_node),Integer.valueOf(weight)});

                edit_txt1.setText("");
                edit_txt2.setText("");
                edit_txt3.setText("");

                adapter = new MyCustomAdapter(listGraphEdge, getApplicationContext());
                listGraphEdge.add(new GraphEdge(Integer.valueOf(start_node), Integer.valueOf(end_node), Integer.valueOf(weight)));
                listView.setAdapter(adapter);

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int [] n : Q){
                    P.add(n[0]);
                    P.add(n[1]);
                }

                int minNode = P.iterator().next();
                int numberOfNodes = P.size();
                System.out.println(numberOfNodes);
                int adjMatrix[][] = new int[numberOfNodes][numberOfNodes];

                for(int i=0;i< numberOfNodes;i++){
                    for(int j = 0; j< numberOfNodes; j++){
                        adjMatrix[i][j] = 999;
                    }
                }

                for(int i=0; i < numberOfNodes; i++){
                    adjMatrix[i][i] = 0;
                }
                if(directed)
                for(int []n : Q){
                    adjMatrix[n[0]-minNode][n[1]-minNode] = n[2];
                }
                else
                    for(int []n : Q){
                        adjMatrix[n[0]-minNode][n[1]-minNode] = adjMatrix[n[1]-minNode][n[0]-minNode] = n[2];
                    }
                for(int i=0;i< numberOfNodes;i++){
                    for(int j = 0; j< numberOfNodes; j++){
                        //System.out.println(adjMatrix[i][j]);
                    }
                }
                Bundle b = new Bundle();
                b.putSerializable("matrix", adjMatrix);

                Intent i = new Intent(MainActivity.this, Matrix_demo.class);
                i.putExtras(b);
                startActivity(i);


            }
        });

    }
    public void getInput(){
        start_node = edit_txt1.getText().toString();
        end_node = edit_txt2.getText().toString();
        weight = edit_txt3.getText().toString();
    }


    class MyCustomAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<GraphEdge> list;
        private Context context;



        public MyCustomAdapter(ArrayList<GraphEdge> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int pos) {
            return list.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.custom_layout, null);
            }

            //Handle TextView and display string from your list
            txt1 = view.findViewById(R.id.txt1);
            txt1.setText(String.format("%d",list.get(position).getStartNode()));

            txt2 = view.findViewById(R.id.txt2);
            txt2.setText(String.format("%d",list.get(position).getEndNode()));

            txt3 = view.findViewById(R.id.txt3);
            txt3.setText(String.format("%d",list.get(position).getWeight()));


            //Handle buttons and add onClickListeners
            Button btn_delete = view.findViewById(R.id.btn_delete);
            Button btn_edit = view.findViewById(R.id.btn_edit);

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int del_startNode = list.get(position).getStartNode();
                    int del_endNode = list.get(position).getEndNode();
                    int del_weight = list.get(position).getWeight();

                    Iterator<int []> ite = Q.iterator();
                    while(ite.hasNext()){
                        int []n = ite.next();
                        if(n[0]==del_startNode && n[1]==del_endNode && n[2]==del_weight){
                            ite.remove();
                        }
                    }
                    P.remove(del_startNode);
                    P.remove(del_endNode);



                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View view1 = inflater.inflate(R.layout.row_edit, null);
                    alert.setView(view1);
                    final AlertDialog dialog = alert.create();
                    dialog.show();
                    Button btn_done = view1.findViewById(R.id.btn_done);

                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editStartNode = view1.findViewById(R.id.editStartNode);
                            editEndNode = view1.findViewById(R.id.editEndNode);
                            editWeight = view1.findViewById(R.id.editWeight);

                            int edit_startNode = list.get(position).getStartNode();
                            int edit_endNode = list.get(position).getEndNode();
                            int edit_weight = list.get(position).getWeight();

                            for(int [] n: Q){
                                if(n[0]==edit_startNode && n[1]==edit_endNode && n[2]==edit_weight){
                                    n[0] = Integer.valueOf(editStartNode.getText().toString());
                                    n[1] = Integer.valueOf(editEndNode.getText().toString());
                                    n[2] = Integer.valueOf(editWeight.getText().toString());
                                }
                            }
                            P.add(Integer.valueOf(editStartNode.getText().toString()));
                            P.add(Integer.valueOf(editEndNode.getText().toString()));

                            list.remove(position);
                            list.add(new GraphEdge(Integer.valueOf(editStartNode.getText().toString()), Integer.valueOf(editEndNode.getText().toString()),Integer.valueOf(editWeight.getText().toString())));
                            txt1.setText(String.format("%d",list.get(position).getStartNode()));

                            txt2.setText(String.format("%d",list.get(position).getEndNode()));

                            txt3.setText(String.format("%d",list.get(position).getWeight()));
                            dialog.dismiss();

                        }
                    });

                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }
}
