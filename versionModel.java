package lab1;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lab1.wordDirectedGraph.edge;

public class versionModel extends Application {
	private Scene scene;
	private Browser brow = new Browser(this);
	MenuBar menuBar = new MenuBar();
	private computeModel kernel = new computeModel();
	private Vector<String> randomWalkCache = new Vector<String>();
	private Integer cacheIndex;
	private versionModel app = this;
	final TextField textField=new TextField();
	private String oldfile = null;
	Button walkBtn=new Button("random");
	@Override 
	public void start(Stage stage) throws IOException {
        // create the scene
        stage.setTitle("Lab1");
        Group group=new Group();
        scene = new Scene(group);
        stage.setScene(scene);  
        Button okBtn=new Button("go");
        Button bridgeBtn=new Button("bridge");
        Button pathBtn=new Button("path");
        Button textBtn=new Button("newtext");
        Button fixBtn=new Button("fix");
        Button saveBtn=new Button("save");
        Slider slider = new Slider();  
        slider.setMin(0);  
        slider.setMax(1);
        slider.setValue(0.5);
        HBox hbox=new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(slider, textField,okBtn, bridgeBtn, pathBtn, walkBtn, textBtn, saveBtn, fixBtn);
        HBox.setHgrow(textField, Priority.ALWAYS);
        VBox vBox=new VBox();
        
        vBox.getChildren().addAll(hbox,brow.browser);
        VBox.setVgrow(brow.browser, Priority.ALWAYS);
        group.getChildren().add(vBox);
        slider.valueProperty().addListener(  
                (ObservableValue<? extends Number> ov, Number old_val,   
                Number new_val) -> {  
                    executeScript(String.format("inputted(%f)", new_val));
            });  
        EventHandler<ActionEvent> handler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	app.showDirectedGraph(textField.getText());
            }
        };
        EventHandler<ActionEvent> brhandler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String word1 = (String) executeScript("src");
            	String word2 = (String) executeScript("tar");
            	queryBridgeWords(word1, word2);
            }
        };
        EventHandler<ActionEvent> sphandler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String word1 = (String) executeScript("src");
            	String word2 = (String) executeScript("tar");
            	calcShortestPath(word1, word2);
            }
        };
        EventHandler<ActionEvent> savehandler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Image image = brow.browser.snapshot(null, null);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
                            new File(oldfile+".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        EventHandler<ActionEvent> inithandler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (walkBtn.getText() == "random") {
            		randomWalk();
            		if (randomWalkCache.size() == 0) {
            			showText("no random walk text");
            			return;
            		}
            		showText(oldfile);
            		walkBtn.setText("next");
            	} else {
            		if (cacheIndex == randomWalkCache.size())
            			walkBtn.setText("random");
            		randomWalkNext();
            	}
            }
        };
        EventHandler<ActionEvent> texthandler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String text = textField.getText();
            	textField.setText(generateNewText(text));
            }
        };
        EventHandler<ActionEvent> fixhandler= new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	fixall();
            }
        };
        brow.browser.setOnDragOver(new EventHandler<DragEvent>() {  
            @Override  
            public void handle(DragEvent event) {  
                if (event.getGestureSource() != brow.browser) {  
                    event.acceptTransferModes(TransferMode.ANY);  
                }                 
            }  
        });  
        brow.browser.setOnDragDropped(new EventHandler<DragEvent>() {  
            @Override  
            public void handle(DragEvent event) {  
                File file = event.getDragboard().getFiles().get(0);  
                showText(file.getAbsolutePath());
                showDirectedGraph(file.getAbsolutePath());
            }  
        }); 
        saveBtn.setOnAction(savehandler);
        pathBtn.setOnAction(sphandler);
        textField.setOnAction(handler);
        okBtn.setOnAction(handler);
        bridgeBtn.setOnAction(brhandler);
        walkBtn.setOnAction(inithandler);
        textBtn.setOnAction(texthandler);
        fixBtn.setOnAction(fixhandler);
        stage.show();
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	void showDirectedGraph(String file) {
		clearOld();
		clean();
		try {
			addGraph(kernel.getGraph(file));
			oldfile = file;
		} catch (IOException e) {
			try {
				if (oldfile != null)
					addGraph(kernel.getGraph(oldfile));
				showText("no such file");
			} catch (IOException e1) {
				//no problem
			}
			showText("no such file");
		}
	}
	void queryBridgeWords(String word1, String word2) {
		clearOld();
		Vector<String> bridgeWords = kernel.getBridgeWord(word1, word2);
		if (bridgeWords.size() == 0) {
			showText("no bridge word");
			return;
		}
		showText(oldfile);
		for (int i = 0; i < bridgeWords.size(); i++) {
			walk(word1, bridgeWords.get(i));
			walk(bridgeWords.get(i), word2);
			focusNode(bridgeWords.get(i));
		}
		selectNode(null);
	}
	String generateNewText(String inputText) {
		clearOld();
		String newText = kernel.generateNewText(inputText);
		return newText;
	}
	void calcShortestPath(String word1, String word2) {
		clearOld();
		Vector<String> path = kernel.getShortestPath(word1, word2);
		if (path.size() == 0) {
			showText("no path");
			return;
		}
		showText(oldfile);
		for (int i = 1; i < path.size(); i++) {
			walk(path.get(i - 1), path.get(i));
		}
		selectNode(null);
	}
	void randomWalk() {
		clearOld();
		randomWalkCache = kernel.getRandomWalkText();
		cacheIndex = 1;
		if (randomWalkCache.size() > 0) {
			selectNode(randomWalkCache.get(0));
		}
	}
	void randomWalkNext() {
		if (cacheIndex < randomWalkCache.size()) {
			walk(randomWalkCache.get(cacheIndex-1), randomWalkCache.get(cacheIndex));
			selectNode(randomWalkCache.get(cacheIndex));
			cacheIndex += 1;
		}
	}
	void clearOld() {
		randomWalkCache.clear();
		cacheIndex = 0;
		walkBtn.setText("random");
		cleanNode();
		cleanWalkPath();
	}
	public Object executeScript(String js) {
		return brow.webEngine.executeScript(js);
	}
	
	public void addNode(String id) {
		executeScript(String.format("addNode('%s')", id));
	}
	public void addLink(String src, String tar, Integer wgt) {
		executeScript(String.format("addLink('%s', '%s', %d)", src, tar, wgt));
	}
	public void addGraph(wordDirectedGraph graph) {
		for (String node : graph.getWordArr()) {
			addNode(node);
		}
		for (edge link: graph.getEdgeArr()) {
			addLink(link.src, link.des, link.weight);
		}
	}
	public void walk(String src, String tar) {
		executeScript(String.format("walkPath('%s', '%s')", src, tar));
	}
	public void cleanWalkPath() {
		executeScript(String.format("cleanPath()"));
	}
	public void selectNode(String node) {
		executeScript(String.format("selectNode('%s')", node));
	}
	public void selectBridgeWords() {
		executeScript(String.format("bridgeword()"));
	}
	public void focusNode(String node) {
		executeScript(String.format("focusNode('%s')", node));
	}
	public void cleanNode() {
		executeScript(String.format("cleanNode()"));
	}
	public void clean() {
		executeScript(String.format("clean()"));
	}
	public void fixall() {
		executeScript(String.format("fixall()"));
	}
	public void showText(String text) {
		textField.setText(text);
	}
}

class Browser extends Region {
    public final WebView browser = new WebView();
    public final WebEngine webEngine = browser.getEngine();
    public versionModel app;
    
    public Browser(versionModel owr) {
    	app = owr;
    	getStyleClass().add("browser");
        String url = getClass().getResource("index.html").toExternalForm();
        webEngine.load(url);
        getChildren().add(browser);
    }
}
//name is