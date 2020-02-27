package org.lwjglb.engine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_I;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Renderer;
import org.lwjglb.engine.graph.lights.DirectionalLight;
import org.lwjglb.engine.graph.lights.PointLight;
import org.lwjglb.engine.graph.weather.Fog;
import org.lwjglb.engine.items.OpenGLItem;
import org.lwjglb.engine.items.SkyBox;
import org.lwjglb.engine.loaders.assimp.StaticMeshesLoader;

import dev.benjaminc.chef_simulator.Game;
import dev.benjaminc.chef_simulator.data.DataMap.DataMapKey;
import dev.benjaminc.chef_simulator.data.location.Location3d;
import dev.benjaminc.chef_simulator.things.Thing;
import dev.benjaminc.chef_utils.graphics.Texture;

public class OpenGLEngine implements Runnable {

	private static final float MOUSE_SENSITIVITY = 0.2f;

	private final Vector3f cameraInc;

	private final Renderer renderer;

	private final Camera camera;

	public Scene scene;

//    private Hud hud;

	private static final float CAMERA_POS_STEP = 0.40f;

	private float angleInc;

	private float lightAngle;

	private boolean firstTime;

	private boolean sceneChanged;

	private Vector3f pointLightPos;

	public static final int TARGET_FPS = 75;

	public static final int TARGET_UPS = 30;

	private final Window window;

	private final Timer timer;

	private final MouseInput mouseInput;

	private double lastFps;

	private int fps;

	private String windowTitle;

	private Mesh[] houseMesh;
	private Mesh[] bunnyMesh;
	
	private OpenGLItem bunny;

	private Map<String, Mesh[]> meshes;
	
	private boolean ready = false;
	
	private List<Runnable> todo;
	
	public OpenGLEngine(String windowTitle, boolean vSync, Window.WindowOptions opts)
			throws Exception {
		this(windowTitle, 0, 0, vSync, opts);
	}

	public OpenGLEngine(String windowTitle, int width, int height, boolean vSync, Window.WindowOptions opts) throws Exception {
//    	hud = new Hud();
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
		angleInc = 0;
		lightAngle = 90;
		firstTime = true;

		this.windowTitle = windowTitle;
		window = new Window(windowTitle, width, height, vSync, opts);
		mouseInput = new MouseInput();
		timer = new Timer();
		
		todo = new ArrayList<Runnable>();
		
		meshes = new HashMap<String, Mesh[]>();
	}

	@Override
	public void run() {
		try {
			init();
			gameLoop();
		} catch (Exception excp) {
			excp.printStackTrace();
		} finally {
			cleanup();
		}
	}

	private void setupLights() {
		SceneLight sceneLight = new SceneLight();
		scene.setSceneLight(sceneLight);

		// Ambient Light
		sceneLight.setAmbientLight(new Vector3f(1.0f, 1.0f, 1.0f));
		sceneLight.setSkyBoxLight(new Vector3f(0.0f, 0.0f, 0.0f));

		// Directional Light
		float lightIntensity = 0.0f;
		Vector3f lightDirection = new Vector3f(0, 1, 1);
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightDirection, lightIntensity);
		sceneLight.setDirectionalLight(directionalLight);

