
import java.util.ArrayList;
import java.util.List;

public class Voiture
{
  
      private int id;
      int x;
      int y;
      private List<Noeud> trajet;
      private List<Noeud> routeRestante;
      private Noeud origine;
      private Noeud destination;
      private boolean pause;
      private boolean arrivee;
      private Noeud currentN;
	   private Noeud nextN;
	   private boolean accident;
	   private boolean bouchon;
	   final static int STOP = 10;
	   private int tpsPanne = 0;
	   public static int conte_id_voiture = 0;
	   private DessinVoiture dessinVoiture;
	   
   

   
   Voiture(){

   }

   Voiture(int id){
      this.id = id;
      trajet = new ArrayList<>();
      routeRestante = new ArrayList<>();
      }

   public Voiture(int no, Noeud origine, Noeud destination) {
      this(no);
      this.origine = origine;
      this.currentN = this.origine;
      x = origine.x;
      y = origine.y;
      this.destination = destination;
      calculerRoute();
      for(Noeud n:trajet)routeRestante.add(n);
       this.nextN = this.currentN;
   }

   public Voiture(int no, int xo, int yo, int xd, int yd) {
      this(no);
      this.origine = ReseauRoutier.getNoeud(xo, yo);
      this.currentN = this.origine;
      x = origine.x;
      y = origine.y;
      this.destination = ReseauRoutier.getNoeud(xd, yd);
      calculerRoute();
      for(Noeud n:trajet)routeRestante.add(n);
       this.nextN = this.currentN;

   }
   
  
   public void calculerRoute()
   {
      boolean enLigne = (origine.getY()==destination.getY());
      Noeud nexto = origine;
      while (!nexto.equals(destination))
      {
         List<Arc> arcSortants = nexto.getArcSortants();
         if(!arcSortants.isEmpty())
         {
            for(Arc arc:arcSortants)
            {
               nexto = arc.getEnd();
               if(nexto==null) System.err.println("pb de nexto : " + arc);
               if (enLigne && nexto.getY()==destination.getY()) break;
               if (!enLigne && nexto.getX()==destination.getX()) break;
            }
            trajet.add(nexto);
         }
         else
         {
            System.err.println("error ID :" + nexto);
            break;
         }
      }
   }
   public void calculernextN()
   {
      
       if (!pause && !accident && !arrivee) {
          
           verifBouchon();
           
           if (!bouchon) {
               if (currentN.equals(destination)) arrivee = true;
               if (nextN.equals(destination)) arrivee = true;
           }
       }
   }
   
 
  public void verifBouchon()
   {
      if(!routeRestante.isEmpty())
      {
         Noeud nexto = routeRestante.get(0);
         if(nexto.getNbCars()==0) setBouchon(false);
         else setBouchon(true);
      }
   }
   
  
   public void allerAunextN()
   {
     
       if ((!pause || !bouchon || !accident) && !routeRestante.isEmpty()) {
          
           if (currentN != nextN) {
               this.currentN.removeCar(this);
           }
           this.nextN.addCar(this);
           this.currentN = this.nextN;
           this.nextN = this.routeRestante.get(0);
           this.routeRestante.remove(0);
       }
   }



   public int getX() { 
      return x;
       }
   public int getY() {
       return y; 
       }
   public boolean isPause() {
      return pause;
       }
   public boolean isArrivee() { 
      return arrivee; }
   public void setPause(boolean pause) 
   { 
      this.pause = pause;

   }

    public Noeud getnextN() {
        return nextN;
    }

    public Noeud getcurrentN() { return currentN; }

    public void setcurrentN(Noeud currentN) {
        this.currentN = currentN;
    }

    public boolean isAccident() { 
       return accident;
        }
   public void setAccident(boolean accident)
   {
      this.accident = accident;
      if (accident)this.pause = true;
   }
   public int getId() {
       return id
       ; }
   public boolean isBouchon() {
       return bouchon;
        }
   public void setBouchon(boolean bouchon) {
       this.bouchon = bouchon;
        }
   
   public void incrementeTpsPanne() {
      tpsPanne++; 
      }
   public boolean isARemorquer() {
       if (this.tpsPanne > Voiture.STOP) {
          return true;
      }
       return false;
   }

    public DessinVoiture getDessinVoiture() {
        return dessinVoiture;
    }

    public void setDessinVoiture(DessinVoiture dessinVoiture) {
        this.dessinVoiture = dessinVoiture;
    }
}
