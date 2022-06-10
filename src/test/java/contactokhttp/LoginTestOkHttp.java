package contactokhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestOkHttp {
    public static final MediaType JSON=MediaType.get("application/json;charset=UTF-8");

    @Test
    public void LoginTest() throws IOException {
        AuthRequestDto requestDto=AuthRequestDto.builder()
                .email("Mm123@gmail.com")
                .password("Mm12345$")
                .build();
        Gson gson=new Gson();
        OkHttpClient client=new OkHttpClient();

        RequestBody requestBody=RequestBody.create(gson.toJson(requestDto),JSON);

        Request request=new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response=client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        AuthResponseDto responseDto=gson.fromJson(response.body().string(),AuthResponseDto.class);

        String token = responseDto.getToken();
        System.out.println(token);

        Assert.assertEquals(response.code(),200);

    }

    @Test
    public void loginWrongPassword() throws IOException {
        AuthRequestDto requestDto= AuthRequestDto.builder()
                .email("Mm123@gmail.com")
                .password("M")
                .build();
        Gson gson=new Gson();
        OkHttpClient client=new OkHttpClient();

        RequestBody requestBody=RequestBody.create(gson.toJson(requestDto),JSON);//body

        Request request=new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody).build();

        Response response = client.newCall(request).execute();

        boolean successful = response.isSuccessful();
        Assert.assertFalse(successful);
        System.out.println(response.code());

        ErrorDto errorDto=gson.fromJson(response.body().string(),ErrorDto.class);
        System.out.println(errorDto.getMessage());
        System.out.println(errorDto.getDetails());
        Assert.assertEquals(errorDto.getDetails(),"uri=/api/login");
        System.out.println(errorDto.getCode());
    }

}
