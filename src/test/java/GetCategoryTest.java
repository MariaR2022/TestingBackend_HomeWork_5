import api.CategoryService;
import dto.GetCategoryResponse;
import enums.CategoryType;
import utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import static enums.CategoryType.FOOD;
import static enums.CategoryType.ELECTRONICS;

public class GetCategoryTest {

    static CategoryService categoryService;
    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositive1Test() {
        Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo("Food"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Food")));
        System.out.println(response.body().getTitle());
    }

    @SneakyThrows
    @Test
    void getCategoryByIdPositive2Test() {
        Response<GetCategoryResponse> response = categoryService.getCategory(2).execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(2));
        assertThat(response.body().getTitle(), equalTo("Electronic"));
        response.body().getProducts().forEach(product ->
                assertThat(product.getCategoryTitle(), equalTo("Electronic")));
        System.out.println(response.body().getTitle());
    }

    @Test
    void getFoodCategoryPositiveTest() throws IOException {
        Response<GetCategoryResponse> response = categoryService
                .getCategory(FOOD.getId())
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(1));
        assertThat(response.body().getTitle(), equalTo(FOOD.getTitle()));
        System.out.println(response.body().getId());
        System.out.println(response.body().getTitle());
        System.out.println(response.body().getProducts());
    }
    @Test
    void getElectronicsCategoryPositiveTest() throws IOException {
        Response<GetCategoryResponse> response = categoryService
                .getCategory(ELECTRONICS.getId())
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getId(), equalTo(2));
        assertThat(response.body().getTitle(), equalTo(ELECTRONICS.getTitle()));
        System.out.println(response.body().getId());
        System.out.println(response.body().getTitle());
    }

}