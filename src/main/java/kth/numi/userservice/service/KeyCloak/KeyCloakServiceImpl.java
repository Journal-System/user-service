package kth.numi.userservice.service.KeyCloak;

import jakarta.ws.rs.core.Response;
import kth.numi.userservice.config.Credentials;
import kth.numi.userservice.config.KeycloakConfig;
import kth.numi.userservice.model.User;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class KeyCloakServiceImpl implements KeyCloakService {

    @Override
    public ResponseEntity<?> addUser(User user) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(user.getPassword());

        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(user.getEmail());
        newUser.setFirstName(user.getFirstname());
        newUser.setLastName(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setCredentials(Collections.singletonList(credential));
        newUser.setEnabled(true);
        newUser.setEmailVerified(true);
        newUser.setRealmRoles(List.of("PATIENT"));

        Keycloak keycloak = KeycloakConfig.getInstance();

        UsersResource usersResource = keycloak.realm("HealthHarbor-Realm").users();

        try {
            RealmResource realmResource = keycloak.realm("HealthHarbor-Realm");
            UsersResource usersRessource = realmResource.users();
            Response response = usersResource.create(newUser);
            String userId = CreatedResponseUtil.getCreatedId(response);
            UserResource userResource = usersRessource.get(userId);
            System.out.printf("User created with userId: %s%n", userId); // JUST FOR TEST
            RoleRepresentation PATIENTRealmRole = realmResource.roles()
                    .get("PATIENT").toRepresentation();
            userResource.roles().realmLevel()
                    .add(Arrays.asList(PATIENTRealmRole));
            ClientRepresentation appClient = realmResource.clients()
                    .findByClientId("user-service").get(0);
            RoleRepresentation patientClientRole = realmResource.clients().get(appClient.getId())
                    .roles().get("PATIENT").toRepresentation();
            userResource.roles()
                    .clientLevel(appClient.getId()).add(Arrays.asList(patientClientRole));
            Keycloak instance = Keycloak.getInstance("https://key-cloak.app.cloud.cbh.kth.se", "HealthHarbor-Realm", user.getEmail(), user.getPassword(),"user-service", "bDphhdOrw0VDIU0O1zoxPU6dM9ZvFCMM");
            TokenManager tokenmanager = instance.tokenManager();
            String accessToken = tokenmanager.getAccessTokenString();
            System.out.println("Access token: " + accessToken);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(accessToken);
        } catch (Exception e) {
            e.printStackTrace(); // Details of the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @Override
    public List<UserRepresentation> getUser(String userName) {
        Keycloak keycloak = KeycloakConfig.getInstance();
        UsersResource usersResource = keycloak.realm("HealthHarbor-Realm").users();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;
    }

    @Override
    public void updateUser(String userId, User user) {
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(user.getPassword());
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(user.getEmail());
        newUser.setFirstName(user.getFirstname());
        newUser.setLastName(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = (UsersResource) KeycloakConfig.getInstance();
        usersResource.get(userId).update(newUser);
    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = (UsersResource) KeycloakConfig.getInstance();
        usersResource.get(userId).remove();
    }

    @Override
    public ResponseEntity<?> getToken(String email, String password) {
        try {
            Keycloak instance = Keycloak.getInstance("https://key-cloak.app.cloud.cbh.kth.se", "HealthHarbor-Realm",
                    email, password,"user-service", "bDphhdOrw0VDIU0O1zoxPU6dM9ZvFCMM");
            TokenManager tokenmanager = instance.tokenManager();
            String accessToken = tokenmanager.getAccessTokenString();
            System.out.println("Access token: " + accessToken);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find the access token");
        }
    }
}
