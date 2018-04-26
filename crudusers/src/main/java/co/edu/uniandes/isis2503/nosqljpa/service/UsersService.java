package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.dto.UserDTO;
import java.io.IOException;
import java.net.URLEncoder;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsersService {
    
    public UsersService() {
    }
    
    @POST
    public void add(UserDTO dto) throws IOException {
        
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
        request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik5VVkVSRGsyUlRkRk5UWkdOakk1TXpKRlF6YzFSREExUmtOQlJqSTVSVU0yUVRjd04wSXpNZyJ9.eyJpc3MiOiJodHRwczovL2lzaXMyNTAzLWpjZ2xvcmlhLmF1dGgwLmNvbS8iLCJzdWIiOiJBb1hCQXZMNm5qSGRKNDZNSHdQMHdoWUIwSmRwOVdxTkBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9pc2lzMjUwMy1qY2dsb3JpYS5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTUyNDY3MDUzOSwiZXhwIjoxNTI0NzU2OTM5LCJhenAiOiJBb1hCQXZMNm5qSGRKNDZNSHdQMHdoWUIwSmRwOVdxTiIsInNjb3BlIjoicmVhZDpjbGllbnRfZ3JhbnRzIGNyZWF0ZTpjbGllbnRfZ3JhbnRzIGRlbGV0ZTpjbGllbnRfZ3JhbnRzIHVwZGF0ZTpjbGllbnRfZ3JhbnRzIHJlYWQ6dXNlcnMgdXBkYXRlOnVzZXJzIGRlbGV0ZTp1c2VycyBjcmVhdGU6dXNlcnMgcmVhZDp1c2Vyc19hcHBfbWV0YWRhdGEgdXBkYXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBkZWxldGU6dXNlcnNfYXBwX21ldGFkYXRhIGNyZWF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgY3JlYXRlOnVzZXJfdGlja2V0cyByZWFkOmNsaWVudHMgdXBkYXRlOmNsaWVudHMgZGVsZXRlOmNsaWVudHMgY3JlYXRlOmNsaWVudHMgcmVhZDpjbGllbnRfa2V5cyB1cGRhdGU6Y2xpZW50X2tleXMgZGVsZXRlOmNsaWVudF9rZXlzIGNyZWF0ZTpjbGllbnRfa2V5cyByZWFkOmNvbm5lY3Rpb25zIHVwZGF0ZTpjb25uZWN0aW9ucyBkZWxldGU6Y29ubmVjdGlvbnMgY3JlYXRlOmNvbm5lY3Rpb25zIHJlYWQ6cmVzb3VyY2Vfc2VydmVycyB1cGRhdGU6cmVzb3VyY2Vfc2VydmVycyBkZWxldGU6cmVzb3VyY2Vfc2VydmVycyBjcmVhdGU6cmVzb3VyY2Vfc2VydmVycyByZWFkOmRldmljZV9jcmVkZW50aWFscyB1cGRhdGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGRlbGV0ZTpkZXZpY2VfY3JlZGVudGlhbHMgY3JlYXRlOmRldmljZV9jcmVkZW50aWFscyByZWFkOnJ1bGVzIHVwZGF0ZTpydWxlcyBkZWxldGU6cnVsZXMgY3JlYXRlOnJ1bGVzIHJlYWQ6cnVsZXNfY29uZmlncyB1cGRhdGU6cnVsZXNfY29uZmlncyBkZWxldGU6cnVsZXNfY29uZmlncyByZWFkOmVtYWlsX3Byb3ZpZGVyIHVwZGF0ZTplbWFpbF9wcm92aWRlciBkZWxldGU6ZW1haWxfcHJvdmlkZXIgY3JlYXRlOmVtYWlsX3Byb3ZpZGVyIGJsYWNrbGlzdDp0b2tlbnMgcmVhZDpzdGF0cyByZWFkOnRlbmFudF9zZXR0aW5ncyB1cGRhdGU6dGVuYW50X3NldHRpbmdzIHJlYWQ6bG9ncyByZWFkOnNoaWVsZHMgY3JlYXRlOnNoaWVsZHMgZGVsZXRlOnNoaWVsZHMgdXBkYXRlOnRyaWdnZXJzIHJlYWQ6dHJpZ2dlcnMgcmVhZDpncmFudHMgZGVsZXRlOmdyYW50cyByZWFkOmd1YXJkaWFuX2ZhY3RvcnMgdXBkYXRlOmd1YXJkaWFuX2ZhY3RvcnMgcmVhZDpndWFyZGlhbl9lbnJvbGxtZW50cyBkZWxldGU6Z3VhcmRpYW5fZW5yb2xsbWVudHMgY3JlYXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRfdGlja2V0cyByZWFkOnVzZXJfaWRwX3Rva2VucyBjcmVhdGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiBkZWxldGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiByZWFkOmN1c3RvbV9kb21haW5zIGRlbGV0ZTpjdXN0b21fZG9tYWlucyBjcmVhdGU6Y3VzdG9tX2RvbWFpbnMgcmVhZDplbWFpbF90ZW1wbGF0ZXMgY3JlYXRlOmVtYWlsX3RlbXBsYXRlcyB1cGRhdGU6ZW1haWxfdGVtcGxhdGVzIiwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIn0.Ni5nuRooXroxrsTaSMTnSjvjimEY45dwctDimc-U9tlV7FfKUTG_CLXyrImI4sOYAMWLeeAetVsM4QcPMnCvsOgC-Hwi9U4VnjFisaKewqcXBKQxOjWV4isvpBOBqBo3Q7qLhz_muWSfr7KvCUQIAVuTqICGJLaqGouAw7znOjLR2EDKig3zRV11Y-mHMv-FlKfGoh_QWsKHP2-rYEE7RPSiyyJBnUR0egrn28Va0h5ejVGJ-tkn320ZT-JsfPpXytj08Bi4JId9JNdIKoGFNaUnbsWDGrxV9SAtDPpQmzpfRp33Oss0po4-nPxR_oopas-5bbfQEuWRVWgaRPnj5g");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        
       
    }
    
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") String id ) throws IOException{
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete request = new HttpDelete("https://isis2503-jcgloria.auth0.com/api/v2/users/" + URLEncoder.encode(id, "UTF-8"));
        request.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ik5VVkVSRGsyUlRkRk5UWkdOakk1TXpKRlF6YzFSREExUmtOQlJqSTVSVU0yUVRjd04wSXpNZyJ9.eyJpc3MiOiJodHRwczovL2lzaXMyNTAzLWpjZ2xvcmlhLmF1dGgwLmNvbS8iLCJzdWIiOiJBb1hCQXZMNm5qSGRKNDZNSHdQMHdoWUIwSmRwOVdxTkBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9pc2lzMjUwMy1qY2dsb3JpYS5hdXRoMC5jb20vYXBpL3YyLyIsImlhdCI6MTUyNDY3MDUzOSwiZXhwIjoxNTI0NzU2OTM5LCJhenAiOiJBb1hCQXZMNm5qSGRKNDZNSHdQMHdoWUIwSmRwOVdxTiIsInNjb3BlIjoicmVhZDpjbGllbnRfZ3JhbnRzIGNyZWF0ZTpjbGllbnRfZ3JhbnRzIGRlbGV0ZTpjbGllbnRfZ3JhbnRzIHVwZGF0ZTpjbGllbnRfZ3JhbnRzIHJlYWQ6dXNlcnMgdXBkYXRlOnVzZXJzIGRlbGV0ZTp1c2VycyBjcmVhdGU6dXNlcnMgcmVhZDp1c2Vyc19hcHBfbWV0YWRhdGEgdXBkYXRlOnVzZXJzX2FwcF9tZXRhZGF0YSBkZWxldGU6dXNlcnNfYXBwX21ldGFkYXRhIGNyZWF0ZTp1c2Vyc19hcHBfbWV0YWRhdGEgY3JlYXRlOnVzZXJfdGlja2V0cyByZWFkOmNsaWVudHMgdXBkYXRlOmNsaWVudHMgZGVsZXRlOmNsaWVudHMgY3JlYXRlOmNsaWVudHMgcmVhZDpjbGllbnRfa2V5cyB1cGRhdGU6Y2xpZW50X2tleXMgZGVsZXRlOmNsaWVudF9rZXlzIGNyZWF0ZTpjbGllbnRfa2V5cyByZWFkOmNvbm5lY3Rpb25zIHVwZGF0ZTpjb25uZWN0aW9ucyBkZWxldGU6Y29ubmVjdGlvbnMgY3JlYXRlOmNvbm5lY3Rpb25zIHJlYWQ6cmVzb3VyY2Vfc2VydmVycyB1cGRhdGU6cmVzb3VyY2Vfc2VydmVycyBkZWxldGU6cmVzb3VyY2Vfc2VydmVycyBjcmVhdGU6cmVzb3VyY2Vfc2VydmVycyByZWFkOmRldmljZV9jcmVkZW50aWFscyB1cGRhdGU6ZGV2aWNlX2NyZWRlbnRpYWxzIGRlbGV0ZTpkZXZpY2VfY3JlZGVudGlhbHMgY3JlYXRlOmRldmljZV9jcmVkZW50aWFscyByZWFkOnJ1bGVzIHVwZGF0ZTpydWxlcyBkZWxldGU6cnVsZXMgY3JlYXRlOnJ1bGVzIHJlYWQ6cnVsZXNfY29uZmlncyB1cGRhdGU6cnVsZXNfY29uZmlncyBkZWxldGU6cnVsZXNfY29uZmlncyByZWFkOmVtYWlsX3Byb3ZpZGVyIHVwZGF0ZTplbWFpbF9wcm92aWRlciBkZWxldGU6ZW1haWxfcHJvdmlkZXIgY3JlYXRlOmVtYWlsX3Byb3ZpZGVyIGJsYWNrbGlzdDp0b2tlbnMgcmVhZDpzdGF0cyByZWFkOnRlbmFudF9zZXR0aW5ncyB1cGRhdGU6dGVuYW50X3NldHRpbmdzIHJlYWQ6bG9ncyByZWFkOnNoaWVsZHMgY3JlYXRlOnNoaWVsZHMgZGVsZXRlOnNoaWVsZHMgdXBkYXRlOnRyaWdnZXJzIHJlYWQ6dHJpZ2dlcnMgcmVhZDpncmFudHMgZGVsZXRlOmdyYW50cyByZWFkOmd1YXJkaWFuX2ZhY3RvcnMgdXBkYXRlOmd1YXJkaWFuX2ZhY3RvcnMgcmVhZDpndWFyZGlhbl9lbnJvbGxtZW50cyBkZWxldGU6Z3VhcmRpYW5fZW5yb2xsbWVudHMgY3JlYXRlOmd1YXJkaWFuX2Vucm9sbG1lbnRfdGlja2V0cyByZWFkOnVzZXJfaWRwX3Rva2VucyBjcmVhdGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiBkZWxldGU6cGFzc3dvcmRzX2NoZWNraW5nX2pvYiByZWFkOmN1c3RvbV9kb21haW5zIGRlbGV0ZTpjdXN0b21fZG9tYWlucyBjcmVhdGU6Y3VzdG9tX2RvbWFpbnMgcmVhZDplbWFpbF90ZW1wbGF0ZXMgY3JlYXRlOmVtYWlsX3RlbXBsYXRlcyB1cGRhdGU6ZW1haWxfdGVtcGxhdGVzIiwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIn0.Ni5nuRooXroxrsTaSMTnSjvjimEY45dwctDimc-U9tlV7FfKUTG_CLXyrImI4sOYAMWLeeAetVsM4QcPMnCvsOgC-Hwi9U4VnjFisaKewqcXBKQxOjWV4isvpBOBqBo3Q7qLhz_muWSfr7KvCUQIAVuTqICGJLaqGouAw7znOjLR2EDKig3zRV11Y-mHMv-FlKfGoh_QWsKHP2-rYEE7RPSiyyJBnUR0egrn28Va0h5ejVGJ-tkn320ZT-JsfPpXytj08Bi4JId9JNdIKoGFNaUnbsWDGrxV9SAtDPpQmzpfRp33Oss0po4-nPxR_oopas-5bbfQEuWRVWgaRPnj5g");
        HttpResponse response = httpClient.execute(request);   
    }
    @PUT
    public void cambiarClave(){
        HttpClient httpClient =  HttpClientBuilder.create().build();
        HttpPut request = new HttpPut("https://isis2503-jcgloria.auth0.com/api/v2/users");
    }
}
