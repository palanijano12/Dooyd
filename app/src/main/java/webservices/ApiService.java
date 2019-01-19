package webservices;

import datamodel.MainItem;
import datamodel.ProfileItem;
import datamodel.SlideItem;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiService {

    @POST
    Call<List<MainItem>> getItems(@Url String url);

    @GET("Slider/GetCustomerSlider")
    Call<List<SlideItem>> getSlideImages();

    @POST("Customer/InsertCustomerProfile")
    Call<String> addCustomerProfile(@Body RequestBody rawData);

    @POST("Login/ValidateLogin")
    Call<String> validateCustomerLogin(@Body RequestBody rawData);

    @GET("Customer/GetCustomerProfile")
    Call<ProfileItem> getCustomerProfile(@Header("Authorization") String value);

}
