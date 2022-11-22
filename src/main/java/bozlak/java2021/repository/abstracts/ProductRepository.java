package bozlak.java2021.repository.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import bozlak.java2021.entities.concretes.Product;
import bozlak.java2021.entities.dtos.ProductWithCategoryDto;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    // ----> Metod isimlendirme kuralları önemli!
    
    // select * from products where product_name = productName;
    List<Product> getByName(String productName);

    // select * from products where product_name = productName and category_id = categoryId limit 1;
    Product getByNameAndCategory_Id(String productName, int categoryId);

    // select * from products where product_name = productName or category_id = categoryId;
    List<Product> getByNameOrCategory_Id(String productName, int categoryId);

    // select * from products where category_id in(1,5,6,8);
    List<Product> getByCategory_IdIn(List<Integer> categoryIds);

    List<Product> getByNameContains(String productNameSearch);

    List<Product> getByNameStartsWith(String productNameSearch);

    // JPQL :
    @Query("from Product where name = :productName and category.id = :categoryId")
    List<Product> getByProductNameAndCategory(String productName, int categoryId);

    /**
     * 2021 Java, 9. ders. DTO konusu
     */
    // SQL  : select p.product_id, p.product_name, c.category_id from products p inner join categories c on p.category_id = c.category_id;
    // JPQL : (One to Many gidilmeli join sıralamasında)
    @Query("Select new bozlak.java2021.entities.dtos.ProductWithCategoryDto(p.id,p.name,c.name) From Category c Inner Join c.products p")
    List<ProductWithCategoryDto> getProductWithCategoryDetails();

}
