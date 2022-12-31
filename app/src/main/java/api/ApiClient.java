package api;

import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBooking;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBookingDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentBookingUpdateDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.AppointmentInfoDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.ImageDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.PatientDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.SignInDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.SignUpPatienceDto;
import com.elijah.ukeme.doctorsappointmentapplication.response.ApiResponse;
import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiClient {



    //@Headers("Content-Type: application/json")
    @POST("patient/signUp")
    Call<ApiResponse> createPatient(@Body SignUpPatienceDto signUpPatienceDto);

    @POST("patient/getInfo")
    Call<PatientDto> signInPatient(@Body SignInDto signInDto);

    @Multipart
    @POST("profileImage/upload")
    Call<ImageDto> uploadProfileImage(@Part MultipartBody.Part image);

    @GET("/appointment/list/all")
    Call<ResponseBody> getAllPatient();

    @GET("/appointment/info")
    Call<JsonElement> getAppointmentInfo(@Query("token") String token);

    @POST("appointment/book")
    Call<ApiResponse> bookAppointment(@Body AppointmentBookingDto appointmentBookingDto, @Query("token") String token);

    @PUT("/appointment/update")
    Call<ApiResponse> updateAppointment(@Body AppointmentBookingUpdateDto bookingUpdateDto, @Query("token") String token);

}
