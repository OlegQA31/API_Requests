package contactokhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import okhttp3.*;
import org.apache.http.client.methods.RequestBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTests {
    public static final MediaType JSON=MediaType.get("application/json;charset=UTF-8");
    Gson gson=new Gson();
    OkHttpClient client=new OkHttpClient();
    int index=(int) (System.currentTimeMillis()/1000)%3600;

    @Test
    public void regSuccess() throws IOException {
        AuthRequestDto requestDto= AuthRequestDto.builder()
                .email("Maga"+index+"@gmail.com")
                .password("Mm12345$")
                .build();
        RequestBody requestBody=RequestBody.create(gson.toJson(requestDto),JSON);
        Request request= new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        AuthResponseDto responseDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        System.out.println(responseDto.getToken());
    }

}
