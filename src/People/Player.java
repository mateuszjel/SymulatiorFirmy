package People;

import Game.Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    final private ArrayList<Game.Technology> technologies = new ArrayList<Game.Technology>(
            Arrays.asList(
                    Game.Technology.BACKEND,
                    Game.Technology.DATABASE,
                    Game.Technology.FRONTEND,
                    Game.Technology.PRESTASHOP,
                    Game.Technology.WORDPRESS
            )
    );

}
