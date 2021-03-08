package me.xemor.pathfindingvisualizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import me.xemor.pathfindingvisualizer.Algorithms.AlgorithmType;
import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;
import me.xemor.pathfindingvisualizer.MazeGeneration.MazeGenerator;
import me.xemor.pathfindingvisualizer.MazeGeneration.RecursiveBacktracker;
import me.xemor.pathfindingvisualizer.MazeGeneration.SimpleRandomMazeGenerator;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    Button pathfind;
    Button generate;
    Button settings;
    SharedPreferences root_preferences;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    /**
     * The entry point to the program.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.view2);
        createPathfindButton(gridView);
        createStartButton();
        createMainMenuButton();
        createGenerateButton();
        createSettingsButton();
        root_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        listener = (sharedPreferences, key) -> {
            if ("Grid_Size".equals(key)) {
                switch (sharedPreferences.getString("Grid_Size", "9x16")) {
                    case "9x16": gridView.recreateGrid(9, 16); break;
                    case "18x32": gridView.recreateGrid(18, 32); break;
                }
            }
        };
        root_preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * The method called to switch to the SettingActivity
     */
    private void changeToSettingsMenu() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * This method sets the generate button to execute the correct maze generator based on
     * the current state of the configuration.
     * If the "Maze_Preference" field is "BACKTRACKER", it will run the recursive backtracker for example.
     */
    private void createGenerateButton() {
        generate = findViewById(R.id.generate_maze);
        generate.setVisibility(View.GONE);
        generate.setOnClickListener(v -> {
            MazeGenerator mazeGenerator = null;
            switch (root_preferences.getString("Maze_Preference", "SIMPLE")) {
                case "BACKTRACKER": mazeGenerator = new RecursiveBacktracker(gridView); break;
                case "SIMPLE": mazeGenerator = new SimpleRandomMazeGenerator(gridView); break;
            }
            mazeGenerator.generate();
        });
    }

    private void createSettingsButton() {
        settings = findViewById(R.id.settings);
        settings.setOnClickListener(v -> {
            changeToSettingsMenu();
        });
    }

    public void createPathfindButton(GridView gridView) {
        pathfind = findViewById(R.id.pathfind);
        pathfind.setVisibility(View.GONE);
        pathfind.setOnClickListener(v -> gridView.runAlgorithm(Speed.valueOf(root_preferences.getString("Speed", "SLOW")), AlgorithmType.valueOf(root_preferences.getString("Algorithm_Preference", "DIJKSTRAS")), root_preferences.getInt("Heuristic_Weighting", 1)));
    }

    private void createMainMenuButton() {
        Button mainMenu = findViewById(R.id.main_menu);
        mainMenu.setVisibility(View.GONE);
        mainMenu.setOnClickListener(v -> {
            pathfind.setVisibility(View.GONE);
            findViewById(R.id.title2).setVisibility(View.VISIBLE);
            mainMenu.setVisibility(View.GONE);
            findViewById(R.id.Start).setVisibility(View.VISIBLE);
            generate.setVisibility(View.GONE);
            settings.setVisibility(View.VISIBLE);
        });
    }

    private void createStartButton() {
        Button button = findViewById(R.id.Start);
        button.setOnClickListener(v -> {
            button.setVisibility(View.GONE);
            findViewById(R.id.title2).setVisibility(View.GONE);
            pathfind.setVisibility(View.VISIBLE);
            findViewById(R.id.main_menu).setVisibility(View.VISIBLE);
            generate.setVisibility(View.VISIBLE);
            settings.setVisibility(View.INVISIBLE);
        });
    }

}
