package webservices;

import datamodel.MainItem;
import datamodel.SlideItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

import java.util.List;

public interface ApiService {

    @POST
    Call<List<MainItem>> getItems(@Url String url);

    @GET("Slider/GetCustomerSlider")
    Call<List<SlideItem>> getSlideImages();

}
