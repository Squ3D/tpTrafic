

import java.util.ArrayList;
import java.util.List;
//import application.AppliTrafic;

public class Noeud {

   int x;
   int y;
   List<Noeud> noeudsAccessibles;
   List<Arc> arcEntrants;
   List<Arc> arcSortants;
   List<Voiture> cars;
   String id;
   boolean principal;


   Noeud(int _x, int _y) {
      x = _x;
      y = _y;
      principal = true;
      id = "(" + x + ";" + y + ")";
      noeudsAccessibles = new ArrayList<>();
      arcEntrants = new ArrayList<>();
      arcSortants = new ArrayList<>();
      cars = new ArrayList<>();
   }

   Noeud(int _x, int _y, boolean _principal) {
      this(_x, _y);
      principal = _principal;
   }

   @Override
public String toString() {
	return "Noeud [x=" + x + ", y=" + y + ", noeudsAccessibles=" + noeudsAccessibles + ", arcEntrants=" + arcEntrants
			+ ", arcSortants=" + arcSortants + ", cars=" + cars + ", id=" + id + ", principal=" + principal + "]";
}

   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Noeud autre = (Noeud)o;
      return id.equals(autre.id);
   }

   public void addCar(Voiture v) {
      cars.add(v);
      if(cars.size()!=1) {
         cars.forEach(x -> x.setAccident(true));
      }
   }

   /**retrait d'une voiture du carrefour.*/
   public void removeCar(Voiture car) {
      boolean retraitOk = cars.remove(car);
      if(!retraitOk)
         System.out.println("erreur dans le retrait de voiture " + this);
   }

   public double getX() { return x;
   }

   public double getY() { 
	   return y;
	   }

   public List<Arc> getArcSortants() { 
	   return arcSortants;
	   }

   public boolean isPrincipal() {
	   return principal;
	   }

   public void addArcEntrant(Arc arc) {
	   arcEntrants.add(arc);
	   }

   public void addArcSortant(Arc arc) {
	   arcSortants.add(arc);
	   }

   public int getNbCars() {
	   return cars.size();
	   }

public List<Noeud> getNoeudsAccessibles() {
	return noeudsAccessibles;
}

public void setNoeudsAccessibles(List<Noeud> noeudsAccessibles) {
	this.noeudsAccessibles = noeudsAccessibles;
}

public List<Arc> getArcEntrants() {
	return arcEntrants;
}

public void setArcEntrants(List<Arc> arcEntrants) {
	this.arcEntrants = arcEntrants;
}

public List<Voiture> getCars() {
	return cars;
}

public void setCars(List<Voiture> cars) {
	this.cars = cars;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public void setX(int x) {
	this.x = x;
}

public void setY(int y) {
	this.y = y;
}

public void setArcSortants(List<Arc> arcSortants) {
	this.arcSortants = arcSortants;
}

public void setPrincipal(boolean principal) {
	this.principal = principal;
}

}
