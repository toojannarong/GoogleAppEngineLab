package src.com.google.appengine.codelab;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import java.util.List;

/**
 * This class handles all the CRUD operations related to
 * Event entity.
 *
 */
public class Event {

  /**
   * Update the product
   * @param name: name of the product
   * @param dueDate : due date
   * @return  updated product
   */
  public static void createOrUpdateProduct(String name, String dueDate) {
    Entity product = getProduct(name);
  	if (product == null) {
  	  product = new Entity("Event", name);
  	  product.setProperty("dueDate", dueDate);
  	} else {
  	  product.setProperty("dueDate", dueDate);
  	}
  	Util.persistEntity(product);
  }

  /**
   * Retrun all the products
   * @param kind : of kind product
   * @return  products
   */
  public static Iterable<Entity> getAllProducts(String kind) {
    return Util.listEntities(kind, null, null);
  }

  /**
   * Get product entity
   * @param name : name of the product
   * @return: product entity
   */
  public static Entity getProduct(String name) {
  	Key key = KeyFactory.createKey("Event",name);
  	return Util.findEntity(key);
  }

  /**
   * Get all items for a product
   * @param name: name of the product
   * @return list of items
   */
  
  public static List<Entity> getItems(String name) {
  	Query query = new Query();
  	Key parentKey = KeyFactory.createKey("Event", name);
  	query.setAncestor(parentKey);
  	query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN, parentKey);
  		List<Entity> results = Util.getDatastoreServiceInstance()
  				.prepare(query).asList(FetchOptions.Builder.withDefaults());
  		return results;
	}
  
  /**
   * Delete product entity
   * @param productKey: product to be deleted
   * @return status string
   */
  public static String deleteProduct(String productKey)
  {
	  Key key = KeyFactory.createKey("Event",productKey);
	  
	  List<Entity> items = getItems(productKey);	  
	  if (!items.isEmpty()){
	      return "Cannot delete, as there are items associated with this product.";	      
	    }	    
	  Util.deleteEntity(key);
	  return "Event deleted successfully";
	  
  }
}
