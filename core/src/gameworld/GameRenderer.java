package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    public GameRenderer(GameWorld world) {
        this.myWorld = world;
        cam = new OrthographicCamera();

        //arg 1: yes to ortho cam (to view things in 2D); arg 2: width of cam; arg 3: height of cam;
        cam.setToOrtho(true, 136, 204);
        shapeRenderer = new ShapeRenderer();
        //attach the shapeRenderer to our camera
        shapeRenderer.setProjectionMatrix(cam.combined);

    }

    public void render() {
        Gdx.app.log("GameRenderer", "render");

        //1. draw background to prevent flickering
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //2. draw the filled rectangle
        //tell shapeRenderer to begin drawing filled shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //choose RGB colour of 87, 109, 120 at full opacity
        shapeRenderer.setColor(87/255.0f, 109/255.0f, 120/255.0f, 1);

        //draws the rectangle from myWorld
//        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y, myWorld.getRect().width, myWorld.getRect().height);

        //tells shapeRenderer to finish rendering (IMPORTANT; must be done every time)
        shapeRenderer.end();

        //3. draw the rectangle's outline
        //tells shapeRenderer to draw outline
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        //choose colour
        shapeRenderer.setColor(255/255.0f, 109/255.0f, 120/255.0f, 1);

        //draws rectangle from myWorld
//        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y, myWorld.getRect().width, myWorld.getRect().height);

        shapeRenderer.end();

    }
}
