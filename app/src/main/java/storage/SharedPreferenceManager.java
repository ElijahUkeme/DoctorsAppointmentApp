package storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.elijah.ukeme.doctorsappointmentapplication.model.PatientDto;
import com.elijah.ukeme.doctorsappointmentapplication.model.SignUpPatienceDto;

public class SharedPreferenceManager {

    private static final String SHARED_PREF_NAME = "My shared preference name";

    private static SharedPreferenceManager mInstance;
    private Context context;

    private SharedPreferenceManager(Context context){
        this.context = context;
    }
    public static synchronized SharedPreferenceManager getInstance(Context context){
        if (mInstance==null){
            mInstance = new SharedPreferenceManager(context);
        }
        return mInstance;
    }
    public void savePatient(PatientDto patientDto){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id",patientDto.getId());
        editor.putString("name",patientDto.getName());
        editor.putString("email",patientDto.getEmail());
        editor.putString("image",patientDto.getProfileImage());
        editor.putString("phone",patientDto.getPhoneNumber());
        editor.putString("gender",patientDto.getGender());
        editor.putString("dob",patientDto.getDateOfBirth());
        editor.putString("category",patientDto.getCategory());
        editor.putInt("cardFee",patientDto.getCardFee());
        editor.putInt("age",patientDto.getAge());
        editor.putString("createdDate",patientDto.getCreatedDate());
        editor.putString("token",patientDto.getToken());
        editor.apply();
    }

    public boolean isPatientLogin(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id",-1) != -1;
    }

    public PatientDto getPatient(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new PatientDto(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("image",null),
                sharedPreferences.getString("phone",null),
                sharedPreferences.getString("gender",null),
                sharedPreferences.getString("dob",null),
                sharedPreferences.getString("category",null),
                sharedPreferences.getInt("cardFee",-1),
                sharedPreferences.getInt("age",-1),
                sharedPreferences.getString("createdDate",null),
                sharedPreferences.getString("token",null)

        );
    }
    public void clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
