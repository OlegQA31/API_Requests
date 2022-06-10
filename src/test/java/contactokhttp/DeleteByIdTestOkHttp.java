package contactokhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.ResponseDeleteByIdDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteByIdTestOkHttp {
    public static final MediaType JSON=MediaType.get("application/json;charset=UTF-8");
    Gson gson=new Gson();
    OkHttpClient client=new OkHttpClient();
    String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Ik1tMTIzQGdtYWlsLmNvbSJ9.f6qvhzHm-ssUTNSdNU7i9YkCqaSO3QHRBibPXTjhoMo";
int id;
    @BeforeMethod
    private void preCond() throws IOException {
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
        id=contactDto1.getId();
    }

    @Test
    public void successDeletionById() throws IOException {
        Request request=new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/"+id)
                .delete()
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();//execute request and returns response
        Assert.assertTrue(response.isSuccessful());

        ResponseDeleteByIdDto statusDto=gson.fromJson(response
                .body().string(),ResponseDeleteByIdDto.class);

        System.out.println(statusDto.getStatus());
        Assert.assertEquals(statusDto.getStatus(),"Contact was deleted!");
    }
}
