package bozlak.java2021.repository.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bozlak.java2021.entities.concretes.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    // ----> Metod isimlendirme kuralları önemli!
    
    // select * from products where product_name = productName;
    List<Product> getByName(String productName);

    // select * from products where product_name = productName and category_id = categoryId limit 1;
    Product getByNameAndCategory(String productName, int categoryId);

    // select * from products where product_name = productName or category_id = categoryId;
    List<Product> getByNameOrCategory(String productName, int categoryId);

    // select * from products where category_id in(1,5,6,8);
    List<Product> getByCategoryIn(List<Integer> categoryIds);

    List<Product> getByNameContains(String productNameSearch);

    List<Product> getByNameStartsWith(String productNameSearch);

    // JPQL :
    @Query("from Product where name = :productName and category.id = :categoryId")
    List<Product> getByProductNameAndCategory(String productName, int categoryId);

}
