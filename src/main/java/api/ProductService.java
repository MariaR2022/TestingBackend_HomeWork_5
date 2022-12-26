package api;

import dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.concurrent.Executor;

public interface ProductService {

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @GET("products")
    Call<List<Product>> getProducts();

    @PUT("products")
    Call<Product> updateProduct(@Body Product updateProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

}