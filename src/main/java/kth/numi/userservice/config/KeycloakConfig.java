package kth.numi.userservice.config;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public class KeycloakConfig {

    static Keycloak keycloak = null;
    final static String serverUrl = "https://key-cloak.app.cloud.cbh.kth.se/";
    public final static String realm = "HealthHarbor-Realm";
    final static String clientId = "user-service";
    final static String clientSecret = "bDphhdOrw0VDIU0O1zoxPU6dM9ZvFCMM";
    final static String userName = "admin";
    final static String password = "xumqy8-vyMgiw-harfut";

    public KeycloakConfig() {}

    public static Keycloak getInstance() {
        if (keycloak == null) {

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(new ResteasyClientBuilderImpl()
                            .connectionPoolSize(10)
                            .build()
                    )
                    .build();
        }
        return keycloak;
    }
}