import { KeycloakService } from "keycloak-angular";
import { environment } from "src/environments/environment";

export function initializeKeycloak(keycloak : KeycloakService): () => Promise<Boolean> {
    return () => keycloak.init({
        config: {
            url: environment.keycloakUrl,
            realm: environment.keycloakRealm,
            clientId: environment.clientId
          },
          initOptions: {
            onLoad: 'login-required'
          }
    })
}