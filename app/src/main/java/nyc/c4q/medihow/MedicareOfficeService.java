package nyc.c4q.medihow;

import java.util.List;

import nyc.c4q.medihow.model.MedicareOffice;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yokilam on 3/3/18.
 */

public interface MedicareOfficeService {
    @GET("resource/d2h4-zpjr.json")
    Call<List<MedicareOffice>> getMedicareOffice();
}
