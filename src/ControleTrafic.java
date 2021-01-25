

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ControleTrafic
{
   
   List<Voiture> voitures;

   Random r;
   
   public ControleTrafic()
   {
      ReseauRoutier.creerReseau();
      voitures = new ArrayList<>();
      r = new Random();
   
   }
   
  
   public Voiture addVoiture(boolean ligne)
   {
      Voiture v = null;
      Noeud debut = null;
      Noeud fin = null;
       int n = (int) ReseauRoutier.getDimMax();
       int r= this.r.nextInt(n-1);
       r +=1;
       if (ligne) {
           debut = ReseauRoutier.getNoeud(0, r);
           fin = ReseauRoutier.getNoeud(n, r);
       } else {
           debut = ReseauRoutier.getNoeud(r, n);
           fin = ReseauRoutier.getNoeud(r, 0);
       }
       v = new Voiture(Voiture.conte_id_voiture++, debut, fin);
      voitures.add(v);
      return v;
   }


   public List<Voiture> removeCar(Voiture v)
   {

      v.getcurrentN().removeCar(v);
      voitures.remove(v);
      return voitures;
   }

   public void calculerPointsnextos() {
      for(Voiture v:voitures)
      {
      
         if(v.isBouchon())v.verifBouchon();
         if(!v.isArrivee() && !v.isPause())
         {
            v.calculernextN();
         }
      }
   }
   
   /**demande � chaque voiture de bouger (se retirer du noeud courant)*/
   public void bougerVoitures()
   {
      for(Voiture v:voitures)
      {
         if(!v.isPause())
         {
            v.allerAunextN();
         }
      }
   }
   

   public void pauseVoiture(int i)
   {
      Voiture v = this.getVoitureById(i);
      v.setPause(!v.isPause());
   }
   
  
   public List<Voiture> getVoitures()
   {
      return voitures;
   }

   public Random getR() {
      return r;
   }

   private Voiture getVoitureById(int id){
      final Voiture[] res = new Voiture[1];
      this.voitures.forEach(voiture -> {
         if (voiture.getId() == id){
            res[0] = voiture;
         }
      });
      return res[0];
   }
}
