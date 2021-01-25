

import java.util.ArrayList;	
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;	
import javafx.animation.KeyValue;	
import javafx.animation.Timeline;	
import javafx.application.Application;	
import javafx.event.EventHandler;	
import javafx.scene.Group;	
import javafx.scene.Scene;	
import javafx.scene.effect.DropShadow;	
import javafx.scene.input.MouseEvent;	
import javafx.scene.paint.Color;	
import javafx.scene.shape.Circle;	
import javafx.scene.shape.Line;	
import javafx.stage.Stage;	
import javafx.util.Duration;	

public class MainClass extends Application implements EventHandler<MouseEvent> {

  

 

   Scene scene;
   long tempo = 500;
   double width = 1000;
   double height = 800;
   double decalage = 50d;
   double max;
   private Group root = new Group();
   List<DessinVoiture> dessinsVoitures;
   ControleTrafic control;


   public void start(Stage primaryStage) {
      dessinsVoitures= new ArrayList<>();
      control = new ControleTrafic();
      max = ReseauRoutier.getDimMax();
      construirePlateauJeu(primaryStage);
   }

   void construirePlateauJeu(Stage primaryStage) {
  
      scene = new Scene(root, 2*decalage + width, 2*decalage + height, Color.WHITE);
      scene.setFill(Color.BLACK);
      dessinEnvironnement(root);
      primaryStage.setTitle("Tp Trafic");
      primaryStage.setScene(scene);
    //-----lancer le timer pour faire vivre la matrice
      primaryStage.show();
      int[] nbTop = {0};
      Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(tempo), 
            event-> {animDeplacement();nbTop[0]++;
               if (nbTop[0] % 2 == 0) {
            	   //Ajout de là voiture à la liste
            	   Voiture v1 = control.addVoiture(control.getR().nextBoolean());
            	  
                   
            	   //Dessin de la voiture
            	   v1.setDessinVoiture(addDessinVoiture(root, decalage, v1));
            	  
            }
            }) );
      littleCycle.setCycleCount(Timeline.INDEFINITE);
      littleCycle.play();
   }



   void dessinEnvironnement(Group troupe) {
      List<Noeud> noeuds = ReseauRoutier.getNoeuds();
      double radius = 1 * decalage / 3;
      for (Noeud noeud:noeuds)
      {
         double cx = decalage + noeud.getX() * (width/max);
         double cy = decalage + noeud.getY() * (height/max);
         Circle c = null;
         if(noeud.isPrincipal())
         {
            c = new Circle(cx, cy, radius);
            c.setFill(Color.GREY);
            c.setOpacity(0.2);
         }
         else
         {
            c = new Circle(cx, cy, radius/3);
            c.setFill(Color.YELLOW);
         }
         troupe.getChildren().add(c);
         c.setSmooth(true);
      }
     
      for (Noeud noeud:noeuds)
      {
         double ox = decalage + noeud.getX() * (width/max);
         double oy = decalage + noeud.getY() * (height/max);
         for (Arc arc:noeud.getArcSortants())
         {
            Noeud dest=arc.getEnd();
            double dx = decalage + dest.getX() * (width/max);
            double dy = decalage + dest.getY() * (height/max);
            Line l = new Line(ox, oy, dx, dy);
            l.setStrokeWidth(6);
            l.setStroke(Color.DARKGOLDENROD);
            troupe.getChildren().add(l);
         }
      }
      List<Voiture> voitures = control.getVoitures();
      for(Voiture v:voitures)
      {
         v.setDessinVoiture(addDessinVoiture(troupe, decalage, v));

      }


   }


   private DessinVoiture addDessinVoiture(Group troupe, double decalage, Voiture v)
   {
      double cx = decalage + v.getX() * width/max;
      double cy = decalage + v.getY() * height/max;
      DessinVoiture dv = new DessinVoiture(cx, cy, 0.1*width/max, v.getId());
      troupe.getChildren().add(dv);
      dv.setSmooth(true);
      dv.setOnMouseClicked(this);
      DropShadow dropShadow = new DropShadow();
      dropShadow.setRadius(5.0);
      dropShadow.setOffsetX(3.0);
      dropShadow.setOffsetY(3.0);
      dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
      dv.setEffect(dropShadow);
      dessinsVoitures.add(dv);
      return dv;
   }


   public void handle(MouseEvent me) {

      Object o = me.getSource();
      if (o instanceof DessinVoiture) {
         DessinVoiture dv = (DessinVoiture) o;
         dv.switchSelected();
         control.pauseVoiture(dessinsVoitures.indexOf(dv));
      }
   }


   private void animDeplacement()
   {
      List<Voiture> voitures = control.getVoitures();
      List<Voiture> ARemorquer = new ArrayList<>();
      this.control.calculerPointsnextos();
      this.control.bougerVoitures();
      int nbVoitures = voitures.size();
      for(int i=0; i<nbVoitures; i++)
      {
         Voiture v = voitures.get(i);
         DessinVoiture dv = v.getDessinVoiture();
         if(!v.isArrivee() && !v.isPause())
         {
            Noeud n = v.getnextN();
            if(n!=null)
            {
               if(!dv.selected)
               {
                  Timeline timeline = new Timeline();
                  double xdest = decalage + n.getX() * width/max;
                  double ydest = decalage + n.getY() * height/max;
                  KeyFrame bougeVoiture = new KeyFrame(new Duration(tempo), 
                        new KeyValue(dv.centerXProperty(), xdest),
                        new KeyValue(dv.centerYProperty(), ydest));
                  timeline.getKeyFrames().add(bougeVoiture);
                  timeline.play();
                  dv.setAnimation(timeline);
               }
            }
         }
         else
         if(v.isAccident())
         {
         // TODO: afficher l'accident (voiture en noir par exemple)
         // TODO: increnement le temps passe en panne
             dv.setFill(Color.BLACK);
             v.incrementeTpsPanne();
         }
         //TODO: si la voiture est arrivee ou est a remorquer (trop de temps passe en panne) la supprimer elle et son dessin
         if(v.isArrivee() || v.isARemorquer())
         {
            //on ajoute la voiture à la liste
             ARemorquer.add(v);
             //on rm
             this.root.getChildren().remove(dv);
         }
      }
      ARemorquer.forEach(voiture -> this.control.removeCar(voiture));
   }

     private DessinVoiture getDessinVoiture(int id)
   {
      DessinVoiture dv = null;
      Optional<DessinVoiture> opt = dessinsVoitures.stream().filter(v->v.getNo()==id).findFirst();
      if(opt.isPresent()) dv = opt.get();
      return dv;
   }


  
   public static void main(String[] args) {
      launch(args);
   }

}
