package co.edu.uniandes.isis2503.nosqljpa.service;

import ch.qos.logback.classic.util.ContextInitializer;
import co.edu.uniandes.isis2503.nosqljpa.dto.UserDTO;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsersService {
    
    private static ArrayList<String> correos = new ArrayList<String>();
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik5VVkVSRGsyUlRkRk5UWkdOakk1TXpKRlF6YzFSREExUmtOQlJqSTVSVU0yUVRjd04wSXpNZyJ9.eyJpc3MiOiJodHRwczovL2lzaXMyNTAzLWpjZ2xvcmlhLmF1dGgwLmNvbS8iLCJzdWIiOiJBb1hCQXZMNm5qSGRKNDZNSHdQMHdoWUIwSmRwOVdxTkBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9pc2lzMjUwMy1qY2dsb3JpYS5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTUyNjA1NzY0MCwiZXhwIjoxNTI2MTQ0MDQwLCJhenAiOiJBb1hCQXZMNm5qSGRKNDZNSHdQMHdoWUIwSmRwOVdxTiIsInNjb3BlIjoicmVhZDpjbGllbnRfZ3JhbnRzIGNyZWF0ZTpjbGllbnRfZ3JhbnRzIGRlbGV0ZTpjbGllbnRfZ3JhbnRzIHVwZGF0ZTpjbGllbnRfZ3JhbnRzIHJlYWQ6dXNlcnMgdXBkYXRlOnVzZXJzIGRlbGV0ZTp1c2VycyBjcmVhdGU6dXNlcnMgcmVhZDp1c2Vyc19hcHBfbWV0YWRhdGEgdXBkYXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBkZWxldGU6dXNlcnNfYXBwX21ldGFkYXRhIGNyZWF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgY3JlYXRlOnVzZXJfdGlja2V0cyByZWFkOmNsaWVudHMgdXBkYXRlOmNsaWVudHMgZGVsZXRlOmNsaWVudHMgY3JlYXRlOmNsaWVudHMgcmVhZDpjbGllbnRfa2V5cyB1cGRhdGU6Y2xpZW50X2tleXMgZGVsZXRlOmNsaWVudF9rZXlzIGNyZWF0ZTpjbGllbnRfa2V5cyByZWFkOmNvbm5lY3Rpb25zIHVwZGF0ZTpjb25uZWN0aW9ucyBkZWxldGU6Y29ubmVjdGlvbnMgY3JlYXRlOmNvbm5lY3Rpb25zIHJlYWQ6cmVzb3VyY2Vfc2VydmVycyB1cGRhdGU6cmVzb3VyY2Vfc2VydmVycyBkZWxldGU6cmVzb3VyY2Vfc2VydmVycyBjcmVhdGU6cmVzb3VyY2Vfc2VydmVycyByZWFkOmRldmljZV9jcmVkZW50aWFscyB1cGRhdGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGRlbGV0ZTpkZXZpY2VfY3JlZGVudGlhbHMgY3JlYXRlOmRldmljZV9jcmVkZW50aWFscyByZWFkOnJ1bGVzIHVwZGF0ZTpydWxlcyBkZWxldGU6cnVsZXMgY3JlYXRlOnJ1bGVzIHJlYWQ6cnVsZXNfY29uZmlncyB1cGRhdGU6cnVsZXNfY29uZmlncyBkZWxldGU6cnVsZXNfY29uZmlncyByZWFkOmVtYWlsX3Byb3ZpZGVyIHVwZGF0ZTplbWFpbF9wcm92aWRlciBkZWxldGU6ZW1haWxfcHJvdmlkZXIgY3JlYXRlOmVtYWlsX3Byb3ZpZGVyIGJsYWNrbGlzdDp0b2tlbnMgcmVhZDpzdGF0cyByZWFkOnRlbmFudF9zZXR0aW5ncyB1cGRhdGU6dGVuYW50X3NldHRpbmdzIHJlYWQ6bG9ncyByZWFkOnNoaWVsZHMgY3JlYXRlOnNoaWVsZHMgZGVsZXRlOnNoaWVsZHMgdXBkYXRlOnRyaWdnZXJzIHJlYWQ6dHJpZ2dlcnMgcmVhZDpncmFudHMgZGVsZXRlOmdyYW50cyByZWFkOmd1YXJkaWFuX2ZhY3RvcnMgdXBkYXRlOmd1YXJkaWFuX2ZhY3RvcnMgcmVhZDpndWFyZGlhbl9lbnJvbGxtZW50cyBkZWxldGU6Z3VhcmRpYW5fZW5yb2xsbWVudHMgY3JlYXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRfdGlja2V0cyByZWFkOnVzZXJfaWRwX3Rva2VucyBjcmVhdGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiBkZWxldGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiByZWFkOmN1c3RvbV9kb21haW5zIGRlbGV0ZTpjdXN0b21fZG9tYWlucyBjcmVhdGU6Y3VzdG9tX2RvbWFpbnMgcmVhZDplbWFpbF90ZW1wbGF0ZXMgY3JlYXRlOmVtYWlsX3RlbXBsYXRlcyB1cGRhdGU6ZW1haWxfdGVtcGxhdGVzIiwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIn0.a2z1ux1JORju-PkkFtrTFvTXTIZgHbM5qIJ2Y_hmqqX0ytEG-Yd5eTxZ6naVBE6Hoxpe5aQlnTvvC9_IZJ2Oys6iu9bPIBeZIjcyBQuSxCrTKDx89ufILobRwTZ32UFQnVAg-LnjKw-C_Z5UvFtRS2tb9t6AVlDrsTVrQ-V0LPujmJ1ZLCrWsDZsRIHWDSlnkHDKc3MThwsqz--VQrqYhBnGNb84w0K96fDrIuCWUj4F8owiTMDlOirsGbMayVShwaHJWM_PclI83bE9LP6oG-fmd7Uv1ZC28d6_qslD42I-oCHuZFQtrx2UzoFpPwJsALqMxurZIUENQpiRDlDS8w";
    
    public UsersService() {
    }
    
    @POST
    public String add(UserDTO dto) throws IOException {
        
        //asignar rol
        String tipoCorreo = dto.getCorreo().split("@")[1];
        if(tipoCorreo.equals("yale.co")){
            dto.setRol("yale");
        }
        else if(tipoCorreo.equals("yalelock.co")){
            dto.setRol("cerradura");
        }
        else if(tipoCorreo.equals("yaleadmin.co")){
            dto.setRol("administrador");
        }
        else if(tipoCorreo.equals("yalesecurity.co")){
            dto.setRol("seguridad");
        }
        else{
            dto.setRol("propietario");
        }
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://isis2503-jcgloria.auth0.com/api/v2/users");
        StringEntity params =new StringEntity("{\"email\": \"" + dto.getCorreo() + "\"," + " \"password\":\"" + dto.getContraseña() + "\"," + " \"connection\": \"Username-Password-Authentication\", \"app_metadata\": {\"roles\": [\""+ dto.getRol() + "\"]}}");
        request.addHeader("content-type", "application/json");
        request.addHeader("Authorization", "Bearer " + token);
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
       
    }
    
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id ) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete request = new HttpDelete("https://isis2503-jcgloria.auth0.com/api/v2/users/" + URLEncoder.encode(id, "UTF-8"));
        request.addHeader("Authorization", "Bearer " + token);
        HttpResponse response = httpClient.execute(request);   
    }
    @GET
    public String obtenerUsuarios() throws IOException{
        HttpClient httpClient =  HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://isis2503-jcgloria.auth0.com/api/v2/users");
        request.addHeader("Authorization", "Bearer " + token);
        HttpResponse response = httpClient.execute(request);
        String respuesta = EntityUtils.toString(response.getEntity());
         return respuesta;
    }
    
    @PUT
    @Path("/{id}")
    public String cambiarClave(@PathParam("id") String id, UserDTO dto)throws IOException{
        HttpClient httpClient =  HttpClientBuilder.create().build();
        HttpPatch request = new HttpPatch("https://isis2503-jcgloria.auth0.com/api/v2/users/" + URLEncoder.encode(id, "UTF-8"));
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("content-type", "application/json");
        StringEntity params =new StringEntity("{\"password\":" + "\"" + dto.getContraseña()+"\"}");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }
    
    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(UserDTO dto) throws IOException{
        String correo = dto.getCorreo();
        String mensaje1 = new String("Sesion del usuario con correo " + dto.getCorreo() + " ha sido cerrada");
        String mensaje2 =  new String("Este usuario no tiene una sesion abierta actualmente");
        for(int i = 0; i<correos.size(); i++){
            if(correos.get(i).equals(correo)){
                correos.remove(i);
                return Response.status(200).entity(mensaje1).build();
            }
        }
        return Response.status(404).entity(mensaje2).build();
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO dto) throws IOException{
        HttpClient httpClient =  HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("https://isis2503-jcgloria.auth0.com/oauth/token");
        request.addHeader("content-type", "application/json");
        StringEntity params =new StringEntity("{\"grant_type\": \"" + "http://auth0.com/oauth/grant-type/password-realm" + "\"," + " \"username\":\"" + dto.getCorreo() + "\"," + "\"password\":\"" + dto.getContraseña()
       + "\"," + " \"audience\":\"" + "uniandes.edu.co/thekeys" + "\"," + " \"scope\":\"" + "openid" + "\"," + " \"client_id\":\"" + "njxtvdPndBOMDB8eTBdWsi5YYnOtFuDm" + "\"," + " \"client_secret\":\"" + "qNp8_GngN8ltWfsci_npGUGyEyrdr7cp6bA3Sj8384-4fzE4dxvlNvrEzPYqmaGT"
           + "\"," + " \"realm\":\"" + "Username-Password-Authentication" + "\"}");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        String respuesta = EntityUtils.toString(response.getEntity());
        String mensaje1 = new String("Se ha iniciado sesion correctamente. \n Correo: " + dto.getCorreo());
        String mensaje2 = new String("Correo o usuario incorrecto");
       String[] respuestaPartida = respuesta.split("\"");
       if(respuestaPartida[1].equals("access_token")){
          boolean yaEsta = false;
           for(int i = 0; i<correos.size(); i++){
               if(correos.get(i).equals(dto.getCorreo())) yaEsta = true; 
           }
           if(!yaEsta) correos.add(dto.getCorreo());
           
           return Response.status(200).entity(mensaje1).build();
       }
       else
       {
           return Response.status(401).entity(mensaje2).build();
       }    
      
}
}