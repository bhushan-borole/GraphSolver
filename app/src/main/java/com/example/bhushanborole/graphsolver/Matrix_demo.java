package com.example.bhushanborole.graphsolver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Matrix_demo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matrix_demo);

        int[][] adjMatrix = (int[][])getIntent().getExtras().getSerializable("matrix");
        TextView txt = findViewById(R.id.txt);
        txt.setText("");
        for(int i=0;i< adjMatrix.length;i++){
            for(int j = 0; j< adjMatrix.length; j++){
                //System.out.println(adjMatrix[i][j]);
                txt.append(String.valueOf(adjMatrix[i][j])+" ");
            }
            txt.append("\n");
        }



    }

}
