package me.xemor.pathfindingvisualizer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;

public class MainActivity extends AppCompatActivity {

    Spinner sizeDropDown;
    Spinner speedsDropDown;
    GridView gridView;
    Button pathfind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.view2);
        createPathfindButton(gridView);
        createSpeeds(gridView);
        createSize(gridView);
        createStartButton();
    }

    public void createPathfindButton(GridView gridView) {
        pathfind = findViewById(R.id.button);
        pathfind.setVisibility(View.GONE);
        pathfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.runAlgorithm(Speed.valueOf((String)speedsDropDown.getSelectedItem()));
            }
        });
    }

    public void createStartButton() {
        Button button = findViewById(R.id.Start);
        button.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeDropDown.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                speedsDropDown.setVisibility(View.GONE);
                findViewById(R.id.speed_image).setVisibility(View.GONE);
                findViewById(R.id.grid_size_image).setVisibility(View.GONE);
                findViewById(R.id.title2).setVisibility(View.GONE);
                pathfind.setVisibility(View.VISIBLE);
            }
        });
    }

    public void createSpeeds(GridView gridView) {
        speedsDropDown = findViewById(R.id.speed);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.speeds));
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        speedsDropDown.setAdapter(adapter);
        speedsDropDown.setBackgroundColor(0);
        speedsDropDown.setSelected(false);
        speedsDropDown.setSelection(1, false);
    }

    public void createSize(GridView gridView) {
        sizeDropDown = findViewById(R.id.grid_size);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.gridSizes));
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sizeDropDown.setAdapter(adapter);
        sizeDropDown.setBackgroundColor(0);
        sizeDropDown.setSelected(false);
        sizeDropDown.setSelection(1, false);
        sizeDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gridView.recreateGrid(9, 16);
                }
                else {
                    gridView.recreateGrid(18, 32);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