		pointLightPos = new Vector3f(0.0f, 25.0f, 0.0f);
		Vector3f pointLightColour = new Vector3f(0.0f, 0.0f, 0.0f);
		PointLight.Attenuation attenuation = new PointLight.Attenuation(1, 0.0f, 0);
		PointLight pointLight = new PointLight(pointLightColour, pointLightPos, lightIntensity, attenuation);
		sceneLight.setPointLightList(new PointLight[] { pointLight });
	}

	/**
	 * Makes a new Bunny
	 * @param xp	the float x position
	 * @param yp	the float y position
	 * @param zp	the float z position
	 * @param xr	the float x rotation
	 * @param yr	the float y rotation
	 * @param zr	the float z rotation
	 */
	public OpenGLItem newBunny(float xp, float yp, float zp, float xr, float yr, float zr) {
		OpenGLItem b = new OpenGLItem(bunnyMesh);
		b.setPosition(xp, yp, zp);
		b.setRotation(b.getRotation().rotationXYZ(xr, yr, zr));
		b.setScale(1f);
		scene.addGameItem(b);
		return b;
	}
	
	private static final String TEXTURE_ROOT_DIR = "assets/carrotgraphics/textures/";
	
	public void newThing(Thing t, Location3d loc) {
		Texture tx = (Texture) (t.getDataMap().get(DataMapKey.TEXTURE));
		final String type = tx.getFilename().substring(0, tx.getFilename().lastIndexOf(".")) + ".obj";
		final int nx = loc.getX();
		final int ny = loc.getY();
		final int nz = loc.getZ();
		mod(new Runnable() {
			@Override public void run() {
				t.getDataMap().put(DataMapKey.TEXTURE_OPENGL, newItem(type, nx, nz, ny, 0.0f, 0.0f, 0.0f));
			}
		}); 
	}
	
	/**
	 * Makes a new Bunny
	 * @param xp	the float x position
	 * @param yp	the float y position
	 * @param zp	the float z position
	 * @param xr	the float x rotation
	 * @param yr	the float y rotation
	 * @param zr	the float z rotation
	 */
	public OpenGLItem newItem(String type, float xp, float yp, float zp, float xr, float yr, float zr) {
		loadMesh(type);
		OpenGLItem b = new OpenGLItem(meshes.get(type));
		b.setPosition(xp, yp, zp);
		b.setRotation(b.getRotation().rotationXYZ(xr, yr, zr));
		b.setScale(1f);
		scene.addGameItem(b);
		return b;
	}
	
	public void loadMesh(String name) {
		loadMesh(name, false);
	}
	public void loadMesh(String name, boolean force) {
		String loc = TEXTURE_ROOT_DIR + name;
		if(!meshes.containsKey(name) || force) {
			String dir = TEXTURE_ROOT_DIR + name.substring(0, name.lastIndexOf("/"));
			try {
				loc = TEXTURE_ROOT_DIR + name;
				meshes.put(name, StaticMeshesLoader.load(loc, dir));
			} catch (Exception e) {
				e.printStackTrace();
				try {
					meshes.put(name, StaticMeshesLoader.load("models/cube.obj", "models"));
				} catch (Exception e1) {
					System.out.println("The cube default texture could not be found.");
					e1.printStackTrace();
				}
			}
		}
	}
	
	protected void init() throws Exception {
		window.init();
		timer.init();
		mouseInput.init(window);
//    	hud.init(window);

		renderer.init(window);

		scene = new Scene();

		bunnyMesh = StaticMeshesLoader.load("assets/carrotgraphics/textures/building/belt.obj", "assets/carrotgraphics/textures/building");
		bunny = new OpenGLItem(bunnyMesh);
//		bunny.setPosition(-1.0f, 1.0f, 4.0f);
//		bunny.setRotation(bunny.getRotation().rotationXYZ(0.0f, (float) Math.toRadians(200), 0.0f));

//		Mesh[] terrainMesh = StaticMeshesLoader.load("models/terrain/terrain.obj", "models/terrain");
//		GameItem terrain = new GameItem(terrainMesh);
//		terrain.setScale(100.0f);

		scene.setGameItems(new OpenGLItem[] { });

//		houseMesh = StaticMeshesLoader.load("models/compass.obj", "models");

		// Shadows
        scene.setRenderShadows(false);

		// Fog
//		Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
//        scene.setFog(new Fog(true, fogColour, 0.02f));

		// Setup SkyBox
//		float skyBoxScale = 100.0f;
//		SkyBox skyBox = new SkyBox("models/skybox.obj", new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
//		skyBox.setScale(skyBoxScale);
//        scene.setSkyBox(skyBox);

		// Setup Lights
		setupLights();

//		camera.getPosition().x = -17.0f;
//		camera.getPosition().y = 17.0f;
//		camera.getPosition().z = -30.0f;
//		camera.getRotation().x = 20.0f;
//		camera.getRotation().y = 140.f;
//		camera.getRotation().z = 45.0f;

		camera.getPosition().x = -16.0f;
		camera.getPosition().y = 4.0f;
		camera.getPosition().z = 8.0f;
		camera.getRotation().x = 0.0f;
		camera.getRotation().y = 90.0f;
		camera.getRotation().z = 0.0f;
		lastFps = timer.getTime();
		fps = 0;
		
		ready = true;
	}

	public boolean isReady() {
		return ready;
	}
	
	public void mod(Runnable r) {
		if(!rendering) {
			r.run();
		} else {
			todo.add(r);
		}
	}
	public void moveItem(Thing t, Location3d newloc) {
		Game.openglEngine.mod(new Runnable() {
			@Override public void run() {
				((OpenGLItem) t.getDataMap().get(DataMapKey.TEXTURE_OPENGL)).setPosition(newloc.getX(), newloc.getZ(), newloc.getY());
			}
		});
	}
	
	private boolean rendering = false;
	
	protected void gameLoop() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;

		boolean running = true;
		while (running && !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			input();

			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
			}

			rendering = true;
			render();
			rendering = false;

			if(todo.size() > 0) {
				for(Runnable r : todo) {
					r.run();
				}
				todo.clear();
			}
			if (!window.isvSync()) {
				sync();
			}
		}
	}

	protected void cleanup() {
		renderer.cleanup();
//      hud.cleanup();
		scene.cleanup();
	}

	private void sync() {
		float loopSlot = 1f / TARGET_FPS;
		double endTime = timer.getLastLoopTime() + loopSlot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
			}
		}
	}

	protected void input() {
		mouseInput.input(window);
		sceneChanged = false;
		cameraInc.set(0, 0, 0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			sceneChanged = true;
			cameraInc.z = -1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			sceneChanged = true;
			cameraInc.z = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_A)) {
			sceneChanged = true;
			cameraInc.x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			sceneChanged = true;
			cameraInc.x = 1;
		}
		if (window.isKeyPressed(340)) { // Left Shift
			sceneChanged = true;
			cameraInc.y = -1;
		} else if (window.isKeyPressed(32)) { // Space
			sceneChanged = true;
			cameraInc.y = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_LEFT)) {
			sceneChanged = true;
			angleInc -= 0.05f;
		} else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
			sceneChanged = true;
			angleInc += 0.05f;
		} else {
			angleInc = 0;
		}
		if (window.isKeyPressed(GLFW_KEY_UP)) {
			sceneChanged = true;
			pointLightPos.y += 0.5f;
		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			sceneChanged = true;
			pointLightPos.y -= 0.5f;
		}
		if (window.isKeyPressed(GLFW_KEY_P)) {
			System.out.println("New House!");
			OpenGLItem house = new OpenGLItem(houseMesh);
			house.setPosition((16 * (float) Math.random())-8+bunny.getPosition().x,
					(16 * (float) Math.random())-8+bunny.getPosition().y,
					(16 * (float) Math.random())-8+bunny.getPosition().z);
			scene.addGameItem(house);
		}
		if (window.isKeyPressed(GLFW_KEY_I)) {
			bunny.setRotation(bunny.getRotation().rotateX((float) Math.toRadians(-5)));
		}
		if (window.isKeyPressed(GLFW_KEY_O)) {
			bunny.setRotation(bunny.getRotation().rotateX((float) Math.toRadians(5)));
		}
		if (window.isKeyPressed(GLFW_KEY_J)) {
			bunny.setRotation(bunny.getRotation().rotateY((float) Math.toRadians(-5)));
		}
		if (window.isKeyPressed(GLFW_KEY_K)) {
			bunny.setRotation(bunny.getRotation().rotateY((float) Math.toRadians(5)));
		}
		if (window.isKeyPressed(GLFW_KEY_N)) {
			bunny.setRotation(bunny.getRotation().rotateZ((float) Math.toRadians(5)));
		}
		if (window.isKeyPressed(GLFW_KEY_M)) {
			bunny.setRotation(bunny.getRotation().rotateZ((float) Math.toRadians(-5)));
		}
		if (window.isKeyPressed(84)) { // T
			bunny.setPosition(bunny.getPosition().x, bunny.getPosition().y, bunny.getPosition().z-.05f);
		}
		if (window.isKeyPressed(71)) { // G
			bunny.setPosition(bunny.getPosition().x, bunny.getPosition().y, bunny.getPosition().z+.05f);
		}
		if (window.isKeyPressed(70)) { // F
			bunny.setPosition(bunny.getPosition().x-.05f, bunny.getPosition().y, bunny.getPosition().z);
		}
		if (window.isKeyPressed(72)) { // H
			bunny.setPosition(bunny.getPosition().x+.05f, bunny.getPosition().y, bunny.getPosition().z);
		}
		if (window.isKeyPressed(89)) { // Y
			bunny.setPosition(bunny.getPosition().x, bunny.getPosition().y+.05f, bunny.getPosition().z);
		}
		if (window.isKeyPressed(82)) { // R
			bunny.setPosition(bunny.getPosition().x, bunny.getPosition().y-.05f, bunny.getPosition().z);
		}
		if (window.isKeyPressed(66)) { // B
			bunny.setScale(bunny.getScale() + .25f);;
		}
		if (window.isKeyPressed(86)) { // R
			bunny.setScale(bunny.getScale() - .25f);;
		}
	}

	protected void update(float interval) {
		if (mouseInput.isRightButtonPressed()) {
			// Update camera based on mouse
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
			sceneChanged = true;
		}

		// Update camera position
		camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP,
				cameraInc.z * CAMERA_POS_STEP);

		lightAngle += angleInc;
		if (lightAngle < 0) {
			lightAngle = 0;
		} else if (lightAngle > 180) {
			lightAngle = 180;
		}
		float zValue = (float) Math.cos(Math.toRadians(lightAngle));
		float yValue = (float) Math.sin(Math.toRadians(lightAngle));
		Vector3f lightDirection = this.scene.getSceneLight().getDirectionalLight().getDirection();
		lightDirection.x = 0;
		lightDirection.y = yValue;
		lightDirection.z = zValue;
		lightDirection.normalize();

		// Update view matrix
		camera.updateViewMatrix();
	}

	protected void render() {
		if (window.getWindowOptions().showFps && timer.getLastLoopTime() - lastFps > 1) {
			lastFps = timer.getLastLoopTime();
			window.setWindowTitle(windowTitle + " - " + fps + " FPS");
			fps = 0;
		}
		fps++;
		if (firstTime) {
			sceneChanged = true;
			firstTime = false;
		}
		renderer.render(window, camera, scene, sceneChanged);
//        hud.render(window);
		window.update();
	}

}
