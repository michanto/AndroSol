package com.example.androsol;

import java.util.ArrayList;
import java.util.List;

import lib.cards.models.GameProperties;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner gameSpinner = (Spinner) findViewById(R.id.game_spinner);

        final List<GameProperties> props = GameProperties.getGames();
        final List<String> games = new ArrayList<String>();
        for (GameProperties gp : props) {
            games.add(gp.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, games);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gameSpinner.setAdapter(adapter);

        loadGame();
    }

    private AndroidGameBoard gameBoard;
    private AndroidDeck deck;

    protected void loadGame() {
        gameBoard = new AndroidGameBoard(
                (FrameLayout) findViewById(R.id.gamespace));
        deck = new StandardDeck();
        deck.setResources(getResources());
        gameBoard.setDeck(deck);

        GameProperties freeCell = GameProperties.getFreeCell();
        gameBoard.getGame().newGame(freeCell);
        gameBoard.layoutBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    public void sendMessage(View view) {
        final Spinner gameSpinner = (Spinner) findViewById(R.id.game_spinner);
        int idx = gameSpinner.getSelectedItemPosition();
        if (idx >= 0) {
            final List<GameProperties> props = GameProperties.getGames();
            MainActivity.this.gameBoard.gameController.getActions().newGame(
                    props.get(idx), MainActivity.this.gameBoard.getGame());
            // gameBoard.getGame().newGame(props.get(idx));
            // gameBoard.draw();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
        case R.id.action_search:
            openSearch();
            return true;
        case R.id.action_settings:
            openSettings();
            return true;
        case R.id.action_undo:
            this.gameBoard.gameController.getActions().undo();
        case R.id.action_redo:
            this.gameBoard.gameController.getActions().reInvoke();
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void openSearch() {

    }

    public void openSettings() {

    }
}
