package com.croustify.backend.services;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;




import com.google.api.client.http.javanet.NetHttpTransport;
import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthService {

    private String clientId = "631494973899-evmarf6m8vr6tfhcflglsl1vt8eljjf4.apps.googleusercontent.com";

    public GoogleIdToken.Payload verifyToken(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                return idToken.getPayload();
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException | java.io.IOException e) {
            System.out.println("Erreur lors de la vérification du token : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }



    public void testGoogleCertificatesAccess() {
        try {
            // URL des certificats Google pour valider les tokens
            URL url = new URL("https://www.googleapis.com/oauth2/v3/certs");

            // Ouverture d'une connexion HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Vérification de la réponse
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Certificates successfully retrieved from Google");
            } else {
                System.out.println("Failed to retrieve certificates, response code: " + responseCode);
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la tentative d'accès aux certificats : " + e.getMessage());
            e.printStackTrace();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

}
