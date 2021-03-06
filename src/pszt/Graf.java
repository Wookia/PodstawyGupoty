package pszt;

import java.util.ArrayList;

import com.sun.glass.events.MouseEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Graf {
	private
	Scene scene;
	Pane pane;
	Stage stage;
	Widok widok;
	ScrollPane scroll;
	public
	Graf(Scene scene, Pane pane, Stage stage, Widok widok, ScrollPane scroll){
		this.scene=scene;
		this.pane=pane;
		this.stage=stage;
		this.widok=widok;
		this.scroll=scroll;
	}
	void tworzGraf(Log log){
		int iloscIter = 0;
		int maxIloscKlauzul = 0;
		ArrayList<Integer> iloscIteracji = log.iloscKlauzulNaIteracje();
		ArrayList<Integer> dlugoscKlauzul = log.dlugoscKlauzulNaIteracje();
		for(Integer i:iloscIteracji){
			if(maxIloscKlauzul<i)maxIloscKlauzul=i;
			iloscIter++;
		}
		final ArrayList<GrafButton> listaKlauzul = new ArrayList<GrafButton>();
		final ArrayList<Pair<Integer, GrafLine>> mapaLinii =  new ArrayList<Pair<Integer, GrafLine>>();
		double max=log.maxWidth()*12+10*maxIloscKlauzul+100;
		pane.getChildren().clear();
		if(max>1000)stage.setWidth(1000+10);
		else stage.setWidth(max+10);
		if(iloscIter*100>600)stage.setHeight(600+10);
		else stage.setHeight(iloscIter*100+10);
		scroll.setHbarPolicy(ScrollBarPolicy.ALWAYS);
	 	scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		pane.setPrefSize(max, iloscIter*100);
		stage.centerOnScreen();
		for(int i = 0; i<iloscIter; i++){
			Text iter = new Text();
			iter.setText("n = " + i);
			iter.setTranslateX(5);
			iter.setTranslateY(10+i*100);
			pane.getChildren().add(iter);
			double last=100;
			double kon= (max-100-dlugoscKlauzul.get(i)*12)/iloscIteracji.get(i);
			for(LogKlauzula klauzula: log.getLog()){
				if(klauzula.getIter()==i){
					final GrafButton klauz = new GrafButton();
					if(log.czyTeza(klauzula)){
						klauz.setKolor(Color.BLUE);
						if(klauzula.czyUzasadnien)klauz.setStyle("-fx-base: #b6e7c9;");
					}
					else if(klauzula.czyUzasadnien){
						klauz.setStyle("-fx-base: #b6e7c9;");
					}
					klauz.setText(klauzula.getDziecko().getNazwa());
					klauz.setMinHeight(20);
					klauz.setMinWidth(10*klauz.getText().length());
					klauz.setTranslateX(last);
					klauz.setTranslateY(i*100);
					final Klauzula rodzic1 = klauzula.getRodzic1();
					final Klauzula rodzic2 = klauzula.getRodzic2();
					klauz.setOnAction(new EventHandler<ActionEvent>() {
			            @Override
			            public void handle(ActionEvent event) {
			        		for(GrafButton button: listaKlauzul){
			        			button.odswietl();
			        		}
			        		klauz.podswietl();

			        		for(Pair<Integer, GrafLine> pair: mapaLinii){
			        			if(pair.getKey().equals(listaKlauzul.indexOf(klauz))){
			        				pair.getValue().podswietl();
			        			}
			        			else pair.getValue().odswietl();
			        		}
			        		
							if(rodzic1!=null && rodzic2!=null){

								for(GrafButton text: listaKlauzul){
									if(rodzic1.getNazwa().equals(text.getText())){
										text.podswietl();
									}
									if(rodzic2.getNazwa().equals(text.getText())){
										text.podswietl();
									}
								}
							
							}
			        		
			            }
			        });
					last = last + klauz.getText().length()*10 + kon;
					listaKlauzul.add(klauz);
					if(rodzic1!=null && rodzic2!=null){
						boolean check = false;
						for(GrafButton text: listaKlauzul){
							if(rodzic1.getNazwa().equals(text.getText())){
								scene.snapshot(null);
								GrafLine line = new GrafLine(klauz.getTranslateX()+klauz.getMinWidth()/2, klauz.getTranslateY(),
										text.getTranslateX()+text.getMinWidth()/2, text.getTranslateY()+text.getMinHeight());

								pane.getChildren().add(line);
								mapaLinii.add(new Pair<Integer, GrafLine>(listaKlauzul.indexOf(klauz), line));
								
								if(check)break;
								check=true;
							}
							else if(rodzic2.getNazwa().equals(text.getText())){
								scene.snapshot(null);
								GrafLine line = new GrafLine(klauz.getTranslateX()+klauz.getMinWidth()/2, klauz.getTranslateY(),
										text.getTranslateX()+text.getMinWidth()/2, text.getTranslateY()+text.getMinHeight());

								pane.getChildren().add(line);
								mapaLinii.add(new Pair<Integer, GrafLine>(listaKlauzul.indexOf(klauz), line));
								
								if(check)break;
								check=true;
							}
						}
					}
					kon++;
				}
			}
			
		}
		for (GrafButton klauz: listaKlauzul){
			pane.getChildren().add(klauz);

		}
		Button btn6 = new Button();
        btn6.setText("Wroc do wyboru");
        btn6.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	widok.wybierzMetode();
            	scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
    		 	scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
            }
        });
        btn6.setTranslateX(max-125);
        btn6.setTranslateY(iloscIter*100-70);
        pane.getChildren().add(btn6);
	}
}
