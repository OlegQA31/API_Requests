package contactokhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewContactTestOkHttp {
    public static final MediaType JSON=MediaType.get("application/json;charset=UTF-8");
    Gson gson=new Gson();
    OkHttpClient client=new OkHttpClient();
    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Ik1tMTIzQGdtYWlsLmNvbSJ9.f6qvhzHm-ssUTNSdNU7i9YkCqaSO3QHRBibPXTjhoMo";

    @Test
    public void addNewContactSuccess() throws IOException {
        int index=(int) (System.currentTimeMillis()/1000)%3600;
        ContactDto contactDto= ContactDto.builder()
                .name("Bill")
                .lastName("Gates")
                .email("Bb"+index+"@gmail.com")
                .phone("4888"+index)
                .address("Israel")
                .description("Best friend")
                .build();
        RequestBody requestBody=RequestBody.create(gson.toJson(contactDto),JSON);
        Request request= new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(requestBody)
                .addHeader("Authorization",token)
                .build();

        Response response=client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        ContactDto contactDto1=gson.fromJson(response.body().string(),ContactDto.class);
        Assert.assertEquals(contactDto1.getName(),contactDto.getName());
        Assert.assertEquals(contactDto1.getEmail(),contactDto.getEmail());
        Assert.assertEquals(contactDto1.getPhone(),contactDto.getPhone());
        Assert.assertEquals(contactDto1.getAddress(),contactDto.getAddress());
        System.out.println(contactDto1.getId());
    }
}
