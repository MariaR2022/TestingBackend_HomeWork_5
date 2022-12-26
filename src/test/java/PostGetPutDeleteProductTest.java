import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javafaker.Faker;
import api.ProductService;
import api.CategoryService;
import dto.Product;
import dto.GetCategoryResponse;
import enums.CategoryType;

import utils.RetrofitUtils;
import retrofit2.Response;
import okhttp3.ResponseBody;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class PostGetPutDeleteProductTest {
    static CategoryService categoryService;
    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
    int id;
    Integer productId;
    Product productUpdate;
    List<Product> products;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) ((Math.random() +1) * 1000))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
    }

//    @SneakyThrows
//    @AfterEach
//    void tearDown() {
//        Response<ResponseBody> response = productService.deleteProduct(id).execute();
//        assertThat(response.isSuccessful(), CoreMatchers.is(true));
//    }

    //POST запрос. Создание продукта.
    @Test
    void postProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();
        System.out.println(response.body().getId());
        System.out.println(response.body().getTitle());
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    //GET запрос. Получение данных об ID продукта.
    @Test
    void getProductIdPositiveTest() throws IOException {
        //Создаем
        Response<Product> response = productService.createProduct(product).execute();
        id = response.body().getId();
        Response<Product> responseGet = productService.getProduct(id).execute();

        assertThat(responseGet.body().getCategoryTitle(), equalTo(product.getCategoryTitle()));
        assertThat(responseGet.body().getId(), equalTo(id));
        System.out.println(response.body().getId());
        System.out.println(response.body().getTitle());
    }

    //GET запрос. Получение списка продуктов.
    @Test
    void getProductsPositiveTest() throws IOException {

        Response<List<Product>> response = productService.getProducts().execute();
        List<Product> products = response.body();
        System.out.println(products);
        assertThat(response.code(), equalTo(200));
    }

    //PUT. Изменение/Обновление ЦЕНЫ продукта.
    @Test
    void updateProductPriceTest() throws IOException {
        Response<Product> response = productService.createProduct(product).execute();
        productId = response.body().getId();
        System.out.println(response.body().getId());
        System.out.println(response.body().getPrice());

        productUpdate = new Product()
                .withId(productId)
                .withTitle(response.body().getTitle())
                .withPrice((int) ((Math.random() + 1)))
                .withCategoryTitle(CategoryType.FOOD.getTitle());
        Response<Product> responseUpdate = productService.updateProduct(productUpdate).execute();
        System.out.println(responseUpdate.body().getId());
        System.out.println(responseUpdate.body().getPrice());
        assertThat(responseUpdate.code(), equalTo(200));

        Response<Product> responseGet = productService.getProduct(productId).execute();
        System.out.println(responseGet.body().getTitle());
        System.out.println(responseGet.body().getPrice());
        assertThat(responseGet.code(), equalTo(200));
    }

    //DELETE. Удаление продукта.
    @Test
    void tearDown() throws IOException{
        //сначала создаем продукт
        Response<Product> response = productService.createProduct(product)
                .execute();
        id = response.body().getId();
        System.out.println(response.body().getId());
        System.out.println(response.body().getTitle());

        Response<ResponseBody> responseDelete = productService.deleteProduct(id).execute();
        assertThat(responseDelete.isSuccessful(), CoreMatchers.is(true));
    }
}