

import java.util.ArrayList;
import java.util.List;

public class ReseauRoutier
{
   private static List<Noeud> noeuds = new ArrayList<>();
   private static List<Arc> arcs = new ArrayList<>();
   private static double dimMax;

   public ReseauRoutier(){
   }


   private static void addArcs(Noeud origine, Noeud destination)
   {
      Arc a = new Arc(origine,destination);
      origine.arcSortants.add(a);
      destination.arcEntrants.add(a);
      arcs.add(a);
   }
   
   public static Noeud getNoeud(int x, int y)
   {
      Noeud result = null;
      boolean found = false;
      int i=0;
      int size= noeuds.size();
      Noeud n=null;
      while (i<size && !found)
      {
         n=noeuds.get(i++);
         found = (n.getX()==x && n.getY()==y);
      }
      if (found) result = n;
      return result;
   }
   
 
   public static void creerReseau()
   {
   
      for(int i=0; i<6; i++)
         for(int j=0; j<6; j++)
         {
            if(i==0 && (j==0 || j==5)) continue;
            if(i==5 && (j==0 || j==5)) continue;
            noeuds.add( new Noeud(i,j));
         }
      Noeud o=null;
      Noeud d=null;
      for(int x=0; x<5; x++)
         for(int y=5; y>0; y--)
         {
            o = getNoeud(x,y);
            if(o==null)continue;
            if (y!=0 && y!=5)
            {
               d = getNoeud(x+1,y);
               if(d==null)continue;
               addArcs(o,d);
            }
            if (x!=0 && x!=5)
            {
               d = getNoeud(x,y-1);
               if(d==null)continue;
               addArcs(o,d);
            }
         }
   }
   
   private static void trouverDimMax()
   {
      double max = noeuds.get(0).x;
      for(Noeud n:noeuds)
      {
         if (n!=null)
         {
            if(max<n.x) max = n.x;
            if(max<n.y) max = n.y;
         }
      }
      dimMax = max;
   }
   

   
   public static void setNoeuds(List<Noeud> noeuds) {
	ReseauRoutier.noeuds = noeuds;
}

public static void setArcs(List<Arc> arcs) {
	ReseauRoutier.arcs = arcs;
}

public static void setDimMax(double dimMax) {
	ReseauRoutier.dimMax = dimMax;
}


   
   public static void addNoeud(Noeud n) {noeuds.add(n);}
   public static void addArc(Arc a) {arcs.add(a);}
   public static double getDimMax() {
      ReseauRoutier.trouverDimMax();
      return dimMax;
   }

   public static List<Noeud> getNoeuds() { return noeuds; }
   public static List<Arc> getArcs() { return arcs; }



public String toString() {
	return "ReseauRoutier [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
			+ "]";
}
}


